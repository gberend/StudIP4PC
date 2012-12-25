package com.sdfol.studipcli.model;

public class Settings {
	private String API_VERSION;
	private boolean ALLOW_CHANGE_USERNAME;
	private boolean ALLOW_CHANGE_EMAIL;
	private boolean ALLOW_CHANGE_NAME;
	private boolean ALLOW_CHANGE_TITLE;
	private String UNI_NAME_CLEAN;

	@Override
	public String toString() {
		return "Settings [API_VERSION=" + API_VERSION
				+ ", ALLOW_CHANGE_USERNAME=" + ALLOW_CHANGE_USERNAME
				+ ", ALLOW_CHANGE_EMAIL=" + ALLOW_CHANGE_EMAIL
				+ ", ALLOW_CHANGE_NAME=" + ALLOW_CHANGE_NAME
				+ ", ALLOW_CHANGE_TITLE=" + ALLOW_CHANGE_TITLE
				+ ", UNI_NAME_CLEAN=" + UNI_NAME_CLEAN + "]";
	}
}