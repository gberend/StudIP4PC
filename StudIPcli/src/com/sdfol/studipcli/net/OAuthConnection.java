package com.sdfol.studipcli.net;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OAuthConnection {
	private final OAuthData params;
	private final OAuthAccessor accessor;

	public OAuthConnection(OAuthData params) {
		this.params = params;
		// Create a provider
		OAuthServiceProvider provider = new OAuthServiceProvider(
				params.getURLforRequestToken(), params.getURLforAuthorize(),
				params.getURLforAccessToken());
		// With provider, create a consumer
		String callbackUrl = null;
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl,
				params.getConsumerKey(), params.getConsumerSecret(), provider);
		// With consumer, we get now a handle (kind of)
		accessor = new OAuthAccessor(consumer);
	}

	public OAuthData getParams() {
		return params;
	}

	public OAuthAccessor getAccessor() {
		return accessor;
	}

	@Override
	public String toString() {
		return "OAuthConnection [params=" + params + "]";
	}

	public boolean orderRequestToken() {
		OAuthClient client = new OAuthClient(new HttpClient4());
		try {
			client.getRequestToken(accessor);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
			// return false;
		}
	}

	public String getAuthorizeWebURL() {
		return String.format("%s?oauth_token=%s&oauth_callback=",
				accessor.consumer.serviceProvider.userAuthorizationURL,
				accessor.requestToken, accessor.consumer.callbackURL);
	}

	public boolean orderAccessToken() {
		List<Entry<String, String>> params = new LinkedList<Entry<String, String>>();
		OAuthClient client = new OAuthClient(new HttpClient4());
		accessor.accessToken = accessor.requestToken;
		try {
			OAuthMessage omessage = client.invoke(accessor, "POST",
					accessor.consumer.serviceProvider.accessTokenURL, params);
			accessor.accessToken = omessage.getParameter(OAuth.OAUTH_TOKEN);
			accessor.tokenSecret = omessage
					.getParameter(OAuth.OAUTH_TOKEN_SECRET);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
			// return false;
		}
	}

	public JsonObject invoke(HttpMethod method, final String url,
			Collection<? extends Entry<?, ?>> parameters) {
		OAuthClient client = new OAuthClient(new HttpClient4());
		try {
			String wholeUrl = params.getServer().getBaseUrl() + "/api/" + url;
			OAuthMessage message = client.invoke(accessor, method.getMethod(),
					wholeUrl, parameters);
			String strMsg = message.readBodyAsString();
			JsonParser parser = new JsonParser();
			return parser.parse(strMsg).getAsJsonObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}