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

/** Resource @JsonSerialize. */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "add" })
public class Action {

	/** Resource @JsonProperty. */
	@JsonProperty("add")
	private Add add;

	/** Resource @JsonIgnore. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * Getter for add attribute
	 * 
	 * @return add attribute
	 */
	@JsonProperty("add")
	public Add getAdd() {
		return add;
	}

	/**
	 * Setter for add attribute
	 * 
	 * @param add
	 *            add to be set
	 */
	@JsonProperty("add")
	public void setAdd(Add add) {
		this.add = add;
	}

	/**
	 * JsonAnyGetter.
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
	 *            Name of the property
	 * @param value
	 *            value of the property
	 */
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
