/* Copyright (C) 2014 Covisint. All Rights Reserved. */
package com.covisint.platform.clog.server.groupservice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.util.concurrent.CheckedFuture;

public class CheckedFutureImpl<T, X extends Exception> implements CheckedFuture<T, X> {

	T obj;
	X ex;
	
	public CheckedFutureImpl(T obj, X ex) {
		this.obj = obj;
		this.ex = ex;
	}
	
	@Override
	public void addListener(Runnable arg0, Executor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		return obj;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T checkedGet() throws X {
		// TODO Auto-generated method stub
		return obj;
	}

	@Override
	public T checkedGet(long arg0, TimeUnit arg1) throws TimeoutException,
			X {
		// TODO Auto-generated method stub
		return null;
	}

}
