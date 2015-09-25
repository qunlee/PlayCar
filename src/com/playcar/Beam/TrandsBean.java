package com.playcar.Beam;

import java.util.ArrayList;
import com.wk.libs.beans.BaseListBean;

/**
 * 动态类
 */
public class TrandsBean extends BaseListBean{
	
	public ArrayList<Trands> trands = new ArrayList<Trands>();
	
	public class Trands {
		public String id;
		public String name;
		public String content;
		public String age;
		
	}

}
