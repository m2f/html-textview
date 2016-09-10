package org.sufficientlysecure.htmltextview.example;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * A plain old Java object that holds a HTML string.
 */
public class NewsItem {

    private String text;
    private String html;

    /**
     * This method is called by data binding as we declared app:html="@{newsItem.html}"
     *
     * @return the HTML string that will be set into {@link HtmlTextView}
     */
    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

