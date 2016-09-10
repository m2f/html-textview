package org.sufficientlysecure.htmltextview.example;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import org.sufficientlysecure.htmltextview.DrawTableLinkSpan;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.sufficientlysecure.htmltextview.example.databinding.ActivityDataBindingExampleBinding;
import org.sufficientlysecure.htmltextview.example.databinding.ActivityExpandableViewBinding;

public class ExpandableViewActivity extends Activity {

    private ActivityExpandableViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expandable_view);
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
                "        <h2><font color='#FF0000'>Know About Me</font></h2>" +
                "        <p>" +
                "            Hello this is in a paragraph, and is long, just joking" +
                "        </p>";

        // create dummy item
        NewsItem item = new NewsItem();
        item.setHtml(content);
        item.setText(this.getString(R.string.simple_text_content));

        // in XML we declared a variable newsItem, data binding generated the set method
        // once set, all fields/values/views are updated accordingly
        binding.setNewsItem(item);
    }
}
