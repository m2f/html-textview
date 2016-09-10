package org.sufficientlysecure.htmltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExpandableHtmlTextView extends LinearLayout implements View.OnClickListener {

    private HtmlTextViewExpandable htmlTextViewExpandable;
    private Button readMoreButton;
    private boolean isCollapsed;
    private boolean enableHtmlGetter;
    private int collapsedLines;
    private String moreButtonText;
    private String lessButtonText;

    public ExpandableHtmlTextView(Context context) {
        this(context, null);
    }

    public ExpandableHtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableHtmlTextView, 0, 0);
        String htmlContent = typedArray.getString(R.styleable.ExpandableHtmlTextView_htmlContent);
        moreButtonText = typedArray.getString(R.styleable.ExpandableHtmlTextView_moreButtonText);
        lessButtonText = typedArray.getString(R.styleable.ExpandableHtmlTextView_lessButtonText);
        isCollapsed = typedArray.getBoolean(R.styleable.ExpandableHtmlTextView_isCollapsed, true);
        enableHtmlGetter = typedArray.getBoolean(R.styleable.ExpandableHtmlTextView_enableImageGetter, false);
        collapsedLines = typedArray.getInt(R.styleable.ExpandableHtmlTextView_collapsedLines, HtmlTextViewExpandable.DEFAULT_COLLAPSED_LINES);
        int animationDuration = typedArray.getInt(R.styleable.ExpandableHtmlTextView_animationDuration, HtmlTextViewExpandable.DEFAULT_ANIMATION_DURATION);
        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.expandable_html_view, this, true);

        htmlTextViewExpandable = (HtmlTextViewExpandable) getChildAt(0);
        htmlTextViewExpandable.setAnimationDuration(animationDuration);
        htmlTextViewExpandable.setCollapsedLines(collapsedLines);
        if(isCollapsed) {
            htmlTextViewExpandable.setExpanded(false);
            htmlTextViewExpandable.setMaxLines(collapsedLines);
        } else {
            htmlTextViewExpandable.setExpanded(true);
            htmlTextViewExpandable.setMaxLines(Integer.MAX_VALUE);
        }
        setHtmlContent(htmlContent);

        readMoreButton = (Button) getChildAt(1);
        readMoreButton.setText(isCollapsed ? moreButtonText : lessButtonText);
        readMoreButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        htmlTextViewExpandable.toggle();
        readMoreButton.setText(htmlTextViewExpandable.isExpanded() ? lessButtonText : moreButtonText);
    }

    public void setHtmlContent(String htmlContent) {
        if(null == htmlContent) htmlContent = "";
        if(enableHtmlGetter) {
            htmlTextViewExpandable.setHtml(htmlContent, new HtmlHttpImageGetter(htmlTextViewExpandable));
        } else {
            htmlTextViewExpandable.setHtml(htmlContent);
        }
        htmlTextViewExpandable.post(new Runnable() {
            @Override
            public void run() {
                Layout layout = htmlTextViewExpandable.getLayout();
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
