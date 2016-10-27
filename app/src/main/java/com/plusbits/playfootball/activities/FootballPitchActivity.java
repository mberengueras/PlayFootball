package com.plusbits.playfootball.activities;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.fragments.EditTextDialogFragment;
import com.plusbits.playfootball.fragments.FormationsListDialogFragment;
import com.plusbits.playfootball.fragments.PlayersListDialogFragment;
import com.plusbits.playfootball.interfaces.EditTextDialogListener;
import com.plusbits.playfootball.interfaces.FormationsListDialogListener;
import com.plusbits.playfootball.interfaces.PlayersListDialogListener;
import com.plusbits.playfootball.listeners.DaDDragListener;
import com.plusbits.playfootball.models.Formation;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.utils.Constants;
import com.plusbits.playfootball.utils.SharedPreferencesUtils;
import com.plusbits.playfootball.utils.SizeUtils;
import com.plusbits.playfootball.utils.StorageUtils;
import com.plusbits.playfootball.utils.UIUtils;
import com.plusbits.playfootball.views.DrawingView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FootballPitchActivity extends AppCompatActivity implements PlayersListDialogListener, FormationsListDialogListener, EditTextDialogListener {
    private static final String DIALOG_FRAGMENT_TAG = "PlayersListDialogFragment";
    private static final String FORMATIONS_DIALOG_FRAGMENT_TAG = "FormationsListDialogFragment";
    private static final String EDIT_DIALOG_FRAGMENT_TAG = "EditDialogFragment";

    @BindView(R.id.content_football_pitch)
    RelativeLayout footballPitch;

    @BindView(R.id.drawingView)
    DrawingView drawingView;

    private Boolean playersAdded = false;
    private ImageView selectedImagePlayer;
    private ArrayList<CircleImageView> playersPieces;
    private Formation currentFormation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_pitch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_football_pitch);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_new_player) {
            this.goToNewPlayerActivity();
        }
        else if(item.getItemId() == R.id.action_players_list) {
            this.goToPlayerListActivity();
        }
        else if(item.getItemId() == R.id.action_save_formation) {
            if(this.currentFormation != null) this.saveCurrentFormation();
            else this.showSaveFormationDialog();
        }
        else if(item.getItemId() == R.id.action_save_as_formation) {
            this.showSaveFormationDialog();
        }
        else if(item.getItemId() == R.id.action_load_formation) {
            this.showSelectFormationDialog();
        }
        else if(item.getItemId() == R.id.action_load_default_formation) {
            Formation formation = new Formation("default");
            formation.setxPlayersPositions(Constants.xPlayersPositions);
            formation.setyPlayersPositions(Constants.yPlayersPositions);
            this.loadFormation(formation);
        }
        else if(item.getItemId() == R.id.action_show_drawing_view) {
            if (drawingView.getVisibility() == View.VISIBLE) drawingView.setVisibility(View.GONE);
            else drawingView.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAllPlayers(){
        playersAdded = true;
        playersPieces = new ArrayList<CircleImageView>();

        double[] xPlayersPositions = Constants.xPlayersPositions;
        double[] yPlayersPositions = Constants.yPlayersPositions;
        Long currentFormationId = SharedPreferencesUtils.getCurrentSavedFormationId(this);
        if (currentFormationId != -1){
            this.currentFormation = StorageUtils.getInstance(this).getFormation(currentFormationId);
            xPlayersPositions = this.currentFormation.getxPlayersPositions();
            yPlayersPositions = this.currentFormation.getyPlayersPositions();
        }

        for (int i = 0; i < Constants.NUM_PLAYERS; ++i){
            CircleImageView iv = getImageView(i, xPlayersPositions[i], yPlayersPositions[i]);
            playersPieces.add(iv);
            iv.setTag(R.string.PLAYER_PIECE_TAG_KEY, i);
            footballPitch.addView(iv);
        }
    }

    private CircleImageView getImageView(int id, double x, double y){
        CircleImageView iv = new CircleImageView(this);
        iv.setBorderWidth((int) (getResources().getDimension(R.dimen.circleImageViewBorderColorWidth) / getResources().getDisplayMetrics().density));
        iv.setBorderColor(getResources().getColor(R.color.circleImageViewBorderColor));

        Long playerID = SharedPreferencesUtils.getPlayerPieceId(this, id);

        if(playerID != -1){
            Player piecePlayer = StorageUtils.getInstance(this).getPlayer(playerID);
            iv.setImageBitmap(BitmapFactory.decodeFile(piecePlayer.getPhotoPath()));
            UIUtils.setPlayerImageBorderColor(this,iv,piecePlayer, true);
            iv.setTag(piecePlayer.getId());
        }
        else {
            iv.setImageResource(R.mipmap.add_icon);
        }

        //setting image size
        int ivSize = this.getDPI(Constants.PLAYER_ICON_SIZE);
        iv.setLayoutParams(new LinearLayout.LayoutParams(ivSize,
                ivSize));
        iv.setX((float) (x * footballPitch.getWidth() - ivSize/2));
        iv.setY((float) (y * footballPitch.getHeight() - ivSize/2));

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
        return this.addDragAndDropToPlayerPiece(iv);
    }

    private CircleImageView addDragAndDropToPlayerPiece(CircleImageView iv){
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

    private void addOponentPlayer(){
        //TODO: Add option for adding other player
    }

    private void loadFormation(Formation formation) {
        int ivSize = this.getDPI(Constants.PLAYER_ICON_SIZE);
        double[] xPlayersPositions = formation.getxPlayersPositions();
        double[] yPlayersPositions = formation.getyPlayersPositions();

        for (int i = 0; i < this.playersPieces.size(); ++i){
            CircleImageView civ = this.playersPieces.get(i);
            civ.setX((float) (xPlayersPositions[i] * footballPitch.getWidth() - ivSize/2));
            civ.setY((float) (yPlayersPositions[i] * footballPitch.getHeight() - ivSize/2));
        }

        Toast toast = Toast.makeText(this, getResources().getString(R.string.toast_formation_loaded), Toast.LENGTH_SHORT);
        toast.show();
    }

    private int getDPI(int size){
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return SizeUtils.getDPI(size, metrics);
    }

    private ArrayList<Pair<Double,Double>> getCurrentPlayersPositions(){
        ArrayList<Pair<Double,Double>> positions = new ArrayList<Pair<Double,Double>>();
        int ivSize = this.getDPI(Constants.PLAYER_ICON_SIZE);

        for (CircleImageView iv : this.playersPieces){
            Double x = Double.valueOf((iv.getX()+ivSize/2)/footballPitch.getWidth());
            Double y = Double.valueOf((iv.getY()+ivSize/2)/footballPitch.getHeight());
            positions.add(new Pair<Double, Double>(x, y));
        }

        return positions;
    }

    private void showSaveFormationDialog(){
        EditTextDialogFragment etdFragment = new EditTextDialogFragment();
        etdFragment.setListener(this);
        etdFragment.setDialogViewInfo(getResources().getString(R.string.saveFormationDialogTitle),
                getResources().getString(R.string.saveFormationDialogMsg),
                getResources().getString(R.string.saveFormationDialogEditTextHint),
                getResources().getString(R.string.saveFormationDialogBtnText));

        FragmentManager fm = this.getSupportFragmentManager();
        etdFragment.show(fm, EDIT_DIALOG_FRAGMENT_TAG);
        //this.saveFormation("prova");
    }

    private void goToDetailPlayer(View view){
        Intent intent=new Intent(this,DetailPlayerActivity.class);
        startActivity(intent);
    }

    private void goToNewPlayerActivity(){
        Intent intent = new Intent(this, NewPlayerActivity.class);
        startActivity(intent);
    }

    private void goToPlayerListActivity(){
        Intent intent = new Intent(this, PlayersListActivity.class);
        startActivity(intent);
    }

    private void showSelectPlayerDialog(){
        PlayersListDialogFragment pldFragment = new PlayersListDialogFragment();
        pldFragment.setListener(this);
        pldFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    private void showSelectFormationDialog(){
        FormationsListDialogFragment fldFragment = new FormationsListDialogFragment();
        fldFragment.setListener(this);
        fldFragment.show(getFragmentManager(), FORMATIONS_DIALOG_FRAGMENT_TAG);
    }

    private void updateStarterPlayer(Player oldPlayer, Player newPlayer){
        if(oldPlayer != null){
            oldPlayer.setStarter(false);
            StorageUtils.getInstance(this).updatePlayer(oldPlayer);
        }
        newPlayer.setStarter(true);
        StorageUtils.getInstance(this).updatePlayer(newPlayer);
    }

    private void saveCurrentFormation(){
        this.saveFormation("", false);
    }

    private void saveFormation(String name){
        this.saveFormation(name, true);
    }

    private void saveFormation(String name, boolean isNewFormation){
        ArrayList<Pair<Double,Double>> positions = this.getCurrentPlayersPositions();
        double [] yPositions = new double[positions.size()];
        double [] xPositions = new double[positions.size()];

        for (int i = 0; i < positions.size(); ++i) {
            Pair<Double,Double> iPoisiton = positions.get(i);
            xPositions[i] = iPoisiton.first;
            yPositions[i] = iPoisiton.second;
        }

        Formation formation = isNewFormation ? new Formation(name) : this.currentFormation;
        formation.setxPlayersPositions(xPositions);
        formation.setyPlayersPositions(yPositions);

        if (isNewFormation){
            StorageUtils.getInstance(this).saveFormation(formation);
        }
        else {
            StorageUtils.getInstance(this).updateFormation(formation);
        }

        SharedPreferencesUtils.saveCurrentFormationId(this, formation.getId());

        Toast toast = Toast.makeText(this, getResources().getString(R.string.toast_formation_saved), Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onPlayerSelected(Player player){
        Long oldPlayerId = (Long) this.selectedImagePlayer.getTag();
        this.selectedImagePlayer.setImageBitmap(BitmapFactory.decodeFile(player.getPhotoPath()));
        this.selectedImagePlayer.setTag(player.getId());
        SharedPreferencesUtils.savePlayerPieceId(this, (int)this.selectedImagePlayer.getTag(R.string.PLAYER_PIECE_TAG_KEY), player.getId());
        this.updateStarterPlayer(StorageUtils.getInstance(this).getPlayer(oldPlayerId), player);
    }

    @Override
    public void onFormationSelected(Formation formation) {
        this.currentFormation = formation;
        this.loadFormation(formation);
    }

    /**
     * Save Formation Dialog Listener Methods
     */

    public void onConfirmButtonPressed(String editTextValue) {
        this.saveFormation(editTextValue);
    }
}
