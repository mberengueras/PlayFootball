package com.plusbits.playfootball.listeners;

import android.support.design.widget.CoordinatorLayout;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by mberengueras on 27/09/2016.
 */

public class DaDDragListener implements View.OnDragListener {

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                //v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                //v.setBackgroundDrawable(normalShape);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View view = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                RelativeLayout container = (RelativeLayout) v;
                CoordinatorLayout.LayoutParams lParams = (CoordinatorLayout.LayoutParams) v.getLayoutParams();
                view.setX(event.getX()- lParams.leftMargin);
                view.setY(event.getY()- lParams.topMargin);
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //v.setBackgroundDrawable(normalShape);
            default:
                break;
        }
        return true;
    }
}
