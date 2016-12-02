package org.literacyapp.content.number;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.literacyapp.LiteracyApplication;
import org.literacyapp.R;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.content.Number;
import org.literacyapp.util.MediaPlayerHelper;
import org.literacyapp.util.TtsHelper;

import java.util.List;

public class NumbersActivity extends AppCompatActivity {

    private NumberDao numberDao;

    private GridLayout numberGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.blue));

        LiteracyApplication literacyApplication = (LiteracyApplication) getApplicationContext();
        numberDao = literacyApplication.getDaoSession().getNumberDao();

        numberGridLayout = (GridLayout) findViewById(R.id.numberGridLayout);


        List<Number> numbers = numberDao.loadAll();
        Log.i(getClass().getName(), "numbers.size(): " + numbers.size());
        for (final Number number : numbers) {
            View numberView = LayoutInflater.from(this).inflate(R.layout.content_numbers_number_view, numberGridLayout, false);
            TextView numberTextView = (TextView) numberView.findViewById(R.id.numberTextView);
            if (!TextUtils.isEmpty(number.getSymbol())) {
                numberTextView.setText(number.getSymbol());
            } else {
                numberTextView.setText(number.getValue().toString());
            }

            numberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(getClass().getName(), "onClick");

                    Log.i(getClass().getName(), "number.getValue(): " + number.getValue());
                    Log.i(getClass().getName(), "number.getSymbol(): " + number.getSymbol());
                    Log.i(getClass().getName(), "number.getWord(): " + number.getWord());
                    if (number.getWord() != null) {
                        Log.i(getClass().getName(), "number.getWord().getText(): " + number.getWord().getText());
                    }

                    String audioFileName = "digit_" + number.getValue();
                    int resourceId = getResources().getIdentifier(audioFileName, "raw", getPackageName());
                    try {
                        if (resourceId != 0) {
                            MediaPlayerHelper.play(getApplicationContext(), resourceId);
                        } else {
                            // Fall-back to TTS
                            if (number.getWord() != null) {
                                TtsHelper.speak(getApplicationContext(), number.getWord().getText());
                            } else {
                                TtsHelper.speak(getApplicationContext(), number.getValue().toString());
                            }
                        }
                    } catch (Resources.NotFoundException e) {
                        // Fall-back to TTS
                        if (number.getWord() != null) {
                            TtsHelper.speak(getApplicationContext(), number.getWord().getText());
                        } else {
                            TtsHelper.speak(getApplicationContext(), number.getValue().toString());
                        }
                    }

                    Intent intent = new Intent();
                    intent.setPackage("org.literacyapp.analytics");
                    intent.setAction("literacyapp.intent.action.USAGE_EVENT");
                    intent.putExtra("packageName", getPackageName());
                    intent.putExtra("numeracySkill", "NUMBER_IDENTIFICATION");
                    sendBroadcast(intent);
                }
            });

            numberGridLayout.addView(numberView);
        }
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getName(), "onStart");
        super.onStart();


    }
}
