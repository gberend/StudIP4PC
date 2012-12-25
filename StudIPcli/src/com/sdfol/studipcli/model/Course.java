package com.sdfol.studipcli.model;

public class Course {
	private String course_id;
	private long start_time;
	private long duration_time;
	private String title;
	private String subtitle;
	private short type;
	private String location;
	private String semester_id;
	private String color;

	@Override
	public String toString() {
		return "Course [course_id=" + course_id + ", start_time=" + start_time
				+ ", duration_time=" + duration_time + ", title=" + title
				+ ", subtitle=" + subtitle + ", type=" + type + ", location="
				+ location + ", semester_id=" + semester_id + ", color="
				+ color + "]";
	}
}