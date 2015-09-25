package com.wk.libs.utils;

import com.playcar.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class T{
	
	
	/**
	 * 短时间显示
	 */
	public static Toast showShort(Context context,String text){
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.toast_transient_notification, null);
        TextView tv = (TextView)v.findViewById(R.id.message);
        tv.setText(text);
        
        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);  
        toast.setGravity(Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);  
        toast.setView(v); 
//        toast.show();
        return toast;
	}
	/**
	 * 长时间显示
	 */
	public static Toast showLong(Context context,String text){
		LayoutInflater inflate = (LayoutInflater)
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.toast_transient_notification, null);
		TextView tv = (TextView)v.findViewById(R.id.message);
		tv.setText(text);
		
		Toast toast = new Toast(context);
//        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);  
		toast.setGravity(Gravity.CENTER, 0, 100);
		toast.setDuration(Toast.LENGTH_LONG);  
		toast.setView(v); 
//		toast.show();
		return toast;
	}

}
