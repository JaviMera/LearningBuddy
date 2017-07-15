package com.javier.learningbuddy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.javier.learningbuddy.R;
import com.javier.learningbuddy.SearchActivity;
import com.javier.learningbuddy.model.Suggestion;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javie on 6/17/2017.
 */

public class SuggestionViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.suggestionTextView)
    TextView suggestionTextView;

    public SuggestionViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
