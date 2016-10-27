package com.plusbits.playfootball.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.plusbits.playfootball.R;
import com.plusbits.playfootball.databinding.ActivityNewPlayerBinding;
import com.plusbits.playfootball.interfaces.NewPlayerHandler;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.utils.CameraUtils;
import com.plusbits.playfootball.utils.StorageUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewPlayerActivity extends AppCompatActivity implements NewPlayerHandler {
    public static int REQUEST_CODE_PICKER = 1;

    @BindView(R.id.playerPhoto)
    CircleImageView playerPhoto;

    @BindView(R.id.numberSpinner)
    Spinner numbersSpinner;

    public Player player;

    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.player = new Player();

        ActivityNewPlayerBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_new_player);
        //ContentNewPlayerBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_new_player);
        binding.setPlayer(this.player);
        binding.setEventsHandler(this);

        //setContentView(R.layout.activity_new_player);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initSpinners();
    }

    private void initSpinners(){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, StorageUtils.getInstance(this).getAllAvailableNumbers());
        numbersSpinner.setAdapter(adapter);
    }

    @OnClick(R.id.playerPhoto)
    public void onPlayerPhotoClick () {
        CameraUtils.openCameraGalleryPicker(this, REQUEST_CODE_PICKER);
    }

    // New Player Handler Methods

    @Override
    public void onConfirmNewPlayerClick(Player newPlayer) {
        newPlayer.setPhotoPath(this.photoPath);
        StorageUtils.getInstance(this).savePlayer(newPlayer);
        this.finish();
    }

    // Life cycle methods

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);
            if(!images.isEmpty()){
                Image image = images.get(0);
                photoPath = image.getPath();
                playerPhoto.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
            }
        }
    }
}
