package com.minsoura.findit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;

import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;


import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class view_profile extends AppCompatActivity {

  static  Bitmap StringToBitmap;

    public static final String UPDATE_URL_BOYS="http://minsoura.xyz/updateProfileBoys.php";
    public static final String UPDATE_URL_GIRLS="http://minsoura.xyz/updateProfileGirls.php";


    //updateKEY
    public static final String UPDATE_KEY_userEmail="userEmail";

    public static final String UPDATE_KEY_userPicSmall="userPicSmall";
    public static final String UPDATE_KEY_userPicMain="userPicMain";
    public static final String UPDATE_KEY_userPicTwo="userPicTwo";
    public static final String UPDATE_KEY_userPicThree="userPicThree";
    public static final String UPDATE_KEY_userPicFour="userPicFour";
    public static final String UPDATE_KEY_userPicFive="userPicFive";

    public static final String UPDATE_KEY_userSayHi="userSayHi";
    public static final String UPDATE_KEY_userRegion="userRegion";
    public static final String UPDATE_KEY_userRegion2="userRegion2";

    public static final String UPDATE_KEY_userAge="userAge";


    //updateVALUE
    public static String UPDATE_VALUE_userEmail="";

    public static String UPDATE_VALUE_userPicSmall="noChange";
    public static  String UPDATE_VALUE_userPicMain="noChange";
    public static  String UPDATE_VALUE_userPicTwo="noChange";
    public static  String UPDATE_VALUE_userPicThree="noChange";
    public static String UPDATE_VALUE_userPicFour="noChange";
    public static String UPDATE_VALUE_userPicFive="noChange";

    public static  String UPDATE_VALUE_userSayHi="";
    public static String UPDATE_Value_userRegion="";
    public static String UPDATE_Value_userRegion2="";

    public static String UPDATE_VALUE_userAge="";



    FloatingActionButton fab2;
    private View mProgressView;
    private View wrapper_View;
    View view;

    static  String escapeIndex;
    static ArrayList<Bitmap> bitmapGroup;
    static ArrayList<Bitmap> allocator;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;
    private static int j = 0;

    private GestureDetector detector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private ImageView expandedImageView;
    ScrollView wrapper;
    private Uri filePath;

    UploadMap uploadMap;

    TextView view_id;
    TextView view_age;
    TextView view_height;
    TextView view_job;
    TextView view_university;

    static String view_region;
    static String view_region2;
    downloadProfile2 task2;


    EditText view_SayHi;
    public   static String imageTest="";

    public   static String getUserID="";
    public   static String  getUserHeight="";
    public   static String getUserAge="";
    public   static String  getUserJob="";
    public   static String getUserSayHi="";
    public   static String getUserRegion="";
    public   static String getUserRegion2="";
    public   static String getUserUniversity="";


   static  String[] getUserStringImage;
    //region,or school or userPicFive to be added later

    ImageView view_main;
    ImageView view_userMain;
    ImageView view_userPic2;
    ImageView view_userPic3;
    ImageView view_userPic4;
    ImageView view_userPic5;

   static String userPic, userEmailIntent2, userGender2;
    Bitmap bitmapR;

    String buttonVerifier;
  static   String imageSelected1;
   static String imageSelected2;
   static String imageSelected3;
  static   String imageSelected4;
    static String imageSelected5;

    TextView change_main;
    TextView change_userPic2;
    TextView change_userPic3;
    TextView change_userPic4;
    TextView change_userPic5;

    static  Bitmap send_userPic1;
    static Bitmap send_userPic2;
    static Bitmap send_userPic3;
    static Bitmap send_userPic4;
    static Bitmap send_userPic5;
    static Bitmap send_userPicSmall;

   static  String[] imageSelected;
    private int PICK_IMAGE_REQUEST =1;
    //////spinner
    Spinner regionSpinner;
    Spinner subRegionSpinner;
    Spinner ageSpinner;

    ArrayAdapter<String> regionAdapter;
    ArrayAdapter<String> ageAdapter;

    ArrayAdapter<String> seoulAdapter;
    ArrayAdapter<String> BusanAdapter;
    ArrayAdapter<String> IncheonAdapter;
    ArrayAdapter<String> DaeguAdapter;
    ArrayAdapter<String> GwanguAdapter;
    ArrayAdapter<String> DaejeonAdapter;
    ArrayAdapter<String> UlsanAdapter;
    ArrayAdapter<String> GyeonggiAdapter;
    ArrayAdapter<String> GanwonAdapter;
    ArrayAdapter<String> ChoongCheongAdapter;
    ArrayAdapter<String> GyeongSangAdapter;
    ArrayAdapter<String> JeonLaSangAdapter;
    ArrayAdapter<String> SejongAdapter;
    ArrayAdapter<String> JejuAdapter;

    List<String> numberList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        Intent intentR = getIntent();

        userEmailIntent2 = intentR.getStringExtra("user_Email");
        userGender2 = intentR.getStringExtra("user_Gender");

        getUserStringImage = new String[5];
        getUserStringImage[0]="";
        getUserStringImage[1]="";
        getUserStringImage[2]="";
        getUserStringImage[3]="";
        getUserStringImage[4]="";

        numberList = new ArrayList<>();
        for(int i=20; i<=60; i++){
            numberList.add(Integer.toString(i));
        }


        mProgressView = findViewById(R.id.view_progress);
        wrapper_View = findViewById(R.id.wrapper);


        view_university =(TextView) findViewById(R.id.view_uni_value);
        view =  findViewById(R.id.mainPic);

        wrapper = (ScrollView) findViewById(R.id.wrapper);

        regionSpinner = (Spinner) findViewById(R.id.regionSpinner);
        subRegionSpinner =(Spinner) findViewById(R.id.subRegionSpinner);
        ageSpinner =(Spinner) findViewById(R.id.ageSpinner);

        ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,numberList);



        seoulAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Seoul));
        BusanAdapter=  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Busan));
        IncheonAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Incheon));
        DaeguAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Daegu));
        GwanguAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Gwangju));
        DaejeonAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Daejeon));
        UlsanAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Ulsan));
        GyeonggiAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Gyeonggi));
        GanwonAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Ganwon));
        ChoongCheongAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.ChoongCheong));
        GyeongSangAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.GyeongSang));
        JeonLaSangAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.JeonLa));
        SejongAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Sejong));
        JejuAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Jeju));

        regionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,(String[])getResources().getStringArray(R.array.Region));
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(regionAdapter);

        ageSpinner.setAdapter(ageAdapter);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getSelectedItem().toString().equals("서울")) {
                    subRegionSpinner.setAdapter(seoulAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));


                } else if (parent.getSelectedItem().toString().equals("경기")) {
                    subRegionSpinner.setAdapter(GyeonggiAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("부산")) {
                    subRegionSpinner.setAdapter(BusanAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("대구")) {
                    subRegionSpinner.setAdapter(DaeguAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("인천")) {
                    subRegionSpinner.setAdapter(IncheonAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("대전")) {
                    subRegionSpinner.setAdapter(DaejeonAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("광주")) {
                    subRegionSpinner.setAdapter(GwanguAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("울산")) {
                    subRegionSpinner.setAdapter(UlsanAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("강원")) {
                    subRegionSpinner.setAdapter(GanwonAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("충청")) {
                    subRegionSpinner.setAdapter(ChoongCheongAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("전라")) {
                    subRegionSpinner.setAdapter(JeonLaSangAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));

                } else if (parent.getSelectedItem().toString().equals("세종")) {
                    subRegionSpinner.setAdapter(SejongAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));


                } else if (parent.getSelectedItem().toString().equals("제주")) {
                    subRegionSpinner.setAdapter(JejuAdapter);
                    subRegionSpinner.setSelection(getIndex(subRegionSpinner, getUserRegion2));
                }

                // UPLOAD_Value_userRegion = parent.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),UPLOAD_Value_userRegion,Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        subRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  UPLOAD_Value_userRegion2 = UPLOAD_Value_userRegion + parent.getSelectedItem().toString();
                // Toast.makeText(getApplicationContext(), UPLOAD_Value_userRegion2, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        escapeIndex="yes";
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        buttonVerifier ="";
       imageSelected1="no";
       imageSelected2="no";
       imageSelected3="no";
       imageSelected4="no";
        imageSelected5="no";

        imageSelected = new String[5];
        imageSelected[0] ="";
        imageSelected[1] ="";
        imageSelected[2] ="";
        imageSelected[3] ="";
        imageSelected[4] ="";


        bitmapGroup = new ArrayList<>();
        allocator = new ArrayList<>();

        detector = new GestureDetector(this, new SwipeGestureDetector());



        bitmapGroup.add(0, null);
        bitmapGroup.add(1, null);
        bitmapGroup.add(2, null);
        bitmapGroup.add(3, null);
        bitmapGroup.add(4, null);

        view_SayHi = (EditText) findViewById(R.id.view_sayHi_value);
        change_main = (TextView) findViewById(R.id.view_change_main);
        change_userPic2 = (TextView) findViewById(R.id.view_change_userPic2);
        change_userPic3 = (TextView) findViewById(R.id.view_change_userPic3);
        change_userPic4 = (TextView) findViewById(R.id.view_change_userPic4);
        change_userPic5 = (TextView) findViewById(R.id.view_change_userPic5);

        view_id = (TextView) findViewById(R.id.view_ID);

        view_height = (TextView) findViewById(R.id.view_height_value);
        view_job = (TextView) findViewById(R.id.view_job_value);


        view_main = (ImageView) findViewById(R.id.view_mainPic);
        view_userMain = (ImageView) findViewById(R.id.view_picOne);
        view_userPic2 = (ImageView) findViewById(R.id.view_picTwo);
        view_userPic3 = (ImageView) findViewById(R.id.view_picThree);
        view_userPic4 = (ImageView) findViewById(R.id.view_picFour);
        view_userPic5 =(ImageView) findViewById(R.id.view_picFive);

        view_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zoomImageFromThumb(v, 0);

            }
        });






        change_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonVerifier = "picOne";

                showFileChooser();

            }
        });

        view_userMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zoomImageFromThumb(v, 0);


            }
        });




        change_userPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonVerifier = "picTwo";
                showFileChooser();

            }
        });

        view_userPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageSelected[1].equals("yes")) {
                    zoomImageFromThumb(v, 1);
                } else if (bitmapGroup.get(1) != null) {
                    zoomImageFromThumb(v, 1);
                } else {
                    Snackbar.make(v, "사진을 선택해 주세요", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });




        change_userPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonVerifier = "picThree";
                showFileChooser();
            }
        });
        view_userPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected[2].equals("yes")) {
                    zoomImageFromThumb(v, 2);
                } else if (bitmapGroup.get(2) != null) {
                    zoomImageFromThumb(v, 2);
                } else {
                    Snackbar.make(v, "사진을 선택해 주세요", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            }
        });




        change_userPic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonVerifier = "picFour";
                showFileChooser();
            }
        });

        view_userPic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected[3].equals("yes")) {
                    zoomImageFromThumb(v, 3);
                } else if (bitmapGroup.get(3) != null) {
                    zoomImageFromThumb(v, 3);
                } else {
                    Snackbar.make(v, "사진을 선택해 주세요", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });

        change_userPic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonVerifier = "picFive";
                showFileChooser();
            }
        });

        view_userPic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected[4].equals("yes")) {
                    zoomImageFromThumb(v, 4);
                } else if (bitmapGroup.get(4) != null) {
                    zoomImageFromThumb(v, 4);
                } else {
                    Snackbar.make(v, "사진을 선택해 주세요", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });








        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 updateUserProfile();


            }
        });

        bringProfile2();
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

    public void updateUserProfile(){

    updateTASK();


    }

    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }





    private void updateTASK() {
        UPDATE_VALUE_userAge = ageSpinner.getSelectedItem().toString();
        UPDATE_Value_userRegion = regionSpinner.getSelectedItem().toString();
        UPDATE_Value_userRegion2 = subRegionSpinner.getSelectedItem().toString();

        UPDATE_VALUE_userEmail=userEmailIntent2;
        UPDATE_VALUE_userSayHi=view_SayHi.getText().toString().trim();



         uploadMap = new UploadMap();
        uploadMap.execute("go");
    }
    class UploadMap extends AsyncTask<String, Void, String> {

        Boolean uploadMapChecker = true;
        requestHandler updateHandler = new requestHandler();


        @Override
        protected String doInBackground(String... params) {
            String Go = params[0];
            String result;
            if(!uploadMapChecker){
                return null;

            }
            HashMap<String, String> data = new HashMap<>();
            data.put(UPDATE_KEY_userEmail,UPDATE_VALUE_userEmail);

            data.put(UPDATE_KEY_userRegion, UPDATE_Value_userRegion);
            data.put(UPDATE_KEY_userRegion2, UPDATE_Value_userRegion2);
            data.put(UPDATE_KEY_userAge, UPDATE_VALUE_userAge);

            if(imageSelected[0].equals("yes")) {
                UPDATE_VALUE_userPicMain= getStringImage(send_userPic1);
                data.put(UPDATE_KEY_userPicMain, UPDATE_VALUE_userPicMain);

                UPDATE_VALUE_userPicSmall = getStringImage(send_userPicSmall);
                data.put(UPDATE_KEY_userPicSmall,UPDATE_VALUE_userPicSmall);
            }else{
                UPDATE_VALUE_userPicMain= "noChange";
                data.put(UPDATE_KEY_userPicMain, UPDATE_VALUE_userPicMain);

                UPDATE_VALUE_userPicSmall = "noChange";
                data.put(UPDATE_KEY_userPicSmall,UPDATE_VALUE_userPicSmall);
            }
            if(!uploadMapChecker){
                return null;

            }

            if(imageSelected[1].equals("yes")) {
                UPDATE_VALUE_userPicTwo= getStringImage(send_userPic2);
                data.put(UPDATE_KEY_userPicTwo, UPDATE_VALUE_userPicTwo);
            }else{
                UPDATE_VALUE_userPicTwo= "noChange";
                data.put(UPDATE_KEY_userPicTwo, UPDATE_VALUE_userPicTwo);

            }
            if(!uploadMapChecker){
                return null;

            }
            if(imageSelected[2].equals("yes")) {
                UPDATE_VALUE_userPicThree= getStringImage(send_userPic3 );
                data.put(UPDATE_KEY_userPicThree, UPDATE_VALUE_userPicThree);
            }else{  UPDATE_VALUE_userPicThree= "noChange";

                data.put(UPDATE_KEY_userPicThree, UPDATE_VALUE_userPicThree);
            }
            if(!uploadMapChecker){
                return null;

            }

            if(imageSelected[3].equals("yes")) {
                UPDATE_VALUE_userPicFour=getStringImage(send_userPic4);
                data.put(UPDATE_KEY_userPicFour, UPDATE_VALUE_userPicFour);
            }else{ UPDATE_VALUE_userPicFour="noChange";
                data.put(UPDATE_KEY_userPicFour, UPDATE_VALUE_userPicFour);
            }
            if(!uploadMapChecker){
                return null;

            }
            if(imageSelected[4].equals("yes")) {
                UPDATE_VALUE_userPicFive=getStringImage(send_userPic5);
                data.put(UPDATE_KEY_userPicFive, UPDATE_VALUE_userPicFive);
            }else{ UPDATE_VALUE_userPicFive="noChange";
                data.put(UPDATE_KEY_userPicFive, UPDATE_VALUE_userPicFive);
            }
            if(!uploadMapChecker){
                return null;

            }


            data.put(UPDATE_KEY_userSayHi,UPDATE_VALUE_userSayHi);
            if(!uploadMapChecker){
                return null;

            }

            if (userGender2.equals("boys")) {
                result = updateHandler.sendPostRequest(UPDATE_URL_BOYS, data);
                return result;
            } else if (userGender2.equals("girls")) {
                result = updateHandler.sendPostRequest(UPDATE_URL_GIRLS, data);
                return result;
            }


            return "result";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);

        }


        protected void onPostExecute(final String s) {
            super.onPostExecute(s);
            showProgress(false);

            if(s.equals("yes")) {
                Snackbar.make(view_main, "업데이트 되었습니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else if(s.equals("no")){
                Snackbar.make(view_main, "다시 시도해 주세요", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else {
                Snackbar.make(view_main, "Error Occurred", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        uploadMapChecker = false;

        }


    }

    private Bitmap decodeToSmallFile(String filePath, Uri uri) {
        try {

            Bitmap b;

            File theFile = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(theFile);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();


            int fileSize = (int) theFile.length()/1024;
            int fileLimit =20;

            if(fileSize > fileLimit) {


                final int REQUIRED_SIZE = 150;
                int scale = 1;
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                        o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2;
                }


                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                fis = new FileInputStream(theFile);
                b = BitmapFactory.decodeStream(fis, null, o2);
                fis.close();



            }else{
                b = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            }
            return b;

        } catch (Exception e) {

        }
        return null;

    }



    private Bitmap decodeFile(String filePath, Uri uri) {
        try {

            Bitmap b = null;

            File theFile = new File(filePath);
            int fileLimit =150;

            int fileSize = (int) theFile.length()/1024;

            int scale=2;
            if(fileSize>=fileLimit){
                 scale= (int) Math.sqrt((double)fileSize /(double)fileLimit);
                if(scale <2){
                    scale=2;
                }
                FileInputStream fis = new FileInputStream(theFile);
                fis.close();
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                fis = new FileInputStream(theFile);
                b=  BitmapFactory.decodeStream(fis, null, o2);
                fis.close();
            }else{
               b= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            }

           return  b;
        } catch (Exception e) {}
        return null;
    }

    public String getRealPathFromURI(Uri contentURI) {
        Uri contentUri = contentURI;

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = null;
        try {
            if (Build.VERSION.SDK_INT > 19) {
                // Will return "image:x*"
                String wholeID = DocumentsContract.getDocumentId(contentUri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";

                cursor = getApplicationContext().getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[] { id }, null);
            } else {
                cursor = getApplicationContext().getContentResolver().query(contentUri,
                        projection, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String path = null;
        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return path;
    }






    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData() != null){

            try{
                if(buttonVerifier.equals("picOne")) {
                    filePath = data.getData();
                    String realPath;
                    //send_userPic1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        send_userPic1 = decodeFile(realPath, data.getData());
                        send_userPicSmall = decodeToSmallFile(realPath, data.getData());


                        // SDK >= 11 && SDK < 19
                    }else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                        send_userPic1  = decodeFile(realPath, data.getData());
                        send_userPicSmall = decodeToSmallFile(realPath, data.getData());

                        // SDK > 19 (Android 4.4)
                    }else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        send_userPic1  = decodeFile(realPath, data.getData());
                        send_userPicSmall = decodeToSmallFile(realPath, data.getData());

                    }

                    if(send_userPic1==null){
                        send_userPic1 = decodeFile(getRealPathFromURI(filePath),filePath);
                        send_userPicSmall= decodeToSmallFile(getRealPathFromURI(filePath),filePath);
                    }
                    view_main.setImageBitmap(send_userPic1);
                    view_userMain.setImageBitmap(send_userPic1);
                    bitmapGroup.set(0, send_userPic1);
                    imageSelected[0]="yes";
                }

                if(buttonVerifier.equals("picTwo")) {
                    //  isImageSelected ="yes";
                    filePath = data.getData();
                    //send_userPic2 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    String realPath;
                    //send_userPic1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        send_userPic2 = decodeFile(realPath, data.getData());

                        // SDK >= 11 && SDK < 19
                    }else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                        send_userPic2  = decodeFile(realPath, data.getData());

                        // SDK > 19 (Android 4.4)
                    }else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        send_userPic2  = decodeFile(realPath, data.getData());

                    }
                    if(send_userPic2==null){
                        send_userPic2 = decodeFile(getRealPathFromURI(filePath),filePath);

                    }


                    view_userPic2.setImageBitmap(send_userPic2);
                    bitmapGroup.set(1, send_userPic2);
                    imageSelected[1]="yes";
                }
                if(buttonVerifier.equals("picThree")){
                    filePath = data.getData();
                   // send_userPic3 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    String realPath;
                    //send_userPic1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        send_userPic3 = decodeFile(realPath, data.getData());

                        // SDK >= 11 && SDK < 19
                    }else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                        send_userPic3  = decodeFile(realPath, data.getData());

                        // SDK > 19 (Android 4.4)
                    }else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        send_userPic3  = decodeFile(realPath, data.getData());

                    }
                    if(send_userPic3==null){
                        send_userPic3 = decodeFile(getRealPathFromURI(filePath),filePath);

                    }



                    view_userPic3.setImageBitmap(send_userPic3);
                    bitmapGroup.set(2, send_userPic3);
                    imageSelected[2]="yes";
                }
                    if(buttonVerifier.equals("picFour")) {
                        filePath = data.getData();
                       // send_userPic4 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        String realPath;
                        //send_userPic1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                        if (Build.VERSION.SDK_INT < 11) {
                            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                            send_userPic4 = decodeFile(realPath, data.getData());

                            // SDK >= 11 && SDK < 19
                        }else if (Build.VERSION.SDK_INT < 19) {
                            realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                            send_userPic4  = decodeFile(realPath, data.getData());

                            // SDK > 19 (Android 4.4)
                        }else {
                            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                            send_userPic4  = decodeFile(realPath, data.getData());

                        }
                        if(send_userPic4==null){
                            send_userPic4 = decodeFile(getRealPathFromURI(filePath),filePath);

                        }

                        view_userPic4.setImageBitmap(send_userPic4);
                        bitmapGroup.set(3, send_userPic4);
                        imageSelected[3] = "yes";
                    }

                if(buttonVerifier.equals("picFive")) {
                    filePath = data.getData();
                    // send_userPic4 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    String realPath;
                    //send_userPic1 = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        send_userPic5 = decodeFile(realPath, data.getData());

                        // SDK >= 11 && SDK < 19
                    }else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                        send_userPic5  = decodeFile(realPath, data.getData());

                        // SDK > 19 (Android 4.4)
                    }else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        send_userPic5  = decodeFile(realPath, data.getData());

                    }
                    if(send_userPic5==null){
                        send_userPic5 = decodeFile(getRealPathFromURI(filePath),filePath);

                    }

                    view_userPic5.setImageBitmap(send_userPic5);
                    bitmapGroup.set(4, send_userPic5);
                    imageSelected[4] = "yes";
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public  void onBackPressed(){

        if(escapeIndex.equals("no") || escapeIndex.equals("duringEnd")) {
            view_main.setAlpha(1f);
            view_userMain.setAlpha(1f);
            view_userPic2.setAlpha(1f);
            view_userPic3.setAlpha(1f);
            view_userPic4.setAlpha(1f);
            view_userPic5.setAlpha(1f);

            expandedImageView.setVisibility(View.GONE);
           // view_main.setImageBitmap(bitmapGroup.get(0));
            wrapper.setVisibility(View.VISIBLE);
            fab2.setVisibility(View.VISIBLE);
            mCurrentAnimator = null;
            escapeIndex="yes";
        }else if(escapeIndex.equals("yes")){
            super.onBackPressed();

        }
    }


    private void zoomImageFromThumb(final View thumbView,  int integer) {

        escapeIndex="no";

        if(mCurrentAnimator != null) {

            mCurrentAnimator.cancel();
        }

        expandedImageView = (ImageView) findViewById(R.id.expanded_image);

        expandedImageView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(detector.onTouchEvent(event)){
                    return true;
                } else {
                    return false;
                }
            }
        });

        expandedImageView.setImageBitmap(bitmapGroup.get(integer));

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;

        if ((float) finalBounds.width() / finalBounds.height() > (float) startBounds
                .width() / startBounds.height()) {

            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;

        } else {

            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight = startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);
        wrapper.setVisibility(View.INVISIBLE);
        fab2.setVisibility(View.INVISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();

        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));

        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
              //  escapeIndex="duringEnd";
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
              //  escapeIndex="duringEnd";
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(mCurrentAnimator != null) {
                    thumbView.setAlpha(1f);
                    expandedImageView.setVisibility(View.GONE);
                    wrapper.setVisibility(View.VISIBLE);
                    fab2.setVisibility(View.VISIBLE);
                    escapeIndex="yes";
                    mCurrentAnimator.cancel();

                }

                AnimatorSet set = new AnimatorSet();

                set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));

                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        wrapper.setVisibility(View.VISIBLE);
                        fab2.setVisibility(View.VISIBLE);
                        mCurrentAnimator = null;
                        //escapeIndex="duringEnd";
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        wrapper.setVisibility(View.VISIBLE);
                        fab2.setVisibility(View.VISIBLE);
                        mCurrentAnimator = null;
                       // escapeIndex="duringEnd";
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }



    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            try {

                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){

                    if(bitmapGroup.size() > j) {
                        j++;

                        if(j < bitmapGroup.size()) {

                            expandedImageView.setImageBitmap(bitmapGroup.get(j));
                            return true;
                        } else {
                            j = 0;
                            expandedImageView.setImageBitmap(bitmapGroup.get(j));
                            return true;
                        }
                    }
                } else if(e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    if(j > 0) {
                        j--;
                        expandedImageView.setImageBitmap(bitmapGroup.get(j));
                        return true;
                    } else {
                        j = bitmapGroup.size() - 1;
                        expandedImageView.setImageBitmap(bitmapGroup.get(j));
                        return true;
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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

            fab2.setVisibility(show ? View.GONE : View.VISIBLE);
            fab2.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    fab2.setVisibility(show ? View.GONE : View.VISIBLE);
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
            fab2.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    public void bringProfile2() {
        //retrieve an intent with the string of userEmail for query
        String userEmailAsKey = userEmailIntent2;
        task2 = new downloadProfile2();
        task2.execute(userEmailAsKey);
    }

     class downloadProfile2 extends AsyncTask<String, Void, String> {

        Boolean task2Checker = true;
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }
         @Override
         protected void onCancelled() {
              task2 = null;
             showProgress(false);
         }
        @Override
        protected String doInBackground(String... userEmail) {
            String URL = "";
            if (userGender2.equals("boys")) {
                URL = "http://minsoura.xyz/downloadProfileBoys.php";
            } else if (userGender2.equals("girls")) {
                URL = "http://minsoura.xyz/downloadProfileGirls.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("userEmail", userEmail[0]);

            try {
                if(!task2Checker){
                    return null;

                }
                requestHandler profileHandler2 = new requestHandler();
                String result = profileHandler2.bringUserProfile(URL, emailSet);

                if(!task2Checker){
                    return null;

                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }

        }

         @Override
        protected void onPostExecute(String s) {
             super.onPostExecute(s);
             if (isCancelled()) {
                 task2 = null;
             }
             // String imageTest="";
             //  String getUserID="";
             //   String getUserHeight="";
             //   String getUserAge="";
             //   String getUserJob="";
          if(task2Checker)
             {
             if (s.equals("noProfile") && !s.isEmpty()) {

                 Toast.makeText(getApplicationContext(), "HTTP_NO_OK", Toast.LENGTH_LONG).show();
             } else {


                 try {
                     JSONObject root2 = new JSONObject(s);
                     JSONArray jsonArray2 = root2.getJSONArray("result");
                     for (int i = 0; i < jsonArray2.length(); i++) {
                         JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
                         getUserStringImage[0] = jsonObject2.getString("userPic");
                         getUserStringImage[1] = jsonObject2.getString("userPicTwo");
                         getUserStringImage[2] = jsonObject2.getString("userPicThree");
                         getUserStringImage[3] = jsonObject2.getString("userPicFour");
                         getUserStringImage[4] = jsonObject2.getString("userPicFive");

                         getUserID = jsonObject2.getString("userID");
                         getUserHeight = jsonObject2.getString("userHeight");
                         getUserAge = jsonObject2.getString("userAge");
                         getUserJob = jsonObject2.getString("userJob");
                         getUserSayHi = jsonObject2.getString("userSayHi");

                         getUserRegion = jsonObject2.getString("userRegion");
                         getUserRegion2 = jsonObject2.getString("userRegion2");
                         getUserUniversity = jsonObject2.getString("userUniversity");


                     }

                 } catch (Exception e) {
                     Log.d("tag", e.getMessage());
                     e.printStackTrace();
                 }

                 if (task2Checker) {

                     //    Toast.makeText(getApplicationContext(), imageTest, Toast.LENGTH_LONG).show();
                     byte[] decodedString = Base64.decode(getUserStringImage[0], Base64.DEFAULT);
                     StringToBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                     // view_main.setImageBitmap(StringToBitmap);
                     //view_userMain.setImageBitmap(StringToBitmap);
                     if (getUserStringImage[0] != null) {
                         bitmapGroup.set(0, getBitmapImage(getUserStringImage[0]));


                         view_main.setImageBitmap(bitmapGroup.get(0));
                         view_userMain.setImageBitmap(bitmapGroup.get(0));
                     }
                     if (getUserStringImage[1] != null) {
                         bitmapGroup.set(1, getBitmapImage(getUserStringImage[1]));
                         view_userPic2.setImageBitmap(bitmapGroup.get(1));
                     }
                     if (getUserStringImage[2] != null) {
                         bitmapGroup.set(2, getBitmapImage(getUserStringImage[2]));
                         view_userPic3.setImageBitmap(bitmapGroup.get(2));
                     }
                     if (getUserStringImage[3] != null) {
                         bitmapGroup.set(3, getBitmapImage(getUserStringImage[3]));
                         view_userPic4.setImageBitmap(bitmapGroup.get(3));
                     }
                     if (getUserStringImage[4] != null) {
                         bitmapGroup.set(4, getBitmapImage(getUserStringImage[4]));
                         view_userPic5.setImageBitmap(bitmapGroup.get(4));
                     }


                     view_id.setText(getUserID);
                     view_height.setText(getUserHeight + "CM");

                     view_job.setText(getUserJob);
                     view_university.setText(getUserUniversity);

                     view_region = getUserRegion;
                     view_region2 = getUserRegion2;

                     regionSpinner.setSelection(getIndex(regionSpinner, getUserRegion));

                     ageSpinner.setSelection(getIndex(ageSpinner, getUserAge));


                     if (getUserSayHi != null) {
                         view_SayHi.setText(getUserSayHi);
                     }


                 }

                 // getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.ImageColumns.TITLE + "=?", new String[]{userEmail});
             }
         }

            showProgress(false);
            task2Checker = false;
        }

    }

    public Bitmap getBitmapImage(String stringImage){

        byte[] decodedString = Base64.decode(stringImage, Base64.DEFAULT);
        StringToBitmap  = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return StringToBitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        Log.e("Destroyed", "DestroyedHAHAHAHA");
        // Save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        view_main.setImageDrawable(null);
        view_userMain.setImageDrawable(null);
        view_userPic2.setImageDrawable(null);
        view_userPic3.setImageDrawable(null);
        view_userPic4.setImageDrawable(null);
        view_userPic5.setImageDrawable(null);
        //expandedImageView.setImageDrawable(null);

        if(uploadMap !=null){
            uploadMap.uploadMapChecker = false;
            uploadMap.cancel(true);
        }if(task2 != null){
            task2.task2Checker = false;
            task2.cancel(true);
        }

        bitmapGroup.clear();
        System.gc();


    }


}

