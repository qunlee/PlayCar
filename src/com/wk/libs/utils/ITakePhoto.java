package com.wk.libs.utils;

import java.util.ArrayList;

public interface ITakePhoto {
	public void handle(String fn);

	public void handle(ArrayList<String> fnList);
}
