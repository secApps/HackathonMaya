package com.example.mahdi.hackathonmaya;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Registration extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
    EditText emails,password1,password2,location,age;
    Spinner user_gender,user_type;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    Button register;
    ProgressDialog pDialog;
    String user_email,pass1,pass2,Uger_age=null,Uger_gender=null,locations,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        emails=(EditText)findViewById(R.id.email_registration);
        password1=(EditText)findViewById(R.id.pass);
        password2=(EditText)findViewById(R.id.editText);
        age=(EditText)findViewById(R.id.age);
        location=(EditText)findViewById(R.id.location);
        user_gender=(Spinner)findViewById(R.id.malefemale);
        user_type=(Spinner)findViewById(R.id.usertype);
        register=(Button)findViewById(R.id.register);
        user_gender = (Spinner) findViewById(R.id.malefemale);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.malefemale, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        user_gender.setAdapter(adapter1);
        user_type = (Spinner) findViewById(R.id.usertype);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.usertype, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        user_type.setAdapter(adapter2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),user_email+pass1+Uger_age+Uger_gender_+location+type,Toast.LENGTH_SHORT).show();
                try{
                 user_email = emails.getText().toString();
                 pass1 = password1.getText().toString();
                 pass2 = password2.getText().toString();
                Uger_age = age.getText().toString();
                 //Uger_gender = user_gender.getSelectedItem().toString();
                 locations = location.getText().toString();
                // type = user_type.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(),user_email+pass1+Uger_age+Uger_gender+location+type,Toast.LENGTH_SHORT).show();
                new Register().execute();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
    class Register extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registration.this);
            pDialog.setMessage("Registering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            JSONParser jsonParser = new JSONParser();




            JSONObject json;
            int success = 0;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("tag", "register"));
                params.add(new BasicNameValuePair("email", user_email));
                params.add(new BasicNameValuePair("password", pass1));
                params.add(new BasicNameValuePair("age", Uger_age));
                params.add(new BasicNameValuePair("gender", Uger_gender));
                params.add(new BasicNameValuePair("type", type));
                params.add(new BasicNameValuePair("location", locations));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                json = jsonParser.makeHttpRequest("http://apimayaapa-env.elasticbeanstalk.com/index.php/login_controller/loginprocess",
                        "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt("success");

                if (success == 1) {

                   return Integer.toString(success);
                } else {

                    return Integer.toString(success);
                }

                // return null;
            } catch (Exception e) {
                // TODO: handle exception
            }
            return null;
        }

        protected void onPostExecute(String mesage) {
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), mesage, Toast.LENGTH_SHORT).show();

        }

    }
}
