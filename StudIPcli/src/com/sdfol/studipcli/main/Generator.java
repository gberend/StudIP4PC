package com.sdfol.studipcli.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sdfol.studipcli.api.IOAuthConnection;
import com.sdfol.studipcli.api.IRestAPI;
import com.sdfol.studipcli.net.HttpMethod;
import com.sdfol.studipcli.utils.BaseUtils;

public final class Generator {

	static void handleJsonObject(JsonObject result) throws IOException {
		if (result == null)
			return;
		Set<Entry<String, JsonElement>> set = result.entrySet();
		if (set.size() == 1) {
			Entry<String, JsonElement> first = set.iterator().next();
			System.out.println(" -> Class: " + first.getKey());
			JsonElement firstValue = first.getValue();
			if (firstValue.isJsonObject()) {
				result = (JsonObject) first.getValue();
				Generator.createClass(first.getKey(), result);
			} else if (firstValue.isJsonArray()) {
				JsonArray array = (JsonArray) first.getValue();
				result = (JsonObject) array.get(0);
				Generator.createClass(BaseUtils.singlize(first.getKey()),
						result);
			}
		}
		System.out.println(result);
		System.out.println();
	}

	static String handleSubObjects(Class<?> propType,
			Entry<String, JsonElement> prop) throws IOException {
		String propTypeName = propType.getSimpleName();
		if (prop != null) {
			if (propType == Object.class) {
				propTypeName = BaseUtils.capitalize(prop.getKey());
				JsonObject subObj = new JsonObject();
				subObj.add(prop.getKey(), prop.getValue());
				handleJsonObject(subObj);
			}
		}
		return propTypeName;
	}

	static Class<?> determineType(JsonElement json) {
		if (json.isJsonArray()) {
			JsonArray array = (JsonArray) json;
			if (array.size() >= 1) {
				Class<?> componentType = determineType(array.get(0));
				return Array.newInstance(componentType, 0).getClass();
			}
			return String[].class;
			// return Array.class;
		}
		if (json.isJsonObject())
			return Object.class;
		if (json.isJsonPrimitive()) {
			try {
				json.getAsLong();
				return long.class;
			} catch (Exception e) {
			}
			try {
				json.getAsDouble();
				return double.class;
			} catch (Exception e) {
			}
			try {
				String str = json.getAsString();
				if (str.equals("true") || str.equals("false"))
					return boolean.class;
				return String.class;
			} catch (Exception e) {
			}
			try {
				json.getAsBoolean();
				return boolean.class;
			} catch (Exception e) {
			}
		}
		// isJsonNull() = true
		return null;
	}

	static void createClass(String name, JsonObject json) throws IOException {
		String className = BaseUtils.capitalize(name);
		File classFile = new File("src/com/sdfol/studipcli/model/" + className
				+ ".java");
		classFile.getParentFile().mkdirs();
		BufferedWriter writer = new BufferedWriter(new FileWriter(classFile));
		// Start class
		writer.write("package com.sdfol.studipcli.model;");
		writer.newLine();
		writer.newLine();
		writer.write("public class " + className + " {");
		writer.newLine();
		// Fields
		for (Entry<String, JsonElement> prop : json.entrySet()) {
			Class<?> propType = determineType(prop.getValue());
			String propTypeName = handleSubObjects(propType, prop);
			writer.write(String.format("\tprivate %s %s;", propTypeName,
					prop.getKey()));
			writer.newLine();
		}
		// Getters
		for (Entry<String, JsonElement> prop : json.entrySet()) {
			writer.newLine();
			Class<?> propType = determineType(prop.getValue());
			String propTypeName = handleSubObjects(propType, prop);
			writer.write(String.format("\tpublic %s %s%s() {", propTypeName,
					propType == boolean.class ? "is" : "get",
					BaseUtils.capitalize(prop.getKey())));
			writer.newLine();
			writer.write(String.format("\t\treturn %s;", prop.getKey()));
			writer.newLine();
			writer.write("\t}");
			writer.newLine();
		}
		// Finish class
		writer.write("}");
		writer.flush();
		writer.close();
		System.out.println("Done with " + classFile.getName() + ".");
	}

