package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.restclienttemplate.models.SampleModel;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.oauth.OAuthLoginActionBarActivity;

// USE TO ACCESS CLIENT
//		RestClient client = RestApplication.getRestClient();
//		client.getHomeTimeline(1, new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, Headers headers, JSON json) {
//				Log.v(json.jsonArray.getJSONObject(0).getLong("id"));
//			}
//		});

// LOAD DATA INTO MODELS FROM JSONARRAY
// ArrayList<Tweet> tweets = Tweet.fromJSON(jsonArray);

// LOAD DATA FROM SINGLE JSON OBJECT
// Tweet t = new Tweet(json);
// t.body = "foo"

// TO SAVE
//AsyncTask<Tweet, Void, Void> task = new AsyncTask<Tweet, Void, Void>() {
//@Override
//protected Void doInBackground(Tweet... tweets) {
//		TwitterDao twitterDao = ((RestApplication) getApplicationContext()).getMyDatabase().twitterDao();
//		twitterDao.insertModel(tweets);
//		return null;
//		};
//		};
//		task.execute(tweets);

// If client and secret are having a problem,


public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	SampleModelDao sampleModelDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final SampleModel sampleModel = new SampleModel();
		sampleModel.setName("CodePath");

		sampleModelDao = ((TwitterApp) getApplicationContext()).getMyDatabase().sampleModelDao();

		AsyncTask.execute(new Runnable() {
			@Override
			public void run() {
				sampleModelDao.insertModel(sampleModel);
			}
		});
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		 Log.i("teiosjois", "logged in successfully.");
		 Intent i = new Intent(this, TimelineActivity.class);
		 startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}



}
