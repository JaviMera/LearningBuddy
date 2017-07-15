package com.javier.learningbuddy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.javier.learningbuddy.adapters.Adapterbase;

/**
 * Created by javie on 6/26/2017.
 */

public abstract class FragmentBase extends Fragment {

    protected SearchActivity parent;
    protected Adapterbase adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.parent = (SearchActivity) context;
    }

    public Adapterbase getAdapter(){

        return this.adapter;
    }
}
