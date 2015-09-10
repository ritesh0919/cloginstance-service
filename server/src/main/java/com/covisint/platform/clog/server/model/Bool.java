/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/** Resource @JsonProperty. */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "should" })
public class Bool {
	
	/** Resource @JsonProperty. */
	@JsonProperty("should")
	private List<Should> should = new ArrayList<Should>();

	/** Resource @JsonProperty. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * JsonAnySetter.
	 * 
	 * @return The should
	 */
	@JsonProperty("should")
	public List<Should> getShould() {
		return should;
	}

	/**
	 * JsonAnySetter.
	 * 
	 * @param should1
	 *            The should
	 */
	@JsonProperty("should")
	public void setShould(List<Should> should1) {
		this.should = should1;
	}

	/**
	 * JsonAnySetter.
	 * 
	 * @return Map
	 */
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	/**
	 * JsonAnySetter.
	 * 
	 * @param name
	 * @param value
	 */
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
