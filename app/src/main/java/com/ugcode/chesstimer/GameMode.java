package com.ugcode.chesstimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class GameMode extends Fragment implements View.OnClickListener {

    private CardView button_1, button_2, button_3,
            button_4, button_5, button_6;

    private ImageView image_1, image_2, image_3, image_4, image_5, image_6;
    private TextView title_text_1, title_text_2, title_text_3,
            title_text_4, title_text_5, title_text_6;
    private TextView bottom_text_1, bottom_text_2, bottom_text_3,
            bottom_text_4, bottom_text_5, bottom_text_6;
    private TextView min_text_1, min_text_2, min_text_3,
            min_text_4, min_text_5, min_text_6;

    private LinearLayout layout_1, layout_2, layout_3, layout_4, layout_5, layout_6;
    private SharedPreferences sharedPref;
    private static final String myData = "myData";
    private SharedPreferences.Editor editor;
    private int cardColor = 0;

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_mode, container, false);

        Context context = getActivity();
        if (context != null) {
            sharedPref = context.getSharedPreferences(myData, Context.MODE_PRIVATE);
            editor = sharedPref.edit();
        }

        button_1 = view.findViewById(R.id.active_button);
        button_2 = view.findViewById(R.id.classical_button);
        button_3 = view.findViewById(R.id.rapid_button);
        button_4 = view.findViewById(R.id.blitz_button);
        button_5 = view.findViewById(R.id.bullet_button);
        button_6 = view.findViewById(R.id.lightning_button);

        layout_1 = view.findViewById(R.id.layout_1);
        layout_2 = view.findViewById(R.id.layout_2);
        layout_3 = view.findViewById(R.id.layout_3);
        layout_4 = view.findViewById(R.id.layout_4);
        layout_5 = view.findViewById(R.id.layout_5);
        layout_6 = view.findViewById(R.id.layout_6);

        image_1 = view.findViewById(R.id.image_1);
        image_2 = view.findViewById(R.id.image_2);
        image_3 = view.findViewById(R.id.image_3);
        image_4 = view.findViewById(R.id.image_4);
        image_5 = view.findViewById(R.id.image_5);
        image_6 = view.findViewById(R.id.image_6);

        title_text_1 = view.findViewById(R.id.title_text_1);
        title_text_2 = view.findViewById(R.id.title_text_2);
        title_text_3 = view.findViewById(R.id.title_text_3);
        title_text_4 = view.findViewById(R.id.title_text_4);
        title_text_5 = view.findViewById(R.id.title_text_5);
        title_text_6 = view.findViewById(R.id.title_text_6);

        bottom_text_1 = view.findViewById(R.id.bottom_text_1);
        bottom_text_2 = view.findViewById(R.id.bottom_text_2);
        bottom_text_3 = view.findViewById(R.id.bottom_text_3);
        bottom_text_4 = view.findViewById(R.id.bottom_text_4);
        bottom_text_5 = view.findViewById(R.id.bottom_text_5);
        bottom_text_6 = view.findViewById(R.id.bottom_text_6);

        min_text_1 = view.findViewById(R.id.min_text_1);
        min_text_2 = view.findViewById(R.id.min_text_2);
        min_text_3 = view.findViewById(R.id.min_text_3);
        min_text_4 = view.findViewById(R.id.min_text_4);
        min_text_5 = view.findViewById(R.id.min_text_5);
        min_text_6 = view.findViewById(R.id.min_text_6);

        getDefaultValue();

        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    private void getDefaultValue() {
        sharedPref = Objects.requireNonNull(getActivity())
                .getSharedPreferences(myData, Context.MODE_PRIVATE);
        int selectedCard = sharedPref.getInt("SelectedCard", 2);

        switch (selectedCard) {
            case 0:
                setValue(0);
                changeAllCardColor();
                changeCardColor(layout_1, title_text_1,
                        image_1, bottom_text_1, min_text_1);
                cardColor = 0;
                break;
            case 1:
                setValue(1);
                changeAllCardColor();
                changeCardColor(layout_2, title_text_2,
                        image_2, bottom_text_2, min_text_2);
                cardColor = 1;
                break;
            case 2:
                setValue(2);
                changeAllCardColor();
                changeCardColor(layout_3, title_text_3,
                        image_3, bottom_text_3, min_text_3);
                cardColor = 2;
                break;
            case 3:
                setValue(3);
                changeAllCardColor();
                changeCardColor(layout_4, title_text_4,
                        image_4, bottom_text_4, min_text_4);
                cardColor = 3;
                break;
            case 4:
                setValue(4);
                changeAllCardColor();
                changeCardColor(layout_5, title_text_5,
                        image_5, bottom_text_5, min_text_5);
                cardColor = 4;
                break;
            case 5:
                setValue(5);
                changeAllCardColor();
                changeCardColor(layout_6, title_text_6,
                        image_6, bottom_text_6, min_text_6);
                cardColor = 5;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (button_1 == v) {
            setValue(0);
            changeAllCardColor();
            changeCardColor(layout_1, title_text_1,
                    image_1, bottom_text_1, min_text_1);
            cardColor = 0;
        } else if (button_2 == v) {
            setValue(1);
            changeAllCardColor();
            changeCardColor(layout_2, title_text_2,
                    image_2, bottom_text_2, min_text_2);
            cardColor = 1;
        } else if (button_3 == v) {
            setValue(2);
            changeAllCardColor();
            changeCardColor(layout_3, title_text_3,
                    image_3, bottom_text_3, min_text_3);
            cardColor = 2;
        } else if (button_4 == v) {
            setValue(3);
            changeAllCardColor();
            changeCardColor(layout_4, title_text_4,
                    image_4, bottom_text_4, min_text_4);
            cardColor = 3;
        } else if (button_5 == v) {
            setValue(4);
            changeAllCardColor();
            changeCardColor(layout_5, title_text_5,
                    image_5, bottom_text_5, min_text_5);
            cardColor = 4;
        } else if (button_6 == v) {
            setValue(5);
            changeAllCardColor();
            changeCardColor(layout_6, title_text_6,
                    image_6, bottom_text_6, min_text_6);
            cardColor = 5;
        }
    }

    private void setValue(int i) {
        editor.putInt("SelectedCard", i);
        editor.apply();
    }

    private void changeAllCardColor() {
        switch (cardColor) {
            case 0:
                layout_1.setBackgroundTintList(ColorStateList
                        .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.white_2)));
                title_text_1.setTextColor(getActivity().getColor(R.color.colorPrimary));
                image_1.setColorFilter(getActivity().getColor(R.color.colorPrimary));
                bottom_text_1.setTextColor(getActivity().getColor(R.color.colorPrimary));
                min_text_1.setTextColor(getActivity().getColor(R.color.colorPrimary));
                break;
            case 1:
                layout_2.setBackgroundTintList(ColorStateList
                        .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.white_2)));
                title_text_2.setTextColor(getActivity().getColor(R.color.colorPrimary));
                image_2.setColorFilter(getActivity().getColor(R.color.colorPrimary));
                bottom_text_2.setTextColor(getActivity().getColor(R.color.colorPrimary));
                min_text_2.setTextColor(getActivity().getColor(R.color.colorPrimary));
                break;
            case 2:
                layout_3.setBackgroundTintList(ColorStateList
                        .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.white_2)));
                title_text_3.setTextColor(getActivity().getColor(R.color.colorPrimary));
                image_3.setColorFilter(getActivity().getColor(R.color.colorPrimary));
                bottom_text_3.setTextColor(getActivity().getColor(R.color.colorPrimary));
                min_text_3.setTextColor(getActivity().getColor(R.color.colorPrimary));
                break;
            case 3:
                layout_4.setBackgroundTintList(ColorStateList
                        .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.white_2)));
                title_text_4.setTextColor(getActivity().getColor(R.color.colorPrimary));
                image_4.setColorFilter(getActivity().getColor(R.color.colorPrimary));
                bottom_text_4.setTextColor(getActivity().getColor(R.color.colorPrimary));
                min_text_4.setTextColor(getActivity().getColor(R.color.colorPrimary));
                break;
            case 4:
                layout_5.setBackgroundTintList(ColorStateList
                        .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.white_2)));
                title_text_5.setTextColor(getActivity().getColor(R.color.colorPrimary));
                image_5.setColorFilter(getActivity().getColor(R.color.colorPrimary));
                bottom_text_5.setTextColor(getActivity().getColor(R.color.colorPrimary));
                min_text_5.setTextColor(getActivity().getColor(R.color.colorPrimary));
                break;
            case 5:
                layout_6.setBackgroundTintList(ColorStateList
                        .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.white_2)));
                title_text_6.setTextColor(getActivity().getColor(R.color.colorPrimary));
                image_6.setColorFilter(getActivity().getColor(R.color.colorPrimary));
                bottom_text_6.setTextColor(getActivity().getColor(R.color.colorPrimary));
                min_text_6.setTextColor(getActivity().getColor(R.color.colorPrimary));
                break;
        }
    }

    private void changeCardColor(LinearLayout layout, TextView title_text,
                                 ImageView image, TextView bottom_text, TextView min_text) {

        layout.setBackgroundTintList(ColorStateList
                .valueOf(Objects.requireNonNull(getActivity()).getColor(R.color.colorPrimary)));
        title_text.setTextColor(getActivity().getColor(R.color.white_2));
        image.setColorFilter(getActivity().getColor(R.color.white_2));
        bottom_text.setTextColor(getActivity().getColor(R.color.white_2));
        min_text.setTextColor(getActivity().getColor(R.color.white_2));
    }
}