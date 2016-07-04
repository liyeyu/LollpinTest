package com.ssc.lollpintest.view;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ssc.lollpintest.R;

public class ImageActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mTextView = (TextView) findViewById(R.id.tv);

        if (getIntent()!=null){
            String text = getIntent().getStringExtra("text");
            mTextView.setText(text);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }
}
