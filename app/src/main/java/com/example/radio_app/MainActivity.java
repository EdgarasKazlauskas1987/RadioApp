package com.example.radio_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    // ToDo
    // Example link: https://androidexample.com/dynamically-create-view-elements
    // https://www.c-sharpcorner.com/UploadFile/8836be/how-to-set-different-events-on-successive-clicks-on-same-but/
    // Archive of stations:
    // https://transliacija.rc.lt/rc128.mp3

    static boolean playing = false;
    static String currentStation = "";


    public void setPlaying(){
        playing = true;
    }

    public void setStopped(){
        playing = false;
    }

    public void setCurrentStation(String url) {
        currentStation = url;
    }
    // ToDo Add Polymorphysm here????
    public void setCurrentStationEmpty() {
        currentStation = "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;

        Station radiocentras = new Station("Radiocentras", "https://stream2.rc.lt/rc128.mp3");
        Station m1 = new Station("M-1", "https://radio.m-1.fm/m1/mp3");
        Station relaxFm = new Station("Relax FM", "https://stream1.relaxfm.lt/relaxfm128.mp3");
        Station ziniuRadijas = new Station("Ziniu Radijas", "https://netradio.ziniur.lt/ziniur.mp3?n=543982");
        Station wisconsinPR = new Station("Wisconsin PR", "https://wpr-ice.streamguys1.com/wpr-ideas-mp3-64");
        Station bbcOne = new Station("BBC one", "http://stream.live.vc.bbcmedia.co.uk/bbc_radio_one");

        ArrayList<Station> stations = new ArrayList<>(Arrays.asList(radiocentras, m1, relaxFm,
                ziniuRadijas, wisconsinPR, bbcOne));

        MediaPlayer player = new MediaPlayer();

        final LinearLayout lm = findViewById(R.id.linearLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);


        for(Station stn : stations) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);

            final Button btn = new Button(this);
            btn.setId(View.generateViewId());
            btn.setText(stn.getName());
            btn.setLayoutParams(params);
            btn.setHeight(Utils.fromDPtoPX(scale, 80));

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if(!playing) {

                        try {
                            System.out.println("Edgaras Starting");
                            player.setDataSource(stn.getUrl());
                            player.prepareAsync();
                            setPlaying();
                            setCurrentStation(stn.getUrl());
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    player.start();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Edgaras Stopping");
                        // If it's same as current station then just stop
                        if (currentStation.equals(stn.getUrl())) {
                            player.stop();
                            player.reset();
                            setStopped();
                            setCurrentStationEmpty();
                        }
                        // If it's different as current station then stop and play new one
                        else {
                            player.stop();
                            player.reset();
                            setCurrentStation(stn.getUrl());
                            try {
                                player.setDataSource(stn.getUrl());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            player.prepareAsync();
                            player.start();
                        }
                    }
                }
            });

            ll.addView(btn);
            lm.addView(ll);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("Edgaras releasing");
        //player.release();
    }
}