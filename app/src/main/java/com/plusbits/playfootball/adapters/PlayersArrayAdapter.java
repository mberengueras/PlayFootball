package com.plusbits.playfootball.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.models.Player;
import com.plusbits.playfootball.utils.Constants;
import com.plusbits.playfootball.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.plusbits.playfootball.utils.Constants.PLAYER_STATES.AVAILABLE;

/**
 * Created by mberengueras on 17/10/2016.
 */

public class PlayersArrayAdapter extends ArrayAdapter<Player> {
    private Context context;
    private List<Player> playersList;
    private boolean isStateLocked;


    public PlayersArrayAdapter(Context context, int resource, List<Player> playersList, boolean isStateLocked) {
        super(context, resource, playersList);

        this.context = context;
        this.playersList = playersList;
        this.isStateLocked = isStateLocked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.player_list_item, null);
            // configure view holder
            viewHolder = new ViewHolder(rowView, context);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        Player player = this.playersList.get(position);

        viewHolder.player = player;
        viewHolder.playerName.setText(player.getName());
        UIUtils.setPlayerImageBorderColor(context, viewHolder.playerIcon, player);
        viewHolder.playerStateRG.check(this.getRadioButtonIdForState(player.getState()));

        if(player.getPhotoPath() != null && !player.getPhotoPath().equals("")){
            viewHolder.playerIcon.setImageBitmap(BitmapFactory.decodeFile(player.getPhotoPath()));
        }

        if (this.isStateLocked) {
            viewHolder.playerStateRG.setVisibility(View.GONE);
        }

        return rowView;
    }

    private int getRadioButtonIdForState(Constants.PLAYER_STATES state){
        switch (state) {
            case AVAILABLE:
                return R.id.rb_state_available;
            case INJURED:
                return R.id.rb_state_injured;
            case SANCTIONED:
                return R.id.rb_state_sanctioned;
            case NOT_CALLED:
                return R.id.rb_state_not_called;
        }

        return R.id.rb_state_available;
    }

    static class ViewHolder {
        @BindView(R.id.playerItemMainView) RelativeLayout mainView;
        @BindView(R.id.playerListName) TextView playerName;
        @BindView(R.id.playerListIcon) CircleImageView playerIcon;
        @BindView(R.id.playerStateRG) RadioGroup playerStateRG;

        public Player player;
        private Context context;

        public ViewHolder(View view, Context context) {
            ButterKnife.bind(this, view);
            this.context = context;
            this.addStateRadioGroupListener();
        }

        public void addStateRadioGroupListener(){
            playerStateRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_state_available:
                            playerIcon.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateAvailableColor));
                            player.setState(AVAILABLE);
                            break;
                        case R.id.rb_state_injured:
                            playerIcon.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateInjuredColor));
                            player.setState(Constants.PLAYER_STATES.INJURED);
                            break;
                        case R.id.rb_state_sanctioned:
                            playerIcon.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateSanctionedColor));
                            player.setState(Constants.PLAYER_STATES.SANCTIONED);
                            break;
                        case R.id.rb_state_not_called:
                            playerIcon.setBorderColor(((Activity)context).getResources().getColor(R.color.playerStateNotCalledColor));
                            player.setState(Constants.PLAYER_STATES.NOT_CALLED);
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }
}
