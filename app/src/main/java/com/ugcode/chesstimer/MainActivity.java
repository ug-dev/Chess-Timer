package com.ugcode.chesstimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    private ImageView top_image, bottom_image;
    private TextView topTimeText, bottomTimeText, topSubtitleText,
            bottomSubtitleText, topMoveText, bottomMoveText;

    private long TopTimeLeftMillis, BottomTimeLeftMillis;
    CountDownTimer BottomCountDownTimer, TopCountDownTimer;

    private ImageButton pauseButton, settingsButton, themesButton, restartButton;

    private int height;
    private String flag = "";
    private boolean pause = false;
    private int TopCount, BottomCount;

    private static final String myData = "myData";
    private int ThemeColor, SelectedCard;
    private String m, s;
    private boolean Vibration, AlertTime, Sound;

    private SoundPool soundPool;
    private int sound1, sound2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(1)
                .build();

        sound1 = soundPool.load(this, R.raw.tick, 1);
        sound2 = soundPool.load(this, R.raw.complete, 1);

        Context context = getApplicationContext();
        sharedPref = context.getSharedPreferences(myData, Context.MODE_PRIVATE);
        ThemeColor = sharedPref.getInt("ThemeColor", 0);

        TopCount = 0;
        BottomCount = 0;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;

        top_image = findViewById(R.id.top_image);
        bottom_image = findViewById(R.id.bottom_image);
        topTimeText = findViewById(R.id.top_image_text);
        bottomTimeText = findViewById(R.id.bottom_image_text);
        topSubtitleText = findViewById(R.id.top_subtitle_text);
        bottomSubtitleText = findViewById(R.id.bottom_subtitle_text);
        topMoveText = findViewById(R.id.top_move_text);
        bottomMoveText = findViewById(R.id.bottom_move_text);
        pauseButton = findViewById(R.id.pauseButton);
        settingsButton = findViewById(R.id.settingsButton);
        themesButton = findViewById(R.id.themesButton);
        restartButton = findViewById(R.id.restartButton);

        bottom_image.setTranslationY(height*20/100);
        top_image.setTranslationY(-height*20/100);
        topTimeText.setTranslationY(-height*10/100);
        bottomTimeText.setTranslationY(height*10/100);
        topSubtitleText.setTranslationY(-height*10/100);
        bottomSubtitleText.setTranslationY(height*10/100);
        pauseButton.setTranslationY(height*15/100);
        settingsButton.setTranslationY(height*15/100);
        restartButton.setTranslationY(height*15/100);
        themesButton.setTranslationY(height*15/100);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        themesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Themes.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.restart_msg)
                        .setCancelable(false)
                        .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restartTimer();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                fullScreenMode();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        top_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("")){
                    startTopTimer();
                    hideExtraButtons();
                    bottomAnimation();
                    hideBottomExtraText();
                    hideTopExtraText();

                    pauseButton.setVisibility(View.VISIBLE);
                } else if (flag.equals("top"))  {
                    if(pause){
                        restartButton.setVisibility(View.GONE);
                        hideExtraButtons();
                        hideTopExtraText();
                        startTopTimer();
                        pause=false;
                    } else {
                        if (Sound) {
                            soundPool.play(sound1, 1, 1, 0, 0, 1);
                        }
                        startBottomTimer();
                        topAnimation();
                        stopTopTimer();
                    }
                    pauseButton.setVisibility(View.VISIBLE);
                }
            }
        });
        
        bottom_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("")){
                    hideExtraButtons();
                    startBottomTimer();
                    topAnimation();
                    hideTopExtraText();
                    hideBottomExtraText();

                    pauseButton.setVisibility(View.VISIBLE);
                } else if (flag.equals("bottom")) {
                    if(pause){
                        restartButton.setVisibility(View.GONE);
                        hideExtraButtons();
                        startBottomTimer();
                        pause=false;
                        hideBottomExtraText();
                    } else {
                        if (Sound) {
                            soundPool.play(sound1, 1, 1, 0, 0, 1);
                        }
                        startTopTimer();
                        bottomAnimation();
                        stopBottomTimer();
                    }
                    pauseButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void restartTimer() {
        fullScreenMode();
        BottomCount = 0;
        TopCount = 0;
        flag = "";
        pause = false;

        SelectedCard = sharedPref.getInt("SelectedCard", 2);
        changeGameMode();

        String msg = "0";
        topMoveText.setText(msg);
        bottomMoveText.setText(msg);

        topSubtitleText.setText(getResources().getString(R.string.subtitle_text));
        bottomSubtitleText.setText(getResources().getString(R.string.subtitle_text));

        topSubtitleText.setVisibility(View.VISIBLE);
        bottomSubtitleText.setVisibility(View.VISIBLE);

        ObjectAnimator animBottomSubtitleText = ObjectAnimator
                .ofFloat(bottomSubtitleText, "alpha", 1f);
        animBottomSubtitleText.setDuration(200);
        animBottomSubtitleText.start();

        ObjectAnimator animTopSubtitleText = ObjectAnimator
                .ofFloat(topSubtitleText, "alpha", 1f);
        animTopSubtitleText.setDuration(200);
        animTopSubtitleText.start();

        pauseButton.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
        
        restartAnimation();
    }

    private void restartAnimation() {
        AnimatorSet set = new AnimatorSet();

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(top_image, "translationY", -height*20/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(settingsButton,
                                "translationY", height*15/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(restartButton,
                                "translationY", height*15/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(themesButton,
                                "translationY", height*15/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(pauseButton,
                                "translationY", height*15/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(bottomTimeText, "translationY", height*10/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(bottomSubtitleText,
                                "translationY", height*10/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(topSubtitleText,
                                "translationY", -height*10/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(bottom_image, "translationY", height*20/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(topTimeText, "translationY", -height*10/100))
        );

        set.setDuration(600);
        set.start();
    }

    private void pauseTimer() {
        showExtraButtons();
        restartButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.GONE);
        if (flag.equals("bottom")) {
            bottomSubtitleText.setVisibility(View.VISIBLE);
            bottomSubtitleText.setText(getResources().getString(R.string.subtitle_resume_text));
            stopBottomTimer();
            pause = true;

            ObjectAnimator animBottomSubtitleText = ObjectAnimator
                    .ofFloat(bottomSubtitleText, "alpha", 1f);
            animBottomSubtitleText.setDuration(200);
            animBottomSubtitleText.start();

        } else if (flag.equals("top")) {
            topSubtitleText.setVisibility(View.VISIBLE);
            topSubtitleText.setText(getResources().getString(R.string.subtitle_resume_text));
            stopTopTimer();
            pause = true;

            ObjectAnimator animTopSubtitleText = ObjectAnimator
                    .ofFloat(topSubtitleText, "alpha", 1f);
            animTopSubtitleText.setDuration(200);
            animTopSubtitleText.start();
        }
    }

    private void hideExtraButtons() {
        settingsButton.setVisibility(View.GONE);
        themesButton.setVisibility(View.GONE);
    }

    private void showExtraButtons() {
        settingsButton.setVisibility(View.VISIBLE);
        themesButton.setVisibility(View.VISIBLE);
    }

    private void changeThemeColor() {
        switch (ThemeColor) {
            case 0:
                top_image.setColorFilter(getColor(R.color.top_image));
                bottom_image.setColorFilter(getColor(R.color.bottom_image));
                bottomSubtitleText.setTextColor(getColor(R.color.text_color));
                topSubtitleText.setTextColor(getColor(R.color.text_color));
                topMoveText.setTextColor(getColor(R.color.text_color));
                bottomMoveText.setTextColor(getColor(R.color.text_color));
                topTimeText.setTextColor(getColor(R.color.text_color));
                bottomTimeText.setTextColor(getColor(R.color.text_color));
                break;
            case 1:
                top_image.setColorFilter(getColor(R.color.top_image_2));
                bottom_image.setColorFilter(getColor(R.color.bottom_image_2));
                bottomSubtitleText.setTextColor(getColor(R.color.top_image_2));
                topSubtitleText.setTextColor(getColor(R.color.bottom_image_2));
                topMoveText.setTextColor(getColor(R.color.bottom_image_2));
                bottomMoveText.setTextColor(getColor(R.color.top_image_2));
                topTimeText.setTextColor(getColor(R.color.bottom_image_2));
                bottomTimeText.setTextColor(getColor(R.color.top_image_2));
                restartButton.setImageResource(R.drawable.ic_restartbutton_dark);
                pauseButton.setImageResource(R.drawable.ic_pausebutton_dark);
                settingsButton.setImageResource(R.drawable.ic_settingsbutton_dark);
                themesButton.setImageResource(R.drawable.ic_themesbutton_dark);
                break;
            case 2:
                top_image.setColorFilter(getColor(R.color.top_image_3));
                bottom_image.setColorFilter(getColor(R.color.bottom_image_3));
                bottomSubtitleText.setTextColor(getColor(R.color.bottom_text_color_3));
                topSubtitleText.setTextColor(getColor(R.color.top_text_color_3));
                topMoveText.setTextColor(getColor(R.color.top_text_color_3));
                bottomMoveText.setTextColor(getColor(R.color.bottom_text_color_3));
                topTimeText.setTextColor(getColor(R.color.top_text_color_3));
                bottomTimeText.setTextColor(getColor(R.color.bottom_text_color_3));
                break;
            case 3:
                top_image.setColorFilter(getColor(R.color.top_image_4));
                bottom_image.setColorFilter(getColor(R.color.bottom_image_4));
                bottomSubtitleText.setTextColor(getColor(R.color.top_image_4));
                topSubtitleText.setTextColor(getColor(R.color.bottom_image_4));
                topMoveText.setTextColor(getColor(R.color.bottom_image_4));
                bottomMoveText.setTextColor(getColor(R.color.top_image_4));
                topTimeText.setTextColor(getColor(R.color.bottom_image_4));
                bottomTimeText.setTextColor(getColor(R.color.top_image_4));
                break;
            case 4:
                top_image.setColorFilter(getColor(R.color.top_image_5));
                bottom_image.setColorFilter(getColor(R.color.bottom_image_5));
                bottomSubtitleText.setTextColor(getColor(R.color.text_color_5));
                topSubtitleText.setTextColor(getColor(R.color.text_color_5));
                topMoveText.setTextColor(getColor(R.color.text_color_5));
                bottomMoveText.setTextColor(getColor(R.color.text_color_5));
                topTimeText.setTextColor(getColor(R.color.text_color_5));
                bottomTimeText.setTextColor(getColor(R.color.text_color_5));
                break;
            case 5:
                top_image.setColorFilter(getColor(R.color.top_image_6));
                bottom_image.setColorFilter(getColor(R.color.bottom_image_6));
                bottomSubtitleText.setTextColor(getColor(R.color.text_color_6));
                topSubtitleText.setTextColor(getColor(R.color.text_color_6));
                topMoveText.setTextColor(getColor(R.color.text_color_6));
                bottomMoveText.setTextColor(getColor(R.color.text_color_6));
                topTimeText.setTextColor(getColor(R.color.text_color_6));
                bottomTimeText.setTextColor(getColor(R.color.text_color_6));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        AlertTime = sharedPref.getBoolean("LowTime", false);
        Vibration = sharedPref.getBoolean("Vibrate", false);
        Sound = sharedPref.getBoolean("Audio", false);
        m = sharedPref.getString("Minute", "00");
        s = sharedPref.getString("Second", "00");

        if (!flag.equals("finish") && !pause) {
            SelectedCard = sharedPref.getInt("SelectedCard", 2);
            changeGameMode();
        }

        fullScreenMode();
        changeThemeColor();
    }

    private void changeGameMode() {
        String msg;

        switch (SelectedCard) {
            case 0:
                TopTimeLeftMillis = 1800000;
                BottomTimeLeftMillis = 1800000;
                msg = "30:00";
                topTimeText.setText(msg);
                bottomTimeText.setText(msg);
                break;
            case 1:
                TopTimeLeftMillis = 900000;
                BottomTimeLeftMillis = 900000;
                msg = "15:00";
                topTimeText.setText(msg);
                bottomTimeText.setText(msg);
                break;
            case 2:
                TopTimeLeftMillis = 600000;
                BottomTimeLeftMillis = 600000;
                msg = "10:00";
                topTimeText.setText(msg);
                bottomTimeText.setText(msg);
                break;
            case 3:
                TopTimeLeftMillis = 300000;
                BottomTimeLeftMillis = 300000;
                msg = "05:00";
                topTimeText.setText(msg);
                bottomTimeText.setText(msg);
                break;
            case 4:
                TopTimeLeftMillis = 180000;
                BottomTimeLeftMillis = 180000;
                msg = "03:00";
                topTimeText.setText(msg);
                bottomTimeText.setText(msg);
                break;
            case 5:
                TopTimeLeftMillis = 60000;
                BottomTimeLeftMillis = 60000;
                msg = "01:00";
                topTimeText.setText(msg);
                bottomTimeText.setText(msg);
                break;
        }
    }

    private void fullScreenMode() {
        getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!flag.equals("") && !pause && !flag.equals("finish")) {
            pauseTimer();
        }
    }

    private void startBottomTimer() {
        BottomCountDownTimer = new CountDownTimer(
                BottomTimeLeftMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                String m1 = String.format(Locale.getDefault(), "%02d", minutes);
                String m2 = String.format(Locale.getDefault(), "%02d", seconds);

                if (AlertTime) {
                    if (m.equals(m1) && s.equals(m2)) {
                        alertTime();
                    }
                }

                String msg = m1 + ":" + m2;
                bottomTimeText.setText(msg);

                BottomTimeLeftMillis = millisUntilFinished;
            }

            public void onFinish() {
                String msg = "00:00";
                bottomTimeText.setText(msg);
                flag = "finish";
                showExtraButtons();
                restartButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);

                vibrateEffect();
                soundPool.play(sound2, 1, 1, 0, 1, 1);
            }
        };

        BottomCountDownTimer.start();
    }

    private void stopBottomTimer() {
        BottomCountDownTimer.cancel();
    }

    private void startTopTimer() {
        TopCountDownTimer =
                new CountDownTimer(TopTimeLeftMillis, 1000) {

                    public void onTick(long millisUntilFinished) {
                        long minutes = (millisUntilFinished / 1000) / 60;
                        long seconds = (millisUntilFinished / 1000) % 60;

                        String m1 = String.format(Locale.getDefault(), "%02d", minutes);
                        String m2 = String.format(Locale.getDefault(), "%02d", seconds);

                        if (AlertTime) {
                            if (m.equals(m1) && s.equals(m2)) {
                                alertTime();
                            }
                        }

                        String msg = m1 + ":" + m2;
                        topTimeText.setText(msg);

                        TopTimeLeftMillis = millisUntilFinished;
                    }

                    public void onFinish() {
                        String msg = "00:00";
                        topTimeText.setText(msg);
                        flag = "finish";
                        showExtraButtons();
                        restartButton.setVisibility(View.VISIBLE);
                        pauseButton.setVisibility(View.GONE);

                        vibrateEffect();
                        soundPool.play(sound2, 1, 1, 0, 1, 1);
                    }
                };

        TopCountDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    private void alertTime() {
        soundPool.play(sound2, 1, 1, 0, 1, 1);

        if (Vibration) {
            vibrateEffect();
        }
    }

    private void vibrateEffect() {
        Vibrator v = (Vibrator) getSystemService(Context. VIBRATOR_SERVICE );
        assert v != null;
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot (1000,
                    VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(1000);
        }
    }

    private void stopTopTimer() {
        TopCountDownTimer.cancel();
    }

    private void hideTopExtraText() {
        ObjectAnimator animTopSubtitleText = ObjectAnimator
                .ofFloat(topSubtitleText, "alpha", 0f)
                .setDuration(200);

        animTopSubtitleText.start();
    }
    private void hideBottomExtraText() {
        ObjectAnimator animBottomSubtitleText = ObjectAnimator
                .ofFloat(bottomSubtitleText, "alpha", 0f)
                .setDuration(200);

        animBottomSubtitleText.start();
    }

    private void bottomAnimation() {
        if (!flag.equals("") && !flag.equals("pause")) {
            BottomCount++;
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(bottom_image,
                                "translationY", height*40/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(settingsButton,
                                "translationY", height*35/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(restartButton,
                                "translationY", height*35/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(themesButton,
                                "translationY", height*35/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(pauseButton,
                                "translationY", height*35/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(bottomTimeText,
                                "translationY", height*20/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(bottomSubtitleText,
                                "translationY", height*20/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(topSubtitleText,
                                "translationY", 0))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator
                                .ofFloat(top_image, "translationY", 0))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator
                                .ofFloat(topTimeText, "translationY", 0))
        );
        set.setDuration(600);
        set.start();

        flag = "top";
        bottomMoveText.setText(String.valueOf(BottomCount));
    }

    private void topAnimation() {
        if (!flag.equals("") && !flag.equals("pause")) {
            TopCount++;
        }
        AnimatorSet set = new AnimatorSet();

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(top_image, "translationY", -height*40/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(settingsButton,
                                "translationY", -height*5/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(restartButton,
                                "translationY", -height*5/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(themesButton,
                                "translationY", -height*5/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(pauseButton,
                                "translationY", -height*5/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(bottomTimeText, "translationY", 0))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(bottomSubtitleText,
                                "translationY", 0))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600,
                        ObjectAnimator.ofFloat(topSubtitleText,
                                "translationY", -height*20/100))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(bottom_image, "translationY", 0))
        );

        set.playTogether(
                Glider.glide(Skill.ExpoEaseOut, 600, ObjectAnimator
                        .ofFloat(topTimeText, "translationY", -height*20/100))
        );

        set.setDuration(600);
        set.start();

        flag = "bottom";
        topMoveText.setText(String.valueOf(TopCount));
    }
}