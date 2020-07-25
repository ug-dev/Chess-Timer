package com.ugcode.chesstimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Themes extends AppCompatActivity implements View.OnClickListener {
    private ImageView theme_preview_1, theme_preview_2, theme_preview_3,
            theme_preview_4, theme_preview_5, theme_preview_6;

    private Button Apply;
    private ImageButton themeBtn1, themeBtn2, themeBtn3,
            themeBtn4, themeBtn5, themeBtn6;

    private ImageView checked1, checked2, checked3,
            checked4, checked5, checked6;

    private int ThemeColor;

    private SharedPreferences sharedPref;
    private static final String myData = "myData";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(myData, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        
        Apply = findViewById(R.id.themes_apply_button);

        theme_preview_1 =findViewById(R.id.theme_preview_1);
        theme_preview_2 =findViewById(R.id.theme_preview_2);
        theme_preview_3 =findViewById(R.id.theme_preview_3);
        theme_preview_4 =findViewById(R.id.theme_preview_4);
        theme_preview_5 =findViewById(R.id.theme_preview_5);
        theme_preview_6 =findViewById(R.id.theme_preview_6);

        checked1 = findViewById(R.id.checked_1);
        checked2 = findViewById(R.id.checked_2);
        checked3 = findViewById(R.id.checked_3);
        checked4 = findViewById(R.id.checked_4);
        checked5 = findViewById(R.id.checked_5);
        checked6 = findViewById(R.id.checked_6);

        themeBtn1 = findViewById(R.id.button_theme_1);
        themeBtn2 = findViewById(R.id.button_theme_2);
        themeBtn3 = findViewById(R.id.button_theme_3);
        themeBtn4 = findViewById(R.id.button_theme_4);
        themeBtn5 = findViewById(R.id.button_theme_5);
        themeBtn6 = findViewById(R.id.button_theme_6);

        themeBtn1.setOnClickListener(this);
        themeBtn2.setOnClickListener(this);
        themeBtn3.setOnClickListener(this);
        themeBtn4.setOnClickListener(this);
        themeBtn5.setOnClickListener(this);
        themeBtn6.setOnClickListener(this);
        
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("ThemeColor", ThemeColor);
                editor.apply();
                startActivity(new Intent(Themes.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(myData, Context.MODE_PRIVATE);
        int themeColor = sharedPref.getInt("ThemeColor", 0);

        switch (themeColor) {
            case 0:
                changeImagePreview(theme_preview_1);
                changeButtonColor(0);
                changeChecked(checked1);
                break;
            case 1:
                changeImagePreview(theme_preview_2);
                changeButtonColor(1);
                changeChecked(checked2);
                break;
            case 2:
                changeImagePreview(theme_preview_3);
                changeButtonColor(2);
                changeChecked(checked3);
                break;
            case 3:
                changeImagePreview(theme_preview_4);
                changeButtonColor(3);
                changeChecked(checked4);
                break;
            case 4:
                changeImagePreview(theme_preview_5);
                changeButtonColor(4);
                changeChecked(checked5);
                break;
            case 5:
                changeImagePreview(theme_preview_6);
                changeButtonColor(5);
                changeChecked(checked6);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == themeBtn1) {
            ThemeColor = 0;
            changeImagePreview(theme_preview_1);
            changeChecked(checked1);
            changeButtonColor(0);
        } else if (v == themeBtn2) {
            ThemeColor = 1;
            changeImagePreview(theme_preview_2);
            changeChecked(checked2);
            changeButtonColor(1);
        } else if (v == themeBtn3) {
            ThemeColor = 2;
            changeImagePreview(theme_preview_3);
            changeChecked(checked3);
            changeButtonColor(2);
        } else if (v == themeBtn4) {
            ThemeColor = 3;
            changeImagePreview(theme_preview_4);
            changeChecked(checked4);
            changeButtonColor(3);
        } else if (v == themeBtn5) {
            ThemeColor = 4;
            changeImagePreview(theme_preview_5);
            changeChecked(checked5);
            changeButtonColor(4);
        } else if (v == themeBtn6) {
            ThemeColor = 5;
            changeImagePreview(theme_preview_6);
            changeChecked(checked6);
            changeButtonColor(5);
        }
    }

    private void changeButtonColor(int i) {
        switch (i) {
            case 0:
                Apply.setBackgroundTintList(ColorStateList.valueOf(
                        getColor(R.color.top_image)));
                Apply.setTextColor(getColor(R.color.white));
                break;
            case 1:
                Apply.setBackgroundTintList(ColorStateList.valueOf(
                        getColor(R.color.top_image_2)));
                Apply.setTextColor(getColor(R.color.white));
                break;
            case 2:
                Apply.setBackgroundTintList(ColorStateList.valueOf(
                        getColor(R.color.top_image_3)));
                Apply.setTextColor(getColor(R.color.white));
                break;
            case 3:
                Apply.setBackgroundTintList(ColorStateList.valueOf(
                        getColor(R.color.top_image_4)));
                Apply.setTextColor(getColor(R.color.bottom_image_4));
                break;
            case 4:
                Apply.setBackgroundTintList(ColorStateList.valueOf(
                        getColor(R.color.top_image_5)));
                Apply.setTextColor(getColor(R.color.white));
                break;
            case 5:
                Apply.setBackgroundTintList(ColorStateList.valueOf(
                        getColor(R.color.top_image_6)));
                Apply.setTextColor(getColor(R.color.white));
                break;
        }
    }

    private void changeChecked(ImageView v) {
        hideAllChecked();
        v.setVisibility(View.VISIBLE);

        v.setScaleX(0.5f);
        v.setScaleY(0.5f);

        ObjectAnimator animLogo = ObjectAnimator
                .ofFloat(v, "scaleY", 1f);

        ObjectAnimator animLogo2= ObjectAnimator
                .ofFloat(v, "scaleX", 1f);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(animLogo, animLogo2);
        animSet.setDuration(150);
        animSet.start();
    }

    private void hideAllChecked() {
        checked1.setVisibility(View.GONE);
        checked2.setVisibility(View.GONE);
        checked3.setVisibility(View.GONE);
        checked4.setVisibility(View.GONE);
        checked5.setVisibility(View.GONE);
        checked6.setVisibility(View.GONE);
    }

    private void changeImagePreview(ImageView v) {
        hideAllImagePreview();
        v.setVisibility(View.VISIBLE);
    }

    private void hideAllImagePreview() {
        theme_preview_1.setVisibility(View.INVISIBLE);
        theme_preview_2.setVisibility(View.INVISIBLE);
        theme_preview_3.setVisibility(View.INVISIBLE);
        theme_preview_4.setVisibility(View.INVISIBLE);
        theme_preview_5.setVisibility(View.INVISIBLE);
        theme_preview_6.setVisibility(View.INVISIBLE);
    }
}