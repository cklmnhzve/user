package com.ldp.datahub.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class BaseAction {

	 public void sendJson(HttpServletResponse response, String text){
    	 PrintWriter write = null;  
    	 response.setContentType("text/html;charset=UTF-8");  
    	 response.setHeader("Pragma", "No-cache");  
    	 response.setHeader("Cache-Control", "no-cache");  
    	 response.setDateHeader("Expires", 0);  
    	 try {  
    		 write = response.getWriter();  
    		 write.write(text);  
    		 write.flush();  
    	 } catch (IOException e) {  
    	 } finally {  
    		 if (write != null)  
    			 write.close();  
    		 write = null;  
    	 }  
    }
}
