package com.sdfol.studipcli.api;

import java.util.Collection;
import java.util.Map.Entry;

import com.google.gson.JsonObject;
import com.sdfol.studipcli.net.HttpMethod;

public interface IOAuthConnection {

	JsonObject invoke(HttpMethod method, final String url,
			Collection<? extends Entry<?, ?>> parameters);
}