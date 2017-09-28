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

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class Tab3 extends Fragment {
    private TableLayout container3;
    private TableLayout continaer4;

    private View mProgressView;
    private View wrapper_View;
    private View wrapper_View2;
    private View tab3_layout;

    static String sendVerifier[];
    static TextView myKey;
   ImageView finalCLick;
    TextView lockStatus;
    static String userGender3;

    static String userEmail;
    UserDecisionTask decisionTask;

    static Integer saveKeyInt;


    static String myID;
    static String genderIndex;
    Dialog b4UnlockingDialog;
   Dialog dialog;
    static String getUserID;
 static String getUserMessage;
    static String getType;
    static String getDateEmail;
    static String getUserCell;
    static String getUserdDay;

    static ArrayList<ProfileStorage> listStorages = new ArrayList<>();
    static  String[] listImagePath;


    downloadProfile3 task3;

    @Override
         public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View v =inflater.inflate(R.layout.tab_3,container,false);

        container3 = (TableLayout) v.findViewById(R.id.PendngCardContainer);
        continaer4 =(TableLayout) v.findViewById(R.id.ConnectedContainer);


        userEmail = getActivity().getIntent().getStringExtra("userEmail");
        userGender3 =getActivity().getIntent().getStringExtra("userGender");


        myKey =(TextView) v.findViewById(R.id.connected_key);


        mProgressView = v.findViewById(R.id.tab3_progress);
        wrapper_View = v.findViewById(R.id.tab3_wrapper);
        wrapper_View2 = v.findViewById(R.id.tab3_wrapper2);
        tab3_layout = v.findViewById(R.id.tab3_layout);


        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                bringProfile3();
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                saveKeyInt = pref.getInt("userKey", 0);
                myKey.setText(Integer.toString(saveKeyInt));

            }
        });

        b4UnlockingDialog = new Dialog(getActivity());


        b4UnlockingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        b4UnlockingDialog.setContentView(R.layout.dialog_before_unlocking);
        b4UnlockingDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.cell_dialog);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        DefaultInflatorOne();
        DefaultInflatorTwo();

        bringProfile3();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        saveKeyInt = pref.getInt("userKey", 0);
        myKey.setText(Integer.toString(saveKeyInt));
        return v;


    }
    public void  DefaultInflatorOne(){
        LayoutInflater inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addedList = inflater.inflate(R.layout.list_default_from,null);





        container3.addView(addedList);


    }  public void  DefaultInflatorTwo(){
        LayoutInflater inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View conList = inflater.inflate(R.layout.list_default_connected,null);





        continaer4.addView(conList);

    }






    public void  ListInflatorFrom(final int indexF){
        LayoutInflater inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addedList = inflater.inflate(R.layout.list_from,null);

        final ImageView listImageFrom = (ImageView) addedList.findViewById(R.id.ListimageFrom);
        listImageFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getActivity(),ViewDateFromMessage.class);
                newIntent.putExtra("user_Email_FM",listStorages.get(indexF).getUserEmail());
                newIntent.putExtra("user_Gender_FM",userGender3);
                startActivity(newIntent);
            }
        });


        if(!listStorages.get(indexF).getUserEmail().equals("")){

            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            if(userGender3.equals("boys")){
                genderIndex = "girls";
            }else if(userGender3.equals("girls")){
                genderIndex ="boys";
            }
            final String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + listStorages.get(indexF).getUserEmail() + "&userGender=" + genderIndex;

            Picasso.with(getContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(listImageFrom, new Callback() {
                        @Override
                        public void onSuccess() {



                        }

                        @Override
                        public void onError() {
                            Random r = new Random();
                            int loaderIndex = r.nextInt(10 - 1) + 1;
                            String stringIndex = Integer.toString(loaderIndex);

                            if (userGender3.equals("boys")) {
                                genderIndex = "girls";
                            } else if (userGender3.equals("girls")) {
                                genderIndex = "boys";
                            }
                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexF).getUserEmail() + "&userGender=" + genderIndex;
                            Picasso.with(getContext())
                                    .load(URL)
                                    .fit()
                                    .centerInside()
                                    .transform(new CircleTransformation())
                                    .into(listImageFrom, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Random r = new Random();
                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                            String stringIndex = Integer.toString(loaderIndex);

                                            if (userGender3.equals("boys")) {
                                                genderIndex = "girls";
                                            } else if (userGender3.equals("girls")) {
                                                genderIndex = "boys";
                                            }
                                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexF).getUserEmail() + "&userGender=" + genderIndex;
                                            Picasso.with(getContext())
                                                    .load(URL)
                                                    .fit()
                                                    .centerInside()
                                                    .transform(new CircleTransformation())
                                                    .into(listImageFrom, new Callback() {
                                                        @Override
                                                        public void onSuccess() {

                                                        }

                                                        @Override
                                                        public void onError() {
                                                            Random r = new Random();
                                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                                            String stringIndex = Integer.toString(loaderIndex);

                                                            if (userGender3.equals("boys")) {
                                                                genderIndex = "girls";
                                                            } else if (userGender3.equals("girls")) {
                                                                genderIndex = "boys";
                                                            }
                                                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexF).getUserEmail() + "&userGender=" + genderIndex;
                                                            Picasso.with(getContext())
                                                                    .load(URL)
                                                                    .fit()
                                                                    .centerInside()
                                                                    .transform(new CircleTransformation())
                                                                    .into(listImageFrom);
                                                        }
                                                    });

                                        }
                                    });

                        }
                    });

        }
        TextView listIdFrom = (TextView) addedList.findViewById(R.id.ListIDFrom);
        listIdFrom.setText(listStorages.get(indexF).getUserID());

        TextView listMessageFrom = (TextView) addedList.findViewById(R.id.ListMessageFrom);
        listMessageFrom.setText(listStorages.get(indexF).getMessage());



        TextView listYesFrom = (TextView) addedList.findViewById(R.id.listYesFrom);

        listYesFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //direct
                //yes/no--> container3.removeAt
                //ConnectionInflator-->bring the listStorages and inflate it again
                //get the indexF or indexT to find where it is located
                //in containerFinal using list_cnnected.xml
                // Snackbar.make(getView(), "Congratulation!", Snackbar.LENGTH_LONG)
                //         .setAction("Action", null).show();


                sendDecision(listStorages.get(indexF).getUserEmail(), userEmail, "yes");


            }
        });

        TextView listNoFrom = (TextView) addedList.findViewById(R.id.listNoFrom);
        listNoFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //direct
                //yes/no--> container3.removeAt

                Snackbar.make(getView(), "Card Eliminated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                sendDecision(listStorages.get(indexF).getUserEmail(), userEmail, "no");
            }
        });










        container3.addView(addedList);

    }
    public void  ListInflatorTo(int indexT){
        LayoutInflater inflater =(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addedList = inflater.inflate(R.layout.list_to,null);



        ImageView listImageTo = (ImageView) addedList.findViewById(R.id.ListImageTo);

        if(!listStorages.get(indexT).getUserEmail().equals("")){
            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            if(userGender3.equals("boys")){
                genderIndex = "girls";
            }else if(userGender3.equals("girls")){
                genderIndex ="boys";
            }
            String URL="http://minsoura.xyz/imageLoader" + stringIndex +".php?userEmail=" + listStorages.get(indexT).getUserEmail() + "&userGender=" + genderIndex;


            Picasso.with(getActivity().getApplicationContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(listImageTo);

        }

        TextView listIdTo = (TextView) addedList.findViewById(R.id.LIstIdTo);
        listIdTo.setText(listStorages.get(indexT).getUserID());

        TextView listMessageTo = (TextView) addedList.findViewById(R.id.ListMessageTo);
        listMessageTo.setText(listStorages.get(indexT).getMessage());






        container3.addView(addedList);

    }





    public void ConnectionInflator(final int indexC) {

        if (task3 != null){

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View addedList = inflater.inflate(R.layout.list_connected, null);
        lockStatus = (TextView) addedList.findViewById(R.id.lockStatus);
        final ImageView imageViewCon1 = (ImageView) addedList.findViewById(R.id.ListimageView1);
        final ImageView imageViewCon2 = (ImageView) addedList.findViewById(R.id.ListimageView2);
        imageViewCon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(getActivity(), ViewDateFromMessage.class);
                theIntent.putExtra("user_Email_FM", listStorages.get(indexC).getUserEmail());
                theIntent.putExtra("user_Gender_FM", userGender3);
                startActivity(theIntent);
            }
        });
        TextView theD_Day = (TextView) addedList.findViewById(R.id.d_day);
        finalCLick = (ImageView) addedList.findViewById(R.id.finalClick);

        try {
            finalCLick.setImageDrawable(getAssetImage(getActivity(), "locked"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (listStorages.get(indexC).getType().equals("yesUnlocked")) {

            try {
                finalCLick.setImageDrawable(getAssetImage(getActivity(), "unlocked"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            lockStatus.setText("");

            //
        }
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        myID = pref.getString("userID", "");

        if (listStorages.get(indexC).getType().equals("yes")) {


            myID = pref.getString("userID", "");
            saveKeyInt = pref.getInt("userKey", 0);
            myKey.setText(Integer.toString(saveKeyInt));

        }
        //no reduction of hearts or points
        TextView textViewCon1 = (TextView) addedList.findViewById(R.id.ListID1);
        TextView textViewCon2 = (TextView) addedList.findViewById(R.id.ListID2);

        finalCLick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listStorages.get(indexC).getType().equals("yes")) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    saveKeyInt = sharedPreferences.getInt("userKey", 0);
                    if (saveKeyInt - 15 >= 0) {


                        b4UnlockingDialog.show();

                        TextView unlockCancel = (TextView) b4UnlockingDialog.findViewById(R.id.unlockCancel);
                        unlockCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                b4UnlockingDialog.dismiss();
                            }
                        });
                        TextView unlockYes = (TextView) b4UnlockingDialog.findViewById(R.id.unlockYes);
                        unlockYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                sendDecisionVersion(listStorages.get(indexC).getUserEmail(), userEmail, "yesUnlocked", listStorages.get(indexC).getUserID(), listStorages.get(indexC).getUserCell());
                                b4UnlockingDialog.dismiss();
                            }
                        });


                    } else {
                        Toast.makeText(getActivity(), "키가 부족합니다", Toast.LENGTH_LONG).show();
                    }
                    //this is where a point or hear shall be reduced upon opening the lock
                }
                if (listStorages.get(indexC).getType().equals("yesUnlocked")) {
                    final Dialog dialog = new Dialog(getActivity());


                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.cell_dialog);
                    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    final TextView cell_ID = (TextView) dialog.findViewById(R.id.cell_Id);
                    TextView cell_cell = (TextView) dialog.findViewById(R.id.cell_userCell);
                    TextView cell_call = (TextView) dialog.findViewById(R.id.cell_call);
                    final ImageView cell_image = (ImageView) dialog.findViewById(R.id.cell_image);


                    cell_call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse("tel:" + listStorages.get(indexC).getUserCell());
                            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                            startActivity(intent);
                        }
                    });


                    if (!listStorages.get(indexC).getUserEmail().equals("")) {
                        Random r = new Random();
                        int loaderIndex = r.nextInt(10 - 1) + 1;
                        String stringIndex = Integer.toString(loaderIndex);

                        if (userGender3.equals("boys")) {
                            genderIndex = "girls";
                        } else if (userGender3.equals("girls")) {
                            genderIndex = "boys";
                        }
                        final String URL = "http://minsoura.xyz/miniImageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;


                        Picasso.with(getContext())
                                .load(URL)
                                .fit()
                                .centerInside()
                                .transform(new CircleTransformation())
                                .into(cell_image, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Random r = new Random();
                                        int loaderIndex = r.nextInt(10 - 1) + 1;
                                        String stringIndex = Integer.toString(loaderIndex);

                                        if (userGender3.equals("boys")) {
                                            genderIndex = "girls";
                                        } else if (userGender3.equals("girls")) {
                                            genderIndex = "boys";
                                        }
                                        final String URL = "http://minsoura.xyz/miniImageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;

                                        Picasso.with(getContext())
                                                .load(URL)
                                                .fit()
                                                .centerInside()
                                                .transform(new CircleTransformation())
                                                .into(cell_image, new Callback() {
                                                    @Override
                                                    public void onSuccess() {

                                                    }

                                                    @Override
                                                    public void onError() {
                                                        Random r = new Random();
                                                        int loaderIndex = r.nextInt(10 - 1) + 1;
                                                        String stringIndex = Integer.toString(loaderIndex);

                                                        if (userGender3.equals("boys")) {
                                                            genderIndex = "girls";
                                                        } else if (userGender3.equals("girls")) {
                                                            genderIndex = "boys";
                                                        }
                                                        final String URL = "http://minsoura.xyz/miniImageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;

                                                        Picasso.with(getContext())
                                                                .load(URL)
                                                                .fit()
                                                                .centerInside()
                                                                .transform(new CircleTransformation())
                                                                .into(cell_image, new Callback() {
                                                                    @Override
                                                                    public void onSuccess() {

                                                                    }

                                                                    @Override
                                                                    public void onError() {

                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });

                    }
                    cell_ID.setText(listStorages.get(indexC).getUserID());
                    cell_cell.setText(listStorages.get(indexC).getUserCell());
                    dialog.show();
                }


                //Bring Cell phone or contacts
                //key is to be consumed.

            }
        });
        if (!userEmail.equals("no")) {

            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + userEmail + "&userGender=" + userGender3;


            Picasso.with(getActivity().getApplicationContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(imageViewCon1, new Callback() {
                        @Override
                        public void onSuccess() {


                        }

                        @Override
                        public void onError() {
                            Random r = new Random();
                            int loaderIndex = r.nextInt(10 - 1) + 1;
                            String stringIndex = Integer.toString(loaderIndex);

                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + userEmail + "&userGender=" + userGender3;

                            Picasso.with(getContext())
                                    .load(URL)
                                    .fit()
                                    .centerInside()
                                    .transform(new CircleTransformation())
                                    .into(imageViewCon1, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Random r = new Random();
                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                            String stringIndex = Integer.toString(loaderIndex);

                                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + userEmail + "&userGender=" + userGender3;


                                            Picasso.with(getContext())
                                                    .load(URL)
                                                    .fit()
                                                    .centerInside()
                                                    .transform(new CircleTransformation())
                                                    .into(imageViewCon1, new Callback() {
                                                        @Override
                                                        public void onSuccess() {

                                                        }

                                                        @Override
                                                        public void onError() {
                                                            Random r = new Random();
                                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                                            String stringIndex = Integer.toString(loaderIndex);

                                                            final String URLsmall = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + userEmail + "&userGender=" + userGender3;

                                                            Picasso.with(getContext())
                                                                    .load(URLsmall)
                                                                    .fit()
                                                                    .centerInside()
                                                                    .transform(new CircleTransformation())
                                                                    .into(imageViewCon1);
                                                        }
                                                    });

                                        }
                                    });

                        }
                    });
        }


        textViewCon1.setText(myID);
        textViewCon2.setText(listStorages.get(indexC).getUserID());

        if (!listStorages.get(indexC).getUserEmail().equals("")) {
            Random r = new Random();
            int loaderIndex = r.nextInt(10 - 1) + 1;
            String stringIndex = Integer.toString(loaderIndex);

            if (userGender3.equals("boys")) {
                genderIndex = "girls";
            } else if (userGender3.equals("girls")) {
                genderIndex = "boys";
            }

            final String URL = "http://minsoura.xyz/ImageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;

            Picasso.with(getContext())
                    .load(URL)
                    .fit()
                    .centerInside()
                    .transform(new CircleTransformation())
                    .into(imageViewCon2, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Random r = new Random();
                            int loaderIndex = r.nextInt(10 - 1) + 1;
                            String stringIndex = Integer.toString(loaderIndex);

                            if (userGender3.equals("boys")) {
                                genderIndex = "girls";
                            } else if (userGender3.equals("girls")) {
                                genderIndex = "boys";
                            }
                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;

                            Picasso.with(getContext())
                                    .load(URL)
                                    .fit()
                                    .centerInside()
                                    .transform(new CircleTransformation())
                                    .into(imageViewCon2, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Random r = new Random();
                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                            String stringIndex = Integer.toString(loaderIndex);

                                            if (userGender3.equals("boys")) {
                                                genderIndex = "girls";
                                            } else if (userGender3.equals("girls")) {
                                                genderIndex = "boys";
                                            }
                                            final String URL = "http://minsoura.xyz/imageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;

                                            Picasso.with(getContext())
                                                    .load(URL)
                                                    .fit()
                                                    .centerInside()
                                                    .transform(new CircleTransformation())
                                                    .into(imageViewCon2, new Callback() {
                                                        @Override
                                                        public void onSuccess() {

                                                        }

                                                        @Override
                                                        public void onError() {
                                                            Random r = new Random();
                                                            int loaderIndex = r.nextInt(10 - 1) + 1;
                                                            String stringIndex = Integer.toString(loaderIndex);

                                                            if (userGender3.equals("boys")) {
                                                                genderIndex = "girls";
                                                            } else if (userGender3.equals("girls")) {
                                                                genderIndex = "boys";
                                                            }

                                                            final String URLsmall = "http://minsoura.xyz/ImageLoader" + stringIndex + ".php?userEmail=" + listStorages.get(indexC).getUserEmail() + "&userGender=" + genderIndex;

                                                            Picasso.with(getContext())
                                                                    .load(URLsmall)
                                                                    .fit()
                                                                    .centerInside()
                                                                    .transform(new CircleTransformation())
                                                                    .into(imageViewCon2);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });

        }
        theD_Day.setText(listStorages.get(indexC).getUserdDay());


        continaer4.addView(addedList);

    }
    }




    public void bringProfile3() {
        //retrieve an intent with the string of userEmail for query
        String userEmailAsKey = userEmail;

        task3 = new downloadProfile3();
        task3.execute(userEmailAsKey);
    }


    class downloadProfile3 extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }
        Boolean checker2 = true;
        @Override
        protected void onCancelled(){

            showProgress(false);
            task3 = null;
        }

        @Override
        protected  void onProgressUpdate(Void...hi){
            super.onProgressUpdate();
            showProgress(true);
        }

        @Override
        protected String doInBackground(String... userEmail) {

            String URL = "";
            if(!checker2){
                return null;
            }
            if (userGender3.equals("boys")) {
                URL = "http://minsoura.xyz/checkConnectBoys.php";
            } else if (userGender3.equals("girls")) {
                URL = "http://minsoura.xyz/checkConnectGirls.php";
            }

            HashMap<String, String> emailSet = new HashMap<>();
            emailSet.put("userEmail", userEmail[0]);

            try {

                requestHandler profileHandler2 = new requestHandler();
                String result = profileHandler2.bringUserProfile(URL, emailSet);
                if(!checker2){
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
            if(isCancelled()){
                task3=null;

            }

            if (s.equals("Error")) {

                Toast.makeText(getActivity().getApplicationContext(), "HTTP_NO_OK", Toast.LENGTH_LONG).show();
            }
            else {

                try {
                    listStorages.clear();

                    JSONArray jsonArray2 = new JSONArray(s);
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(i);

                        getUserID = jsonObject2.getString("dateID");
                        getUserMessage = jsonObject2.getString("message");
                        getType = jsonObject2.getString("type");
                        getDateEmail = jsonObject2.getString("dateEmail");
                        getUserCell = jsonObject2.getString("dateCell");
                        getUserdDay = jsonObject2.getString("d_day");



                        listStorages.add(new ProfileStorage(getUserID,getDateEmail,getUserMessage,getType,getUserCell,getUserdDay));
                    }


                } catch (Exception e) {
                    Log.d("tag", e.getMessage());
                    e.printStackTrace();
                }

                Collections.reverse(listStorages);

                listImagePath = new String[listStorages.size()];


                continaer4.removeAllViews();
                container3.removeAllViews();
                Integer scriptFloaterOne=0;
                Integer scriptFloaterTwo =0;

                sendVerifier= new String[listStorages.size()];
                if(listStorages.size() ==0){
                    Snackbar.make(getView(), "도착한 메세지가 없습니다.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                if(listStorages.size() !=0 && task3 != null) {
                    for (int i = 0; i < listStorages.size(); i++) {
                        if (listStorages.get(i).getType().equals("TO")) {
                            scriptFloaterOne =scriptFloaterOne +1;

                            ListInflatorTo(i);
                            sendVerifier[i] = "TO";
                        } else if (listStorages.get(i).getType().equals("FROM")) {
                            scriptFloaterOne = scriptFloaterOne +1;

                            ListInflatorFrom(i);
                            sendVerifier[i] = "FROM";
                        } else if (listStorages.get(i).getType().equals("yes")) {
                            scriptFloaterTwo = scriptFloaterTwo +1;
                            ConnectionInflator(i);
                            sendVerifier[i] = "YES";

                        } else if (listStorages.get(i).getType().equals("no")) {

                        } else if (listStorages.get(i).getType().equals("yesUnlocked")) {

                            scriptFloaterTwo = scriptFloaterTwo +1;
                            ConnectionInflator(i);
                            sendVerifier[i] = "yesUnlocked";
                        }

                    }
                }
                if (scriptFloaterOne ==0){
                    DefaultInflatorOne();
                }if (scriptFloaterTwo==0){
                    DefaultInflatorTwo();
                }


            }

            showProgress(false);
        checker2 = false;
        }

    }


























    public void sendDecision(String DateEmail, String UserEmail,String decision){


        decisionTask = new UserDecisionTask(DateEmail,UserEmail,decision);
        decisionTask.execute();


    }

    public void sendDecisionVersion(String DateEmail, String UserEmail,String decision, String id, String cell){


        decisionTask = new UserDecisionTask(DateEmail,UserEmail,decision,id,cell);
        decisionTask.execute();


    }




    public class UserDecisionTask extends AsyncTask<Void, Void, String> {
        private final String mDateEmail;

        private final String mUserEmail;

        private final String mDecision;
        Boolean checker=true;
        String theID;
        String theCell;

        UserDecisionTask(String DateEmail, String UserEmail,String Decision) {
            mDateEmail = DateEmail;
            mUserEmail = UserEmail;
            mDecision = Decision;

        }


        UserDecisionTask(String DateEmail, String UserEmail,String Decision,String ID,String cell) {
            mDateEmail = DateEmail;
            mUserEmail = UserEmail;
            mDecision = Decision;
            theID =ID;
            theCell = cell;

        }


        @Override
        protected void onCancelled() {
            decisionTask= null;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected String doInBackground(Void... params) {
            if(!checker){
                Log.e("Checker","Checker is Working");
                return null;

            }
            String URL="";

            if(userGender3.equals("boys")) {

                URL = "http://minsoura.xyz/DecisionMadeByBoys.php";
                if(mDecision.equals("yesUnlocked")){
                    URL = "http://minsoura.xyz/UnlockNotificationBoys.php";
                }

            }else if(userGender3.equals("girls")){

                URL = "http://minsoura.xyz/DecisionMadeByGirls.php";
                if(mDecision.equals("yesUnlocked")){
                    URL = "http://minsoura.xyz/UnlockNotificationGirls.php";
                }

            }


            HashMap<String, String> SendSet = new HashMap<>();
            SendSet.put("decision", mDecision);
            SendSet.put("dateEmail", mDateEmail);
            SendSet.put("userEmail", mUserEmail);





            try {
                requestHandler LikeDeliverHandler = new requestHandler();
                String result = LikeDeliverHandler.sendPostRequest(URL, SendSet);
                if(!checker){
                    Log.e("Checker","Checker is Working");
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
                decisionTask=null;
            }

            showProgress(false);
            if (receivedLine.equals("yes")) {

                Snackbar.make(getView(), "Decision Delivered!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                saveKeyInt = sharedPreferences.getInt("userKey", 0);


                myKey.setText(Integer.toString(saveKeyInt));
                 bringProfile3();

                //TODO: I could make a intent that delivers retrieved data from the DB such as the ones for the USER PROFILE;

            } else if (receivedLine.equals("no")) {
                Snackbar.make(getView(), "Decision Not Delivered", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            } else if(receivedLine.equals("unlocked")){
                Snackbar.make(getView(), "락이 풀렸습니다!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                final SharedPreferences.Editor editor = pref.edit();

                editor.putInt("userKey", saveKeyInt - 15);
                editor.apply();
                saveKeyInt = pref.getInt("userKey", 0);
                myKey.setText(Integer.toString(saveKeyInt));
                lockStatus.setText("");

                try {
                    finalCLick.setImageDrawable(getAssetImage(getActivity(),"unlocked"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bringProfile3();
            } else if(receivedLine.equals("unlockedFailed")){
                Snackbar.make(getView(), "락을 풀지 못했습니다", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            checker = false;
        }
    }


















    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            wrapper_View2.setVisibility(show ? View.GONE : View.VISIBLE);
            wrapper_View2.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    wrapper_View2.setVisibility(show ? View.GONE : View.VISIBLE);


                }
            });

            tab3_layout.setVisibility(show ? View.GONE : View.VISIBLE);
            tab3_layout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tab3_layout.setVisibility(show ? View.GONE : View.VISIBLE);


                }
            });

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
            wrapper_View2.setVisibility(show ? View.GONE : View.VISIBLE);
            tab3_layout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("Tag", "FragmentA.onDestroyView() has been called.");



        if(task3 !=null) {

            task3.cancel(true);
            task3.checker2 = false;
            task3 =null;
        }

        if(decisionTask !=null){

            decisionTask.cancel(true);
            decisionTask.checker = false;
            decisionTask =null;
        }
        continaer4.removeAllViews();
        container3.removeAllViews();
        System.gc();

    }

    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".png")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }
























}
