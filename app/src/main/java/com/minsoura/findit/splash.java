package com.minsoura.findit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/**
 * Created by Administrator on 2015-05-07.
 */
public class splash extends Activity {

  /*
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }*/

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        StartAnimations();
      ///  Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
      //  window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
       // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
       // window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.Black));

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
       // LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        ImageView l=(ImageView) findViewById(R.id.splashImage);
        l.clearAnimation();
        l.startAnimation(anim);
        CountDown _tik;
        if(LoginActivityVerifier()){
            _tik=new CountDown(3000,1000,this,logIn.class);
            _tik.start();
        }else{
            _tik=new CountDown(3000,1000,this,WelcomeScreen.class);
            _tik.start();
        }

    }

    public Boolean LoginActivityVerifier(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String verifier;
        verifier = sharedPreferences.getString("LoginActivityVerifier", "WelcomeScreen");
        if(verifier.equals("login")){
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
}