	static void generateInterface(
			SortedMap<String, Entry<String, List<String>>> abstrMeths)
			throws IOException {
		File apiJava = new File("src/com/sdfol/studipcli/api/IRestAPI.java");
		apiJava.getParentFile().mkdirs();
		BufferedWriter apiWriter = new BufferedWriter(new FileWriter(apiJava));
		apiWriter.write("package com.sdfol.studipcli.api;");
		apiWriter.newLine();
		apiWriter.newLine();
		apiWriter
				.write(String.format("import %s;", JsonObject.class.getName()));
		apiWriter.newLine();
		apiWriter.newLine();
		apiWriter.write("/**");
		apiWriter.newLine();
		apiWriter.write(" * Generated method calls for");
		apiWriter.newLine();
		apiWriter.write(" * http://studip.github.com/studip-rest.ip/");
		apiWriter.newLine();
		apiWriter.write(" */");
		apiWriter.newLine();
		apiWriter.write("public interface IRestAPI {");
		apiWriter.newLine();
		for (Entry<String, Entry<String, List<String>>> entry : abstrMeths
				.entrySet()) {
			apiWriter.newLine();
			apiWriter.write("\t/**");
			apiWriter.newLine();
			apiWriter.write(String.format("\t * Route => %s", entry.getValue()
					.getKey()));
			apiWriter.newLine();
			apiWriter.write("\t */");
			apiWriter.newLine();
			StringBuilder bld = new StringBuilder();
			for (String param : entry.getValue().getValue()) {
				if (bld.length() >= 1)
					bld.append(", ");
				bld.append("String ").append(param);
			}
			apiWriter.write(String.format("\tpublic %s %s(%s);",
					JsonObject.class.getSimpleName(), entry.getKey(),
					bld.toString()));
			apiWriter.newLine();
		}
		apiWriter.write("}");
		apiWriter.flush();
		apiWriter.close();
		System.out.println("Done.");
	}

	static void generateImpl(
			SortedMap<String, Entry<String, List<String>>> abstrMeths)
			throws IOException {
		File apiJava = new File("src/com/sdfol/studipcli/impl/RestAPI.java");
		apiJava.getParentFile().mkdirs();
		BufferedWriter implWriter = new BufferedWriter(new FileWriter(apiJava));
		implWriter.write("package com.sdfol.studipcli.impl;");
		implWriter.newLine();
		implWriter.newLine();
		implWriter
				.write(String.format("import %s;", JsonObject.class.getName()));
		implWriter.newLine();
		implWriter.write(String.format("import %s;", IRestAPI.class.getName()));
		implWriter.newLine();
		implWriter.write(String.format("import %s;",
				IOAuthConnection.class.getName()));
		implWriter.newLine();
		implWriter
				.write(String.format("import %s;", HttpMethod.class.getName()));
		implWriter.newLine();
		implWriter.newLine();
		implWriter.write("public class RestAPI implements IRestAPI {");
		implWriter.newLine();
		implWriter.write("\tprivate final IOAuthConnection connection;");
		implWriter.newLine();
		implWriter.newLine();
		implWriter.write("\tpublic RestAPI(IOAuthConnection connection) {");
		implWriter.newLine();
		implWriter.write("\t\tthis.connection = connection;");
		implWriter.newLine();
		implWriter.write("\t}");
		implWriter.newLine();
		for (Entry<String, Entry<String, List<String>>> entry : abstrMeths
				.entrySet()) {
			implWriter.newLine();
			StringBuilder bld = new StringBuilder();
			for (String param : entry.getValue().getValue()) {
				if (bld.length() >= 1)
					bld.append(", ");
				bld.append("String ").append(param);
			}
			implWriter.write(String.format("\tpublic %s %s(%s) {",
					JsonObject.class.getSimpleName(), entry.getKey(),
					bld.toString()));
			implWriter.newLine();
			implWriter.write(String.format("\t\tString route = "));
			String[] parts = entry.getValue().getKey().split("/");
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (part.isEmpty())
					continue;
				if (i > 1)
					implWriter.write(" + " + '"' + "/" + '"' + " + ");
				if (part.startsWith(":")) {
					implWriter.write(BaseUtils.decapitalize(BaseUtils
							.clean(part)));
					continue;
				}
				implWriter.write('"' + part + '"');
			}
			implWriter.write(";");
			implWriter.newLine();
			String httpMethod = HttpMethod.parse(entry.getKey());
			implWriter
					.write("\t\tJsonObject jsonObj = connection.invoke(HttpMethod."
							+ httpMethod + ", route, null);");
			implWriter.newLine();
			implWriter.write("\t\treturn jsonObj;");
			implWriter.newLine();
			implWriter.write("\t}");
			implWriter.newLine();
		}
		implWriter.write("}");
		implWriter.flush();
		implWriter.close();
		System.out.println("Done.");
	}
}