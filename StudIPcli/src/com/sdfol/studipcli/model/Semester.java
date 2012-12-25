package com.sdfol.studipcli.model;

public class Semester {
	private String semester_id;
	private String title;
	private String description;
	private long begin;
	private long end;
	private long seminars_begin;
	private long seminars_end;

	@Override
	public String toString() {
		return "Semester [semester_id=" + semester_id + ", title=" + title
				+ ", description=" + description + ", begin=" + begin
				+ ", end=" + end + ", seminars_begin=" + seminars_begin
				+ ", seminars_end=" + seminars_end + "]";
	}
}