package com.dd.platform.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Constituency implements Serializable {

	private static final long serialVersionUID = 4410740551790241188L;

	// Identity
	
	// DD Identity
	private String id;

	// TODO: Add external source system identities
	
	// Attributes
	
	private String name;

	// Properties
	private Map<String, Object> properties;

	public Constituency(String id, String name) {
		this.id = id;
		this.name = name;
		this.properties = new HashMap<String, Object>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("name", name)
			.append("properties", properties.toString()).toString();
	}

}
