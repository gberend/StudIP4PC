package com.sdfol.studipcli.model;

public class User {
	private String user_id;
	private String username;
	private String perms;
	private String title_pre;
	private String forename;
	private String lastname;
	private String title_post;
	private String email;
	private String avatar_small;
	private String avatar_medium;
	private String avatar_normal;
	private String phone;
	private String homepage;
	private String privadr;
	private String skype;
	private boolean skype_show;

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username
				+ ", perms=" + perms + ", title_pre=" + title_pre
				+ ", forename=" + forename + ", lastname=" + lastname
				+ ", title_post=" + title_post + ", email=" + email
				+ ", avatar_small=" + avatar_small + ", avatar_medium="
				+ avatar_medium + ", avatar_normal=" + avatar_normal
				+ ", phone=" + phone + ", homepage=" + homepage + ", privadr="
				+ privadr + ", skype=" + skype + ", skype_show=" + skype_show
				+ "]";
	}
}