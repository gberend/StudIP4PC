package com.sdfol.studipcli.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.google.gson.JsonObject;
import com.sdfol.studipcli.api.IOAuthConnection;
import com.sdfol.studipcli.api.IRestAPI;
import com.sdfol.studipcli.net.HttpMethod;
import com.sdfol.studipcli.utils.BaseUtils;

public final class Generator {

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