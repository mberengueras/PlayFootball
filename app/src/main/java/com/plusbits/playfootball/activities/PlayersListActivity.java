package com.plusbits.playfootball.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.adapters.PlayersArrayAdapter;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.utils.StorageUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayersListActivity extends AppCompatActivity {
    @BindView(R.id.playersListContainer) LinearLayout playersListContainer;
    @BindView(R.id.playersList) ListView playersListView;
    @BindView(R.id.playersCallUpContainer) RelativeLayout playersCallUpContainer;
    @BindView(R.id.availablePlayersList) ListView availablePlayersList;
    @BindView(R.id.injuredPlayersList) ListView injuredPlayersList;
    @BindView(R.id.sanctionedPlayersList) ListView sanctionedPlayersList;
    @BindView(R.id.notCalledPlayersList) ListView notCalledPlayersList;

    private ArrayList<Player> playersList;
    private ArrayList<Player> availablePlayers, injuedPlayers, sanctionedPlayers, notCalledPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initPlayersList();
        this.initCallUpLists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_players_list);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save_list) {
            this.saveCallUp();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initPlayersList(){
        this.playersList = StorageUtils.getInstance(this).getAllPlayers();
        PlayersArrayAdapter adapter = new PlayersArrayAdapter(this, R.layout.player_list_item, playersList, false);

        this.playersListView.setAdapter(adapter);
    }

    private void initCallUpLists(){
        availablePlayers =  new ArrayList<Player>();
        injuedPlayers = new ArrayList<Player>();
        sanctionedPlayers = new ArrayList<Player>();
        notCalledPlayers = new ArrayList<Player>();

        this.availablePlayersList.setAdapter(new PlayersArrayAdapter(this, R.layout.player_list_item, availablePlayers, true));
        this.injuredPlayersList.setAdapter(new PlayersArrayAdapter(this, R.layout.player_list_item, injuedPlayers, true));
        this.sanctionedPlayersList.setAdapter(new PlayersArrayAdapter(this, R.layout.player_list_item, sanctionedPlayers, true));
        this.notCalledPlayersList.setAdapter(new PlayersArrayAdapter(this, R.layout.player_list_item, notCalledPlayers, true));
    }

    private void updateCallUpView(){
        availablePlayers.clear();
        injuedPlayers.clear();
        sanctionedPlayers.clear();
        notCalledPlayers.clear();

        for (Player player : this.playersList) {
            switch (player.getState()){
                case AVAILABLE:
                    availablePlayers.add(player);
                    break;
                case INJURED:
                    injuedPlayers.add(player);
                    break;
                case SANCTIONED:
                    sanctionedPlayers.add(player);
                    break;
                case NOT_CALLED:
                    notCalledPlayers.add(player);
                    break;

            }
        }

        ((BaseAdapter)this.availablePlayersList.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter)this.injuredPlayersList.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter)this.sanctionedPlayersList.getAdapter()).notifyDataSetChanged();
        ((BaseAdapter)this.notCalledPlayersList.getAdapter()).notifyDataSetChanged();
    }

    private void saveCallUp(){
        StorageUtils.getInstance(this).savePlayers(this.playersList);
        this.generateCallUp();
    }

    private void generateCallUp(){

        this.updateCallUpView();

        playersListContainer.setVisibility(View.GONE);
        playersCallUpContainer.setVisibility(View.VISIBLE);

        // Generate bitmap in the main thread
        Handler mainHandler = new Handler(this.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                generateCallUpImage();
            }
        };
        mainHandler.post(myRunnable);
    }

    private void generateCallUpImage(){
        Bitmap b = Bitmap.createBitmap(playersCallUpContainer.getWidth(), playersCallUpContainer.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        playersCallUpContainer.draw(c);
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), b, "title", null);
        this.shareBitmap(bitmapPath);
    }

    private void shareBitmap(String bitmapPath) {
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri );
        startActivity(Intent.createChooser(intent , "Share"));
    }
}
