package com.sdfol.studipcli.net;

public enum HttpMethod {
	Get("GET"), Post("POST");

	private final String method;

	private HttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}
}