package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableBulletView extends LinearLayout implements View.OnClickListener {

    private ExpandableBulletTextView expandableBulletTextView;
    private Button readMoreButton;
    private boolean isCollapsed;
    private int collapsedLines;
    private String moreButtonText;
    private String lessButtonText;

    public ExpandableBulletView(Context context) {
        this(context, null);
    }

    public ExpandableBulletView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableBulletView, 0, 0);
        int arrayContentRes = typedArray.getResourceId(R.styleable.ExpandableBulletView_arrayContent, -1);
        String[] arrayContent;
        if(arrayContentRes == -1) {
            arrayContent = new String[0];
        } else {
            arrayContent = getResources().getStringArray(arrayContentRes);
        }
        moreButtonText = typedArray.getString(R.styleable.ExpandableBulletView_moreButtonText);
        lessButtonText = typedArray.getString(R.styleable.ExpandableBulletView_lessButtonText);
        isCollapsed = typedArray.getBoolean(R.styleable.ExpandableBulletView_isCollapsed, true);
        collapsedLines = typedArray.getInt(R.styleable.ExpandableBulletView_collapsedLines, ExpandableBulletTextView.DEFAULT_COLLAPSED_LINES);
        int animationDuration = typedArray.getInt(R.styleable.ExpandableBulletView_animationDuration, ExpandableBulletTextView.DEFAULT_ANIMATION_DURATION);
        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_bullet_view, this, true);

        expandableBulletTextView = (ExpandableBulletTextView) getChildAt(0);
        expandableBulletTextView.setAnimationDuration(animationDuration);
        expandableBulletTextView.setCollapsedLines(collapsedLines);
        if(isCollapsed) {
            expandableBulletTextView.setExpanded(false);
            expandableBulletTextView.setMaxLines(collapsedLines);
        } else {
            expandableBulletTextView.setExpanded(true);
            expandableBulletTextView.setMaxLines(Integer.MAX_VALUE);
        }
        setArrayContent(arrayContent);

        readMoreButton = (Button) getChildAt(1);
        readMoreButton.setText(isCollapsed ? moreButtonText : lessButtonText);
        readMoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        expandableBulletTextView.toggle();
        readMoreButton.setText(expandableBulletTextView.isExpanded() ? lessButtonText : moreButtonText);
    }

    public void setArrayContent(String[] arrayContent) {
        if(null == arrayContent) arrayContent = new String[0];
        expandableBulletTextView.setArrayContent(arrayContent);
        expandableBulletTextView.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = expandableBulletTextView.getLayout();
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
