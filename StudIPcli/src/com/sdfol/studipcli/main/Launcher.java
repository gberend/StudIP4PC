package com.sdfol.studipcli.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sdfol.studipcli.api.IRestAPI;
import com.sdfol.studipcli.core.JsonHelper;
import com.sdfol.studipcli.impl.RestAPI;
import com.sdfol.studipcli.net.OAuthConnection;
import com.sdfol.studipcli.net.OAuthData;
import com.sdfol.studipcli.net.Server;
import com.sdfol.studipcli.utils.BaseUtils;

public class Launcher {

	public static void main(String[] args) throws IOException {
		// Load connection configuration
		File connCfgFile = new File("conn.cfg");
		if (!(connCfgFile.exists() && connCfgFile.canRead())) {
			System.err.println("No connection configuration file found!");
			return;
		}
		// Check if arguments are given
		if (args == null || args.length <= 0) {
			printHelp();
			return;
		}
		// Load first file
		Properties props = new Properties();
		props.loadFromXML(new FileInputStream(connCfgFile));
		// Initialize server and OAuth
		Server server = new Server(props.getProperty("serverName"),
				props.getProperty("serverUrl"));
		OAuthData oauth = new OAuthData(server,
				props.getProperty("consumerKey"),
				props.getProperty("consumerSecret"));
		OAuthConnection conn = new OAuthConnection(oauth);
		// Look for existing authorization
		File authCfgFile = new File("auth.cfg");
		System.out.println("Searching for '" + authCfgFile + "'...");
		// Load it from file
		if (authCfgFile.exists() && authCfgFile.canRead()) {
			props = new Properties();
			props.loadFromXML(new FileInputStream(authCfgFile));
			conn.getAccessor().accessToken = props.getProperty("accessToken");
			conn.getAccessor().tokenSecret = props.getProperty("accessSecret");
		} else {
			// Just connect as new app
			connectAsNewApp(conn);
			return;
		}
		// Handle commands
		String cmd = args[0];
		if (cmd.equalsIgnoreCase("system")) {
			printSystem(conn);
		} else if (cmd.equalsIgnoreCase("courses")) {
			System.out.println(Arrays.toString(JsonHelper.getCourses(conn)));
		} else if (cmd.equalsIgnoreCase("contacts")) {
			System.out.println(Arrays.toString(JsonHelper.getContactIDs(conn)));
		} else if (cmd.equalsIgnoreCase("messages")) {
			System.out.println(JsonHelper.getMessages(conn));
		} else if (cmd.equalsIgnoreCase("semesters")) {
			System.out.println(Arrays.toString(JsonHelper.getSemesters(conn)));
		} else if (cmd.equalsIgnoreCase("user")) {
			System.out.println(JsonHelper.getCurrentUser(conn));
		} else if (cmd.equalsIgnoreCase("routes")) {
			printRoutes(conn);
		} else if (cmd.equalsIgnoreCase("genesis")) {
			printGenesis(conn);
		} else if (cmd.equalsIgnoreCase("test")) {
			printTest(conn);
		} else {
			System.err.println("Unknown command!");
		}
	}

	private static void printTest(OAuthConnection connection) {
		IRestAPI rest = new RestAPI(connection);

		for (Method method : rest.getClass().getMethods()) {
			if (method.getDeclaringClass() == Object.class
					|| method.getName().equals("getDiscovery"))
				continue;
			// if (!method.getName().equalsIgnoreCase("getStudipColors"))
			// continue;
			try {
				System.out.println(" - " + method);
				Object[] args = (Object[]) Array.newInstance(Object.class,
						method.getParameterTypes().length);
				JsonObject result = (JsonObject) method.invoke(rest, args);
				Generator.handleJsonObject(result);
			} catch (Exception e) {
				Throwable cause = e;
				while (cause.getCause() != null)
					cause = cause.getCause();
				System.err.println(BaseUtils.toString(cause, true));
			}
		}
	}

	private static void printGenesis(OAuthConnection conn) throws IOException {
		SortedMap<String, Entry<String, List<String>>> abstrMeths = getAllAbstractMethods(conn);
		Generator.generateInterface(abstrMeths);
		Generator.generateImpl(abstrMeths);
	}

