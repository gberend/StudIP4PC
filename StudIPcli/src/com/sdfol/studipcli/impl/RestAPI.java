package com.sdfol.studipcli.impl;

import com.google.gson.JsonObject;
import com.sdfol.studipcli.api.IRestAPI;
import com.sdfol.studipcli.api.IOAuthConnection;
import com.sdfol.studipcli.net.HttpMethod;

public class RestAPI implements IRestAPI {
	private final IOAuthConnection connection;

	public RestAPI(IOAuthConnection connection) {
		this.connection = connection;
	}

	public JsonObject deleteContactsByUserId(String userId) {
		String route = "contacts" + "/" + userId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Delete, route, null);
		return jsonObj;
	}

	public JsonObject deleteContactsGroupsByGroupId(String groupId) {
		String route = "contacts" + "/" + "groups" + "/" + groupId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Delete, route, null);
		return jsonObj;
	}

	public JsonObject deleteContactsGroupsByGroupIdAndUserId(String groupId, String userId) {
		String route = "contacts" + "/" + "groups" + "/" + groupId + "/" + userId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Delete, route, null);
		return jsonObj;
	}

	public JsonObject deleteMessagesByMessageId(String messageId) {
		String route = "messages" + "/" + messageId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Delete, route, null);
		return jsonObj;
	}

	public JsonObject deleteNewsByNewsId(String newsId) {
		String route = "news" + "/" + newsId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Delete, route, null);
		return jsonObj;
	}

	public JsonObject deleteNewsByNewsIdOnlyCommentsByCommentId(String newsId, String commentId) {
		String route = "news" + "/" + newsId + "/" + "comments" + "/" + commentId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Delete, route, null);
		return jsonObj;
	}

	public JsonObject getActivitiesByRangeId(String rangeId) {
		String route = "activities(" + "/" + rangeId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getContacts() {
		String route = "contacts";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getContactsGroups() {
		String route = "contacts" + "/" + "groups";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getContactsGroupsByGroupId(String groupId) {
		String route = "contacts" + "/" + "groups" + "/" + groupId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCourses() {
		String route = "courses";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesByCourseId(String courseId) {
		String route = "courses" + "/" + courseId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesByCourseIdOnlyEvents(String courseId) {
		String route = "courses" + "/" + courseId + "/" + "events";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesByCourseIdOnlyMembersByStatus(String courseId, String status) {
		String route = "courses" + "/" + courseId + "/" + "members(" + "/" + status;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesByCourseIdOnlyWiki(String courseId) {
		String route = "courses" + "/" + courseId + "/" + "wiki";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesByCourseIdOnlyWikiByPageAndVersion(String courseId, String page, String version) {
		String route = "courses" + "/" + courseId + "/" + "wiki" + "/" + page + "/" + version;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesSemester() {
		String route = "courses" + "/" + "semester";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getCoursesSemesterBySemesterId(String semesterId) {
		String route = "courses" + "/" + "semester" + "/" + semesterId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getDiscovery() {
		String route = "discovery";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getDocumentsByDocumentId(String documentId) {
		String route = "documents" + "/" + documentId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getDocumentsByDocumentIdOnlyDownload(String documentId) {
		String route = "documents" + "/" + documentId + "/" + "download";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getDocumentsByRangeIdOnlyFolderByFolderId(String rangeId, String folderId) {
		String route = "documents" + "/" + rangeId + "/" + "folder(" + "/" + folderId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getEvents() {
		String route = "events";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getEventsIcal() {
		String route = "events" + "/" + "ical";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getMessages() {
		String route = "messages";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getMessagesByBox(String box) {
		String route = "messages" + "/" + box;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getMessagesByBoxAndFolder(String box, String folder) {
		String route = "messages" + "/" + box + "/" + folder;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getMessagesByMessageId(String messageId) {
		String route = "messages" + "/" + messageId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getNewsByNewsId(String newsId) {
		String route = "news" + "/" + newsId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getNewsByNewsIdOnlyComments(String newsId) {
		String route = "news" + "/" + newsId + "/" + "comments";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getNewsByNewsIdOnlyCommentsByCommentId(String newsId, String commentId) {
		String route = "news" + "/" + newsId + "/" + "comments" + "/" + commentId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getNewsRangeByRangeId(String rangeId) {
		String route = "news(" + "/" + "range" + "/" + rangeId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getSemesters() {
		String route = "semesters";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getSemestersBySemesterId(String semesterId) {
		String route = "semesters" + "/" + semesterId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getStudipColors() {
		String route = "studip" + "/" + "colors";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getStudipSettings() {
		String route = "studip" + "/" + "settings";
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject getUserByUserId(String userId) {
		String route = "user(" + "/" + userId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Get, route, null);
		return jsonObj;
	}

	public JsonObject postContactsGroups() {
		String route = "contacts" + "/" + "groups";
		JsonObject jsonObj = connection.invoke(HttpMethod.Post, route, null);
		return jsonObj;
	}

	public JsonObject postMessages() {
		String route = "messages";
		JsonObject jsonObj = connection.invoke(HttpMethod.Post, route, null);
		return jsonObj;
	}

	public JsonObject postNewsByNewsIdOnlyComments(String newsId) {
		String route = "news" + "/" + newsId + "/" + "comments";
		JsonObject jsonObj = connection.invoke(HttpMethod.Post, route, null);
		return jsonObj;
	}

	public JsonObject postNewsRangeByRangeId(String rangeId) {
		String route = "news(" + "/" + "range" + "/" + rangeId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Post, route, null);
		return jsonObj;
	}

	public JsonObject putContactsByUserId(String userId) {
		String route = "contacts" + "/" + userId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Put, route, null);
		return jsonObj;
	}

	public JsonObject putContactsGroupsByGroupIdAndUserId(String groupId, String userId) {
		String route = "contacts" + "/" + "groups" + "/" + groupId + "/" + userId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Put, route, null);
		return jsonObj;
	}

	public JsonObject putMessagesByMessageIdOnlyRead(String messageId) {
		String route = "messages" + "/" + messageId + "/" + "read";
		JsonObject jsonObj = connection.invoke(HttpMethod.Put, route, null);
		return jsonObj;
	}

	public JsonObject putMessagesRead() {
		String route = "messages" + "/" + "read";
		JsonObject jsonObj = connection.invoke(HttpMethod.Put, route, null);
		return jsonObj;
	}

	public JsonObject putNewsByNewsId(String newsId) {
		String route = "news" + "/" + newsId;
		JsonObject jsonObj = connection.invoke(HttpMethod.Put, route, null);
		return jsonObj;
	}
}