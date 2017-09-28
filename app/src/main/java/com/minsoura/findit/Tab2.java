package com.minsoura.findit;

/**
 * Created by min on 2016-01-07.
 */
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Tab2 extends Fragment {


    ArrayList<String> dateList;

    static String genderIndex;




    static String[] DateGroupEmail;
    sendRatingTask sendRatingTask;
    static String saveAge,saveAgeType,saveRegion;


    static String deliveryController="yes";
   static ArrayList<ProfileStorage> profileStorages = new ArrayList<>();
    static String getUserEmail;
    static String getUserID;
   static String  getUserHeight="";
    static String getUserAge="";
    static String  getUserJob="";
    static  String getUserSayHi="";
    public static String getUserRegion = "";
    public static String getUserRegion2 = "";
    public static String getUserUniv = "";
    public static String getUserRating="";
    UserSelectionTask selectionTask;


    static Integer keyUniv,keyHeight,keyHot;

    downAgainProfile taskAgain;

    static String[] sendRating;

    public static  String sendAge,sendRegion,sendUniv,sendHeight,sendHot,sendAgeType,sendKey;

   private TableLayout container2;
   static String userGender;

    static String userEmail;
    downloadProfile task;
   static FABToolbarLayout fabToolbarLayout;
    ImageView getToday,getSpecial;

    TextView specialText,todayText;
    private View mProgressView;
    private View wrapper_View;

    static ArrayList<ProfileStorage> newhorizon;




    List<String> numberList;
    List<String> heightList;

    ArrayAdapter<String> regionAdapter;
    ArrayAdapter<String> ageAdapter;

    ArrayAdapter<String> heightAdapter;
    ArrayAdapter<String> univAdapter;

    static Integer saveKeyInt;

    Dialog dialog;


    CheckBox ageSame ;
    CheckBox ageAbove ;
    CheckBox ageBelow;

    Spinner regionSpinner ;
    Spinner ageSpinner ;

    CheckBox hotUser;

   TextView univText ;
   TextView heightText;
   TextView hotText;
    TextView finalText ;

    TextView cancelText;
    TextView goText;





     Spinner heightSpinner ;
    Spinner univSpinner ;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_2, container, false);

        //profileStorages.clear();
        String theKey = getResources().getString(R.string.theKey);
        dateList = new ArrayList<String>();

        DateGroupEmail = new String[2];
        DateGroupEmail[0] ="start";
        DateGroupEmail[1]= "start";


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        saveAge = sharedPreferences.getString("dateAge","20");
        saveAgeType = sharedPreferences.getString("dateAgeType", "normal");
        saveRegion = sharedPreferences.getString("dateRegion", "서울");
        saveKeyInt = sharedPreferences.getInt("userKey", 0);




        dialog= new Dialog(getContext());


        numberList = new ArrayList<>();
        for(int i=20; i<=60; i++){
            numberList.add(Integer.toString(i));

        }

        heightList = new ArrayList<>();
        heightList.add("NONE");
        for(int i=145; i<=200; i++){
            heightList.add(Integer.toString(i));

        }

        keyUniv=0;
        keyHeight=0;
        keyHot=0;




        fabToolbarLayout =(FABToolbarLayout) v.findViewById(R.id.fabtoolbar);
        fabToolbarLayout.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorMiddle));



        specialText =(TextView) v.findViewById(R.id.specialText);
        todayText =(TextView) v.findViewById(R.id.todayText);

        getToday =(ImageView) v.findViewById(R.id.getToday);

        getSpecial =(ImageView) v.findViewById(R.id.getSpecial);


        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_special);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);







        ageSame =(CheckBox) dialog.findViewById(R.id.ageSameCheck);
        ageAbove =(CheckBox) dialog.findViewById(R.id.ageAboveCheck);
        ageBelow =(CheckBox) dialog.findViewById(R.id.ageBelowCheck);
        hotUser =(CheckBox) dialog.findViewById(R.id.hotCheck);

        univText =(TextView) dialog.findViewById(R.id.univText);
        heightText =(TextView) dialog.findViewById(R.id.heightText);
        hotText =(TextView) dialog.findViewById(R.id.hotText);
        finalText =(TextView) dialog.findViewById(R.id.finalText);

        cancelText =(TextView) dialog.findViewById(R.id.cancelText);
        goText =(TextView) dialog.findViewById(R.id.goText);


        univSpinner =(Spinner) dialog.findViewById(R.id.univSpinner);
        regionSpinner = (Spinner) dialog.findViewById(R.id.regionSpinner);
        ageSpinner =(Spinner) dialog.findViewById(R.id.ageSpinner);
        heightSpinner =(Spinner) dialog.findViewById(R.id.heightSpinner);
        regionAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Region));
        ageAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,numberList);
        heightAdapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,heightList);
        univAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.university));

        regionSpinner.setAdapter(regionAdapter);
        ageSpinner.setAdapter(ageAdapter);

        univSpinner.setAdapter(univAdapter);
        univSpinner.setSelection(getIndex(univSpinner, "NONE"));
        heightSpinner.setSelection(getIndex(heightSpinner, "NONE"));
        sendHot = "NONE";

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




        ageSpinner.setSelection(getIndex(ageSpinner, saveAge));
        regionSpinner.setSelection(getIndex(regionSpinner, saveRegion));
        if(saveAgeType.equals("same")){
            ageSame.setChecked(true);

        }else if(saveAgeType.equals("above")){
            ageAbove.setChecked(true);
        }else if(saveAgeType.equals("below")){
            ageBelow.setChecked(true);

        }


        hotText.setText("X  " + Integer.toString(keyHot));
        univSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().toString().equals("NONE")) {

                    keyUniv = 5;
                    univText.setText("X  " + Integer.toString(keyUniv));
                    finalText.setText("총 키 " + Integer.toString(keyHot + keyUniv + keyHeight) + "개가 사용됩니다.");
                } else {

                    keyUniv = 0;
                    univText.setText("X  " + Integer.toString(keyUniv));
                    finalText.setText("총 키 " + Integer.toString(keyHot + keyUniv + keyHeight) + "개가 사용됩니다.");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        heightSpinner.setAdapter(heightAdapter);
        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getSelectedItem().toString().equals("NONE")) {

                    keyHeight = 2;
                    heightText.setText("X  " + Integer.toString(keyHeight));
                    finalText.setText("총 키 " + Integer.toString(keyHot + keyUniv + keyHeight) + "개가 사용됩니다.");
                } else {

                    keyHeight = 0;
                    heightText.setText("X  " + Integer.toString(keyHeight));
                    finalText.setText("총 키 " + Integer.toString(keyHot + keyUniv + keyHeight) + "개가 사용됩니다.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        hotUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    keyHot = 15;
                    hotText.setText("X  " + Integer.toString(keyHot));
                    finalText.setText("총 키 " + Integer.toString(keyHot + keyUniv + keyHeight) + "개가 사용됩니다.");
                } else {

                    keyHot = 0;
                    hotText.setText("X  " + Integer.toString(keyHot));
                    finalText.setText("총 키 " + Integer.toString(keyHot + keyUniv + keyHeight) + "개가 사용됩니다.");

                }
            }
        });

        ageSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ageBelow.setChecked(false);
                    ageAbove.setChecked(false);
                }
            }
        });

        ageBelow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ageSame.setChecked(false);
                    ageAbove.setChecked(false);
                }
            }
        });

        ageAbove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ageSame.setChecked(false);
                    ageBelow.setChecked(false);

                }
            }
        });




        FloatingActionButton floatingActionButton = (FloatingActionButton) v.findViewById(R.id.fabtoolbar_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDelivery();
                fabToolbarLayout.show();
                fabToolbarLayout.setActivated(true);

            }
        });
        floatingActionButton.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));

        getToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (deliveryController.equals("yes")) {
                    bringProfile();

                } else if (deliveryController.equals("onRestart")) {


                    //set a timer so that for a new day it will set profileStorages 0
                    deliveryController = "no";

                } else if (deliveryController.equals("no")) {

                    Snackbar.make(getView(), "오늘의 소개팅을 이미 받았습니다.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


                fabToolbarLayout.hide();
                fabToolbarLayout.setActivated(false);


            }
        });



        specialText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabToolbarLayout.hide();
                fabToolbarLayout.setActivated(false);



                goText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sendAge = ageSpinner.getSelectedItem().toString();
                        sendRegion = regionSpinner.getSelectedItem().toString();
                        sendUniv = univSpinner.getSelectedItem().toString();
                        sendHeight = heightSpinner.getSelectedItem().toString();


                        if (!sendUniv.equals("NONE")) {
                            keyUniv = 5;
                        }
                        if (!sendHeight.equals("NONE")) {
                            keyHeight = 2;
                        }


                        if (hotUser.isChecked()) {
                            sendHot = "yes";
                            keyHot = 15;

                        } else {
                            sendHot = "NONE";

                        }

                        if (ageSame.isChecked()) {
                            sendAgeType = "same";
                        } else if (ageAbove.isChecked()) {
                            sendAgeType = "above";
                        } else if (ageBelow.isChecked()) {
                            sendAgeType = "below";
                        } else {
                            sendAgeType = "normal";
                        }

                        sendKey = Integer.toString(keyHeight + keyUniv + keyHot);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        saveKeyInt = sharedPreferences.getInt("userKey", 0);
                        if (saveKeyInt - (keyHeight + keyUniv + keyHot) >= 0) {

                            if (deliveryController.equals("yes") && (!sendUniv.equals("NONE") || !sendHeight.equals("NONE") || !sendHot.equals("NONE"))) {
                                BringSpecialCards();
                                dialog.dismiss();

                            } else if (deliveryController.equals("onRestart")) {

                                deliveryController = "no";
                                //set a timer so that for a new day it will set profileStorages 0


                            } else if (deliveryController.equals("no")) {
                                Snackbar.make(getView(), "오늘의 소개팅을 이미 받았습니다.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            } else if (sendUniv.equals("NONE") && sendHeight.equals("NONE") && sendHot.equals("NONE")) {
                                finalText.setText("Nothing Set");
                            }
                        } else {
                            Toast.makeText(getActivity(), "키가 부족합니다", Toast.LENGTH_LONG).show();
                        }


                    }
                });



                dialog.show();


            }
        });

        getSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fabToolbarLayout.hide();
                fabToolbarLayout.setActivated(false);

                goText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sendAge = ageSpinner.getSelectedItem().toString();
                        sendRegion = regionSpinner.getSelectedItem().toString();
                        sendUniv = univSpinner.getSelectedItem().toString();
                        sendHeight = heightSpinner.getSelectedItem().toString();


                        if (!sendUniv.equals("NONE")) {
                            keyUniv = 5;
                        }
                        if (!sendHeight.equals("NONE")) {
                            keyHeight = 2;
                        }


                        if (hotUser.isChecked()) {
                            sendHot = "yes";
                            keyHot = 15;

                        } else {
                            sendHot = "NONE";

                        }

                        if (ageSame.isChecked()) {
                            sendAgeType = "same";
                        } else if (ageAbove.isChecked()) {
                            sendAgeType = "above";
                        } else if (ageBelow.isChecked()) {
                            sendAgeType = "below";
                        } else {
                            sendAgeType = "normal";
                        }

                        sendKey = Integer.toString(keyHeight + keyUniv + keyHot);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        saveKeyInt = sharedPreferences.getInt("userKey", 0);
                        if (saveKeyInt - (keyHeight + keyUniv + keyHot) >= 0) {

                            if (deliveryController.equals("yes") && (!sendUniv.equals("NONE") || !sendHeight.equals("NONE") || !sendHot.equals("NONE"))) {
                                BringSpecialCards();
                                dialog.dismiss();

                            } else if (deliveryController.equals("onRestart")) {


                                //set a timer so that for a new day it will set profileStorages 0
                                deliveryController = "no";

                            } else if (deliveryController.equals("no")) {
                                Snackbar.make(getView(), "오늘의 소개팅을 이미 받았습니다.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            } else if (sendUniv.equals("NONE") && sendHeight.equals("NONE") && sendHot.equals("NONE")) {
                                finalText.setText("설정된 조건이 없습니다.");
                            }
                        } else {
                            Toast.makeText(getActivity(), "키가 부족합니다", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                dialog.show();

            }
        });


        todayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (deliveryController.equals("yes")) {
                    bringProfile();

                } else if (deliveryController.equals("onRestart")) {


                    //set a timer so that for a new day it will set profileStorages 0
                    deliveryController ="no";

                } else if (deliveryController.equals("no")) {
                    Snackbar.make(getView(), "오늘의 소개팅을 이미 받았습니다.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }


                fabToolbarLayout.hide();
                fabToolbarLayout.setActivated(false);

            }
        });
        container2 = (TableLayout) v.findViewById(R.id.DateCardContainer);
        newhorizon = new ArrayList<ProfileStorage>();

        userEmail = getActivity().getIntent().getStringExtra("userEmail");
        userGender =getActivity().getIntent().getStringExtra("userGender");


        LayoutInflater theFirstInflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View waitingScreen = theFirstInflator.inflate(R.layout.waiting_screen,null);

        ImageView theScreenSaver = (ImageView) waitingScreen.findViewById(R.id.theScreenSaver);
        try {
            theScreenSaver.setImageDrawable(getAssetImage(getContext(),"waiting"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        container2.addView(waitingScreen);


        mProgressView = v.findViewById(R.id.tab2_progress);
        wrapper_View = v.findViewById(R.id.tab2_wrapper);









                bringToday();







        return v;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();



        if(task !=null) {
            task.taskChecker = false;
            task.cancel(true);
        }

        if(selectionTask !=null){
            selectionTask.selectionTaskChecker = false;
            selectionTask.cancel(true);
        }
        if(sendRatingTask !=null){
            sendRatingTask.cancel(true);

        }
        if(taskAgain !=null){
            taskAgain.taskAgainChecker = false;
            taskAgain.cancel(true);
        }
        System.gc();
        container2.removeAllViews();

    }



/////////////
    public void checkDelivery(){


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date dateNow = new Date();
        String dateStringNow = formatter.format(dateNow);
        Integer dateIntNow = Integer.valueOf(dateStringNow);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String dateStringSaved = sharedPreferences.getString("dateSaved", "100");
        Integer dateIntSaved = Integer.valueOf(dateStringSaved);
        Integer deliveryChecker = dateIntNow - dateIntSaved;

        if(deliveryChecker ==0){
            deliveryController="no";
        } else {
            deliveryController="yes";

        }
    }




    /////////////



    public class sendRatingTask extends AsyncTask<Void, Void, String> {


         String mUserEmail;
         String mUserBeauty;
         String mDateEmail;


        sendRatingTask( String dateEmail, String UserBeauty,String UserEmail) {


            mUserBeauty = UserBeauty;
            mDateEmail = dateEmail;
            mUserEmail = UserEmail;

        }

        @Override
        protected void onCancelled(){
            sendRatingTask =null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected String doInBackground(Void... params) {

            String URL="";

            if(userGender.equals("boys")) {

                URL = "http://minsoura.xyz/sendRatingTaskBoys.php";

            }else if(userGender.equals("girls")){

                URL = "http://minsoura.xyz/sendRatingTaskGirls.php";

            }


            HashMap<String, String> SendSet = new HashMap<>();

            SendSet.put("dateEmail", mDateEmail);
            SendSet.put("userBeauty",mUserBeauty);
            SendSet.put("userEmail",mUserEmail);






            try {
                requestHandler LikeDeliverHandler = new requestHandler();
                String result = LikeDeliverHandler.sendPostRequest(URL, SendSet);

                return result;

            } catch (Exception e) {

                return null;
            }


        }

        @Override
        protected void onPostExecute(final String receivedLine) {
            super.onPostExecute(receivedLine);
            if(isCancelled()){
                sendRatingTask =null;
            }

            showProgress(false);
            if (receivedLine == null ||receivedLine.equals("Error")) {

                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                //TODO: I could make a intent that delivers retrieved data from the DB such as the ones for the USER PROFILE;

            } else if (receivedLine.equals("ratingRecorded")) {
                Toast.makeText(getActivity(),"키 5개가 추가됩니다.",Toast.LENGTH_LONG).show();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                 saveKeyInt = sharedPreferences.getInt("userKey",0);

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                final SharedPreferences.Editor editor = pref.edit();
                 editor.putInt("userKey", saveKeyInt+5);
                editor.apply();

                Tab3.myKey.setText(Integer.toString(saveKeyInt +5));
                ///nothing happes -->no key reduction
            }else{
                Toast.makeText(getActivity(),receivedLine,Toast.LENGTH_LONG).show();



            }


        }
    }





















    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }




    private void BringCardsInflator(final int index){




        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addedCardsRow = inflater.inflate(R.layout.card,null);
        RatingBar ratingBar = (RatingBar) addedCardsRow.findViewById(R.id.ratingBar);

        ratingBar.setFocusable(false);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                sendRating[index] = Float.toString(rating);
                ratingBar.setIsIndicator(true);
               // ratingBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorAccent)));

                sendRatingTask = new sendRatingTask(profileStorages.get(index).getUserEmail(),sendRating[index],userEmail);
                sendRatingTask.execute();



            }
        });

        TextView cardId =(TextView) addedCardsRow.findViewById(R.id.cardID);
        cardId.setText(profileStorages.get(index).getUserID());

        TextView cardAge =(TextView) addedCardsRow.findViewById(R.id.cardAge);
        cardAge.setText(profileStorages.get(index).getUserAge());

        TextView carHeight =(TextView) addedCardsRow.findViewById(R.id.cardHeight);
        carHeight.setText(profileStorages.get(index).getUserHeight()+"CM");

        TextView cardJob =(TextView) addedCardsRow.findViewById(R.id.cardJob);
        cardJob.setText(profileStorages.get(index).getUserJob());

        TextView cardUniv =(TextView) addedCardsRow.findViewById(R.id.cardUniv);
        cardUniv.setText(profileStorages.get(index).getUserUniversity());

        TextView cardRegion =(TextView) addedCardsRow.findViewById(R.id.cardRegion);
        cardRegion.setText(profileStorages.get(index).getUserRegion()
        +" "+ profileStorages.get(index).getUserRegion2()        );





        final ImageView cardPicMain = (ImageView) addedCardsRow.findViewById(R.id.CardMainPic);

        if(!profileStorages.get(index).getUserEmail().equals("")){



            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            if(userGender.equals("boys")){
                genderIndex = "girls";
            }else if(userGender.equals("girls")){
                genderIndex ="boys";
            }

            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
            Picasso.with(getContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(cardPicMain, new Callback() {
                        @Override
                        public void onSuccess() {


                        }

                        @Override
                        public void onError() {


                            Random r = new Random();
                            int loaderIndex = r.nextInt(10 - 1) + 1;
                            String stringIndex = Integer.toString(loaderIndex);

                            if (userGender.equals("boys")) {
                                genderIndex = "girls";
                            } else if (userGender.equals("girls")) {
                                genderIndex = "boys";
                            }
                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
                            Picasso.with(getContext())
                                    .load(URL)
                                    .fit()
                                    .centerInside()
                                    .transform(new CircleTransformation())
                                    .into(cardPicMain, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Random r = new Random();
                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                            String stringIndex = Integer.toString(loaderIndex);

                                            if (userGender.equals("boys")) {
                                                genderIndex = "girls";
                                            } else if (userGender.equals("girls")) {
                                                genderIndex = "boys";
                                            }
                                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
                                            Picasso.with(getContext())
                                                    .load(URL)
                                                    .fit()
                                                    .centerInside()
                                                    .transform(new CircleTransformation())
                                                    .into(cardPicMain, new Callback() {
                                                        @Override
                                                        public void onSuccess() {

                                                        }

                                                        @Override
                                                        public void onError() {
                                                            Random r = new Random();
                                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                                            String stringIndex = Integer.toString(loaderIndex);
                                                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
                                                            if (userGender.equals("boys")) {
                                                                genderIndex = "girls";
                                                            } else if (userGender.equals("girls")) {
                                                                genderIndex = "boys";
                                                            }
                                                            Picasso.with(getContext())
                                                                    .load(URL)
                                                                    .fit()
                                                                    .centerInside()
                                                                    .transform(new CircleTransformation())
                                                                    .into(cardPicMain);

                                                        }
                                                    });
                                        }
                                    });
                        }
                    });

        } cardPicMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), view_dateProfile.class);
                intent.putExtra("DateEmail", profileStorages.get(index).getUserEmail());
                intent.putExtra("UserGender", userGender);
                intent.putExtra("UserEmail", userEmail);

                String size = Integer.toString(profileStorages.size());
                intent.putExtra("size", size);
                String listIndex = Integer.toString(index);
                intent.putExtra("listIndex", listIndex);
                intent.putExtra("userID", profileStorages.get(index).getUserID());
                intent.putExtra("userRegion", profileStorages.get(index).getUserRegion());
                intent.putExtra("userRegion2", profileStorages.get(index).getUserRegion2());
                intent.putExtra("userUniversity", profileStorages.get(index).getUserUniversity());
                startActivity(intent);
            }
        });




        container2.addView(addedCardsRow);


    }


    private void BringTodayInflator(final int index){




        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View addedCardsRow = inflater.inflate(R.layout.card,null);
        RatingBar ratingBar = (RatingBar) addedCardsRow.findViewById(R.id.ratingBar);

        ratingBar.setFocusable(false);
        if(profileStorages.get(index).getUserRating().equals("0")){

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    sendRating[index] = Float.toString(rating);
                    ratingBar.setIsIndicator(true);
                   // ratingBar.setProgressTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorAccent)));

                    sendRatingTask = new sendRatingTask(profileStorages.get(index).getUserEmail(),sendRating[index],userEmail);
                    sendRatingTask.execute();



                }
            });




        }else{
            Float ratingFloat = Float.parseFloat(profileStorages.get(index).getUserRating()) ;
            ratingBar.setRating(ratingFloat);
            ratingBar.setIsIndicator(true);
        }




        TextView cardId =(TextView) addedCardsRow.findViewById(R.id.cardID);
        cardId.setText(profileStorages.get(index).getUserID());

        TextView cardAge =(TextView) addedCardsRow.findViewById(R.id.cardAge);
        cardAge.setText(profileStorages.get(index).getUserAge());

        TextView carHeight =(TextView) addedCardsRow.findViewById(R.id.cardHeight);
        carHeight.setText(profileStorages.get(index).getUserHeight()+"CM");

        TextView cardJob =(TextView) addedCardsRow.findViewById(R.id.cardJob);
        cardJob.setText(profileStorages.get(index).getUserJob());

        TextView cardUniv =(TextView) addedCardsRow.findViewById(R.id.cardUniv);
        cardUniv.setText(profileStorages.get(index).getUserUniversity());

        TextView cardRegion =(TextView) addedCardsRow.findViewById(R.id.cardRegion);
        cardRegion.setText(profileStorages.get(index).getUserRegion()
                +" "+ profileStorages.get(index).getUserRegion2()        );





        final ImageView cardPicMain = (ImageView) addedCardsRow.findViewById(R.id.CardMainPic);

        if(!profileStorages.get(index).getUserEmail().equals("")){



            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            if(userGender.equals("boys")){
                genderIndex = "girls";
            }else if(userGender.equals("girls")){
                genderIndex ="boys";
            }
            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;

            Picasso.with(getContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(cardPicMain, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Random r = new Random();
                            int loaderIndex = r.nextInt(10 - 1) + 1;
                            String stringIndex = Integer.toString(loaderIndex);

                            if(userGender.equals("boys")){
                                genderIndex = "girls";
                            }else if(userGender.equals("girls")){
                                genderIndex ="boys";
                            }
                            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
                            Picasso.with(getContext())
                                    .load(URL)
                                    .fit()
                                    .centerInside()
                                    .transform(new CircleTransformation())
                                    .into(cardPicMain, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Random r = new Random();
                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                            String stringIndex = Integer.toString(loaderIndex);

                                            if(userGender.equals("boys")){
                                                genderIndex = "girls";
                                            }else if(userGender.equals("girls")){
                                                genderIndex ="boys";
                                            }
                                            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
                                            Picasso.with(getContext())
                                                    .load(URL)
                                                    .fit()
                                                    .centerInside()
                                                    .transform(new CircleTransformation())
                                                    .into(cardPicMain, new Callback() {
                                                        @Override
                                                        public void onSuccess() {

                                                        }

                                                        @Override
                                                        public void onError() {

                                                            Random r = new Random();
                                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                                            String stringIndex = Integer.toString(loaderIndex);

                                                            if(userGender.equals("boys")){
                                                                genderIndex = "girls";
                                                            }else if(userGender.equals("girls")){
                                                                genderIndex ="boys";
                                                            }
                                                            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + profileStorages.get(index).getUserEmail() + "&userGender=" + genderIndex;
                                                            Picasso.with(getContext())
                                                                    .load(URL)
                                                                    .fit()
                                                                    .centerInside()
                                                                    .transform(new CircleTransformation())
                                                                    .into(cardPicMain);

                                                        }
                                                    });
                                        }
                                    });
                        }
                    });

        } cardPicMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), view_dateProfile.class);
                intent.putExtra("DateEmail", profileStorages.get(index).getUserEmail());
                intent.putExtra("UserGender", userGender);
                intent.putExtra("UserEmail", userEmail);

                String size = Integer.toString(profileStorages.size());
                intent.putExtra("size", size);
                String listIndex = Integer.toString(index);
                intent.putExtra("listIndex", listIndex);
                intent.putExtra("userID", profileStorages.get(index).getUserID());
                intent.putExtra("userRegion", profileStorages.get(index).getUserRegion());
                intent.putExtra("userRegion2", profileStorages.get(index).getUserRegion2());
                intent.putExtra("userUniversity", profileStorages.get(index).getUserUniversity());
                startActivity(intent);
            }
        });




        container2.addView(addedCardsRow);


    }













    public void bringProfile(){
        //retrieve an intent with the string of userEmail for query
    //Age, AgeType, Region
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
       SharedPreferences.Editor editor = pref.edit();

        editor.putString(DateGroupEmail[0],"yes");
        editor.putString(DateGroupEmail[1],"yes");

        editor.apply();


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        saveAge = sharedPreferences.getString("dateAge","20");
        saveAgeType = sharedPreferences.getString("dateAgeType","normal");
        saveRegion = sharedPreferences.getString("dateRegion","서울");


        task = new downloadProfile(saveAge,saveAgeType,saveRegion);
        task.execute(userEmail);
    }

    public class downloadProfile extends AsyncTask<String,Void,String> {

        String age,ageType,region;
        Boolean taskChecker = true;
        downloadProfile(String age,String ageType,String region){

            this.age =age;
            this.ageType = ageType;
            this.region = region;

        }
        @Override
        protected void onCancelled(){
            task = null;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            showProgress(true);

        }

        @Override
        protected String doInBackground(String...userEmail){
            String URL ="";
            if(userGender.equals("boys")) {
                //bring girl's profile
                //need a new php code which reflects user's preference
                URL = "http://minsoura.xyz/GetCardsForBoys.php";
            }else if(userGender.equals("girls")){
                //bring boy's profile
                URL = "http://minsoura.xyz/GetCardsForGirls.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("userEmail", userEmail[0]);
            emailSet.put("cardAge",age);
            emailSet.put("cardAgeType",ageType);
            emailSet.put("cardRegion",region);

            try{
                if(!taskChecker){
                    return null;

                }
                requestHandler profileHandler = new requestHandler();
                String result = profileHandler.bringUserProfile(URL, emailSet);
                if(!taskChecker){
                    return null;

                }
                return result;

            }catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }

        }

        @Override
        public void onPostExecute(String s){
            super.onPostExecute(s);
            if(isCancelled()){
                task =null;
            }

            if(s.equals("noProfile")){

                Toast.makeText(getContext(), "HTTP_NO_OK", Toast.LENGTH_LONG).show();
            } else if(s.trim().equals("noSuchPerson")){
                Toast.makeText(getContext(),"준비된 소개팅이 없습니다. 나이와 지역 설정을 바꿔보세요.",  Toast.LENGTH_LONG).show();
                deliveryController ="yes";

            }

            else{



                    try{


                    JSONArray jsonArray = new JSONArray(s);
                    for (int i=0; i<jsonArray.length(); i++){


                        JSONObject jsonObject = jsonArray.getJSONObject(i);



                        getUserID = jsonObject.getString("userID");
                        getUserHeight =jsonObject.getString("userHeight");
                        getUserEmail = jsonObject.getString("userEmail");
                        getUserAge =jsonObject.getString("userAge");
                        getUserJob =jsonObject.getString("userJob");

                        getUserSayHi =jsonObject.getString("userSayHi");
                        getUserRegion  =jsonObject.getString("userRegion");
                        getUserRegion2 =jsonObject.getString("userRegion2");
                        getUserUniv  =jsonObject.getString("userUniversity");




                        if(getUserID !=null) {
                            profileStorages.add(new ProfileStorage(getUserID, getUserHeight, getUserJob, getUserAge, getUserEmail, getUserRegion, getUserRegion2, getUserUniv, getUserSayHi));
                        }
                    }
                }
                catch (Exception e){
                    Log.d("tag", e.getMessage());
                    e.printStackTrace();
                }





                if(profileStorages.size() !=0 && taskChecker) {
                    container2.removeAllViews();
                    for (int i = 0; i < profileStorages.size(); i++) {
                        BringCardsInflator(i);
                       DateGroupEmail[i]= profileStorages.get(i).getUserEmail();
                    }

                    deliveryController = "no";
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                    Date dateSaved = new Date();
                    String dateStringSaved = formatter.format(dateSaved);

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    final SharedPreferences.Editor editor = pref.edit();
                    editor.putString("dateSaved", dateStringSaved);


                    editor.apply();
                    sendRating = new String[profileStorages.size()];

                }else
                {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                    deliveryController = "yes";

                }




            }
            showProgress(false);
            taskChecker = false;
        }



    }

    public void bringToday(){


            taskAgain = new downAgainProfile();
            taskAgain.execute(userEmail);

    }

    public class downAgainProfile extends AsyncTask<String,Void,String> {


        Boolean taskAgainChecker = true;
        @Override
        protected void onCancelled(){
            taskAgain =null;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            showProgress(true);


        }

        @Override
        protected String doInBackground(String...userEmail){
            String URL ="";
            if(userGender.equals("boys")) {
                //bring girl's profile
                //need a new php code which reflects user's preference
                URL = "http://minsoura.xyz/getCardsAgainBoys.php";
            }else if(userGender.equals("girls")){
                //bring boy's profile
                URL = "http://minsoura.xyz/getCardsAgainGirls.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("userEmail", userEmail[0]);


            try{
                if(!taskAgainChecker){
                    return null;
                }
                requestHandler profileHandler = new requestHandler();
                String result = profileHandler.bringUserProfile(URL, emailSet);
                if(!taskAgainChecker){
                    return null;
                }
                return result;

            }catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }

        }
        @Override
        public void onPostExecute(String s){
            super.onPostExecute(s);

            if(isCancelled()){
                taskAgain =null;
            }
            profileStorages.clear();



            if(s.equals("noProfile")){

                Toast.makeText(getContext(), "HTTP_NO_OK", Toast.LENGTH_LONG).show();
            } else if(s.equals("newDating")){
                Toast.makeText(getActivity(), "오늘의 데이팅이 기다립니다.", Toast.LENGTH_SHORT).show();
                deliveryController = "yes";

            }

            else{



                try{


                    JSONArray jsonArray = new JSONArray(s);
                    for (int i=0; i<jsonArray.length(); i++){


                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        getUserID = jsonObject.getString("userID");
                        getUserHeight =jsonObject.getString("userHeight");
                        getUserEmail = jsonObject.getString("userEmail");
                        getUserAge =jsonObject.getString("userAge");
                        getUserJob =jsonObject.getString("userJob");

                        getUserSayHi =jsonObject.getString("userSayHi");
                        getUserRegion  =jsonObject.getString("userRegion");
                        getUserRegion2 =jsonObject.getString("userRegion2");
                        getUserUniv  =jsonObject.getString("userUniversity");
                        getUserRating = jsonObject.getString("userRating");


                        if(getUserID !=null) {

                            profileStorages.add(new ProfileStorage(getUserID, getUserHeight, getUserJob, getUserAge, getUserEmail, getUserRegion, getUserRegion2, getUserUniv, getUserSayHi,getUserRating));
                        }
                    }
                }
                catch (Exception e){
                    Log.d("tag", e.getMessage());
                    e.printStackTrace();
                }


                if(profileStorages.size() > 0 && taskAgainChecker) {
                    container2.removeAllViews();
                    for (int i = 0; i < profileStorages.size(); i++) {
                        BringTodayInflator(i);

                    }
                    deliveryController = "no";

                    sendRating = new String[profileStorages.size()];
                    //key reduction

                }else
                {
                    Toast.makeText(getActivity(), "오늘의 데이팅이 기다립니다.", Toast.LENGTH_SHORT).show();
                   deliveryController = "yes";

                }
            }
            showProgress(false);
            taskAgainChecker = false;
        }



    }





    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
         void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            wrapper_View.setVisibility(show ? View.GONE : View.VISIBLE);
            wrapper_View.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    wrapper_View.setVisibility(show ? View.GONE : View.VISIBLE);
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
            wrapper_View.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }






    public void BringSpecialCards(){

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(DateGroupEmail[0],"yes");
        editor.putString(DateGroupEmail[1],"yes");

        editor.apply();


        selectionTask = new UserSelectionTask(userEmail);
        selectionTask.execute();


    }




    public class UserSelectionTask extends AsyncTask<Void, Void, String> {


        private final String mUserEmail;
        Boolean selectionTaskChecker = true;

        UserSelectionTask( String UserEmail) {

            mUserEmail = UserEmail;



        }

        @Override
        protected void onCancelled(){
            selectionTask =null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected String doInBackground(Void... params) {

            String URL="";

            if(userGender.equals("boys")) {

                URL = "http://minsoura.xyz/BringSpecialCardsForBoys.php";

            }else if(userGender.equals("girls")){

                URL = "http://minsoura.xyz/BringSpecialCardsForGirls.php";

            }


            HashMap<String, String> SendSet = new HashMap<>();

            SendSet.put("userEmail", mUserEmail);
            SendSet.put("cardAge",sendAge);
            SendSet.put("cardAgeType",sendAgeType);
            SendSet.put("cardRegion",sendRegion);
            SendSet.put("cardUniv",sendUniv);
            SendSet.put("cardHeight", sendHeight);
            SendSet.put("cardHot", sendHot);





            try {
                if(!selectionTaskChecker){
                    return null;
                }
                requestHandler LikeDeliverHandler = new requestHandler();
                String result = LikeDeliverHandler.sendPostRequest(URL, SendSet);
                if(!selectionTaskChecker){
                    return null;
                }
                return result;

            } catch (Exception e) {

                return null;
            }


        }

        @Override
        protected void onPostExecute(final String receivedLine) {
            super.onPostExecute(receivedLine);
            if(isCancelled()){
                selectionTask =null;
            }

            showProgress(false);
            if (receivedLine == null ||receivedLine.equals("Error")) {

                Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();
                //TODO: I could make a intent that delivers retrieved data from the DB such as the ones for the USER PROFILE;

            } else if (receivedLine.equals("noSuchPerson")) {
                        Toast.makeText(getContext(),"검색된 사람이 없습니다",Toast.LENGTH_LONG).show();
                        ///nothing happes -->no key reduction
            }else{


                try{


                    JSONArray jsonArray = new JSONArray(receivedLine);
                    for (int i=0; i<jsonArray.length(); i++){



                        JSONObject jsonObject = jsonArray.getJSONObject(i);



                        getUserID = jsonObject.getString("userID");
                        getUserHeight =jsonObject.getString("userHeight");
                        getUserEmail = jsonObject.getString("userEmail");
                        getUserAge =jsonObject.getString("userAge");
                        getUserJob =jsonObject.getString("userJob");

                        getUserSayHi =jsonObject.getString("userSayHi");
                        getUserRegion  =jsonObject.getString("userRegion");
                        getUserRegion2 =jsonObject.getString("userRegion2");
                        getUserUniv  =jsonObject.getString("userUniversity");



                        if(getUserID !=null) {
                            profileStorages.add(new ProfileStorage(getUserID, getUserHeight, getUserJob, getUserAge, getUserEmail, getUserRegion, getUserRegion2, getUserUniv, getUserSayHi));
                        }
                    }
                }
                catch (Exception e){
                    Log.d("tag", e.getMessage());
                    e.printStackTrace();
                }



                if(profileStorages.size() !=0 && selectionTaskChecker) {
                    container2.removeAllViews();
                    for (int i = 0; i < profileStorages.size(); i++) {
                        BringCardsInflator(i);

                    }
                    deliveryController = "no";

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                    Date dateSaved = new Date();
                    String dateStringSaved = formatter.format(dateSaved);

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    final SharedPreferences.Editor editor = pref.edit();
                    editor.putString("dateSaved", dateStringSaved);
                    editor.apply();



                    sendRating = new String[profileStorages.size()];
                    //key reduction

                }else{
                    Toast.makeText(getActivity(),receivedLine, Toast.LENGTH_SHORT).show();
                    //  Snackbar.make(getView(), "No Such Person Found", Snackbar.LENGTH_LONG)
                    //    .setAction("Action", null).show();
                    deliveryController = "yes";
                }









            }
            selectionTaskChecker = false;

        }
    }


    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".png")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

}




















