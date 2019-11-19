package com.sxp.patMag.util;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

/**
 * 工具类
 */
public   class getAjax {
	 
  public static void sendListToJson(List list,HttpServletResponse response) {
        Gson g = new Gson();
		String value=g.toJson(list);
        try {
        	PrintWriter writer = response.getWriter();
			writer.print(value);
			writer.flush();
			writer.close(); 
		 } catch (IOException e) {
			 e.printStackTrace();
		} 
 }
  public static String  ObjectToJson(Object value) {
      Gson g = new Gson();
		String res=g.toJson(value);
      return res;
}
  public static String  ListToJson(List list) {
      Gson g = new Gson();
		String value=g.toJson(list);
		return value;
      
	  }
  public static void sendValue(Object value,HttpServletResponse response) {
  try {
      	PrintWriter writer = response.getWriter();
			writer.print(value);
			writer.flush();
			writer.close(); 
		 } catch (IOException e) {
			 e.printStackTrace();
		} 
	  }
  public static void sendValueToJson(Object value,HttpServletResponse response) {
  try {
	    Gson g = new Gson();
		String v=g.toJson(value);
      	PrintWriter writer = response.getWriter();
			writer.print(v);
			writer.flush();
			writer.close(); 
		 } catch (IOException e) {
			 e.printStackTrace();
		} 
	  }
  
 
}
