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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.jokedisplay.JokeActivity;

public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.JokeTaskHandler {

    private InterstitialAd mInterstitialAd;

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
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                mProgressBar.setVisibility(View.VISIBLE);
                launchTask();
            }
        });
        requestNewInterstitial();

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
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
            ;
        }
    }
}
