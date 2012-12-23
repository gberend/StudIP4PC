package com.sdfol.studipcli.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.sdfol.studipcli.net.HttpMethod;
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
			System.out
					.println("Got request token: " + conn.orderRequestToken());
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
				loginTicket = html.split("login_ticket\" value=\"", 2)[1]
						.split("\">", 2)[0];
			}
			// Log it
			System.out.println("Got security token: " + securityToken);
			System.out.println("Got login ticket: " + loginTicket);
			// Push user name and password along with the received tokens
			HttpPost httpPost = new HttpPost(conn.getAuthorizeWebURL());
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("security_token",
					securityToken));
			nameValuePairs.add(new BasicNameValuePair("login_ticket",
					loginTicket));
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
			props = new Properties();
			props.setProperty("accessToken", conn.getAccessor().accessToken);
			props.setProperty("accessSecret", conn.getAccessor().tokenSecret);
			props.storeToXML(new FileOutputStream("auth.cfg"), "Auth config");
			// Just end here
			System.out.println("Done with login. Please try again.");
			return;
		}

		// Test
		List<Entry<String, String>> invParams = new LinkedList<Entry<String, String>>();
		conn.invoke(HttpMethod.Get, "activities" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "contacts" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "courses" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "discovery" + ".json", invParams);
		// conn.invoke(HttpMethod.Get, "documents" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "messages" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "news" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "semesters" + ".json", invParams);
		// conn.invoke(HttpMethod.Get, "studip" + ".json", invParams);
		conn.invoke(HttpMethod.Get, "user" + ".json", invParams);
		// conn.invoke(HttpMethod.Get, "users" + ".json", invParams);
	}
}