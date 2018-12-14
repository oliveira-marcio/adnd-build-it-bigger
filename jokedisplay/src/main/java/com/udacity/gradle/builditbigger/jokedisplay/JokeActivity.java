package com.udacity.gradle.builditbigger.jokedisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        TextView jokeTextView = (TextView) findViewById(R.id.jokeTextView);
        if (joke != null && joke.length() != 0) {
            jokeTextView.setText(joke);
        }
    }
}
