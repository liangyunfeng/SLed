package com.lyf.app.sled.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lyf.app.sled.R;

public class MainActivity extends Activity {

    ImageView mLed, mMusic, mHandWriting, mGlowStick, mSpark, mMeteor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        mLed = (ImageView) findViewById(R.id.led);
        mHandWriting = (ImageView) findViewById(R.id.hand_writing);
        mGlowStick = (ImageView) findViewById(R.id.stick);
        mMusic = (ImageView) findViewById(R.id.music);
        mSpark = (ImageView) findViewById(R.id.spark);
        mMeteor = (ImageView) findViewById(R.id.meteor);

        mLed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LedSettingsActivity.class);
                startActivity(intent);
            }
        });

        mHandWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HandWritingActivity.class);
                startActivity(intent);
            }
        });

        mGlowStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GlowStickSettingActivity.class);
                startActivity(intent);
            }
        });

        mMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(intent);
            }
        });

        mSpark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SparkActivity.class);
                startActivity(intent);
            }
        });

        mMeteor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeteorActivity.class);
                startActivity(intent);
            }
        });
    }
}
