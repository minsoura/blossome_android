package com.minsoura.findit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;

public class view_dateProfile extends AppCompatActivity {
    static String DateEmail;
    static String UserGender;
    static String UserEmail;

    static String userID;
    static String userRegion;
    static  String userRegion2;
    static String userUniversity;
    static  String genderIndex;
 public  static String sendMessage="hi";
    UserLikingTask likingTask;
    View view;
    static  String size;
    static String listIndex;



    final Context context = this;
    TextView view_id;
    TextView view_age;
    TextView view_height;
    TextView view_job;
    TextView view_region;
    TextView view_SayHi;
    TextView view_univ;

    static Integer saveKeyInt;
    public static String getUserID = "";
    public static String getUserHeight = "";
    public static String getUserAge = "";
    public static String getUserJob = "";
    public static String getUserSayHi = "";
    public static String getUserRegion = "";
    public static String getUserRegion2 = "";
    public static String getUserUniv = "";

    static String[] getUserStringImage;
    //region,or school or userPicFive to be added later

    ImageView view_main;
    ImageView view_userMain;
    ImageView view_userPic2;
    ImageView view_userPic3;
    ImageView view_userPic4;
    ImageView view_userPic5;
    downloadProfile2 task2;

    String buttonVerifier;



    static String escapeIndex;
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


    private View mProgressView;
    private View wrapper_View;

