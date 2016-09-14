package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class BulletTextView extends TextView {

    public BulletTextView(Context context) {
        super(context);
    }

    public BulletTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BulletTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableBulletView, 0, 0);
        int arrayContentRes = typedArray.getResourceId(R.styleable.ExpandableBulletView_arrayContent, -1);
        String[] arrayContent;
        if(arrayContentRes == -1) {
            arrayContent = new String[0];
        } else {
            arrayContent = getResources().getStringArray(arrayContentRes);
        }
        typedArray.recycle();
        if(arrayContent.length > 0) {
            setArrayContent(arrayContent);
        }
    }

    public void setArrayContent(String[] points) {
        String pointersStr = TextUtils.join("\n", points);
        SpannableString span = new SpannableString(pointersStr);
        int spanStart = 0, len = points.length;
        for(int i = 0; i <  len - 1; i++) {
            int spanEnd = spanStart + points[i].length() + 1;
            span.setSpan(new StarSpan(8), spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanStart = spanEnd;
        }
        span.setSpan(new StarSpan(8), spanStart, pointersStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.setText(span);
    }
}
