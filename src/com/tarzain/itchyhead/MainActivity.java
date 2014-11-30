package com.tarzain.itchyhead;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AppEventsLogger;

public class MainActivity extends ActionBarActivity {

	private TextView txtDipsNum;
	private TextView txtKey;
	private EditText eTextAns;
	private TextView txtCorrectAns;
	private TextView txtCurrentLevel;

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private Button btn7;
	private Button btn8;
	private Button btn9;
	private Button btnOk;
	private int randomMaxNum;
	private int randomNum;

	private int randomMaxNumMin = 5;
	private int randomMaxNumMax = 12;

	private int currentRMaxRandom;

	private String currentAns = "";
	private int activeOkNum = 0;

	private String key = "";
	private String tmp_prev = "0";

	private int numofCorrectAns = 0;

	private int level_1_num = 1;

	public static int numOfQuestions;
	public static int numOfRightAns;

	@Override
	protected void onResume() {
		super.onResume();

		// Logs 'install' and 'app activate' App Events.
		AppEventsLogger.activateApp(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Logs 'app deactivate' App Event.
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeViews();

		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					generateRundom();

					break;
				}
			}
		};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"You are going to given series of numbers, enter that numbers in reverse order and show how strong your mind is! Ex: if displayed numbers are 4 5 1, enter 1 5 4 that simple..")
				.setPositiveButton("Got it!", dialogClickListener).show();

		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "1";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "2";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "3";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "4";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "5";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "6";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "7";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "8";
				eTextAns.setText(reverseString(currentAns));

			}
		});
		btn9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				currentAns += "9";
				eTextAns.setText(reverseString(currentAns));

			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				txtKey.setText(key);
				numofCorrectAns = 0;
				level_1_num++;

				if (level_1_num == 11) {
					Intent intent = new Intent(MainActivity.this,
							ResultActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					getBaseContext().startActivity(intent);
				}

				for (int i = 0; i < key.length(); i++) {
					if (reverseString(key).charAt(i) == currentAns.charAt(i)) {
						numofCorrectAns++;
					} else {
						break;
					}
				}

				txtCorrectAns.setText("Correct Answers: "
						+ String.valueOf(numofCorrectAns));

				numOfRightAns += numofCorrectAns;

				new BackgroundTaskOkBtn().execute((Void) null);

			}
		});
	}

	private void initializeViews() {

		txtDipsNum = (TextView) findViewById(R.id.txtDispNum);
		txtKey = (TextView) findViewById(R.id.txtKey);
		eTextAns = (EditText) findViewById(R.id.eTxtAns);
		txtCorrectAns = (TextView) findViewById(R.id.txtCorrectAns);
		txtCurrentLevel = (TextView) findViewById(R.id.txtCurrentLevel);

		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);
		btn5 = (Button) findViewById(R.id.btn5);
		btn6 = (Button) findViewById(R.id.btn6);
		btn7 = (Button) findViewById(R.id.btn7);
		btn8 = (Button) findViewById(R.id.btn8);
		btn9 = (Button) findViewById(R.id.btn9);
		btnOk = (Button) findViewById(R.id.btnOk);

		numOfQuestions = 0;
		numOfRightAns = 0;

	}

	private int genRandomMaxNum() {
		Random r = new Random();
		return r.nextInt(randomMaxNumMax - randomMaxNumMin + 1)
				+ randomMaxNumMin;
	}

	private int genRandomNum() {
		Random r = new Random();
		return r.nextInt(9 - 1 + 1) + 1;
	}

	public class BackgroundTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			txtDipsNum.setText("");
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				Thread.sleep(1000);
				activeOkNum++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			String tmp = String.valueOf(genRandomNum());

			if (tmp_prev != tmp) {
				key += tmp;
				txtDipsNum.setText(tmp);
			}
			tmp_prev = tmp;

			if (activeOkNum == currentRMaxRandom) {
				btnOk.setEnabled(true);

			}

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

	}

	public class BackgroundTaskOkBtn extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				Thread.sleep(1000);
				activeOkNum++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			txtDipsNum.setText("");
			txtKey.setText("");
			eTextAns.setText("");
			activeOkNum = 0;
			tmp_prev = "0";
			currentAns = "";
			key = "";
			txtCorrectAns.setText("");

			generateRundom();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

	}

	private String reverseString(String s) {

		return new StringBuffer(s).reverse().toString();
	}

	private void generateRundom() {
		txtCurrentLevel.setText(String.valueOf(level_1_num) + " of 10");
		numofCorrectAns = 0;
		btnOk.setEnabled(false);
		currentRMaxRandom = genRandomMaxNum();
		numOfQuestions += currentRMaxRandom;
		for (int i = 0; i < currentRMaxRandom; i++) {

			new BackgroundTask().execute((Void) null);
			txtDipsNum.setText("");
		}

	}

}
