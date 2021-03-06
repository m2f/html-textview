package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableHtmlView extends LinearLayout implements View.OnClickListener {

    private ExpandableHtmlTextView expandableHtmlTextView;
    private Button readMoreButton;
    private boolean isCollapsed;
    private boolean enableHtmlGetter;
    private int collapsedLines;
    private String moreButtonText;
    private String lessButtonText;

    public ExpandableHtmlView(Context context) {
        this(context, null);
    }

    public ExpandableHtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableHtmlView, 0, 0);
        String htmlContent = typedArray.getString(R.styleable.ExpandableHtmlView_htmlContent);
        moreButtonText = typedArray.getString(R.styleable.ExpandableHtmlView_moreButtonText);
        lessButtonText = typedArray.getString(R.styleable.ExpandableHtmlView_lessButtonText);
        isCollapsed = typedArray.getBoolean(R.styleable.ExpandableHtmlView_isCollapsed, true);
        enableHtmlGetter = typedArray.getBoolean(R.styleable.ExpandableHtmlView_enableImageGetter, false);
        collapsedLines = typedArray.getInt(R.styleable.ExpandableHtmlView_collapsedLines, ExpandableHtmlTextView.DEFAULT_COLLAPSED_LINES);
        int animationDuration = typedArray.getInt(R.styleable.ExpandableHtmlView_animationDuration, ExpandableHtmlTextView.DEFAULT_ANIMATION_DURATION);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.ExpandableHtmlView_textSize, 0);
        String textColor = typedArray.getString(R.styleable.ExpandableHtmlView_textColor);

        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_html_view, this, true);

        expandableHtmlTextView = (ExpandableHtmlTextView) getChildAt(0);
        if(textSize > 0) expandableHtmlTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if(null != textColor) expandableHtmlTextView.setTextColor(Color.parseColor(textColor));

        expandableHtmlTextView.setAnimationDuration(animationDuration);
        expandableHtmlTextView.setCollapsedLines(collapsedLines);
        if(isCollapsed) {
            expandableHtmlTextView.setExpanded(false);
            expandableHtmlTextView.setMaxLines(collapsedLines);
        } else {
            expandableHtmlTextView.setExpanded(true);
            expandableHtmlTextView.setMaxLines(Integer.MAX_VALUE);
        }
        setHtmlContent(htmlContent);

        readMoreButton = (Button) getChildAt(1);
        readMoreButton.setText(isCollapsed ? moreButtonText : lessButtonText);
        readMoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        expandableHtmlTextView.toggle();
        readMoreButton.setText(expandableHtmlTextView.isExpanded() ? lessButtonText : moreButtonText);
    }

    public void setHtmlContent(String htmlContent) {
        if(null == htmlContent) htmlContent = "";
        if(enableHtmlGetter) {
            expandableHtmlTextView.setHtml(htmlContent, new HtmlHttpImageGetter(expandableHtmlTextView));
        } else {
            expandableHtmlTextView.setHtml(htmlContent);
        }
        expandableHtmlTextView.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = expandableHtmlTextView.getLayout();
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

    public ExpandableHtmlTextView getExpandableHtmlTextView() {
        return expandableHtmlTextView;
    }
}
