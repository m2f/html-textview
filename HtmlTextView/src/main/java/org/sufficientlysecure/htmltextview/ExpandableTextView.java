package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableTextView extends LinearLayout implements View.OnClickListener {

    private TextViewExpandable textViewExpandable;
    private Button readMoreButton;
    private boolean isCollapsed;
    private int collapsedLines;
    private String moreButtonText;
    private String lessButtonText;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, 0, 0);
        String textContent = typedArray.getString(R.styleable.ExpandableTextView_textContent);
        moreButtonText = typedArray.getString(R.styleable.ExpandableTextView_moreButtonText);
        lessButtonText = typedArray.getString(R.styleable.ExpandableTextView_lessButtonText);
        isCollapsed = typedArray.getBoolean(R.styleable.ExpandableTextView_isCollapsed, true);
        collapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_collapsedLines, TextViewExpandable.DEFAULT_COLLAPSED_LINES);
        int animationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animationDuration, TextViewExpandable.DEFAULT_ANIMATION_DURATION);
        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_view, this, true);

        textViewExpandable = (TextViewExpandable) getChildAt(0);
        textViewExpandable.setAnimationDuration(animationDuration);
        textViewExpandable.setCollapsedLines(collapsedLines);
        if(isCollapsed) {
            textViewExpandable.setExpanded(false);
            textViewExpandable.setMaxLines(collapsedLines);
        } else {
            textViewExpandable.setExpanded(true);
            textViewExpandable.setMaxLines(Integer.MAX_VALUE);
        }
        setTextContent(textContent);

        readMoreButton = (Button) getChildAt(1);
        readMoreButton.setText(isCollapsed ? moreButtonText : lessButtonText);
        readMoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        textViewExpandable.toggle();
        readMoreButton.setText(textViewExpandable.isExpanded() ? lessButtonText : moreButtonText);
    }

    public void setTextContent(String textContent) {
        if(null == textContent) textContent = "";
        textViewExpandable.setText(textContent);
        textViewExpandable.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = textViewExpandable.getLayout();
                if(layout != null) {
                    int lineCount = layout.getLineCount();
                    if(lineCount > 0) {
                        int ellipsisCount = layout.getEllipsisCount(lineCount - 1);
                        boolean isVisible = ellipsisCount > 0 || lineCount > collapsedLines;
                        readMoreButton.setVisibility( isVisible ? VISIBLE : GONE);
                    }
                }
            }
        });
    }
}
