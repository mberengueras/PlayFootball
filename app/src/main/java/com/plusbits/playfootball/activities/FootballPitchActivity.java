package com.plusbits.playfootball.activities;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.fragments.PlayersListDialogFragment;
import com.plusbits.playfootball.interfaces.PlayersListDialogListener;
import com.plusbits.playfootball.listeners.DaDDragListener;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.utils.Constants;
import com.plusbits.playfootball.utils.SharedPreferencesUtils;
import com.plusbits.playfootball.utils.SizeUtils;
import com.plusbits.playfootball.utils.StorageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootballPitchActivity extends AppCompatActivity implements PlayersListDialogListener {
    private static final String DIALOG_FRAGMENT_TAG = "PlayersListDialogFragment";

    @BindView(R.id.content_football_pitch)
    RelativeLayout footballPitch;

    private Boolean playersAdded = false;
    private ImageView selectedImagePlayer;

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
                goToNewPlayerActivity();
            }
        });

        ButterKnife.bind(this);
        //icon.setOnTouchListener(new DaDTouchListener());
        footballPitch.setOnDragListener(new DaDDragListener());

        final ViewTreeObserver observer= footballPitch.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(!playersAdded) addAllPlayers();
                    }
                });
    }

    private void addAllPlayers(){
        playersAdded = true;
        for (int i = 0; i < Constants.NUM_PLAYERS; ++i){
            ImageView iv = getImageView(i);
            iv.setTag(R.string.PLAYER_PIECE_TAG_KEY, i);
            footballPitch.addView(iv);
        }
    }

    private ImageView getImageView(int id){
        ImageView iv = new ImageView(this);

        Long playerID = SharedPreferencesUtils.getPlayerPieceId(this, id);

        if(playerID != -1){
            Player piecePlayer = StorageUtils.getInstance(this).getPlayer(playerID);
            iv.setImageBitmap(BitmapFactory.decodeFile(piecePlayer.getPhotoPath()));
        }
        else {
            iv.setImageResource(R.mipmap.add_icon);
        }

        //setting image size
        int ivSize = this.getDPI(Constants.PLAYER_ICON_SIZE);
        iv.setLayoutParams(new LinearLayout.LayoutParams(ivSize,
                ivSize));
        iv.setX((float) (Constants.xPlayersPositions[id] * footballPitch.getWidth() - ivSize/2));
        iv.setY((float) (Constants.yPlayersPositions[id] * footballPitch.getHeight() - ivSize/2));

        Log.i("onTouch", "ImageView x: " + String.valueOf(iv.getX()));
        //Add event for going to detail
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImagePlayer = (ImageView) view;
                showSelectPlayerDialog();//goToDetailPlayer(view);
            }
        });

        //Add event for starting drag
        iv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        return iv;
    }

    private int getDPI(int size){
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return SizeUtils.getDPI(size, metrics);
    }

    private void goToDetailPlayer(View view){
        Intent intent=new Intent(this,DetailPlayerActivity.class);
        startActivity(intent);
    }

    private void goToNewPlayerActivity(){
        Intent intent = new Intent(this, NewPlayerActivity.class);
        startActivity(intent);
    }

    private void showSelectPlayerDialog(){
        PlayersListDialogFragment pldFragment = new PlayersListDialogFragment();
        pldFragment.setListener(this);
        pldFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void onPlayerSelected(Player player){
        this.selectedImagePlayer.setImageBitmap(BitmapFactory.decodeFile(player.getPhotoPath()));
        this.selectedImagePlayer.setTag(player.getId());
        SharedPreferencesUtils.savePlayerPieceId(this, (int)this.selectedImagePlayer.getTag(R.string.PLAYER_PIECE_TAG_KEY), player.getId());
    }
}
