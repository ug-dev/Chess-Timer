package com.ugcode.chesstimer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Audio extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private Switch switch1, switch2, switch3;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView cancelButton, setButton, alertTimeSubtitle;
    private EditText minutes, seconds;
    private RelativeLayout vibrateTag, alertTimeTag;

    private SharedPreferences sharedPref;
    private static final String myData = "myData";
    private SharedPreferences.Editor editor;

    private String minute, second;
    private boolean check1, check2, check3;

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_audio, container, false);

        Context context = getActivity();
        if (context != null) {
            sharedPref = context
                    .getSharedPreferences(myData, Context.MODE_PRIVATE);
            editor = sharedPref.edit();
        }

        switch1 = view.findViewById(R.id.switch_1);
        switch2 = view.findViewById(R.id.switch_2);
        switch3 = view.findViewById(R.id.switch_3);
        alertTimeSubtitle = view.findViewById(R.id.alert_time_subtitle);

        vibrateTag = view.findViewById(R.id.vibrate_tag);
        alertTimeTag = view.findViewById(R.id.alert_time_tag);

        getAllDefaultValues();

        if (check2) {
            vibrateTag.setVisibility(View.VISIBLE);
            alertTimeTag.setVisibility(View.VISIBLE);
        } else {
            vibrateTag.setVisibility(View.GONE);
            alertTimeTag.setVisibility(View.GONE);
        }

        switch1.setOnCheckedChangeListener(this);
        switch2.setOnCheckedChangeListener(this);
        switch3.setOnCheckedChangeListener(this);

        alertTimeTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAlertTimeDialog();
            }
        });

        return view;
    }

    private void getAllDefaultValues() {
        String m = sharedPref.getString("Minute", "00");
        String s = sharedPref.getString("Second", "00");
        boolean c1 = sharedPref.getBoolean("Audio", false);
        boolean c2 = sharedPref.getBoolean("LowTime", false);
        boolean c3 = sharedPref.getBoolean("Vibrate", false);

        String msg = m + ":" + s;
        alertTimeSubtitle.setText(msg);

        minute = m;
        second = s;
        check1 = c1;
        check2 = c2;
        check3 = c3;

        switch1.setChecked(c1);
        switch2.setChecked(c2);
        switch3.setChecked(c3);
    }

    private void createAlertTimeDialog() {
        builder = new AlertDialog.Builder(getActivity());
        @SuppressLint("InflateParams") final View view = getLayoutInflater()
                .inflate(R.layout.alert_time_dialog, null);

        minutes = view.findViewById(R.id.minutes_alertTime);
        seconds = view.findViewById(R.id.seconds_alertTime);
        cancelButton = view.findViewById(R.id.cancel_button);
        setButton = view.findViewById(R.id.set_button);

        minutes.setText(minute);
        seconds.setText(second);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                minute = minutes.getText().toString().trim();
                second = seconds.getText().toString().trim();

                setAllSettings(minute, second, check1, check2, check3);
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void setAllSettings(String m, String s, boolean c1, boolean c2, boolean c3) {
        if (minute.length() < 2) {
            m = "0" + m;
        }

        if (second.length() < 2) {
            s = "0" + s;
        }

        if (Integer.parseInt(minute) > 30) {
            m = "29";
        }

        if (Integer.parseInt(second) > 60) {
            s = "59";
        }

        String msg = m + ":" + s;
        alertTimeSubtitle.setText(msg);

        editor.putString("Minute", m);
        editor.putString("Second", s);
        editor.putBoolean("Audio", c1);
        editor.putBoolean("LowTime", c2);
        editor.putBoolean("Vibrate", c3);
        editor.apply();
    }

    private void showOrHideTags(boolean s) {
        if (s) {
            vibrateTag.setVisibility(View.VISIBLE);
            alertTimeTag.setVisibility(View.VISIBLE);
        } else {
            vibrateTag.setVisibility(View.GONE);
            alertTimeTag.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == switch1) {
            check1 = isChecked;
            setAllSettings(minute, second, check1, check2, check3);
        } else if (buttonView == switch2) {
            check2 = isChecked;
            showOrHideTags(isChecked);
            setAllSettings(minute, second, check1, check2, check3);
        } else if (buttonView == switch3) {
            check3 = isChecked;
            setAllSettings(minute, second, check1, check2, check3);
        }
    }
}