    static String dialogController;
    static  String combinedCOntroller;
    FloatingActionButton fab2;
    static   String sendorwhat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_date_profile);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);

        Intent intentR = getIntent();
        DateEmail = intentR.getStringExtra("DateEmail");
        UserGender = intentR.getStringExtra("UserGender");
        UserEmail = intentR.getStringExtra("UserEmail");

        size = intentR.getStringExtra("size");
        listIndex = intentR.getStringExtra("listIndex");
        userID = intentR.getStringExtra("userID");

         userRegion = intentR.getStringExtra("userRegion");
         userRegion2 = intentR.getStringExtra("userRegion2");
         userUniversity = intentR.getStringExtra("userUniversity");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        saveKeyInt = sharedPreferences.getInt("userKey", 0);

        getUserStringImage = new String[5];
        getUserStringImage[0] = "";
        getUserStringImage[2] = "";
        getUserStringImage[1] = "";
        getUserStringImage[3] = "";
        getUserStringImage[4] = "";


        Integer sizeFromString = Integer.valueOf(size);

        final SharedPreferences.Editor editor = pref.edit();
        //TODO: erase it for real USAGE



        //TODO:

        sendorwhat = pref.getString(DateEmail, "yes");
        combinedCOntroller = DateEmail + dialogController;

        mProgressView = findViewById(R.id.view_DateProgress);
        wrapper_View = findViewById(R.id.DateWrapper);


        view = findViewById(R.id.view_DateMainPic);

        wrapper = (ScrollView) findViewById(R.id.DateWrapper);

        escapeIndex = "yes";
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        buttonVerifier = "";


        bitmapGroup = new ArrayList<>();
        allocator = new ArrayList<>();

        detector = new GestureDetector(this, new SwipeGestureDetector());


        bitmapGroup.add(0, null);
        bitmapGroup.add(1, null);
        bitmapGroup.add(2, null);
        bitmapGroup.add(3, null);
        bitmapGroup.add(4, null);



        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (sendorwhat.equals("yes")) {


                    final  Dialog dialog = new Dialog(view_dateProfile.this);


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_form);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final EditText dialogMessage = (EditText) dialog.findViewById(R.id.dialogMessage);
                    TextView toID = (TextView) dialog.findViewById(R.id.dialogID);
                   final ImageView dialogImage = (ImageView) dialog.findViewById(R.id.dialogImage);
                     TextView toKey =(TextView) dialog.findViewById(R.id.toKey);



                    toKey.setText(Integer.toString(saveKeyInt));


                    toID.setText(userID);
                    Random r = new Random();
                    int loaderIndex = r.nextInt(10 - 1) + 1;
                    String stringIndex = Integer.toString(loaderIndex);

                    if(UserGender.equals("boys")){
                        genderIndex = "girls";
                    }else if(UserGender.equals("girls")){
                        genderIndex ="boys";
                    }


                    final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + DateEmail + "&userGender=" + genderIndex;
                    Picasso.with(getApplicationContext())
                            .load(URL)
                            .fit()
                            .centerInside()
                            .transform(new CircleTransformation())
                            .into(dialogImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Random r = new Random();
                                    int loaderIndex = r.nextInt(10 - 1) + 1;
                                    String stringIndex = Integer.toString(loaderIndex);

                                    if(UserGender.equals("boys")){
                                        genderIndex = "girls";
                                    }else if(UserGender.equals("girls")){
                                        genderIndex ="boys";
                                    }
                                    final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + DateEmail + "&userGender=" + genderIndex;
                                    Picasso.with(getApplicationContext())
                                            .load(URL)
                                            .fit()
                                            .centerInside()
                                            .transform(new CircleTransformation())
                                            .into(dialogImage, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError() {
                                                    Random r = new Random();
                                                    int loaderIndex = r.nextInt(10 - 1) + 1;
                                                    String stringIndex = Integer.toString(loaderIndex);

                                                    if(UserGender.equals("boys")){
                                                        genderIndex = "girls";
                                                    }else if(UserGender.equals("girls")){
                                                        genderIndex ="boys";
                                                    }
                                                    final   String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + DateEmail + "&userGender=" + genderIndex;
                                                    Picasso.with(getApplicationContext())
                                                            .load(URL)
                                                            .fit()
                                                            .centerInside()
                                                            .transform(new CircleTransformation())
                                                            .into(dialogImage);



                                                }
                                            });
                                }
                            });


                    final TextView sendText = (TextView) dialog.findViewById(R.id.dialogSend);
                    sendText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            editor.putString(DateEmail, "sent");
                            editor.apply();
                            sendorwhat ="sent";
                            sendMessage = dialogMessage.getText().toString();


                            sendLike();
                            dialog.dismiss();




                        }
                    });

                    dialog.show();
                }else{
                    Snackbar.make(view, "이미 메세지를 보냈습니다.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }





            }
        });

        view_SayHi = (TextView) findViewById(R.id.view_DateSayHi);

        //  change_userPic5 = (TextView) findViewById(R.id.view_change_userPic5);

        view_id = (TextView) findViewById(R.id.view_DateID);
        view_age = (TextView) findViewById(R.id.view_DateAge_value);
        view_height = (TextView) findViewById(R.id.view_DateHeight_value);
        view_job = (TextView) findViewById(R.id.view_DateJob_value);
        view_univ =(TextView) findViewById(R.id.view_DateUni_value);
        view_region =(TextView) findViewById(R.id.view_DateRegion_value);

        view_main = (ImageView) findViewById(R.id.view_DateMainPic);
        view_userMain = (ImageView) findViewById(R.id.view_DatePicOne);
        view_userPic2 = (ImageView) findViewById(R.id.view_DatePicTwo);
        view_userPic3 = (ImageView) findViewById(R.id.view_DatePicThree);
        view_userPic4 = (ImageView) findViewById(R.id.view_DatePicFour);
        view_userPic5 = (ImageView) findViewById(R.id.view_DatePicFive);


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

                if (bitmapGroup.get(1) != null) {
                    zoomImageFromThumb(v, 1);
                }

            }
        });


        view_userPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapGroup.get(2) != null) {
                    zoomImageFromThumb(v, 2);
                }
            }
        });


        view_userPic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapGroup.get(3) != null) {
                    zoomImageFromThumb(v, 3);
                }

            }
        });
        view_userPic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmapGroup.get(4) != null) {
                    zoomImageFromThumb(v, 4);
                }

            }
        });


        bringProfile3();
    }

    public void sendLike(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        saveKeyInt = sharedPreferences.getInt("userKey", 0);
        if(saveKeyInt - 2>=0) {
            String dialogMessage = sendMessage;
            likingTask = new UserLikingTask(DateEmail, UserEmail, dialogMessage);
            likingTask.execute();
        }else
        {
            Toast.makeText(view_dateProfile.this, "키가 부족합니다.", Toast.LENGTH_SHORT).show();
        }


    }




    public class UserLikingTask extends AsyncTask<Void, Void, String> {
        private final String mDateEmail;

        private final String mUserEmail;

        private final String mText;
        Boolean likingTaskChecker = true;

        UserLikingTask(String DateEmail, String UserEmail,String loveMessage) {
            mDateEmail = DateEmail;
            mUserEmail = UserEmail;
            mText = loveMessage;


        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected String doInBackground(Void... params) {

            String URL="";

            if(UserGender.equals("boys")) {

              URL = "http://minsoura.xyz/SendLikeToGIrlsForBoys.php";

            }else if(UserGender.equals("girls")){

               URL = "http://minsoura.xyz/SendLikeToBoysForGirls.php";

            }


            HashMap<String, String> SendSet = new HashMap<>();
            SendSet.put("text", mText);
            SendSet.put("dateEmail", mDateEmail);
            SendSet.put("userEmail", mUserEmail);





            try {
                if(!likingTaskChecker){
                    return null;
                }
                requestHandler LikeDeliverHandler = new requestHandler();
                String result = LikeDeliverHandler.sendPostRequest(URL, SendSet);
                if(!likingTaskChecker){
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
            showProgress(false);
            if (receivedLine.equals("yes") && !receivedLine.isEmpty()) {

                Snackbar.make(view, "메세지가 전달 되었습니다!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                saveKeyInt = sharedPreferences.getInt("userKey", 0);

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final SharedPreferences.Editor editor = pref.edit();
                editor.putInt("userKey", saveKeyInt - 2);
                editor.apply();

                //TODO: I could make a intent that delivers retrieved data from the DB such as the ones for the USER PROFILE;

            } else if (receivedLine.equals("no")) {
                Snackbar.make(view, "메세지가 전달 되지 않았습니다.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
            likingTaskChecker = false;

        }
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
                            fab2.setVisibility(View.VISIBLE);
                            mCurrentAnimator = null;

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            thumbView.setAlpha(1f);
                            expandedImageView.setVisibility(View.GONE);
                            wrapper.setVisibility(View.VISIBLE);
                            fab2.setVisibility(View.VISIBLE);
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


        public void bringProfile3() {
            //retrieve an intent with the string of userEmail for query
            String userEmailAsKey = DateEmail;
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
        protected String doInBackground(String... userEmail) {
            String URL = "";
            if (UserGender.equals("boys")) {
                URL = "http://minsoura.xyz/downloadProfileGirls.php";

            } else if (UserGender.equals("girls")) {
                URL = "http://minsoura.xyz/downloadProfileBoys.php";

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
            if(task2Checker)
            {
            // String imageTest="";
            //  String getUserID="";
            //   String getUserHeight="";
            //   String getUserAge="";
            //   String getUserJob="";
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
                        getUserUniv = jsonObject2.getString("userUniversity");


                    }

                } catch (Exception e) {
                    Log.d("tag", e.getMessage());
                    e.printStackTrace();
                }


                //    Toast.makeText(getApplicationContext(), imageTest, Toast.LENGTH_LONG).show();


                // view_main.setImageBitmap(StringToBitmap);
                //view_userMain.setImageBitmap(StringToBitmap);
                if (task2Checker) {
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

                    view_univ.setText(getUserUniv);
                    view_region.setText(getUserRegion + " " + getUserRegion2);
                    view_id.setText(getUserID);
                    view_height.setText(getUserHeight + "CM");
                    view_age.setText(getUserAge);
                    view_job.setText(getUserJob);
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

        public Bitmap getBitmapImage(String stringImage) {

            Bitmap StringToBitmap;
            byte[] decodedString = Base64.decode(stringImage, Base64.DEFAULT);
            StringToBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            return StringToBitmap;
        }

        @Override
        public void onBackPressed() {

            if (escapeIndex.equals("no")) {
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
                escapeIndex = "yes";
                mCurrentAnimator = null;
            } else {
                super.onBackPressed();
            }
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

        if(likingTask !=null){
            likingTask.likingTaskChecker = false;
            likingTask = null;

        }if(task2 !=null){
            task2.task2Checker = false;
            task2 = null;
        }

       // expandedImageView.setImageDrawable(null);

        bitmapGroup.clear();
        System.gc();


    }


}
