package com.minsoura.findit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import android.widget.ImageView;
import android.widget.ScrollView;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;



public class ViewDateFromMessage  extends AppCompatActivity {
    static  Bitmap StringToBitmap;

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

    downloadProfile2 task2;

    TextView view_id;
    TextView view_age;
    TextView view_height;
    TextView view_job;
    TextView view_university;

    static String view_region;
    static String view_region2;



    TextView view_SayHi;


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

    static String userEmailIntent2, userGender2;


    String buttonVerifier;
    static   String imageSelected1;
    static String imageSelected2;
    static String imageSelected3;
    static   String imageSelected4;
    static String imageSelected5;


    static  String[] imageSelected;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_date_from_message);
        Intent intentR = getIntent();

        userEmailIntent2 = intentR.getStringExtra("user_Email_FM");
        userGender2 = intentR.getStringExtra("user_Gender_FM");

        getUserStringImage = new String[5];
        getUserStringImage[0]="";
        getUserStringImage[1]="";
        getUserStringImage[2]="";
        getUserStringImage[3]="";
        getUserStringImage[4]="";




        mProgressView = findViewById(R.id.view_DateProgress);
        wrapper_View = findViewById(R.id.DateWrapper);


       view = findViewById(R.id.view_DateMainPic);

        wrapper = (ScrollView) findViewById(R.id.DateWrapper);

        view_university =(TextView) findViewById(R.id.view_DateUni_value);


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


        view_SayHi = (TextView) findViewById(R.id.view_DateSayHi);


        view_id = (TextView) findViewById(R.id.view_DateID);
        view_age =(TextView) findViewById(R.id.view_DateAge_value);

        view_height = (TextView) findViewById(R.id.view_DateHeight_value);
        view_job = (TextView) findViewById(R.id.view_DateJob_value);


        view_main = (ImageView) findViewById(R.id.view_DateMainPic);
        view_userMain = (ImageView) findViewById(R.id.view_DatePicOne);
        view_userPic2 = (ImageView) findViewById(R.id.view_DatePicTwo);
        view_userPic3 = (ImageView) findViewById(R.id.view_DatePicThree);
        view_userPic4 = (ImageView) findViewById(R.id.view_DatePicFour);
        view_userPic5 =(ImageView) findViewById(R.id.view_DatePicFive);

        view_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zoomImageFromThumb(v, 0);

            }
        });






        view_userMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                zoomImageFromThumb(v, 0);


            }
        });





        view_userPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (imageSelected[1].equals("yes")) {
                    zoomImageFromThumb(v, 1);
                } else if (bitmapGroup.get(1) != null) {
                    zoomImageFromThumb(v, 1);
                }

            }
        });





        view_userPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected[2].equals("yes")) {
                    zoomImageFromThumb(v, 2);
                } else if (bitmapGroup.get(2) != null) {
                    zoomImageFromThumb(v, 2);
                }
            }
        });





        view_userPic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected[3].equals("yes")) {
                    zoomImageFromThumb(v, 3);
                } else if (bitmapGroup.get(3) != null) {
                    zoomImageFromThumb(v, 3);
                }

            }
        });


        view_userPic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageSelected[4].equals("yes")) {
                    zoomImageFromThumb(v, 4);
                } else if (bitmapGroup.get(4) != null) {
                    zoomImageFromThumb(v, 4);
                }

            }
        });


        bringProfile2();


    }

    public void bringProfile2() {
        //retrieve an intent with the string of userEmail for query
        String userEmailAsKey = userEmailIntent2;
       task2 = new downloadProfile2();
        task2.execute(userEmailAsKey);
    }


    class downloadProfile2 extends AsyncTask<String, Void, String> {
        Boolean checker = true;

        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected String doInBackground(String... userEmail) {
            String URL = "";
            if(!checker){
                return null;
            }
            if (userGender2.equals("boys")) {
                URL = "http://minsoura.xyz/downloadProfileGirls.php";
            } else if (userGender2.equals("girls")) {
                URL = "http://minsoura.xyz/downloadProfileBoys.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("userEmail", userEmail[0]);

            try {

                requestHandler profileHandler2 = new requestHandler();
                String result = profileHandler2.bringUserProfile(URL, emailSet);
                if(!checker){
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
            if(checker)
            {
            if (!s.isEmpty() && s.equals("noProfile")) {

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


                //    Toast.makeText(getApplicationContext(), imageTest, Toast.LENGTH_LONG).show();
                byte[] decodedString = android.util.Base64.decode(getUserStringImage[0], android.util.Base64.DEFAULT);
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
                view_age.setText(getUserAge);
                view_job.setText(getUserJob);
                view_university.setText(getUserUniversity);

                view_region = getUserRegion;
                view_region2 = getUserRegion2;


                if (getUserSayHi != null) {
                    view_SayHi.setText(getUserSayHi);
                }


                // getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.ImageColumns.TITLE + "=?", new String[]{userEmail});
            }
        }

            showProgress(false);
            checker =false;
        }

    }
    public Bitmap getBitmapImage(String stringImage){

        byte[] decodedString = android.util.Base64.decode(stringImage, Base64.DEFAULT);
        StringToBitmap  = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return StringToBitmap;
    }


    private void zoomImageFromThumb(final View thumbView, int integer) {

        escapeIndex = "no";

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        expandedImageView = (ImageView) findViewById(R.id.expanded_image);

        expandedImageView.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (detector.onTouchEvent(event)) {
                    escapeIndex = "yes";
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
        findViewById(R.id.DateContainer).getGlobalVisibleRect(finalBounds, globalOffset);

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
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mCurrentAnimator != null) {
                    escapeIndex = "yes";
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

                        mCurrentAnimator = null;

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        wrapper.setVisibility(View.VISIBLE);

                        mCurrentAnimator = null;

                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            try {

                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    if (bitmapGroup.size() > j) {
                        j++;

                        if (j < bitmapGroup.size()) {

                            expandedImageView.setImageBitmap(bitmapGroup.get(j));
                            return true;
                        } else {
                            j = 0;
                            expandedImageView.setImageBitmap(bitmapGroup.get(j));
                            return true;
                        }
                    }
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                    if (j > 0) {
                        j--;
                        expandedImageView.setImageBitmap(bitmapGroup.get(j));
                        return true;
                    } else {
                        j = bitmapGroup.size() - 1;
                        expandedImageView.setImageBitmap(bitmapGroup.get(j));
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

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

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            wrapper_View.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    @Override
    public void onBackPressed() {

        if (escapeIndex.equals("no")) {
            view_main.setAlpha(1f);
            view_userMain.setAlpha(1f);
            view_userPic2.setAlpha(1f);
            view_userPic3.setAlpha(1f);
            view_userPic4.setAlpha(1f);

            expandedImageView.setVisibility(View.GONE);
            // view_main.setImageBitmap(bitmapGroup.get(0));
            wrapper.setVisibility(View.VISIBLE);

            escapeIndex = "yes";
            mCurrentAnimator = null;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        Log.e("Destroyed", "ViewDateFromMessage Activity was Destroyed");
        // Save the note's current draft, because the activity is stopping
        // and we want to be sure the current note progress isn't lost.
        view_main.setImageDrawable(null);
        view_userMain.setImageDrawable(null);
        view_userPic2.setImageDrawable(null);
        view_userPic3.setImageDrawable(null);
        view_userPic4.setImageDrawable(null);
        view_userPic5.setImageDrawable(null);
        //expandedImageView.setImageDrawable(null);

        if(task2 !=null){
            task2.cancel(true);
            task2.checker = false;
        }
        bitmapGroup.clear();
        System.gc();


    }
}
