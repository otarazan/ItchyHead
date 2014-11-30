package com.tarzain.itchyhead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

public class ResultActivity extends Activity {

	private TextView txtScore;
	private Button btnContinue;
	private TextView txtAverage;
	private Button btnShare;

	private int scorePercentege;
	private int average;

	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result_layout);

		txtScore = (TextView) findViewById(R.id.txtScore);
		btnContinue = (Button) findViewById(R.id.btnContinue);
		txtAverage = (TextView) findViewById(R.id.txtAverage);
		btnShare = (Button) findViewById(R.id.btnShare);

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

		scorePercentege = (100 * MainActivity.numOfRightAns)
				/ MainActivity.numOfQuestions;
		average = MainActivity.numOfRightAns / 10;
		txtAverage.setText("Average: " + String.valueOf(average));

		txtScore.setText(String.valueOf(scorePercentege) + "%");
		btnContinue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ResultActivity.this,
						MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				getBaseContext().startActivity(intent);

			}
		});

		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
						ResultActivity.this)
						.setLink("https://developers.facebook.com/android")
						.setDescription(
								"My Percentage is : "
										+ String.valueOf(scorePercentege) + "%"
										+ "Average: " + String.valueOf(average))
						.build();
				uiHelper.trackPendingDialogCall(shareDialog.present());

			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}

}
