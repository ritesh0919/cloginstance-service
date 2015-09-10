/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.model;

import java.util.HashMap;
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
@JsonPropertyOrder({ "bool" })
public class Filter {

	/** Resource @JsonProperty. */
	@JsonProperty("bool")
	private Bool bool;

	/** Resource @JsonProperty. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * JsonAnySetter.
	 * 
	 * @return The bool
	 */
	@JsonProperty("bool")
	public Bool getBool() {
		return bool;
	}

	/**
	 * JsonAnySetter.
	 * 
	 * @param bool1
	 *            The bool
	 */
	@JsonProperty("bool")
	public void setBool(Bool bool1) {
		this.bool = bool1;
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
