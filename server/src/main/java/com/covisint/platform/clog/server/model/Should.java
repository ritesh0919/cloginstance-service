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
@JsonPropertyOrder({ "term" })
public class Should {
	
	/** Resource @JsonProperty. */
	@JsonProperty("term")
	private Term term = new Term();

	/** Resource @JsonProperty. */
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * JsonProperty.
	 * 
	 * @return The term
	 */
	@JsonProperty("term")
	public Term getTerm() {
		return term;
	}

	/**
	 * JsonProperty.
	 * 
	 * @param term1
	 *            The term
	 */
	@JsonProperty("term")
	public void setTerm(Term term1) {
		this.term = term1;
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
