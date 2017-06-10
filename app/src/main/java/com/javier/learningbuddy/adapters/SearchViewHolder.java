package com.javier.learningbuddy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.javier.learningbuddy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 6/3/2017.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemTitle)
    TextView titleTextView;

    @BindView(R.id.itemChannelTitle)
    TextView channelTitleTextView;

    public SearchViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setTitle(String title) {

        this.titleTextView.setText(title);
    }

    public void setChannelTitle(String channelTitle) {

        this.channelTitleTextView.setText(channelTitle);
    }

    public String getTitle() {

        return this.titleTextView.getText().toString();
    }

    public String getChannelTitle() {

        return this.channelTitleTextView.getText().toString();
    }
}
