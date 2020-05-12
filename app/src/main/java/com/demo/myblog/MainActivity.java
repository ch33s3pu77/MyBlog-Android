package com.demo.myblog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demo.myblog.auth.SignUp;
import com.demo.myblog.volley.VolleySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String appURL, EMAIL, PASSWORD;
    MaterialEditText mEmail, mPassword;
    Button mButton;
    Activity mContext = this;
    TextView mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appURL = "http://192.168.247.100/api/login.php";
        mEmail = findViewById(R.id.text_Email);
        mPassword = findViewById(R.id.text_Password);
        mSignUp = findViewById(R.id.lb_SignUp);

        mSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        mButton = findViewById(R.id.btn_Login);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                login();
            }
        });
    }

    private void login() {
        EMAIL = mEmail.getText().toString();
        PASSWORD = mPassword.getText().toString();
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
        } else if (PASSWORD.isEmpty()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setMessage("Password cannot be empty");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else if (PASSWORD.length() < 8) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setMessage("Password must be at least 8 characters");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else if (PASSWORD.length() > 15) {
            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
            alert.setMessage("Password must be greater than 15 characters");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, appURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("true")){
                        Intent intent = new Intent(mContext,Dashboard.class);
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
                    }
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
                public Map<String, String> getHeaders() throws AuthFailureError{
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Accept", "Application/json; charset=UTF-8");
                    return params;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", EMAIL);
                    params.put("password", PASSWORD);
                    return params;
                }
            };
            VolleySingleton.getInstance().addRequestQueue(stringRequest);
        }
    }
}
