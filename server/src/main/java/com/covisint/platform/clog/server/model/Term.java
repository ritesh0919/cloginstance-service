/* Copyright (C) 2014 Covisint. All Rights Reserved. */

package com.covisint.platform.clog.server.model;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/** Resource @JsonProperty. */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
// @JsonPropertyOrder({ "realm_id", "component_name" })
public class Term {

	/** Resource @JsonProperty. */
	@JsonProperty("x-realm")
	private String x_realm;

	/** Resource @JsonProperty. */
	@JsonProperty("group_id")
	private String groupId;

	/** Resource @JsonProperty. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * JsonProperty.
	 * 
	 * @return The x_realm
	 */
	@JsonProperty("x-realm")
	public String getX_realm() {
		return x_realm;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param x_realm1
	 *            The x_realm
	 */
	@JsonProperty("x-realm")
	public void setX_realm(String x_realm) {
		this.x_realm = x_realm;
	}

	/**
	 * JsonProperty.
	 * 
	 * @return The groupId
	 */
	@JsonProperty("group_id")
	public String getGroupId() {
		return groupId;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param groupId1
	 *            The group_id
	 */
	@JsonProperty("group_id")
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	 * JsonAnyGetter.
	 * 
	 * @param name
	 * @param value
	 */
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
