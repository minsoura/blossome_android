package com.minsoura.findit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by min on 2016-03-13.
 */
public class RegistrationByStudentCard extends Activity{

    UploadImage ui = new UploadImage();
    UploadTransfer ut = new UploadTransfer();
    public static final String UPLOAD_URL_BOYS ="http://minsoura.xyz/uploadRegistrationBoys2.php";
    public static final String UPLOAD_URL_GIRLS ="http://minsoura.xyz/uploadRegistrationGirls2.php";

    public static final String UPLOAD_URL_TRANSFER ="http://minsoura.xyz/userTransfer.php";

    public static final String UPLOAD_KEY_userName="userName";
    public static final String UPLOAD_KEY_userID="userID";
    public static final String UPLOAD_KEY_userEmail="userEmail";
    public static final String UPLOAD_KEY_userCell="userCell";
    public static final String UPLOAD_KEY_userPW="userPW";
    public static final String UPLOAD_KEY_userHeight="userHeight";
    public static final String UPLOAD_KEY_userAge="userAge";
    public static final String UPLOAD_KEY_userJob="userJob";

    public static final String UPLOAD_KEY_userPicSmall ="userPicSmall";
    public static final String UPLOAD_KEY_userPic="userPic";
    public static final String UPLOAD_KEY_userPicStudent="userPicStudent";
    public static final String UPLOAD_KEY_userGender="userGender";
    public static final String UPLOAD_KEY_userRegion="userRegion";
    public static final String UPLOAD_KEY_userRegion2="userRegion2";
    public static final String UPLOAD_KEY_userUniversity="userUniversity";


    public static  String UPLOAD_Value_userName="";
    public static  String UPLOAD_Value_userID="";
    public static  String UPLOAD_Value_userEmail="";
    public static  String UPLOAD_Value_userCell="";
    public static String UPLOAD_Value_userPW="";
    public static  String UPLOAD_Value_userHeight="";
    public static  String UPLOAD_Value_userAge="";
    public static  String UPLOAD_Value_userJob="";
    public static String UPLOAD_Value_userPicStudent="";
    public static String UPLOAD_Value_userPic="";

    public static String UPLOAD_Value_userGender="";
    public static String UPLOAD_Value_userRegion="";
    public static String UPLOAD_Value_userRegion2="";
    public static String UPLOAD_Value_userUniversity="";
    public static String UPLOAD_Value_userPicSmall="";

    private View mProgressView2;
    private View mLoginFormView2;
    private int PICK_IMAGE_REQUEST =1;

    private EditText name,ID,Email,Cell,PW,PwRE,Height,Age,Job,univSetter;
    private CheckBox male;
    private CheckBox female;
    private Uri filePath;
    private ImageView imageView;
    View view;
    private Bitmap bitmap;
    static Bitmap smallBitmap;

    private Bitmap bitmapStudent;
    String isImageSelected="";
    String isImageSelected2="";
    Spinner regionSpinner;
    Spinner subRegionSpinner;

    String buttonVerifier="";


    Dialog infoPolicyDialog;

    ArrayAdapter<String> regionAdapter;



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


    ImageView imageStudentCard;



    static String theVerifierSaved;


