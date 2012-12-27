package com.sdfol.studipcli.model;

public class Event {
	private String event_id;
	private String course_id;
	private long start;
	private long end;
	private String title;
	private String description;
	private String categories;
	private String room;

	public String getEventId() {
		return event_id;
	}

	public String getCourseId() {
		return course_id;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCategories() {
		return categories;
	}

	public String getRoom() {
		return room;
	}
}