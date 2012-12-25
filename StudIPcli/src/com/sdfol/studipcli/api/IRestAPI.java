package com.sdfol.studipcli.api;

import com.google.gson.JsonObject;

/**
 * Generated method calls for
 * http://studip.github.com/studip-rest.ip/
 */
public interface IRestAPI {

	/**
	 * Route => /contacts/:user_id
	 */
	public JsonObject deleteContactsByUserId(String userId);

	/**
	 * Route => /contacts/groups/:group_id
	 */
	public JsonObject deleteContactsGroupsByGroupId(String groupId);

	/**
	 * Route => /contacts/groups/:group_id/:user_id
	 */
	public JsonObject deleteContactsGroupsByGroupIdAndUserId(String groupId, String userId);

	/**
	 * Route => /messages/:message_id
	 */
	public JsonObject deleteMessagesByMessageId(String messageId);

	/**
	 * Route => /news/:news_id
	 */
	public JsonObject deleteNewsByNewsId(String newsId);

	/**
	 * Route => /news/:news_id/comments/:comment_id
	 */
	public JsonObject deleteNewsByNewsIdOnlyCommentsByCommentId(String newsId, String commentId);

	/**
	 * Route => /activities(/:range_id)
	 */
	public JsonObject getActivitiesByRangeId(String rangeId);

	/**
	 * Route => /contacts
	 */
	public JsonObject getContacts();

	/**
	 * Route => /contacts/groups
	 */
	public JsonObject getContactsGroups();

	/**
	 * Route => /contacts/groups/:group_id
	 */
	public JsonObject getContactsGroupsByGroupId(String groupId);

	/**
	 * Route => /courses
	 */
	public JsonObject getCourses();

	/**
	 * Route => /courses/:course_id
	 */
	public JsonObject getCoursesByCourseId(String courseId);

	/**
	 * Route => /courses/:course_id/events
	 */
	public JsonObject getCoursesByCourseIdOnlyEvents(String courseId);

	/**
	 * Route => /courses/:course_id/members(/:status)
	 */
	public JsonObject getCoursesByCourseIdOnlyMembersByStatus(String courseId, String status);

	/**
	 * Route => /courses/:course_id/wiki
	 */
	public JsonObject getCoursesByCourseIdOnlyWiki(String courseId);

	/**
	 * Route => /courses/:course_id/wiki/:page(/:version)
	 */
	public JsonObject getCoursesByCourseIdOnlyWikiByPageAndVersion(String courseId, String page, String version);

	/**
	 * Route => /courses/semester
	 */
	public JsonObject getCoursesSemester();

	/**
	 * Route => /courses/semester/:semester_id
	 */
	public JsonObject getCoursesSemesterBySemesterId(String semesterId);

	/**
	 * Route => /discovery
	 */
	public JsonObject getDiscovery();

	/**
	 * Route => /documents/:document_id
	 */
	public JsonObject getDocumentsByDocumentId(String documentId);

	/**
	 * Route => /documents/:document_id/download
	 */
	public JsonObject getDocumentsByDocumentIdOnlyDownload(String documentId);

	/**
	 * Route => /documents/:range_id/folder(/:folder_id)
	 */
	public JsonObject getDocumentsByRangeIdOnlyFolderByFolderId(String rangeId, String folderId);

	/**
	 * Route => /events
	 */
	public JsonObject getEvents();

	/**
	 * Route => /events/ical
	 */
	public JsonObject getEventsIcal();

	/**
	 * Route => /messages
	 */
	public JsonObject getMessages();

	/**
	 * Route => /messages/:box
	 */
	public JsonObject getMessagesByBox(String box);

	/**
	 * Route => /messages/:box/:folder
	 */
	public JsonObject getMessagesByBoxAndFolder(String box, String folder);

	/**
	 * Route => /messages/:message_id
	 */
	public JsonObject getMessagesByMessageId(String messageId);

	/**
	 * Route => /news/:news_id
	 */
	public JsonObject getNewsByNewsId(String newsId);

	/**
	 * Route => /news/:news_id/comments
	 */
	public JsonObject getNewsByNewsIdOnlyComments(String newsId);

	/**
	 * Route => /news/:news_id/comments/:comment_id
	 */
	public JsonObject getNewsByNewsIdOnlyCommentsByCommentId(String newsId, String commentId);

	/**
	 * Route => /news(/range/:range_id)
	 */
	public JsonObject getNewsRangeByRangeId(String rangeId);

	/**
	 * Route => /semesters
	 */
	public JsonObject getSemesters();

	/**
	 * Route => /semesters/:semester_id
	 */
	public JsonObject getSemestersBySemesterId(String semesterId);

	/**
	 * Route => /studip/colors
	 */
	public JsonObject getStudipColors();

	/**
	 * Route => /studip/settings
	 */
	public JsonObject getStudipSettings();

	/**
	 * Route => /user(/:user_id)
	 */
	public JsonObject getUserByUserId(String userId);

	/**
	 * Route => /contacts/groups
	 */
	public JsonObject postContactsGroups();

	/**
	 * Route => /messages
	 */
	public JsonObject postMessages();

	/**
	 * Route => /news/:news_id/comments
	 */
	public JsonObject postNewsByNewsIdOnlyComments(String newsId);

	/**
	 * Route => /news(/range/:range_id)
	 */
	public JsonObject postNewsRangeByRangeId(String rangeId);

	/**
	 * Route => /contacts/:user_id
	 */
	public JsonObject putContactsByUserId(String userId);

	/**
	 * Route => /contacts/groups/:group_id/:user_id
	 */
	public JsonObject putContactsGroupsByGroupIdAndUserId(String groupId, String userId);

	/**
	 * Route => /messages/:message_id/read
	 */
	public JsonObject putMessagesByMessageIdOnlyRead(String messageId);

	/**
	 * Route => /messages/read
	 */
	public JsonObject putMessagesRead();

	/**
	 * Route => /news/:news_id
	 */
	public JsonObject putNewsByNewsId(String newsId);
}