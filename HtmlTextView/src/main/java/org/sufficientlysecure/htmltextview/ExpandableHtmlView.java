package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableHtmlView extends LinearLayout implements View.OnClickListener {

    private ExpandableHtmlTextView expandableHtmlTextView;
    private Button readMoreButton;

    public ExpandableHtmlView(Context context) {
        this(context, null);
    }

    public ExpandableHtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableView, 0, 0);
        String textContent = typedArray.getString(R.styleable.ExpandableView_textContent);
        String buttonText = typedArray.getString(R.styleable.ExpandableView_buttonText);
        int collapsedLines = typedArray.getInt(R.styleable.ExpandableView_collapsedLines, ExpandableTextView.DEFAULT_COLLAPSED_LINES);
        int animationDuration = typedArray.getInt(R.styleable.ExpandableView_animationDuration, ExpandableTextView.DEFAULT_ANIMATION_DURATION);
        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_html_view, this, true);

        expandableHtmlTextView = (ExpandableHtmlTextView) getChildAt(0);
        expandableHtmlTextView.setMaxLines(collapsedLines);
        expandableHtmlTextView.setAnimationDuration(animationDuration);
        expandableHtmlTextView.setHtml(textContent);

        readMoreButton = (Button) getChildAt(1);
        readMoreButton.setText(buttonText);
        readMoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        expandableHtmlTextView.toggle();
    }
}
