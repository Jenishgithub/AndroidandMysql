package com.example.androidandmysql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button bSubmit;
	EditText etName, etAge, etEmail;
//hererererer
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set strictpolicy mode
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_main);
		bSubmit = (Button) findViewById(R.id.buttonSubmitt);
		etName = (EditText) findViewById(R.id.edtitName);
		etAge = (EditText) findViewById(R.id.editAge);
		etEmail = (EditText) findViewById(R.id.editemail);
		bSubmit.setOnClickListener(new View.OnClickListener() {
			InputStream is = null;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// storing the values inside editext inside String variables
				String name = "" + etName.getText().toString();
				String age = "" + etAge.getText().toString();
				String email = "" + etEmail.getText().toString();

				// setting the namevalue pairs
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						1);
				// adding string variable inside namevaluepairs
				nameValuePairs.add(new BasicNameValuePair("name", name));
				nameValuePairs.add(new BasicNameValuePair("age", age));
				nameValuePairs.add(new BasicNameValuePair("email", email));

				// setting up the connection inside try catch block
				try {
					// setting up the default http client
					HttpClient httpClient = new DefaultHttpClient();
					// setting up http post method and passing the url in case
					// of online database and ip addres in case of localhost
					// database
					// And php file which serves as the link between the android
					// app and the database.

					HttpPost httpPost = new HttpPost(
							"http://10.0.2.2/tutorial.php");
					// passing the nameValuePairs inside the httpPost
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					// Getting the repsonse
					HttpResponse response = httpClient.execute(httpPost);

					// setting up the entity
					HttpEntity entity = response.getEntity();

					// setting up the content inside an input stream reader
					// lets define the input stream reader
					is = entity.getContent();
					// Displaying the toast message if data is entered
					// succesfully
					String msg = "Data is entered succesfully";
					Toast.makeText(getApplicationContext(), msg,
							Toast.LENGTH_LONG).show();
				}// write the catch blocks to handle the exception
				catch (ClientProtocolException e) {
					Log.e("ClientProtocol", "Log_tag");
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Not inserted",
							Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					Log.e("Log_tag", "IOException");
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Not inserted",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
