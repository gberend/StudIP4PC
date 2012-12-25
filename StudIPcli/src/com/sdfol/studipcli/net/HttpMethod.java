package com.sdfol.studipcli.net;

public enum HttpMethod {
	Get("GET"), Post("POST"), Delete("DELETE"), Put("PUT");

	private final String method;

	private HttpMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public static String parse(String value) {
		if (value.startsWith("get"))
			return Get.name();
		if (value.startsWith("delete"))
			return Delete.name();
		if (value.startsWith("post"))
			return Post.name();
		if (value.startsWith("put"))
			return Put.name();
		return null;
	}
}