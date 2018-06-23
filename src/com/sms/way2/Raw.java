package com.sms.way2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Raw {
	
	Connect c= new Connect();
	URL loginUri,sendUri;
	
	
	
	

	public String SignIn(String username, String password) {
		
		String token;
		
		
		try {
			int flag=0;
			loginUri = new URL("http://site24.way2sms.com/Login1.action");
			String res = c.getResponse(loginUri, "username="+username+"&password="+password);
		       Scanner scn = new Scanner(res);
		       
		       while (scn.hasNextLine()) {
		    	   
				String line = scn.nextLine();
				
				if(line.contains("input type")){
					
					if (line.contains("Token")) {
						
						flag = 1;
						
						String l = line.replace("<input type=\"hidden\" name=\"Token\" id=\"Token\" value=\"", " ");
						String l2 = l.replace("\" />", "");
						return l2.trim();
					}
				}
		       }
		       if (flag == 0) {
				return "error";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return "connectionError";
		}
		
		return null;
	}





	public String sendMessage(String toMob, String message, String token) {
		// TODO Auto-generated method stub
		
		try {
			
			sendUri = new URL("http://site24.way2sms.com/smstoss.action");
			String res = c.getResponse(sendUri, "ssaction=ss&Token="+token+"&mobile="+toMob+"&message="+message+"&msgLen=135");
			if (res.contains("submitted successfully")) {
				
				return "sent";
			} else {
				if (res.contains("Rejected")) {
					return "rejected";
				} else {
					return "Failed";

				}
				
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "Connection Failure";
		}
		
		
		
	}

}
