package io.github.riwcwt.discovery;

public class InstanceDetails {
	private String description;

	public InstanceDetails() {
		this("");
	}

	public InstanceDetails(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
