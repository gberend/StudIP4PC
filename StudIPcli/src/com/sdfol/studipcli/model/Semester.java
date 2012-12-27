package com.sdfol.studipcli.model;

public class Semester {
	private String semester_id;
	private String title;
	private String description;
	private long begin;
	private long end;
	private long seminars_begin;
	private long seminars_end;

	public String getSemesterId() {
		return semester_id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public long getBegin() {
		return begin;
	}

	public long getEnd() {
		return end;
	}

	public long getSeminarsBegin() {
		return seminars_begin;
	}

	public long getSeminarsEnd() {
		return seminars_end;
	}
}