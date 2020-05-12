package com.demo.myblog.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demo.myblog.R;
import com.demo.myblog.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {
    private String ID, NAME, EMAIL, DATE_CREATED, appURL;
    Activity mContext = this;
    TextView mId, mName, mEmail, mDateCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mId = findViewById(R.id.txt_ID);
        mName = findViewById(R.id.txt_Name);
        mEmail = findViewById(R.id.txt_email);
        mDateCreated = findViewById(R.id.txt_DateCreated);
        appURL = "http://192.168.247.100/api/getUserDetails.php?email=" + EMAIL;
        Intent data = getIntent();
        EMAIL = data.getStringExtra("email");
    }

    public void getUserDetails(){
        if (EMAIL.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setMessage("Email cannot be empty");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, appURL , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        ID = jsonObject.getString("id");
                        NAME = jsonObject.getString("name");
                        EMAIL = jsonObject.getString("email");
                        DATE_CREATED = jsonObject.getString("created_date");

                        mId.setText(ID);
                        mName.setText(NAME);
                        mEmail.setText(EMAIL);
                        mDateCreated.setText(DATE_CREATED);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    /*if(response.equals("true")){
                        Intent intent = new Intent(mContext, UserProfile.class);
                        intent.putExtra("email", EMAIL);
                        startActivity(intent);
                    } else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                        alert.setMessage(response);
                        alert.setCancelable(false);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }*/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder alert;
                    NetworkResponse response = error.networkResponse;
                    if(response != null && response.data != null){
                        switch(response.statusCode){
                            case 400:
                                alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Error");
                                alert.setMessage(response.data.toString());
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                break;
                            case 404:
                                alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Error");
                                alert.setMessage(response.data.toString());
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                break;
                            case 403:
                                alert = new AlertDialog.Builder(mContext);
                                alert.setTitle("Error");
                                alert.setMessage(response.data.toString());
                                alert.setCancelable(false);
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                                break;
                        }
                    } else {
                        alert = new AlertDialog.Builder(mContext);
                        alert.setTitle("Error");
                        alert.setMessage("There was an erro. \n Please try again.");
                        alert.setCancelable(false);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert.show();
                    }
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Accept", "Application/json; charset=UTF-8");
                    return params;
                }

                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", EMAIL);
                    return params;
                }*/
            };
            VolleySingleton.getInstance().addRequestQueue(stringRequest);
        }
    }

}
