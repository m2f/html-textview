package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableView extends LinearLayout implements View.OnClickListener {

    private ExpandableTextView expandableTextView;
    private Button readMoreButton;

    public ExpandableView(Context context) {
        this(context, null);
    }

    public ExpandableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableView, 0, 0);
        String textContent = typedArray.getString(R.styleable.ExpandableView_textContent);
        String buttonText = typedArray.getString(R.styleable.ExpandableView_buttonText);
        int collapsedLines = typedArray.getInt(R.styleable.ExpandableView_collapsedLines, ExpandableTextView.DEFAULT_COLLAPSED_LINES);
        int animationDuration = typedArray.getInt(R.styleable.ExpandableView_animationDuration, ExpandableTextView.DEFAULT_ANIMATION_DURATION);
        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_view, this, true);

        expandableTextView = (ExpandableTextView) getChildAt(0);
        expandableTextView.setMaxLines(collapsedLines);
        expandableTextView.setAnimationDuration(animationDuration);
        expandableTextView.setText(textContent);

        readMoreButton = (Button) getChildAt(1);
        readMoreButton.setText(buttonText);
        readMoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("XXX", "Button Clicked");
        expandableTextView.toggle();
    }
}
