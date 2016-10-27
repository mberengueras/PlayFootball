package com.plusbits.playfootball.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.plusbits.playfootball.R;
import com.plusbits.playfootball.interfaces.EditTextDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditTextDialogFragment extends DialogFragment {
    @BindView(R.id.edit_dialog_confirm_btn)
    Button confirmBtn;

    @BindView(R.id.edit_dialog_msg)
    TextView dialgMsgTV;

    @BindView(R.id.edit_dialog_editText)
    EditText dialogEditText;

    private String title;
    private String msg;
    private String editTextHint;
    private String btnMsg;
    private EditTextDialogListener listener;

    public EditTextDialogFragment() {
    }

    public void setDialogViewInfo(String title, String msg, String editTextHint, String btnMsg) {
        this.title = title;
        this.msg = msg;
        this.editTextHint = editTextHint;
        this.btnMsg = btnMsg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_text_dialog, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(this.title);

        dialgMsgTV.setText(msg);
        confirmBtn.setText(btnMsg);
        dialogEditText.setHint(editTextHint);

        return view;
    }

    @OnClick(R.id.edit_dialog_confirm_btn)
    public void onConfirmBtnPressed() {
        this.listener.onConfirmButtonPressed(dialogEditText.getText().toString());
        this.dismiss();
    }

    public EditTextDialogListener getListener() {
        return listener;
    }

    public void setListener(EditTextDialogListener listener) {
        this.listener = listener;
    }
}
