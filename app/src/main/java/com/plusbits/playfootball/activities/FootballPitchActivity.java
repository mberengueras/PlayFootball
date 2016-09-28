package com.plusbits.playfootball.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.listeners.DaDDragListener;
import com.plusbits.playfootball.listeners.DaDTouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootballPitchActivity extends AppCompatActivity {
    @BindView(R.id.add_icon)
    ImageView icon;

    @BindView(R.id.content_football_pitch)
    RelativeLayout footballPitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_pitch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);
        icon.setOnTouchListener(new DaDTouchListener());
        footballPitch.setOnDragListener(new DaDDragListener());
    }

}
