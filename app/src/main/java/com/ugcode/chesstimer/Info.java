package com.ugcode.chesstimer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Info extends Fragment {

    private Button contactUs;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        contactUs = view.findViewById(R.id.contact_us_button);

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = "Chess Timer v1.0.0 - Feedback about app";
                String model = Build.MODEL;
                String version = Build.VERSION.RELEASE;

                String recList = "gadhavanaumang007@gmail.com";
                String[] rec = recList.split(",");

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, rec);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, "Android " + version + " " + model);
                startActivity(Intent.createChooser(intent, "Choose an email client"));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}