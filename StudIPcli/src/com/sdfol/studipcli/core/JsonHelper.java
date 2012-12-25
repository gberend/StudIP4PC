package com.sdfol.studipcli.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sdfol.studipcli.model.ColorSettings;
import com.sdfol.studipcli.model.Course;
import com.sdfol.studipcli.model.PostBox;
import com.sdfol.studipcli.model.Semester;
import com.sdfol.studipcli.model.Settings;
import com.sdfol.studipcli.model.User;
import com.sdfol.studipcli.net.HttpMethod;
import com.sdfol.studipcli.net.OAuthConnection;

public final class JsonHelper {

	private JsonHelper() {
	}

	public static User getCurrentUser(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject jsonObj = conn.invoke(HttpMethod.Get, "user" + ".json",
				invParams);
		JsonObject userObj = jsonObj.getAsJsonObject("user");
		Gson gson = (new GsonBuilder()).create();
		return gson.fromJson(userObj, User.class);
	}

	public static Semester[] getSemesters(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject jsonObj = conn.invoke(HttpMethod.Get, "semesters" + ".json",
				invParams);
		JsonArray semsArray = jsonObj.getAsJsonArray("semesters");
		Gson gson = (new GsonBuilder()).create();
		Semester[] semesters = new Semester[semsArray.size()];
		for (int i = 0; i < semsArray.size(); i++)
			semesters[i] = gson.fromJson(semsArray.get(i), Semester.class);
		return semesters;
	}

	public static PostBox getMessages(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject jsonObj = conn.invoke(HttpMethod.Get, "messages" + ".json",
				invParams);
		Gson gson = (new GsonBuilder()).create();
		return gson.fromJson(jsonObj, PostBox.class);
	}

	public static Course[] getCourses(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject jsonObj = conn.invoke(HttpMethod.Get, "courses" + ".json",
				invParams);
		JsonArray coursesArray = jsonObj.getAsJsonArray("courses");
		Gson gson = (new GsonBuilder()).create();
		Course[] courses = new Course[coursesArray.size()];
		for (int i = 0; i < coursesArray.size(); i++)
			courses[i] = gson.fromJson(coursesArray.get(i), Course.class);
		return courses;
	}

	public static String[] getContactIDs(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject jsonObj = conn.invoke(HttpMethod.Get, "contacts" + ".json",
				invParams);
		JsonArray contactsArray = jsonObj.getAsJsonArray("contacts");
		String[] contacts = new String[contactsArray.size()];
		for (int i = 0; i < contacts.length; i++)
			contacts[i] = contactsArray.get(i).getAsString();
		return contacts;
	}

	public static Settings getStudIPSettings(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject rawSettings = conn.invoke(HttpMethod.Get, "studip/settings"
				+ ".json", invParams);
		Gson gson = (new GsonBuilder()).create();
		return gson.fromJson(rawSettings, Settings.class);
	}

	public static ColorSettings getStudIPColors(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject colors = conn.invoke(HttpMethod.Get, "studip/colors"
				+ ".json", invParams);
		JsonElement innerObj = colors.get("colors");
		Gson gson = (new GsonBuilder()).create();
		return gson.fromJson(innerObj, ColorSettings.class);
	}

	public static Set<Entry<String, JsonElement>> getRoutes(OAuthConnection conn) {
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		JsonObject discovery = conn.invoke(HttpMethod.Get, "discovery"
				+ ".json", invParams);
		JsonObject routes = discovery.getAsJsonObject("routes");
		return routes.entrySet();
	}
}