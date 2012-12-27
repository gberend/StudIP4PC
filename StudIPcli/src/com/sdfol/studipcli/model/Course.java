package com.sdfol.studipcli.model;

public class Course {
	private String course_id;
	private long start_time;
	private long duration_time;
	private String title;
	private String subtitle;
	private long type;
	private Modules modules;
	private String description;
	private String location;
	private String semester_id;
	private String[] teachers;
	private String[] tutors;
	private String[] students;
	private String color;

	public String getCourseId() {
		return course_id;
	}

	public long getStartTime() {
		return start_time;
	}

	public long getDurationTime() {
		return duration_time;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public long getType() {
		return type;
	}

	public Modules getModules() {
		return modules;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}

	public String getSemesterId() {
		return semester_id;
	}

	public String[] getTeachers() {
		return teachers;
	}

	public String[] getTutors() {
		return tutors;
	}

	public String[] getStudents() {
		return students;
	}

	public String getColor() {
		return color;
	}
}