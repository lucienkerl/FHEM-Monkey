package de.vegie1996.fhem_monkey.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import de.vegie1996.fhem_monkey.R;

/**
 * Created by lucienkerl on 26/01/16.
 */
@EViewGroup(R.layout.simple_listview_item)
public class SimpleListItemView extends LinearLayout {

    @ViewById(R.id.ll_root)
    LinearLayout rootLayout;

    @ViewById(R.id.textView_title)
    TextView textViewTitle;

    @ViewById(R.id.textView_subtitle)
    TextView textViewSubtitle;

    @ViewById(R.id.checkBox)
    CheckBox checkBox;

    public SimpleListItemView(Context context) {
        super(context);
    }

    @AfterViews
    public void init() {
        textViewTitle.setVisibility(GONE);
        textViewSubtitle.setVisibility(GONE);
        checkBox.setVisibility(GONE);
    }

    public SimpleListItemView setTitle(String title) {
        if (title != null) {
            textViewTitle.setVisibility(VISIBLE);
            textViewTitle.setText(title);
        }
        return this;
    }

    public SimpleListItemView setSubtitle(String subtitle) {
        if (subtitle != null) {
            textViewSubtitle.setVisibility(VISIBLE);
            textViewSubtitle.setText(subtitle);
        }
        return this;
    }

    public SimpleListItemView setIsChildItem() {
        float px20 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        float px10 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -10, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        params.setMargins(Math.round(px20), Math.round(px10), Math.round(px20), Math.round(px10));
        rootLayout.setLayoutParams(params);
        return this;
    }

    public SimpleListItemView setCheckBox(boolean isChecked, @NonNull OnClickListener listener) {
        checkBox.setVisibility(VISIBLE);
        checkBox.setChecked(isChecked);
        checkBox.setOnClickListener(listener);
        return this;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}
