package com.gorkarevilla.mubalooteam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Creates a Dialog to show all the information about the Author of the APP.
 *
 * @author Gorka Revilla
 *
 */
public class AboutMe extends DialogFragment {

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.aboutme, null));

        builder.setPositiveButton(R.string.close, null);

        return builder.create();
    }

}
