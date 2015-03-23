package com.example.mahdi.hackathonmaya;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Feedback extends ActionBarActivity {
  EditText email_feed,text;
    String email_ids,texts;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);
        Button contact=(Button)findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();

                if (fm.findFragmentById(android.R.id.content) == null) {
                    ContactFragment list = new ContactFragment();
                    fm.beginTransaction().add(android.R.id.content, list).commit();
                }
            }
        });
        Button send=(Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_feed=(EditText)findViewById(R.id.feed_email);
                text=(EditText)findViewById(R.id.write_feed);
                email_ids = email_feed.getText().toString();
                texts = text.getText().toString();
                new sendFeedback().execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class sendFeedback extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Feedback.this);
            pDialog.setMessage("Sending...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            JSONParser jsonParser = new JSONParser();




            JSONObject json;


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                params.add(new BasicNameValuePair("uemail", email_ids));
                params.add(new BasicNameValuePair("feedback", texts));


                Log.d("request!", "starting");
                // getting product details by making HTTP request
                json = jsonParser.makeHttpRequest("http://apimayaapa-env.elasticbeanstalk.com/index.php/feedback_controller/sendfeedback",
                        "POST", params);



                // json success tag



                if (json.length() == 0) {

                    return "sent";
                } else {

                    return "something went wrong";
                }

                // return null;
            } catch (Exception e) {
                // TODO: handle exception
            }
            return null;
        }

        protected void onPostExecute(String mesage) {
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(),mesage,Toast.LENGTH_SHORT).show();

        }
}}
