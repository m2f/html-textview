package org.sufficientlysecure.htmltextview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

/**
 *
 *  Referenced from :
 * https://codexplo.wordpress.com/2013/09/07/android-expandable-textview/
 * And
 * https://github.com/Blogcat/Android-ExpandableTextView/blob/master/expandabletextview/src/main/java/at/blogc/android/views/ExpandableTextView.java
 *
 */
public class ExpandableTextView extends TextView {

    public static final int DEFAULT_COLLAPSED_LINES = 2;
    public static final int DEFAULT_ANIMATION_DURATION = 500;

    private final int maxLines;
    private int collapsedLines;
    private int animationDuration;
    private boolean animating;
    private boolean expanded;
    private int collapsedHeight;

    private TimeInterpolator expandInterpolator;
    private TimeInterpolator collapseInterpolator;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.collapsedLines = typedArray.getInt(R.styleable.ExpandableTextView_collapsedLines, DEFAULT_COLLAPSED_LINES);
        this.animationDuration = typedArray.getInt(R.styleable.ExpandableTextView_animationDuration, DEFAULT_ANIMATION_DURATION);
        typedArray.recycle();

        this.setMaxLines(collapsedLines);

        maxLines = super.getMaxLines();

        // create default interpolators
        this.expandInterpolator = new AccelerateDecelerateInterpolator();
        this.collapseInterpolator = new AccelerateDecelerateInterpolator();
    }

    public boolean toggle() {
        return this.expanded ? this.collapse() : this.expand();
    }

    /**
     * Expand this {@link ExpandableTextView}.
     * @return true if expanded, false otherwise.
     */
    public boolean expand() {
        if (!this.expanded && !this.animating && this.maxLines >= 0) {
            this.animating = true;

            // get collapsed height
            this.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY),
                         MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            this.collapsedHeight = this.getMeasuredHeight();

            // set maxLines to MAX Integer, so we can calculate the expanded height
            this.setMaxLines(Integer.MAX_VALUE);

            // get expanded height
            this.measure(MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), MeasureSpec.EXACTLY),
                         MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            final int expandedHeight = this.getMeasuredHeight();

            // animate from collapsed height to expanded height
            final ValueAnimator valueAnimator = ValueAnimator.ofInt(this.collapsedHeight, expandedHeight);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    final ViewGroup.LayoutParams layoutParams = ExpandableTextView.this.getLayoutParams();
                    layoutParams.height = (int) animation.getAnimatedValue();
                    ExpandableTextView.this.setLayoutParams(layoutParams);
                }
            });

            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(final Animator animation) {
                    // if fully expanded, set height to WRAP_CONTENT, because when rotating the device
                    // the height calculated with this ValueAnimator isn't correct anymore
                    final ViewGroup.LayoutParams layoutParams = ExpandableTextView.this.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ExpandableTextView.this.setLayoutParams(layoutParams);

                    // keep track of current status
                    ExpandableTextView.this.expanded = true;
                    ExpandableTextView.this.animating = false;
                }
            });

            // set interpolator
            valueAnimator.setInterpolator(this.expandInterpolator);

            // start the animation
            valueAnimator
                    .setDuration(this.animationDuration)
                    .start();

            return true;
        }

        return false;
    }

    /**
     * Collapse this {@link TextView}.
     * @return true if collapsed, false otherwise.
     */
    public boolean collapse() {
        if (this.expanded && !this.animating && this.maxLines >= 0) {
            this.animating = true;

            // get expanded height
            final int expandedHeight = this.getMeasuredHeight();

            // animate from expanded height to collapsed height
            final ValueAnimator valueAnimator = ValueAnimator.ofInt(expandedHeight, this.collapsedHeight);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    final ViewGroup.LayoutParams layoutParams = ExpandableTextView.this.getLayoutParams();
                    layoutParams.height = (int) animation.getAnimatedValue();
                    ExpandableTextView.this.setLayoutParams(layoutParams);
                }
            });

            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(final Animator animation) {
                    // set maxLines to original value
                    ExpandableTextView.this.setMaxLines(ExpandableTextView.this.maxLines);

                    // if fully collapsed, set height to WRAP_CONTENT, because when rotating the device
                    // the height calculated with this ValueAnimator isn't correct anymore
                    final ViewGroup.LayoutParams layoutParams = ExpandableTextView.this.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    ExpandableTextView.this.setLayoutParams(layoutParams);

                    // keep track of current status
                    ExpandableTextView.this.expanded = false;
                    ExpandableTextView.this.animating = false;
                }
            });

            // set interpolator
            valueAnimator.setInterpolator(this.collapseInterpolator);

            // start the animation
            valueAnimator
                    .setDuration(this.animationDuration)
                    .start();

            return true;
        }

        return false;
    }

    /**
     * Sets the duration of the expand / collapse animation.
     * @param animationDuration duration in milliseconds.
     */
    public void setAnimationDuration(final int animationDuration) {
        this.animationDuration = animationDuration;
    }
}
