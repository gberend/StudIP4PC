package com.sdfol.studipcli.model;

public class ColorSettings {
	private String background;
	private String dark;
	private String light;

	public String getBackground() {
		return background;
	}

	public String getDark() {
		return dark;
	}

	public String getLight() {
		return light;
	}

	@Override
	public String toString() {
		return "ColorSettings [background=" + background + ", dark=" + dark
				+ ", light=" + light + "]";
	}
}