package com.sms.way2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.StrictMode;

import android.os.StrictMode;

public class Connect {
	

	
	@SuppressLint("NewApi") String getResponse(URL uri,String writeData){
		
		StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder().build());
		
		StringBuilder sb = null;
		
		
		try {

            

            
            HttpURLConnection client = null;

            client = (HttpURLConnection) uri.openConnection();
            client.setRequestMethod("POST");

            System.setProperty("http.agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:47.0) Gecko/20100101 Firefox/47.0");
            
            client.setDoOutput(true);

           
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeBytes(writeData);
            out.flush();
            out.close();
            
            

            

            int rCode = client.getResponseCode();
            
            InputStream in = client.getInputStream();
            
            
            
            
            InputStreamReader ipr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ipr);
            
            String line;
            sb =  new StringBuilder();
            while ((line = br.readLine()) != null) {
            	sb.append(line+"\n");
				
			}
            
            in.close();
            ipr.close();br.close();
            


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
		
		return sb.toString();
		
	}

}
