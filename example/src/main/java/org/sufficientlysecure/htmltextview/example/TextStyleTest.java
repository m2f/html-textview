package org.sufficientlysecure.htmltextview.example;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.sufficientlysecure.htmltextview.BulletTextView;

public class TextStyleTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_style_test);
        BulletTextView btv = (BulletTextView) findViewById(R.id.bullet_text_view);
        Resources res = getResources();
        String[] planets = res.getStringArray(R.array.planets_array);
        btv.setArrayContent(planets);
    }
}
