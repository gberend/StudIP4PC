package com.sdfol.studipcli.net;

public class OAuthData {
	private final Server server;
	private final String consumerKey;
	private final String consumerSecret;

	public OAuthData(Server server, String consumerKey, String consumerSecret) {
		this.server = server;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	public Server getServer() {
		return server;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	@Override
	public String toString() {
		return "OAuthData [server=" + server + ", consumerKey=" + consumerKey
				+ ", consumerSecret=" + consumerSecret + "]";
	}

	/**
	 * Step 1 of 3: Requests a temporary unauthorized token from the server
	 */
	public String getURLforRequestToken() {
		return server.getBaseUrl() + "/oauth/request_token";
	}

	/**
	 * Step 2 of 3: User gets a dialog which wants to know his user name and
	 * password
	 */
	public String getURLforAuthorize() {
		return server.getBaseUrl() + "/oauth/authorize";
	}

	/**
	 * Step 3 of 3: Requests an always valid token in exchange for the initial
	 * token
	 */
	public String getURLforAccessToken() {
		return server.getBaseUrl() + "/oauth/access_token";
	}
}