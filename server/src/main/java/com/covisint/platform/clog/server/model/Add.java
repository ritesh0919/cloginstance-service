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
@JsonPropertyOrder({ "index", "alias", "filter" })
public class Add {
	/** Resource @JsonProperty. */
	@JsonProperty("index")
	private String index;

	/** Resource @JsonProperty. */
	@JsonProperty("alias")
	private String alias;

	/** Resource @JsonProperty. */
	@JsonProperty("filter")
	private Filter filter;

	/** Resource @JsonProperty. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * JsonProperty.
	 * 
	 * @return The index
	 */
	@JsonProperty("index")
	public String getIndex() {
		return index;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param index1
	 *            The index
	 */
	@JsonProperty("index")
	public void setIndex(String index1) {
		this.index = index1;
	}

	/**
	 * JsonProperty.
	 * 
	 * @return The alias
	 */
	@JsonProperty("alias")
	public String getAlias() {
		return alias;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param alias1
	 *            The alias
	 */
	@JsonProperty("alias")
	public void setAlias(String alias1) {
		this.alias = alias1;
	}

	/**
	 * JsonProperty.
	 * 
	 * @return The filter
	 */
	@JsonProperty("filter")
	public Filter getFilter() {
		return filter;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param filter1
	 *            The filter
	 */
	@JsonProperty("filter")
	public void setFilter(Filter filter1) {
		this.filter = filter1;
	}

	/**
	 * JsonProperty.
	 * 
	 * @return Map
	 */
	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param name
	 * @param value
	 */
	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
