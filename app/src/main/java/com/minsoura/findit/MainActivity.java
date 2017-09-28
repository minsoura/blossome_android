package com.minsoura.findit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import android.provider.MediaStore;

import android.support.design.widget.NavigationView;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static Boolean inAppProcessChecker2 = false;
    private static final String TAG = "test";

    myKeyAdapter theAdapter;
    myNotiAdapter NotiAdatper;
    ArrayList<keyData> theList;
    ArrayList<notiData> NotiList;
    Menu theMenu;
    Uri updateUri;



    sendEmailTask SendEmail;
    AccountTask accountTask;
    downloadNotification notiTask;

    static String keyValue;
    static  String keyDesc;
    static String keyDate;
    static String keyAccum;



    keyHistory keyHistoryTask;
    Dialog dialog;
    Dialog dialogEmail;
    static Dialog keyDialog;
    Dialog keySettingDialog;
    Dialog updateDialog;
    TextView updateCancel;
    TextView updateGo;

    Dialog accountRemovalDialog;
    TextView accountRemoveText;
    TextView accountCancelText;

    Dialog accountHoldingDialog;
    TextView holdingYesText;
    TextView holdingCancelText;

    Dialog keyHistoryDialog;
    TextView historyCloseText;
    ListView keyUsageList;

    Dialog notificationDialog;
    TextView notiCloseText;
    ListView notificationList;

    Dialog manualDialog;
    TextView manualLater;
    TextView manualShow;

    Switch checkHolding;

    Switch removeGo;
    Switch keyHistoryGo;
    TextView keySettingCancel;

    TextView myKeyVal;
    AlertDialog noticeDialog;


    static Integer saveKeyInt;





    CheckBox ageSame ;
    CheckBox ageAbove ;
    CheckBox ageBelow;

    Spinner regionSpinner ;
    Spinner ageSpinner ;

    TextView cancelText;
    TextView saveText;


    List<String> numberList;
    ArrayAdapter<String> regionAdapter;
    ArrayAdapter<String> ageAdapter;

    static String saveAge,saveRegion,saveAgeType;

    downloadProfile task;
    static Bitmap bitmap;
    ImageView navi_userPic;

    TextView navi_userEmail;

    TextView navi_logOut;


    EditText emailMessage;
    EditText emailTitle;
    TextView emailSend;
    static String messageString,titleString;

    View view;
    static String userEmailIntent;
    static  String userGender;



    List<String> keyList;
    ViewPager pager;
    ViewPagerAdapter adapter;

    SlidingTabLayout tabs;
    CharSequence Titles[]={"데이트", "메세지/인연"};
    int Numboftabs =2;

    static String userNotification="";
    static String userNotiCode="";
    static String notificationVerifier="";
    static String userNotiTitle="";
    static String userNotiDate="";
    static String userNotiCodeUnchecked="";



    static String previousNotiCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = new View(getApplicationContext());
        Intent intent = getIntent();
        userEmailIntent = intent.getStringExtra("userEmail");
        userGender = intent.getStringExtra("userGender");
        String makeActionVerifier = "normal";
               if(intent.getStringExtra("makeAction") != null){
                   makeActionVerifier = intent.getStringExtra("makeAction");
               }



        //TODO: check if the user wants to get the notification or not//make a variable to set it on or off and see if it is on or not before setting the notification

        //NotificationEventReceiver.setupAlarm(getApplicationContext());

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();

        //TODO:remove it for release version


        //TODO: I will have to add to the user configuration where notification can be turned on or off
        //TODO: for now it's set about 30 seconds but I really should set it like about 1 hour or so to see if there is a new message

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        navi_userEmail =(TextView) headerView.findViewById(R.id.navi_userEmail);
        navi_userEmail.setText(userEmailIntent);

        navi_userPic = (ImageView) headerView.findViewById( R.id.navi_userPic);
        navi_logOut =(TextView) headerView.findViewById(R.id.logOut);
        navi_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: do something...

            if( inAppProcessChecker2 ==false) {
                finish();
            }else{

                Log.e("checker2",Boolean.toString(inAppProcessChecker2));

                Toast.makeText(MainActivity.this,"잠시만 기다려주십시오.",Toast.LENGTH_LONG).show();
            }

            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        if(!userEmailIntent.equals("no")){

            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + userEmailIntent + "&userGender=" + userGender;

            Picasso.with(getApplicationContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(navi_userPic, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Random r = new Random();
                            int loaderIndex = r.nextInt(10 - 1) + 1;
                            String stringIndex = Integer.toString(loaderIndex);

                            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + userEmailIntent + "&userGender=" + userGender;

                            Picasso.with(getApplicationContext())
                                    .load(URL)
                                    .fit()
                                    .centerInside()
                                    .transform(new CircleTransformation())
                                    .into(navi_userPic, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Random r = new Random();
                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                            String stringIndex = Integer.toString(loaderIndex);

                                            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + userEmailIntent + "&userGender=" + userGender;

                                            Picasso.with(getApplicationContext())
                                                    .load(URL)
                                                    .fit()
                                                    .centerInside()
                                                    .transform(new CircleTransformation())
                                                    .into(navi_userPic, new Callback() {
                                                        @Override
                                                        public void onSuccess() {

                                                        }

                                                        @Override
                                                        public void onError() {
                                                            Toast.makeText(getApplicationContext(), "no Image", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        saveAge = sharedPreferences.getString("dateAge", "20");
        saveAgeType = sharedPreferences.getString("dateAgeType", "normal");
        saveRegion = sharedPreferences.getString("dateRegion", "서울");
        saveKeyInt = sharedPreferences.getInt("userKey", 0);




        keyList = new ArrayList<String>();
        theList = new ArrayList<keyData>();
        theAdapter = new myKeyAdapter(this,theList);

        NotiList = new ArrayList<notiData>();
        NotiAdatper = new myNotiAdapter(this, NotiList);

        manualDialog = new Dialog(this);
        manualDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        manualDialog.setContentView(R.layout.dialog_manual);
        manualDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        manualLater =(TextView) manualDialog.findViewById(R.id.manualLater);
        manualLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manualDialog.dismiss();
            }
        });


        manualShow =(TextView) manualDialog.findViewById(R.id.manualShow);
        manualShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent haIntent = new Intent(MainActivity.this,Manual.class);
                startActivity(haIntent);

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("ManualDialogVerifier", "noDialog");
                editor.apply();
                manualDialog.dismiss();
            }
        });
        keyHistoryDialog = new Dialog(this);

        keyHistoryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        keyHistoryDialog.setContentView(R.layout.dialog_key_history);
        keyHistoryDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        historyCloseText = (TextView) keyHistoryDialog.findViewById(R.id.historyClose);
        historyCloseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyHistoryDialog.dismiss();
                keyHistoryGo.setChecked(false);

            }
        });

        keyUsageList =(ListView) keyHistoryDialog.findViewById(R.id.keyUsageList);

        notificationDialog = new Dialog(this);
        notificationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        notificationDialog.setContentView(R.layout.dialog_notification);
        notificationDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        notificationList =(ListView) notificationDialog.findViewById(R.id.notificationList);
        notiCloseText =(TextView) notificationDialog.findViewById(R.id.notiClose);
        notiCloseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationDialog.dismiss();


                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("notificationVerifier", "no");
                editor.putString("userNotiCode", userNotiCodeUnchecked);
                theMenu.getItem(0).setIcon(R.drawable.burn7);
                editor.apply();

            }
        });




        updateDialog = new Dialog(this);
        updateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateDialog.setContentView(R.layout.dialog_update);
        updateDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        updateCancel =(TextView) updateDialog.findViewById(R.id.updateCancel);
        updateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });
        updateGo =(TextView) updateDialog.findViewById(R.id.appUpdate);
        updateGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updateUri !=null) {
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, updateUri);
                    startActivity(intent2);
                }

            }
        });

        dialog= new Dialog(this);
        dialogEmail = new Dialog(this);
        keyDialog = new Dialog(this);
        keySettingDialog = new Dialog(this);
        accountRemovalDialog = new Dialog(this);
        accountRemovalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        accountRemovalDialog.setContentView(R.layout.dialog_removal);
        accountRemovalDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        accountRemoveText =(TextView) accountRemovalDialog.findViewById(R.id.accountDelete);
        accountRemoveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:aynctask to php that deletes all the user data matching the email
                accountTask = new AccountTask(userEmailIntent, "accountRemoval");
                accountTask.execute();

            }
        });
        accountCancelText=(TextView) accountRemovalDialog.findViewById(R.id.accountCancel);
        accountCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountRemovalDialog.dismiss();
                removeGo.setChecked(false);
            }
        });
        accountHoldingDialog = new Dialog(this);
        accountHoldingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        accountHoldingDialog.setContentView(R.layout.dialog_holding);
        accountHoldingDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
         holdingYesText =(TextView) accountHoldingDialog.findViewById(R.id.holdingYes);
        holdingYesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:aynctask to php that puts a no mark for availability column
                accountTask = new AccountTask(userEmailIntent, "accountHolding");
                accountTask.execute();
            }
        });
         holdingCancelText =(TextView) accountHoldingDialog.findViewById(R.id.holdingCancel);
        holdingCancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountHoldingDialog.dismiss();
                checkHolding.setChecked(false);
            }
        });




        numberList = new ArrayList<>();
        for(int i=20; i<=60; i++){
            numberList.add(Integer.toString(i));

        }





        keySettingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        keySettingDialog.setContentView(R.layout.dialog_setting);
        keySettingDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        checkHolding =(Switch) keySettingDialog.findViewById(R.id.key_setting_holding);
        removeGo =(Switch) keySettingDialog.findViewById(R.id.key_setting_remove);
        keyHistoryGo =(Switch) keySettingDialog.findViewById(R.id.key_setting_history);
        keySettingCancel =(TextView) keySettingDialog.findViewById(R.id.keySettingCancel);


        keyHistoryGo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //TODO://aynctask

                    GetKeyHistory();



                }
                else{

                }
            }
        });
        checkHolding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    accountHoldingDialog.show();
                }else{

                }
            }
        });

        removeGo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    accountRemovalDialog.show();
                }
            }
        });


        keySettingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keySettingDialog.dismiss();
            }
        });






        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_date_setting);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        dialogEmail.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEmail.setContentView(R.layout.email_form);
        dialogEmail.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        emailMessage =(EditText) dialogEmail.findViewById(R.id.emailMessage);
        emailTitle =(EditText) dialogEmail.findViewById(R.id.emailTitle);

        emailSend =(TextView) dialogEmail.findViewById(R.id.emailSend);

        emailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                messageString = emailMessage.getText().toString();
                titleString = emailTitle.getText().toString() + " This is by " + userEmailIntent;


                if (!messageString.equals("") && !titleString.equals("")) {

                    SendEmail = new sendEmailTask(userEmailIntent, messageString, titleString);
                    SendEmail.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "메세지,제목을 입력하세요", Toast.LENGTH_LONG).show();
                }


                dialogEmail.dismiss();


            }
        });

        ageSame =(CheckBox) dialog.findViewById(R.id.ageSameCheck);
        ageAbove =(CheckBox) dialog.findViewById(R.id.ageAboveCheck);
        ageBelow =(CheckBox) dialog.findViewById(R.id.ageBelowCheck);

        regionSpinner = (Spinner) dialog.findViewById(R.id.regionSpinner);
        ageSpinner =(Spinner) dialog.findViewById(R.id.ageSpinner);
        regionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Region));
        ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,numberList);

        regionSpinner.setAdapter(regionAdapter);
        ageSpinner.setAdapter(ageAdapter);

        ageSpinner.setSelection(getIndex(ageSpinner,saveAge));
        regionSpinner.setSelection(getIndex(regionSpinner,saveRegion));
        if(saveAgeType.equals("same")){
            ageSame.setChecked(true);

        }else if(saveAgeType.equals("above")){
            ageAbove.setChecked(true);
        }else if(saveAgeType.equals("below")){
            ageBelow.setChecked(true);

        }

        cancelText =(TextView) dialog.findViewById(R.id.cancelText);
        saveText =(TextView) dialog.findViewById(R.id.saveText);


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





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        drawer.setStatusBarBackground(R.color.Black);
        toggle.syncState();


        adapter = new ViewPagerAdapter(getSupportFragmentManager(),Titles, Numboftabs);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);

            }
        });


        tabs.setViewPager(pager);





        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveAge = ageSpinner.getSelectedItem().toString();
                saveRegion = regionSpinner.getSelectedItem().toString();

                if (ageSame.isChecked()) {
                    saveAgeType = "same";
                } else if (ageAbove.isChecked()) {
                    saveAgeType = "above";
                } else if (ageBelow.isChecked()) {
                    saveAgeType = "below";
                } else {
                    saveAgeType = "normal";
                }


                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor editor = pref.edit();
                editor.putString("dateAge", saveAge);
                editor.putString("dateRegion", saveRegion);
                editor.putString("dateAgeType", saveAgeType);
                editor.apply();

                dialog.dismiss();


            }
        });

        if(makeActionVerifier.equals("tab2")){
            tabs.TheMagicMethod(1);


        }else if(makeActionVerifier.equals("openNotification")){
            bringNotification();
            pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = pref.edit();
            editor.putString("userNotiCode", userNotiCodeUnchecked);
            editor.apply();
        }else{
            NotificationEventReceiver.setupAlarm(getApplicationContext());
            bringProfile();
        }

       pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        previousNotiCode= pref.getString("userNotiCode", "0");
        userNotiCodeUnchecked =pref.getString("userNotiCodeUnchecked","0");

        notificationVerifier = pref.getString("notificationVerifier","hi");

        if(ManualDialogVerifier()){
            manualDialog.show();





        }else {

        }


}private void displayLicensesAlertDialog() {
        WebView view = (WebView) LayoutInflater.from(this).inflate(R.layout.dialog_notices, null);
        view.loadUrl("file:///android_asset/NOTICES.html");
        noticeDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setTitle("")
                .setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }


    class keyData{



        public String getKeyDate() {
            return keyDate;
        }

        public String getKeyContent() {
            return keyContent;
        }

        public String getKeyValue() {
            return keyValue;
        }
        private String keyDate;

        public keyData(String keyDate, String keyContent, String keyValue) {
            this.keyDate = keyDate;
            this.keyContent = keyContent;
            this.keyValue = keyValue;
        }

        private String keyContent;
        private String keyValue;

    }

    private class myKeyAdapter extends ArrayAdapter<keyData>{
        private LayoutInflater mInflater;

        public myKeyAdapter(Context context, ArrayList<keyData> object) {


            super(context, 0, object);
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public View getView(int position, View v, ViewGroup parent) {
            View view = null;

            if (v == null) {

                view = mInflater.inflate(R.layout.my_listo, null);
            } else {

                view = v;
            }


            final keyData data = this.getItem(position);

            if (data != null) {

                TextView keyDate = (TextView) view.findViewById(R.id.list_date);
                TextView keyContent = (TextView) view.findViewById(R.id.list_content);
                TextView keyValue = (TextView) view.findViewById(R.id.list_keyValue);

                keyDate.setText(data.getKeyDate());

                keyContent.setText(data.getKeyContent());
                keyValue.setText(data.getKeyValue());

            }

            return view;

        }
    }

    class notiData{

       String notiDate;

        public notiData(String notiDate, String notiTitle, String notiContent) {
            this.notiDate = notiDate;
            this.notiTitle = notiTitle;
            this.notiContent = notiContent;
        }

        private String notiTitle;
        private String notiContent;
        public String getNotiContent() {
            return notiContent;
        }

        public String getNotiDate() {
            return notiDate;
        }

        public String getNotiTitle() {
            return notiTitle;
        }





    }

    private class myNotiAdapter extends ArrayAdapter<notiData>{
        private LayoutInflater mInflater;

        public myNotiAdapter(Context context, ArrayList<notiData> object) {


            super(context, 0, object);
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public View getView(int position, View v, ViewGroup parent) {
            View view = null;

            if (v == null) {

                view = mInflater.inflate(R.layout.my_noti, null);
            } else {

                view = v;
            }


            final notiData Ndata = this.getItem(position);

            if (Ndata != null) {


                TextView notiTitle = (TextView) view.findViewById(R.id.notiTItle);
                TextView notiContent = (TextView) view.findViewById(R.id.userNotification);
                TextView notiDate = (TextView) view.findViewById(R.id.notiDate);
                notiDate.setText(Ndata.getNotiDate().toUpperCase());

                notiTitle.setText(Ndata.getNotiTitle());
                notiContent.setText(Ndata.getNotiContent());

            }

            return view;

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



    public void bringProfile(){
        //retrieve an intent with the string of userEmail for query
        String userEmailAsKey= userEmailIntent;
        task = new downloadProfile();
        task.execute(userEmailAsKey);
    }

    public class downloadProfile extends AsyncTask<String,Void,String> {
        @Override
        protected  void onCancelled(){
            task =null;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String...userEmail){
            String URL ="";
            if(userGender.equals("boys")) {
               URL = "http://minsoura.xyz/mainProfileBoys.php";
            }else if(userGender.equals("girls")){
                URL = "http://minsoura.xyz/mainProfileGirls.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("userEmail",userEmail[0]);

            try{

                requestHandler profileHandler = new requestHandler();
                String result = profileHandler.bringUserProfile(URL, emailSet);

                return result;

            }catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }

        }
        @Override
        protected  void onPostExecute(String s){
            super.onPostExecute(s);
            if(isCancelled()){
                task = null;
            }

            if(s.equals("noProfile")){

                Toast.makeText(getApplicationContext(),"HTTP_NO_OK",Toast.LENGTH_LONG).show();
            }

            else{

            try{


                JSONArray jsonArray = new JSONArray(s);
                for (int i=0; i<jsonArray.length(); i++){
                       JSONObject jsonObject = jsonArray.getJSONObject(i);

                        userNotiCode = jsonObject.getString("notiCode");
                        userNotification = jsonObject.getString("notiContent");
                        userNotiTitle =jsonObject.getString("notiTitle");
                        userNotiDate = jsonObject.getString("notiDate");

                        NotiAdatper.add(new notiData(userNotiDate,userNotiTitle,userNotification));

                    }
                }
                catch (Exception e){
                    Log.d("tag",e.getMessage());
                    e.printStackTrace();
                }

                    try {

                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        final SharedPreferences.Editor editor = pref.edit();



                        previousNotiCode = pref.getString("userNotiCode", "0");

                        if(previousNotiCode.equals(userNotiCode)){
                            notificationVerifier = "no";

                        }else{
                            notificationVerifier = "new";
                        }

                        if(notificationVerifier.equals("new")){

                            theMenu.getItem(0).setIcon(R.drawable.burn_pink);
                        }
                        editor.putString("notificationVerifier",notificationVerifier);

                        editor.apply();



                    }catch (Exception e){

                    }

                    Collections.reverse(NotiList);
                    notificationList.setAdapter(NotiAdatper);

            }
        }

    }


    public void GetKeyHistory(){
        //retrieve an intent with the string of userEmail for query
        String userEmailAsKey= userEmailIntent;
        keyHistoryTask = new keyHistory();
        keyHistoryTask.execute(userEmailAsKey);
    }

    public class keyHistory extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String...userEmail){
            String URL ="";
            if(userGender.equals("boys")) {
                URL = "http://minsoura.xyz/getKeyHistoryBoys.php";
            }else if(userGender.equals("girls")){
                URL = "http://minsoura.xyz/getKeyHistoryGirls.php";
            }

            HashMap<String, String> keySet = new HashMap<>();
            keySet.put("userEmail", userEmail[0]);


            try{

                requestHandler profileHandler = new requestHandler();
                String result = profileHandler.bringUserProfile(URL, keySet);

                return result;

            }catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }

        }
        @Override
        protected  void onPostExecute(String s){
            super.onPostExecute(s);

                    keyList.clear();

            if(s.equals("error")){

            }else{



                try{

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    keyValue = jsonObject.getString("keyValue");
                    keyDesc= jsonObject.getString("keyDesc");
                    keyDate =jsonObject.getString("keyDate");
                    keyAccum = jsonObject.getString("keyAccumulated");

                    theAdapter.add(new keyData(keyDate,keyDesc,keyAccum));
                    //profileArrayLists.add(new profileList(userID, userEmail, userName, userHeight, userAge, userJob, bitmap));
                    // Toast.makeText(getApplicationContext(),userID,Toast.LENGTH_LONG).show();
                }

            }
            catch (Exception e){
                Log.d("tag",e.getMessage());
                e.printStackTrace();
            }
                //keyAdapter.notifyDataSetChanged();

               Collections.reverse(theList);


                keyUsageList.setAdapter(theAdapter);
                keyHistoryDialog.show();

            }



        }

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }else if(Tab2.fabToolbarLayout.isToolbar()){
            Tab2.fabToolbarLayout.hide();

        }

       else
        {
            moveTaskToBack(true); return;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.theMenu = menu;

        if(notificationVerifier.equals("new")) {
            theMenu.getItem(0).setIcon(R.drawable.burn_pink);

        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.notification) {

            //TODO: dialog for notification
            //TODO: make it show the content I write in the table
            //TODO:bring notification

            bringNotification();
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final SharedPreferences.Editor editor = pref.edit();
            editor.putString("userNotiCode", userNotiCodeUnchecked);
            theMenu.getItem(0).setIcon(R.drawable.burn7);
            editor.apply();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.update_Profile) {


           Intent intent = new Intent(MainActivity.this, view_profile.class);
           intent.putExtra("user_Email", userEmailIntent);
           intent.putExtra("user_Gender",userGender);


           startActivity(intent);

        } else if (id == R.id.nav_manage) {
           dialogEmail.show();


        } else if (id == R.id.nav_share) {

           Intent sendIntent = new Intent();
           sendIntent.setAction(Intent.ACTION_SEND);
           sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.minsoura.findit");
           sendIntent.setType("text/plain");
           startActivity(Intent.createChooser(sendIntent, "SHARE THE APP"));


        } else if (id == R.id.DatingPreference) {
           dialog.show();

        }else if (id == R.id.app_setting) {
           keySettingDialog.show();
       }
       else if (id == R.id.nav_notices) {
          displayLicensesAlertDialog();
       } else if (id == R.id.nav_manual) {
           Intent haIntent = new Intent(MainActivity.this, Manual.class);
           startActivity(haIntent);
       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<String> getData(){
        ArrayList<String> Info = new ArrayList<>();
        Info.add(0, userEmailIntent);
        Info.add(1, userGender);
        return Info;
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Restart", "Restart Here");




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        Log.e("Destroyed", "DestroyedHAHAHAHA");
        // Save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        if(Tab2.deliveryController.equals("no"))
        Tab2.deliveryController="onRestart";

        if(task !=null) {
            task.cancel(true);
        }



        if(SendEmail !=null) {
            SendEmail.cancel(true);
        }

        if(accountTask !=null) {
            accountTask.cancel(true);
        }

        if(notiTask !=null) {
            notiTask.cancel(true);
        }



        System.gc();


    }





    public class sendEmailTask extends AsyncTask<Void, Void, String> {


        private final String mUserEmail;

        String mEmailMessage;
        String mEmailTitle;


        sendEmailTask( String UserEmail, String EmailMessage, String EmailTitle) {

            mUserEmail = UserEmail;
            mEmailTitle = EmailTitle;
            mEmailMessage = EmailMessage;



        }

        @Override
        protected void onCancelled() {
            SendEmail= null;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            String URL="http://minsoura.xyz/sendEmail.php";



            HashMap<String, String> SendSet = new HashMap<>();

            SendSet.put("userEmail", mUserEmail);
            SendSet.put("emailMessage",mEmailMessage);
            SendSet.put("emailTitle", mEmailTitle);





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
                SendEmail=null;
            }

            if(receivedLine.equals("sent")){
                Toast.makeText(getApplicationContext(), "이메일이 성공적으로 발송되었습니다", Toast.LENGTH_SHORT).show();
            }else if(receivedLine.equals("notSent")){
                Toast.makeText(getApplicationContext(),"not sent", Toast.LENGTH_LONG).show();
            }else
            {   Toast.makeText(getApplicationContext(),receivedLine, Toast.LENGTH_LONG).show();

            }








            }


        }
    public class AccountTask extends AsyncTask<Void, Void, String> {


        private final String mUserEmail;
        String mFunctionVerifier;


        AccountTask( String UserEmail, String functionVerifier) {

            mUserEmail = UserEmail;

            mFunctionVerifier = functionVerifier;
        }



        @Override
        protected  void onCancelled(){


            accountTask=null;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            String URL="";
            if(userGender.equals("boys")){
                if(mFunctionVerifier.equals("accountHolding")){
                    URL="http://minsoura.xyz/accountHoldingBoys.php";
                }else if(mFunctionVerifier.equals("accountRemoval")){
                    URL="http://minsoura.xyz/accountRemovalBoys.php";
                }

            }else if(userGender.equals("girls")){
                if(mFunctionVerifier.equals("accountHolding")){
                    URL="http://minsoura.xyz/accountHoldingGirls.php";
                }else if(mFunctionVerifier.equals("accountRemoval")){
                    URL="http://minsoura.xyz/accountRemovalGirls.php";
                }
            }

            HashMap<String, String> SendSet = new HashMap<>();
            SendSet.put("userEmail", mUserEmail);

            try {
                requestHandler AccountHandler = new requestHandler();
                String result = AccountHandler.sendPostRequest(URL, SendSet);

                return result;

            } catch (Exception e) {

                return null;
            }


        }

        @Override
        protected void onPostExecute(final String receivedLine) {
            super.onPostExecute(receivedLine);
            if(isCancelled()){
                accountTask=null;
            }

            if(receivedLine.equals("accountHeld")){
                Toast.makeText(getApplicationContext(), "계정이 홀딩 되었습니다.", Toast.LENGTH_SHORT).show();


                CountDown _tik;

                _tik= new CountDown(2000,1000,MainActivity.this,logIn.class);
                _tik.start();



            }else if(receivedLine.equals("accountRemoved")){
                Toast.makeText(getApplicationContext(),"계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();

                CountDown _tik;

                _tik= new CountDown(2000,1000,MainActivity.this,logIn.class);
                _tik.start();

            }else
            {   Toast.makeText(getApplicationContext(),receivedLine, Toast.LENGTH_LONG).show();

            }
        }


    }

    public Boolean ManualDialogVerifier(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String verifier;
        verifier = sharedPreferences.getString("ManualDialogVerifier", "yesDialog");
        if(verifier.equals("yesDialog")){
            return true;
        }else{
            return false;
        }
    }

    public class CountDown extends CountDownTimer {
        private Activity _act;
        private Class _cls;

        public CountDown(long millisInFuture, long countDownInterval, Activity act, Class cls) {
            super(millisInFuture, countDownInterval);
            _act = act;
            _cls = cls;
        }

        @Override
        public void onFinish() {
            _act.startActivity(new Intent(_act, _cls));
            _act.finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }


    }
    public void bringNotification(){
        //retrieve an intent with the string of userEmail for query

        notiTask = new downloadNotification();
        notiTask.execute();
    }

    public class downloadNotification extends AsyncTask<Void,Void,String> {
        @Override
        protected  void onCancelled(){
            notiTask =null;

        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void...hi){
            String URL ="";
            if(userGender.equals("boys")) {
                URL = "http://minsoura.xyz/getNotification.php";
            }else if(userGender.equals("girls")){
                URL = "http://minsoura.xyz/getNotification.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("hi","hi");

            try{

                requestHandler profileHandler = new requestHandler();
                String result = profileHandler.bringUserProfile(URL, emailSet);

                return result;

            }catch (Exception e)
            {
                e.printStackTrace();

                return null;
            }

        }
        @Override
        protected  void onPostExecute(String s){
            super.onPostExecute(s);
            if(isCancelled()){
                notiTask=null;
            }


            if(s.equals("noProfile")){

                Toast.makeText(getApplicationContext(),"HTTP_NO_OK",Toast.LENGTH_LONG).show();
            }

            else{

                try{
                    NotiAdatper.clear();

                    JSONArray jsonArray = new JSONArray(s);
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        userNotiCode = jsonObject.getString("notiCode");
                        userNotification = jsonObject.getString("notiContent");
                        userNotiTitle =jsonObject.getString("notiTitle");
                        userNotiDate = jsonObject.getString("notiDate");

                        NotiAdatper.add(new notiData(userNotiDate,userNotiTitle,userNotification));

                    }
                }
                catch (Exception e){
                    Log.d("tag",e.getMessage());
                    e.printStackTrace();
                }
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        final SharedPreferences.Editor editor = pref.edit();




                        previousNotiCode = pref.getString("userNotiCode", "0");

                        if(previousNotiCode.equals(userNotiCode)){
                            notificationVerifier = "no";
                        }else{
                            notificationVerifier = "new";
                        }
                        if(notificationVerifier.equals("new")) {


                        }

                 editor.putString("notificationVerifier", notificationVerifier);
                 editor.putString("userNotiCode",userNotiCode);
                editor.apply();


                      Collections.reverse(NotiList);

                     notificationList.setAdapter(NotiAdatper);
                        Integer size = NotiAdatper.getCount();



                    if(NotiAdatper.getItem(0).getNotiTitle().equals("UPDATE")){
                        updateUri = Uri.parse(NotiAdatper.getItem(0).getNotiContent());


                        updateDialog.show();

                    }else {
                        notificationDialog.show();
                    }

                }




                // getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.ImageColumns.TITLE + "=?", new String[]{userEmail});
            }
        }

}





















