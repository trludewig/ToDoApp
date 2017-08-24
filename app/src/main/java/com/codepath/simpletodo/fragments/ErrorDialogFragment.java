package com.codepath.simpletodo.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.util.Constants;

import static com.codepath.simpletodo.R.id.dismiss;

/**
 * Created by tludewig on 8/23/17.
 */

public class ErrorDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.error_dialog, container, false);
        getDialog().setTitle(Constants.OOPS);
        TextView tvMessage = (TextView) rootView.findViewById(R.id.message);
        tvMessage.setText(getArguments().getString(Constants.ERROR_MESSAGE));

        Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }
}
