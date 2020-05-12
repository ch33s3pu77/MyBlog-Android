package com.demo.myblog.auth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demo.myblog.Dashboard;
import com.demo.myblog.R;
import com.demo.myblog.volley.VolleySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private String appURL, NAME, EMAIL, PASSWORD, CONFIRM_PASSWORD;
    MaterialEditText mName, mEmail, mPassword, mConfirm_Password;
    CheckBox mTermsConditions;
    Button mSignUp;
    Activity mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        appURL = "http://192.168.247.100/api/login.php";
        mName = findViewById(R.id.text_Name);
        mEmail = findViewById(R.id.text_Email);
        mPassword = findViewById(R.id.text_Password);
        mConfirm_Password = findViewById(R.id.text_ConfirmPassword);
        mTermsConditions = findViewById(R.id.cbTermsConditions);
        mSignUp = findViewById(R.id.btn_SignUp);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                signUp();
            }
        });
    }

    private void signUp() {
            NAME = mName.getText().toString();
            EMAIL = mEmail.getText().toString();
            PASSWORD = mPassword.getText().toString();
            CONFIRM_PASSWORD = mConfirm_Password.getText().toString();
            if(NAME.isEmpty()){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("Name cannot be empty");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }else if(NAME.length() <=4){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("Name cannot be less than 4 characters");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            } else if(EMAIL.isEmpty()){
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
            }else if(PASSWORD.isEmpty()){
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
            } else if(PASSWORD.length() < 8){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("Password cannot be less than 8 characters");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            } else if(PASSWORD.length()  > 15){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("Password cannot be more than 15 characters");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            } else if (!PASSWORD.equals(CONFIRM_PASSWORD)){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("Passwords do not match");
                alert.setCancelable(false);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            } else if(!mTermsConditions.isChecked()){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("Please accept terms and conditions");
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
                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setTitle("Success");
                            alert.setMessage("You have successfully created and account \n you can now login");
                            alert.setCancelable(false);
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setTitle("Error");
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
                        NetworkResponse response = error.networkResponse;
                        AlertDialog.Builder alert;
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
                            alert.setTitle("There was an error \n Please try again");
                            alert.setMessage(response.data.toString());
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

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("name", NAME);
                        params.put("email", EMAIL);
                        params.put("password", PASSWORD);
                        return params;
                    }
                };
                VolleySingleton.getInstance().addRequestQueue(stringRequest);
            }

        }
    }

