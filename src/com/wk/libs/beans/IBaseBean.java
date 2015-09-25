package com.wk.libs.beans;

import java.io.Serializable;

public abstract class IBaseBean<T> implements Serializable{

	public abstract String status();

	public abstract String info();

	public T data;

}
