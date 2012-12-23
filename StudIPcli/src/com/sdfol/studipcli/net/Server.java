package com.sdfol.studipcli.net;

import java.net.URL;

import com.sdfol.studipcli.utils.BaseUtils;

public class Server {
	private final String name;
	private final URL baseUrl;

	public Server(String name, String baseUrl) {
		this.name = name.trim();
		this.baseUrl = BaseUtils.createURL(baseUrl);
	}

	public String getName() {
		return name;
	}

	public URL getBaseUrl() {
		return baseUrl;
	}

	@Override
	public String toString() {
		return "Server [name=" + name + ", baseUrl=" + baseUrl + "]";
	}
}