    static String userEmail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_by_student_card);
        initialize();

        Age.setText("20");
    }

    public void initialize(){
        theVerifierSaved ="not";
        mLoginFormView2 = findViewById(R.id.login_form2);
        mProgressView2 = findViewById(R.id.login_progress2);


        infoPolicyDialog = new Dialog(this);
        infoPolicyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        infoPolicyDialog.setContentView(R.layout.dialog_info_policy);
        infoPolicyDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView okButton = (TextView) infoPolicyDialog.findViewById(R.id.IGOTIT);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPolicyDialog.dismiss();
            }
        });
        infoPolicyDialog.show();

        regionSpinner = (Spinner) findViewById(R.id.regionSpinner);
        subRegionSpinner =(Spinner) findViewById(R.id.subRegionSpinner);




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



        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getSelectedItem().toString().equals("서울")) {
                    subRegionSpinner.setAdapter(seoulAdapter);


                } else if (parent.getSelectedItem().toString().equals("경기")) {
                    subRegionSpinner.setAdapter(GyeonggiAdapter);

                } else if (parent.getSelectedItem().toString().equals("부산")) {
                    subRegionSpinner.setAdapter(BusanAdapter);

                } else if (parent.getSelectedItem().toString().equals("대구")) {
                    subRegionSpinner.setAdapter(DaeguAdapter);

                } else if (parent.getSelectedItem().toString().equals("인천")) {
                    subRegionSpinner.setAdapter(IncheonAdapter);

                } else if (parent.getSelectedItem().toString().equals("대전")) {
                    subRegionSpinner.setAdapter(DaejeonAdapter);

                } else if (parent.getSelectedItem().toString().equals("광주")) {
                    subRegionSpinner.setAdapter(GwanguAdapter);

                } else if (parent.getSelectedItem().toString().equals("울산")) {
                    subRegionSpinner.setAdapter(UlsanAdapter);

                } else if (parent.getSelectedItem().toString().equals("강원")) {
                    subRegionSpinner.setAdapter(GanwonAdapter);

                } else if (parent.getSelectedItem().toString().equals("충청")) {
                    subRegionSpinner.setAdapter(ChoongCheongAdapter);

                } else if (parent.getSelectedItem().toString().equals("전라")) {
                    subRegionSpinner.setAdapter(JeonLaSangAdapter);

                } else if (parent.getSelectedItem().toString().equals("세종")) {
                    subRegionSpinner.setAdapter(SejongAdapter);


                } else if (parent.getSelectedItem().toString().equals("제주")) {
                    subRegionSpinner.setAdapter(JejuAdapter);
                }

                UPLOAD_Value_userRegion = parent.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),UPLOAD_Value_userRegion,Toast.LENGTH_LONG).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        subRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UPLOAD_Value_userRegion2 = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        name = (EditText) findViewById(R.id.registerName);
        ID = (EditText) findViewById(R.id.registerID);
        Email = (EditText) findViewById(R.id.registerEmail);
        univSetter =(EditText) findViewById(R.id.univSetter);
        Cell = (EditText) findViewById(R.id.registerCell);
        PW = (EditText) findViewById(R.id.registerPassword);
        PwRE = (EditText) findViewById(R.id.registerPassword2);
        Height =(EditText) findViewById(R.id.registerHeight);
        Age =(EditText) findViewById(R.id.registerAge);
        Job = (EditText) findViewById(R.id.registerJob);





        male =(CheckBox) findViewById(R.id.checkBox);
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked ) {
                    UPLOAD_Value_userGender = "male";
                    female.setChecked(false);
                }
            }
        });
        female = (CheckBox) findViewById(R.id.checkBox2);
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    UPLOAD_Value_userGender = "female";
                    male.setChecked(false);
                }
            }
        });



        imageView = (ImageView) findViewById(R.id.mainPic);
        try {
            imageView.setImageDrawable(getAssetImage(getApplicationContext(),"pica"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setContentDescription("PHOTO");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                buttonVerifier ="picMain";

            }
        });

        imageStudentCard = (ImageView) findViewById(R.id.imageStudentCard);
        try {
            imageStudentCard.setImageDrawable(getAssetImage(getApplicationContext(),"pica"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageStudentCard.setContentDescription("PHOTO");
        imageStudentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
                buttonVerifier="picStudentCard";

            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( checkRequirements()) {

                    uploadRegistration();
                }
            }
        });
    }

    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".PNG")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public boolean checkRequirements(){


        boolean cancel = false;

        PW.setError(null);
        if (TextUtils.isEmpty(Job.getText().toString().trim())) {
            Job.setError(getString(R.string.error_field_required));
            Job.requestFocus();
            cancel = true;
        }

        if (TextUtils.isEmpty(Email.getText())) {
            Email.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(Email.getText().toString())) {
            Email.setError(getString(R.string.error_invalid_email));

            cancel = true;
        }
        if (TextUtils.isEmpty(univSetter.getText())) {
            univSetter.setError(getString(R.string.error_field_required));
            cancel = true;
        }
        //check for age field
        if (TextUtils.isEmpty(Age.getText().toString().trim())) {
            Age.setError(getString(R.string.error_field_required));
            Age.requestFocus();
            cancel = true;
        }  if (Integer.valueOf(Age.getText().toString()) >55 || Integer.valueOf(Age.getText().toString())<20) {
            Age.setError(getString(R.string.error_age));
            Age.requestFocus();
            cancel = true;
        }


        if (TextUtils.isEmpty(Height.getText().toString().trim())) {
            Height.setError(getString(R.string.error_field_required));
            Height.requestFocus();
            cancel = true;
        }

        //check for a valid password:1.larger than 8 character, 2.should not be empty, 3.PW==PwRe
        if (TextUtils.isEmpty(PW.getText().toString().trim()) || !isPasswordValid(PW.getText().toString().trim())) {
            PW.setError(getString(R.string.error_invalid_password));
            PW.requestFocus();
            cancel = true;
        }else if(!isPasswordRetyped()){
            PW.setError(getString(R.string.error_check_password));
            PW.requestFocus();
            cancel = true;
        }


        if (TextUtils.isEmpty(Cell.getText().toString().trim())) {
            Cell.setError(getString(R.string.error_field_required));
            Cell.requestFocus();
            cancel=true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(Email.getText().toString().trim())) {
            Email.setError(getString(R.string.error_field_required));
            Email.requestFocus();
            cancel = true;
        }

        //check for ID
        if (TextUtils.isEmpty(ID.getText().toString().trim())) {
            ID.setError(getString(R.string.error_field_required));
            ID.requestFocus();
            cancel = true;
        }
        //check for name
        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            name.setError(getString(R.string.error_field_required));
            name.requestFocus();
            cancel = true;
        }


        //check for male and female check
        if (!male.isChecked() && !female.isChecked()) {

            Snackbar.make(imageView, "성별을 확인해주세요.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            cancel = true;
        }
        if(isImageSelected.equals("yes")){

        }else {
            Snackbar.make(imageView, "프로필 사진을 선택해주세요", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            cancel = true;
        }

        if(isImageSelected2.equals("yes")){

        }else {
            Snackbar.make(imageView, "학생증 사진을 업로드해주세요", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            cancel = true;
        }


        if (cancel) {


            return false;
        } else{
            return true;
        }
    }

    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }
    private  boolean isPasswordRetyped(){
        if(PW.getText().toString().trim().equals(PwRE.getText().toString().trim()))
        {
            return true;
        }else
        {
            return false;
        }
    }

    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

    private void showFileChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //   startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGE_REQUEST);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), 1);
    }



    private String decodeString(String encoded) {
        byte[] dataDec = Base64.decode(encoded, Base64.DEFAULT);
        String decodedString = "";
        try {

            decodedString = new String(dataDec, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } finally {

            return decodedString;
        }
    }private Bitmap decodeFile(String filePath, Uri uri) {
        try {

            Bitmap b = null;

            File theFile = new File(filePath);
            int fileLimit =150;

            int fileSize = (int) theFile.length()/1024;

            int scale=2;
            if(fileSize>=fileLimit){
                scale= (int) Math.sqrt((double)fileSize /(double)fileLimit);
                if(scale <4){
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
            path = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!= null && data.getData() != null){

        if(requestCode==1 &&resultCode == RESULT_OK && data!= null && data.getData() != null){
            filePath = data.getData();
            String realPath;
            try {

                if (buttonVerifier.equals("picMain")){
                    // bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                    // bitmap = decodeFile(getRealPathFromUri2(filePath),filePath);
                    //  smallBitmap = decodeToSmallFile(getRealPathFromUri2(filePath),filePath);

                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        bitmap = decodeFile(realPath, data.getData());
                        smallBitmap = decodeToSmallFile(realPath, data.getData());
                        if (bitmap == null) {
                            bitmap = decodeFile(getRealPathFromURI(filePath), filePath);
                            smallBitmap = decodeToSmallFile(getRealPathFromURI(filePath), filePath);
                        }
                        // SDK >= 11 && SDK < 19
                    } else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                        bitmap = decodeFile(realPath, data.getData());
                        smallBitmap = decodeToSmallFile(realPath, data.getData());
                        if (bitmap == null) {
                            bitmap = decodeFile(getRealPathFromURI(filePath), filePath);
                            smallBitmap = decodeToSmallFile(getRealPathFromURI(filePath), filePath);
                        }
                        // SDK > 19 (Android 4.4)
                    } else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        bitmap = decodeFile(realPath, data.getData());
                        smallBitmap = decodeToSmallFile(realPath, data.getData());
                        if (bitmap == null) {
                            bitmap = decodeFile(getRealPathFromURI(filePath), filePath);
                            smallBitmap = decodeToSmallFile(getRealPathFromURI(filePath), filePath);
                        }
                    }
                     /*    Picasso.with(getApplicationContext())
                        .load(realPath)
                        .fit()
                        .centerCrop()
                        .into(imageView);*/
                imageView.setImageBitmap(bitmap);

                isImageSelected = "yes";

            } if(buttonVerifier.equals("picStudentCard")){



                    if (Build.VERSION.SDK_INT < 11) {
                        realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        bitmapStudent = decodeFile(realPath, data.getData());

                        if (bitmapStudent == null) {
                            bitmapStudent = decodeFile(getRealPathFromURI(filePath), filePath);

                        }
                        // SDK >= 11 && SDK < 19
                    } else if (Build.VERSION.SDK_INT < 19) {
                        realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                        bitmapStudent = decodeFile(realPath, data.getData());

                        if (bitmapStudent == null) {
                            bitmapStudent= decodeFile(getRealPathFromURI(filePath), filePath);
                            smallBitmap = decodeToSmallFile(getRealPathFromURI(filePath), filePath);
                        }
                        // SDK > 19 (Android 4.4)
                    } else {
                        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        bitmapStudent= decodeFile(realPath, data.getData());
                        smallBitmap = decodeToSmallFile(realPath, data.getData());
                        if (bitmapStudent== null) {
                            bitmapStudent = decodeFile(getRealPathFromURI(filePath), filePath);
                            smallBitmap = decodeToSmallFile(getRealPathFromURI(filePath), filePath);
                        }
                    }
                     /*    Picasso.with(getApplicationContext())
                        .load(realPath)
                        .fit()
                        .centerCrop()
                        .into(imageView);*/
                    imageStudentCard.setImageBitmap(bitmapStudent);

                    isImageSelected2 = "yes";










                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void uploadRegistration(){


        UPLOAD_Value_userRegion2 = subRegionSpinner.getSelectedItem().toString();
        UPLOAD_Value_userRegion = regionSpinner.getSelectedItem().toString();
        UPLOAD_Value_userUniversity = univSetter.getText().toString();

        UPLOAD_Value_userName= name.getText().toString().trim();

        UPLOAD_Value_userID= ID.getText().toString().trim();
        UPLOAD_Value_userEmail=Email.getText().toString();
        UPLOAD_Value_userCell =Cell.getText().toString().trim();
        UPLOAD_Value_userPW = PW.getText().toString().trim();
        UPLOAD_Value_userHeight =Height.getText().toString().trim();
        UPLOAD_Value_userAge = Age.getText().toString().trim();
        UPLOAD_Value_userJob= Job.getText().toString().trim();




        if(bitmap !=null && bitmapStudent !=null) {

            UPLOAD_Value_userPic = getStringImage(bitmap);
            UPLOAD_Value_userPicSmall = getStringImage(smallBitmap);

            UPLOAD_Value_userPicStudent = getStringImage(bitmapStudent);

            ut = new UploadTransfer();
            ut.execute(bitmapStudent);
        }else{
            Toast.makeText(getApplicationContext(),"이미지가 준비되지 않았습니다",Toast.LENGTH_LONG).show();

        }


    }

    class UploadImage extends AsyncTask<Bitmap,Void,String> {


        requestHandler rh = new requestHandler();

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            showProgress(true);

        }
        @Override
        protected  void onPostExecute(final String s){
            super.onPostExecute(s);
            showProgress(false);





            if(s.trim().equals("yes")){

                Snackbar.make(imageView, "가입완료! 관리자의 승인을 기다려주세요.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //TODO: Maybe I could put this functionality in the mainActivity.
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("LoginActivityVerifier", "login");
                editor.apply();
                //TODO: I will have to make a page where users can see it.


                CountDown _tik;

                _tik= new CountDown(2000,1000,RegistrationByStudentCard.this,logIn.class);
                _tik.start();



            }else if(s.trim().equals("no")){
                Snackbar.make(imageView, "가입 실패", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else if(s.trim().equals("changeID")){
                ID.setError(getString(R.string.error_duplicate_ID));
                ID.requestFocus();
            }else if(s.trim().equals("changeEmail")){

                Email.setError(getString(R.string.error_duplicate_Email));
                Email.requestFocus();

            }else if(s.trim().equals("changeID_EMAIL")){

                ID.setError(getString(R.string.error_duplicate_ID));
                ID.requestFocus();

                Email.setError(getString(R.string.error_duplicate_Email));


            }else if(s.trim().equals("1")){




            }else if(s.equals("Request Entity Too Large")){
                Snackbar.make(imageView, "사진 용량이 너무 큽니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            else {
                Snackbar.make(imageView, s, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }


        @Override
        protected String doInBackground(Bitmap...params){
            Bitmap bitmap = params[0];




            HashMap<String,String> data = new HashMap<>();

            data.put(UPLOAD_KEY_userName,UPLOAD_Value_userName);
            data.put(UPLOAD_KEY_userID,UPLOAD_Value_userID);
            data.put(UPLOAD_KEY_userEmail,UPLOAD_Value_userEmail);
            data.put(UPLOAD_KEY_userCell,UPLOAD_Value_userCell);
            data.put(UPLOAD_KEY_userPW,UPLOAD_Value_userPW);
            data.put(UPLOAD_KEY_userHeight,UPLOAD_Value_userHeight);
            data.put(UPLOAD_KEY_userAge,UPLOAD_Value_userAge);
            data.put(UPLOAD_KEY_userJob, UPLOAD_Value_userJob);
            data.put(UPLOAD_KEY_userPicSmall,UPLOAD_Value_userPicSmall);
            data.put(UPLOAD_KEY_userPic,UPLOAD_Value_userPic);

            data.put(UPLOAD_KEY_userGender,UPLOAD_Value_userGender);

            data.put(UPLOAD_KEY_userRegion, UPLOAD_Value_userRegion);
            data.put(UPLOAD_KEY_userRegion2,UPLOAD_Value_userRegion2);
            data.put(UPLOAD_KEY_userUniversity,UPLOAD_Value_userUniversity);

            if(UPLOAD_Value_userGender.equals("male")){
                String result = rh.sendPostRequest(UPLOAD_URL_BOYS,data);
                return result;
            }
            else if(UPLOAD_Value_userGender.equals("female")){
                String result = rh.sendPostRequest(UPLOAD_URL_GIRLS,data);
                return result;
            }else{
                return "Cannot make sendPOST";
            }


        }
    }


    class UploadTransfer extends AsyncTask<Bitmap,Void,String> {


        requestHandler rh = new requestHandler();

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            showProgress(true);

        }
        @Override
        protected  void onPostExecute(final String s){
            super.onPostExecute(s);
            showProgress(false);

            if(s.trim().equals("yes")){

                ui = new UploadImage();
                ui.execute(bitmap);

            }else if(s.trim().equals("no")){

            }

          else if(s.equals("Request Entity Too Large")){
                Snackbar.make(imageView, "사진 용량이 너무 큽니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            else {
                Snackbar.make(imageView, s +"1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }


        @Override
        protected String doInBackground(Bitmap...params){
            Bitmap bitmap = params[0];
            HashMap<String,String> data = new HashMap<>();
            data.put(UPLOAD_KEY_userName,UPLOAD_Value_userName);
            data.put(UPLOAD_KEY_userID,UPLOAD_Value_userID);
            data.put(UPLOAD_KEY_userEmail,UPLOAD_Value_userEmail);
            data.put(UPLOAD_KEY_userCell,UPLOAD_Value_userCell);
            data.put(UPLOAD_KEY_userPicStudent, UPLOAD_Value_userPicStudent);
            data.put(UPLOAD_KEY_userUniversity,UPLOAD_Value_userUniversity);

            if(UPLOAD_Value_userGender.equals("male")){
                data.put(UPLOAD_KEY_userGender,"boys");
                String result = rh.sendPostRequest(UPLOAD_URL_TRANSFER,data);
                return result;
            }
            else if(UPLOAD_Value_userGender.equals("female")){
                data.put(UPLOAD_KEY_userGender,"girls");
                String result = rh.sendPostRequest(UPLOAD_URL_TRANSFER,data);
                return result;
            }else{
                return "Cannot make sendPOST";
            }


        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView2.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView2.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView2.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView2.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView2.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView2.setVisibility(show ? View.GONE : View.VISIBLE);
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
            Intent intent = new Intent(_act,_cls);
            startActivity(intent);
            _act.finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }


    }








    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Destroyed", "Registration Activity was Destroyed");

        if(imageView !=null){
            imageView.setImageDrawable(null);
        }
        if(imageStudentCard !=null){
            imageStudentCard.setImageDrawable(null);
        }

        System.gc();


    }

}
