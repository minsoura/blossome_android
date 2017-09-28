package com.minsoura.findit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;



import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import android.view.inputmethod.EditorInfo;

import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;


/**
 * A login screen that offers login via email/password.
 */
public class logIn extends AppCompatActivity  {
    static String userEmail;

    public static   String userID="";
    public   static   String getuserEmail="";

    public  static   String userHeight="";
    public  static  String userAge="";
    public  static   String userJob="";


    static String userKey;

    static  String userLoginResult="";

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private TextView textViewRegister;
    Dialog activationDialog;

    Dialog verifyChoiceDialog;
    ImageView mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mainView =(ImageView) findViewById(R.id.mainView);
        try {
            mainView.setImageDrawable(getAssetImage(getApplicationContext(),"black_blossom"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        textViewRegister = (TextView) findViewById(R.id.textViewRegister);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        verifyChoiceDialog = new Dialog(this);
        verifyChoiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verifyChoiceDialog.setContentView(R.layout.dialog_verify_options);
        verifyChoiceDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView verifyByEmail = (TextView) verifyChoiceDialog.findViewById(R.id.verifyByEmail);
        final TextView verifyByStudentCard =(TextView) verifyChoiceDialog.findViewById(R.id.verifyByStudentcard);

        verifyByEmail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterByEmail();
                verifyChoiceDialog.dismiss();
            }
        });

        verifyByStudentCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterByStudentCard();
                verifyChoiceDialog.dismiss();

            }
        });

        textViewRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyChoiceDialog.show();



            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);



        activationDialog = new Dialog(this);
        activationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activationDialog.setContentView(R.layout.dialog_account_activation);
        activationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView cancelDialog =(TextView) activationDialog.findViewById(R.id.activationCancel);
        cancelDialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activationDialog.dismiss();
            }

        });
        TextView accountActivation =(TextView) activationDialog.findViewById(R.id.accountActivate);
        accountActivation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuthTask = new UserLoginTask(userEmail,"activation");
                mAuthTask.execute();

            }
        });
    }
    private  void openRegisterByEmail(){
        Intent intentRegister = new Intent(logIn.this, Registration.class);
        startActivity (intentRegister);


    }

    private  void openRegisterByStudentCard(){
        Intent intentRegister = new Intent(logIn.this, RegistrationByStudentCard.class);
        startActivity (intentRegister);


    }


    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".PNG")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }




    /**
     * Callback received when a permissions request has been completed.
     */



    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        userEmail =email;
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password,"normal");
            mAuthTask.execute();
        }
    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }





    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        String mPassword;
        String mFunctionVerifier;


        UserLoginTask(String email, String password, String FunctionVerifier) {
            mEmail = email;
            mPassword = password;
            mFunctionVerifier= FunctionVerifier;

        }

        UserLoginTask(String email, String FunctionVerifier) {
            mEmail = email;
            mFunctionVerifier= FunctionVerifier;

        }

        @Override
        protected String doInBackground(Void... params) {
            String URL = "";
            HashMap<String, String> logSet = new HashMap<>();
            if(mFunctionVerifier.equals("normal")){
                URL="http://minsoura.xyz/logIn.php";
                logSet.put("userEmail",mEmail);
                logSet.put("userPW",mPassword);
            }else if(mFunctionVerifier.equals("activation")){
                URL="http://minsoura.xyz/accountActivate.php";
                logSet.put("userEmail",mEmail);
            }




            try {
                requestHandler logSetHandler = new requestHandler();
                String result = logSetHandler.sendPostRequest(URL, logSet);

                return result;

            } catch (Exception e) {

                return null;
            }


        }

        @Override
        protected void onPostExecute(final String receivedLine) {
            super.onPostExecute(receivedLine);

            mAuthTask=null;
            showProgress(false);



            try{
                if(receivedLine.equals("girlsActivated")){


                    Intent intent = new Intent(logIn.this, MainActivity.class);
                intent.putExtra("userEmail",mEmail);
                intent.putExtra("userGender", "girls");
                    startActivity(intent);
                    finish();

            }else if(receivedLine.equals("boysActivated")){


                Intent intent = new Intent(logIn.this, MainActivity.class);
                intent.putExtra("userEmail",mEmail);
                intent.putExtra("userGender","boys");
                startActivity(intent);
                finish();
                }
                else if(receivedLine.equals("1")){
                Toast.makeText(getApplicationContext(),"서버와의 연결상태가 좋지 않습니다.",Toast.LENGTH_LONG).show();
                 }

                JSONArray jsonArray = new JSONArray(receivedLine);
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    userLoginResult = jsonObject.getString("loginResult");
                    if (userLoginResult.equals("yes_boys")) {

                        userKey = jsonObject.getString("userKey");
                        userID = jsonObject.getString("userID");
                        getuserEmail =jsonObject.getString("userEmail");

                        userHeight =jsonObject.getString("userHeight");
                        userAge =jsonObject.getString("userAge");
                        userJob =jsonObject.getString("userJob");


                        if(!userID.equals("")) {



                            try {
                                Integer userKeyInt = Integer.parseInt(userKey);
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                final SharedPreferences.Editor editor = pref.edit();

                                editor.putString("userEmail",getuserEmail);
                                editor.putString("userID", userID);
                                editor.putInt("userKey", userKeyInt);
                                editor.apply();



                            }catch (Exception e){
                                        e.printStackTrace();
                            }
                        }

                        Intent intent = new Intent(logIn.this, MainActivity.class);
                        intent.putExtra("userEmail",getuserEmail);
                        intent.putExtra("userGender","boys");
                        startActivity(intent);
                        finish();
                        //TODO: I could make a intent that delivers retrieved data from the DB such as the ones for the USER PROFILE;

                    }else if(userLoginResult.equals("boys_deactivated")){

                        activationDialog.show();
                        break;


                    } else if(userLoginResult.equals("boys_notReady")){
                        //TODO://
                        Toast.makeText(getApplicationContext(),"관리자의 승인을 기다려주세요 :)",Toast.LENGTH_LONG).show();
                        break;


                    }

                    else if(userLoginResult.equals("yes_girls")){

                        userKey = jsonObject.getString("userKey");
                        userID = jsonObject.getString("userID");
                        getuserEmail =jsonObject.getString("userEmail");

                        userHeight =jsonObject.getString("userHeight");
                        userAge =jsonObject.getString("userAge");
                        userJob =jsonObject.getString("userJob");





                        if(!userID.equals("")) {
                            try {
                                Integer userKeyInt = Integer.parseInt(userKey);


                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                final SharedPreferences.Editor editor = pref.edit();

                                editor.putString("userEmail",getuserEmail);
                                editor.putString("userID", userID);
                                editor.putInt("userKey", userKeyInt);

                                editor.apply();


                            }catch (Exception e){

                            }

                        }


                        Intent intent = new Intent(logIn.this, MainActivity.class);
                        intent.putExtra("userEmail",getuserEmail);
                        intent.putExtra("userGender", "girls");
                        startActivity(intent);
                        finish();
                    }else if(userLoginResult.equals("girls_deactivated")){
                        activationDialog.show();
                        break;


                    }else if(userLoginResult.equals("girls_notReady")){
                        //TODO:
                        Toast.makeText(getApplicationContext(),"관리자의 승인을 기다려주세요 :)",Toast.LENGTH_LONG).show();
                        break;

                    }
                    else if(userLoginResult.equals("noID")){

                        mEmailView.setError(getString(R.string.error_invalid_email));
                        mEmailView.requestFocus();
                        break;

                    }  else if(userLoginResult.equals("incorrectPW")){

                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                        break;

                    }else {
                        break;
                    }
                }
            }
            catch (Exception e){
                Log.d("tag",e.getMessage());
                e.printStackTrace();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
           showProgress(false);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("LogIn","Destroyed");
    }

}

