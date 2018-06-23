package com.sms.way2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends ActionBarActivity {
	EditText usr,pass;
	Button sbmt;
	
	Raw r = new Raw();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		usr = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);
		sbmt = (Button) findViewById(R.id.submit);
		
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		final SharedPreferences.Editor ed = settings.edit();
		
		
		sbmt.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(usr != null && usr.length() == 10 && pass != null){
					
					String token = r.SignIn(usr.getEditableText().toString(), pass.getEditableText().toString());
					
					if (token.equals("error") || token.charAt(0) == '<') {
						Toast.makeText(getApplicationContext(), "Inavlid Username/Pasword", Toast.LENGTH_LONG).show();
						
					} else {
						if (token.equals("connectionError")) {
							Toast.makeText(getApplicationContext(), "No Internet/Unable to SignIn", Toast.LENGTH_LONG).show();
							
						} else {
							ed.putString("username", usr.getEditableText().toString());
							ed.putString("password", pass.getEditableText().toString());
							ed.putString("isSignedIn", "true");
							
							ed.putString("tokenId",token );
							
							ed.apply();
							
							Intent i = new Intent(getApplicationContext(),MainActivity.class);
							
							

							finish();
							startActivity(i);

						}

					}
					
					
				}
				else{
					Toast.makeText(getApplicationContext(), "Username must be Mobile Number/Empty Password", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
	}
	
	

}
