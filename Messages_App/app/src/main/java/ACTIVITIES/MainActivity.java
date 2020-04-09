package ACTIVITIES;

import android.content.Intent;
import android.os.Bundle;

import com.example.messages_app.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, MessageList.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

            }
        }