	private static SortedMap<String, Entry<String, List<String>>> getAllAbstractMethods(
			OAuthConnection conn) {
		SortedMap<String, Entry<String, List<String>>> map = new TreeMap<String, Entry<String, List<String>>>();
		for (Entry<String, JsonElement> entry : JsonHelper.getRoutes(conn)) {
			String[] entryLabelParts = entry.getKey().split("/");
			JsonObject methodsObj = entry.getValue().getAsJsonObject();
			for (Entry<String, JsonElement> methodEntry : methodsObj.entrySet()) {
				if (!methodEntry.getValue().getAsBoolean())
					continue;
				List<String> params = new LinkedList<String>();
				StringBuilder methBld = new StringBuilder();
				methBld.append(methodEntry.getKey());
				boolean isFirstParam = true;
				boolean lastWasParam = false;
				for (String entryLabelPart : entryLabelParts) {
					if (entryLabelPart.length() <= 1)
						continue;
					if (entryLabelPart.startsWith(":")) {
						params.add(BaseUtils.decapitalize(BaseUtils
								.clean(entryLabelPart)));
						if (isFirstParam) {
							isFirstParam = false;
							methBld.append("By");
						} else {
							methBld.append("And");
						}
						lastWasParam = true;
					} else if (lastWasParam) {
						lastWasParam = false;
						methBld.append("Only");
						isFirstParam = true;
					}
					methBld.append(BaseUtils.capitalize(BaseUtils
							.clean(entryLabelPart)));
				}
				map.put(methBld.toString(),
						new SimpleEntry<String, List<String>>(entry.getKey(),
								params));
			}
		}
		return map;
	}

	private static void printSystem(OAuthConnection conn) {
		System.out.println(JsonHelper.getStudIPColors(conn));
		System.out.println(JsonHelper.getStudIPSettings(conn));
	}

	private static void printRoutes(OAuthConnection conn) {
		for (Entry<String, JsonElement> entry : JsonHelper.getRoutes(conn)) {
			System.out.println(" * " + entry.getKey());
			System.out.println("      " + entry.getValue());
		}
	}

	private static void printHelp() {
		System.out.println(" StudIP Command line interface ");
		System.out.println("###############################");
		System.out.println();
		System.out.println("  <no arg>     show this help");
		System.out.println("  'system'     some system info");
		System.out.println("  'courses'    list all courses");
		System.out.println("  'contacts'   list all contacts");
		System.out.println("  'messages'   list all messages");
		System.out.println("  'semesters'  list all semesters");
		System.out.println("  'user'       get the current user");
		System.out.println("  'routes'     list all routes");
		System.out.println("  'genesis'    generate REST API client");
		System.out.println("  'test'       experimental testing");
		System.out.println();
	}

	private static void connectAsNewApp(OAuthConnection conn)
			throws FileNotFoundException, IOException {
		System.out.println("Got request token: " + conn.orderRequestToken());
		System.out.println("Auth web URL: " + conn.getAuthorizeWebURL());
		// Ask for user name and password
		BufferedReader buffr = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.print("Your name >> ");
		String userName = buffr.readLine().trim();
		System.out.print("Your password >> ");
		String password = buffr.readLine().trim();
		// Get HTTP client
		DefaultHttpClient httpClient = BaseUtils.createHttpClient();
		// Fetch security token and login ticket by simply doing a GET
		HttpGet httpGet = new HttpGet(conn.getAuthorizeWebURL());
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		String securityToken = null;
		String loginTicket = null;
		if (entity != null) {
			String html = EntityUtils.toString(entity);
			securityToken = html.split("security_token\" value=\"", 2)[1]
					.split("\">", 2)[0];
			loginTicket = html.split("login_ticket\" value=\"", 2)[1].split(
					"\">", 2)[0];
		}
		// Log it
		System.out.println("Got security token: " + securityToken);
		System.out.println("Got login ticket: " + loginTicket);
		// Push user name and password along with the received tokens
		HttpPost httpPost = new HttpPost(conn.getAuthorizeWebURL());
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("security_token",
				securityToken));
		nameValuePairs.add(new BasicNameValuePair("login_ticket", loginTicket));
		nameValuePairs.add(new BasicNameValuePair("loginname", userName));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		response = httpClient.execute(httpPost);
		entity = response.getEntity();
		String authUrl = null;
		if (entity != null) {
			String html = EntityUtils.toString(entity);
			String[] formParts = html.split("<form action=\"", 2);
			if (formParts.length != 2) {
				System.err.println("Login failed!");
				return;
			}
			authUrl = formParts[1].split("\"")[0];
			String[] parts = conn.getAuthorizeWebURL().split("//", 2);
			String srvProto = parts[0] + "//";
			String srvHost = parts[1];
			String baseServer = srvProto + srvHost.split("/", 2)[0];
			authUrl = baseServer + authUrl;
		}
		// Accept the application
		System.out.println("Got accept URL: " + authUrl);
		httpPost = new HttpPost(authUrl);
		nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("allow", ""));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
		response = httpClient.execute(httpPost);
		entity = response.getEntity();
		if (entity != null) {
			boolean success = response.getStatusLine().getStatusCode() == 200;
			System.out.println("Accepted app: " + success);
			EntityUtils.consume(entity);
		}
		// Log it
		System.out.println("Got access token: " + conn.orderAccessToken());
		// Save to file
		Properties props = new Properties();
		props.setProperty("accessToken", conn.getAccessor().accessToken);
		props.setProperty("accessSecret", conn.getAccessor().tokenSecret);
		props.storeToXML(new FileOutputStream("auth.cfg"), "Auth config");
		// Just end here
		System.out.println("Done with login. Please try again.");
	}
}