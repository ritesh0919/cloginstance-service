/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.covisint.platform.clog.server.model.Action;
import com.covisint.platform.clog.server.model.Add;
import com.covisint.platform.clog.server.model.Bool;
import com.covisint.platform.clog.server.model.ClogInstance;
import com.covisint.platform.clog.server.model.Filter;
import com.covisint.platform.clog.server.model.Should;
import com.covisint.platform.clog.server.model.Term;

/**
 * 
 * @author ritesh.gupta
 *
 */
public class ClogInstanceJsonHelper {

	/**
	 * createJsonForClogInstance.
	 * 
	 * @param alias
	 * @param index
	 * @param groupid
	 * @param realmid
	 * @return String
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public String createJsonForClogInstance(String alias, String index, String groupid, String realmid) 
			throws IOException {
		
		return new ObjectMapper().writeValueAsString(createClogInstance(alias, index, groupid, realmid));
	}

	/**
	 * createClogInstance.
	 * 
	 * @param alias
	 * @param index
	 * @param groupid
	 * @param realmid
	 * @return ClogInstance
	 */
	private ClogInstance createClogInstance(String alias, String index, String groupid, String realmid) {

		final ClogInstance clogInstance = new ClogInstance();
		final Filter filter = createFilter(createBool(createShouldList(groupid, realmid)));
		clogInstance.setActions(creatActionList(createAdd(alias, index, filter)));
		return clogInstance;

	}

	/**
	 * creatActionList.
	 * 
	 * @param add
	 * @return list of action
	 */
	private List<Action> creatActionList(Add add) {
		final List<Action> actionList = new ArrayList<Action>();
		final Action action = new Action();
		action.setAdd(add);
		actionList.add(action);
		return actionList;
	}

	/**
	 * createAdd.
	 * 
	 * @param alias
	 * @param index
	 * @param filter
	 * @return add
	 */
	private Add createAdd(String alias, String index, Filter filter) {
		final Add add = new Add();
		add.setAlias(alias);
		add.setIndex(index);
		add.setFilter(filter);
		return add;
	}

	/**
	 * createFilter.
	 * 
	 * @param bool
	 * @return filter
	 */
	private Filter createFilter(Bool bool) {
		final Filter filter = new Filter();
		filter.setBool(bool);
		return filter;
	}

	/**
	 * createBool.
	 * 
	 * @param shouldList
	 * @return bool
	 */
	private Bool createBool(List<Should> shouldList) {
		final Bool bool = new Bool();
		bool.setShould(shouldList);
		return bool;
	}

	/**
	 * createShouldList.
	 * 
	 * @param groupid
	 * @param realmid
	 * @return list of should
	 */
	private List<Should> createShouldList(String groupid, String realmid) {
		final List<Should> shouldList = new ArrayList<Should>();
		final Should should = new Should();
		final Term term = new Term();
		term.setGroupId(groupid);
		should.setTerm(term);
		final Should should1 = new Should();
		final Term t1 = new Term();
		t1.setX_realm(realmid);
		should1.setTerm(t1);
		shouldList.add(should);
		shouldList.add(should1);
		return shouldList;
	}

}
