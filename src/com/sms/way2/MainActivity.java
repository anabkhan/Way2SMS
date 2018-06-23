package com.sms.way2;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	TextView res;
	EditText mob,msg;
	Button send,contactsPick;
	
	//SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	Raw r = new Raw();
	
	String token;

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      
        
        CookieHandler.setDefault(new CookieManager());
        
        res=(TextView) findViewById(R.id.status);
        mob = (EditText)findViewById(R.id.mob);
        msg = (EditText)findViewById(R.id.msg);
        send = (Button) findViewById(R.id.send);
        contactsPick = (Button) findViewById(R.id.selectContact);
        
        
        //SignIn Check
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
    	
         
        
       if ( settings.getString("isSignedIn", "false").equals("false")) {
    	   Intent intent = new Intent("com.sms.way2.Sign_In");
			startActivity(intent);
			finish();
	} else {
		
		
		  //readcontact();
		  
	        
	       signIn();
	       
		

	}
       
        
        /*
        SharedPreferences.Editor ed = settings.edit();
        ed.putString("username", "usr");
        ed.apply();
        */

      
        
        //readcontact();

        
        
        send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String toMob = mob.getEditableText().toString(),message = msg.getEditableText().toString();
				
				if(toMob.length() != 10){
					Toast.makeText(getApplicationContext(), "Mobile Number not 10 digit", Toast.LENGTH_LONG).show();
				}else{
					if(message.length() > 139 || message.length() == 0){
						res.setText("message Limit Exceeded Or Empty Message:"+message.length());
						
					}else{
						//now send message
						Raw r = new Raw();
						String status = r.sendMessage(toMob,message,settings.getString("tokenId", "0"));
						if (status.equals("sent")) {
							Toast.makeText(getApplicationContext(), "Message Sent Successfully !", Toast.LENGTH_LONG).show();
							msg.setText(null);
							res.setText("Message Sent");
							
						} else {
							if (status.equals("Failed")) {
								signIn();
								res.setText("Message Sending Failed.Try Again");
								
							}
							if (status.equals("Connection Failure")) {
								res.setText("No Working Internet Connection");
								Toast.makeText(getApplicationContext(), "Connection Failure", Toast.LENGTH_LONG).show();
								
							}
							if (status.equals("rejected")) {
								res.setText("Daily Limit Exceeded");
								
							}

						}
						
					}
				}
				
				
			}
		});
        
        contactsPick.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				readcontact();
				
				
			}
		});
        
        
        

        /*
		try {
			
			String tokenId;
	        res=(TextView) findViewById(R.id.status);
	        
	        Connect c= new Connect();
	        URL uri,senduri;
			uri = new URL("http://site24.way2sms.com/Login1.action");
			senduri = new URL("http://site24.way2sms.com/smstoss.action");
		       String ss = c.getResponse(uri, "username=9198687148&password=pavilliong4");
		       res.setText(ss);
		       //String sms = c.getResponse(senduri, "ssaction=ss&Token=D9A43AB2EE935E3E2C51643D3508FFD6.w810&mobile=7310018329&message=check&msgLen=135");
		       //res.append(sms);
		       Scanner scn = new Scanner(ss);
		       
		       while (scn.hasNextLine()) {
		    	   
				String line = scn.nextLine();
				
				if(line.contains("input type")){
					
					if (line.contains("Token")) {
						
						
						
						String l = line.replace("<input type=\"hidden\" name=\"Token\" id=\"Token\" value=\"", " ");
						String l2 = l.replace("\" />", "");
						tokenId = l2.trim();
						res.setText(tokenId);
						String rr = c.getResponse(senduri, "ssaction=ss&Token="+tokenId+"&mobile=7310018329&message=messege aaya be&msgLen=135");
						res.setText(rr);
						break;
						
					}
					
				}
				
			} 
		       scn.close();
		       
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        */
        
    }

    @SuppressLint("NewApi") private void signIn() {
    	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
    	Raw r = new Raw();
		// TODO Auto-generated method stub
    	res = (TextView)findViewById(R.id.status);
    	
    	res.setText("Signing In...");
    	
    	token = r.SignIn(settings.getString("username", null),settings.getString("password", null));
	       if(token.equals("error")){
	    	   res.setText("Error While SignIn");
	       }else{
	    	   if (token.equals("connectionError")) {
	    		   res.setText("No Internet");
				
			} else {
				
		    	   SharedPreferences.Editor ed = settings.edit();
		    	   ed.putString("tokenId", token);
		    	   ed.apply();
		    	   res.setText("Logged In");

			}
	    	   
	    	   

	       }
	       
		
	}

	final int PICK_CONTACT=100;
    
    private void readcontact() {
    	
    	mob = (EditText) findViewById(R.id.mob);
    	
    	final int PICK_CONTACT=100;
		// TODO Auto-generated method stub
    	
       
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
			startActivityForResult(intent,PICK_CONTACT);
    }
			
			
   

    public void onActivityResult(int reqCode, int resultCode, Intent data) {

    	try {
            super.onActivityResult(reqCode, resultCode, data);

            switch (reqCode) {
              case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    
                    final String[] phoneProjection = new String[]{Phone.DATA};
                    
                    Cursor cursor = getContentResolver().query(contactData, phoneProjection, null, null, null);
                    try {
                  	  while (cursor.moveToNext()) {
  						String number = cursor.getString(0);
  						number = number.replace("+91", "");
  						mob.setText(number.replaceAll(" ", ""));
  					}
  					
  				} finally{
  					cursor.close();
  				}
                 }
               break;
            }
		} catch (Exception e) {
			// TODO: handle exception
			res = (TextView)findViewById(R.id.status);
			res.setText(e.toString());
		}
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
      	 
        	 Intent intent = new Intent("com.sms.way2.Sign_In");
  			startActivity(intent);
             return true;
         }
        
        return super.onOptionsItemSelected(item);
    }
}
