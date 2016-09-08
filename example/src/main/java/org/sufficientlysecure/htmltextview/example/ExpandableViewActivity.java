package org.sufficientlysecure.htmltextview.example;

import android.app.Activity;
import android.os.Bundle;

import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

public class ExpandableViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_view);
            String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam ut eros sed arcu auctor tincidunt" +
                    "    id sit amet elit. Mauris in faucibus neque. Suspendisse facilisis urna nec nisi convallis tincidunt." +
                    "    Mauris at elit et arcu viverra auctor. Nullam et arcu ultricies, iaculis dolor efficitur, tristique eros." +
                    "    Interdum et malesuada <b>some bold text in here</b> fames ac ante ipsum primis in faucibus." +
                    "    Integer nec aliquet mi." +
                    "    <ul>" +
                    "        <li>One</li>" +
                    "        <li>Two</li>" +
                    "        <li>Three</li>" +
                    "    </ul>" +
                    "        <h2><font color='#FF0000'>Know About Me1</font></h2>" +
                    "        <p>" +
                    "            Hello this is in a paragraph, and is long." +
                    "        </p>";
        
        HtmlTextView textView = (HtmlTextView) findViewById(R.id.html_text_view);
        //textView.setHtml(getString(R.string.html_text_content), new HtmlResImageGetter(textView));
        textView.setHtml(content);
        //textView.setHtml(R.raw.example);
    }
}
