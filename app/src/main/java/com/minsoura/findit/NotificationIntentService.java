package com.minsoura.findit;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by min on 2016-02-15.
 */
public class NotificationIntentService extends IntentService {

    static String userGender3="";

    static String userEmail="";
    static String notificationVerifier="";
    static String previousNotiCode;

    static String getMessageChecker="hi";
    static String getNotiCode="0";


    static ArrayList<ProfileStorage> listStorages = new ArrayList<>();
    static  String[] listImagePath;

    static Bitmap myBitmap;
    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification() {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        previousNotiCode = pref.getString("userNotiCode", "0");

        userEmail = MainActivity.userEmailIntent;
        userGender3 = MainActivity.userGender;

        if(userEmail != null){
            bringProfile3();
            Log.e("NOTI","NOTI DONE");
        }else
        {

        }





    }

    public void makeNotiFunction(){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Connect")
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary))
                .setContentText("메세지함을 확인하세요.")
                .setSmallIcon(R.drawable.burn_pink);
        Intent Tab2Intent = new Intent(this,MainActivity.class);
        Tab2Intent.putExtra("userEmail",userEmail);
        Tab2Intent.putExtra("userGender",userGender3);
        Tab2Intent.putExtra("makeAction","tab2");
        Tab2Intent.setAction("one");
        sendBroadcast(Tab2Intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                Tab2Intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    public void makeNotiFunction2(){
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Connect")
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimary))
                .setContentText("새로운 공지사항이 있습니다.")
                .setSmallIcon(R.drawable.burn_pink);
        Intent Tab2Intent = new Intent(this,MainActivity.class);
        Tab2Intent.putExtra("userEmail", userEmail);
        Tab2Intent.putExtra("userGender", userGender3);
        Tab2Intent.putExtra("makeAction","openNotification");


        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                Tab2Intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }




    public void bringProfile3() {
        //retrieve an intent with the string of userEmail for query
        String userEmailAsKey = userEmail;



        class downloadProfile3 extends AsyncTask<String, Void, String> {

            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected  void onProgressUpdate(Void...hi){
                super.onProgressUpdate();

            }

            @Override
            protected String doInBackground(String... userEmail) {
                String URL = "";
                if (userGender3.equals("boys")) {
                    URL = "http://minsoura.xyz/messageCheckerBoys.php";
                } else if (userGender3.equals("girls")) {
                    URL = "http://minsoura.xyz/messageCheckerGirls.php";
                }

                HashMap<String, String> emailSet = new HashMap<>();
                emailSet.put("userEmail", userEmail[0]);

                try {

                    requestHandler profileHandler2 = new requestHandler();
                    String result = profileHandler2.bringUserProfile(URL, emailSet);

                    return result;

                } catch (Exception e) {
                    e.printStackTrace();

                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // String imageTest="";
                //  String getUserID="";
                //   String getUserHeight="";
                //   String getUserAge="";
                //   String getUserJob="";
                if (s.equals("Error")) {

                    Toast.makeText(getApplicationContext(), "HTTP_NO_OK", Toast.LENGTH_LONG).show();
                }
                else {
                    try {
                        listStorages.clear();

                        JSONArray jsonArray2 = new JSONArray(s);
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject jsonObject2 = jsonArray2.getJSONObject(i);

                            getMessageChecker= jsonObject2.getString("messageChecker");
                            getNotiCode = jsonObject2.getString("notiCode");

                        }


                    } catch (Exception e) {
                        Log.d("tag", e.getMessage());
                        e.printStackTrace();
                    }

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    final SharedPreferences.Editor editor = pref.edit();


                    if(previousNotiCode.equals(getNotiCode)){
                        notificationVerifier = "no";

                    }else{
                        notificationVerifier = "new";
                    }

                    if(notificationVerifier.equals("new")){


                        makeNotiFunction2();


                    }
                    editor.putString("notificationVerifier",notificationVerifier);
                    editor.putString("userNotiCodeUnchecked",getNotiCode);

                    editor.apply();

                    if(getMessageChecker.equals("yes") ){



                        makeNotiFunction();




                    }else if(getMessageChecker.equals("none")){
                        //do thing....

                    }






                }
            }

        }



        downloadProfile3 task3 = new downloadProfile3();
        task3.execute(userEmailAsKey);
    }



}
