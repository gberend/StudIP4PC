package com.sdfol.studipcli.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public final class BaseUtils {

	private BaseUtils() {
	}

	public static URL createURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static DefaultHttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		httpClient.setRedirectStrategy(new DefaultRedirectStrategy() {
			@Override
			public boolean isRedirected(HttpRequest request,
					HttpResponse response, HttpContext context)
					throws ProtocolException {
				boolean isRedirect = super.isRedirected(request, response,
						context);
				if (!isRedirect) {
					int responseCode = response.getStatusLine().getStatusCode();
					if (responseCode == 301 || responseCode == 302)
						return true;
				}
				return isRedirect;
			}
		});
		return httpClient;
	}

	public static String clean(String label) {
		return label.replace('(', ' ').replace(')', ' ').replace(':', ' ')
				.trim();
	}

	public static String capitalize(String label) {
		return replaceSubs(label.substring(0, 1).toUpperCase()
				+ label.substring(1));
	}

	public static String decapitalize(String label) {
		return replaceSubs(label.substring(0, 1).toLowerCase()
				+ label.substring(1));
	}

	private static String replaceSubs(String result) {
		int index;
		while ((index = result.indexOf('_')) != -1) {
			char sign = result.charAt(index + 1);
			result = result.replace("_" + sign, ("" + sign).toUpperCase());
		}
		return result;
	}
}