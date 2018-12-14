package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.jokedisplay.JokeActivity;

public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.JokeTaskHandler {

    private Button mJokeButton;
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mProgressBar = (ProgressBar) root.findViewById(R.id.loading_indicator);

        mJokeButton = (Button) root.findViewById(R.id.joke_buton);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTask();
            }
        });
        return root;
    }

    public void launchTask() {
        mProgressBar.setVisibility(View.VISIBLE);
        mJokeButton.setEnabled(false);
        new EndpointsAsyncTask(this).execute();
    }

    @Override
    public void onTaskCompleted(String result) {
        mProgressBar.setVisibility(View.GONE);
        mJokeButton.setEnabled(true);

        if (result != null) {
            Intent myIntent = new Intent(getActivity(), JokeActivity.class);
            myIntent.putExtra(Intent.EXTRA_TEXT, result);
            startActivity(myIntent);
        } else {
            Toast.makeText(getActivity(), R.string.toast_error_joke, Toast.LENGTH_SHORT).show();
        }
    }
}
