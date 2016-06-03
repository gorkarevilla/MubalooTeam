package com.gorkarevilla.mubalooteam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Dialog that shows the information of a Member
 *
 * @Author Gorka Revilla
 */
public class MemberDialog extends DialogFragment {

    private String _name;
    private String _role;
    private String _img;
    private Boolean _isLeader;

/*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //Load arguments annd put in the dialog
        _name = getArguments().getString("name");
        _role = getArguments().getString("role");
        _img = getArguments().getString("img");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.member, null));



        builder.setPositiveButton(R.string.close, null);

        return builder.create();
    }

*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load arguments annd put in the dialog
        _name = getArguments().getString("name");
        _role = getArguments().getString("role");
        _img = getArguments().getString("img");
        _isLeader = getArguments().getBoolean("isLeader");

        // R.layout.my_layout - that's the layout where your textview is placed
        View view = inflater.inflate(R.layout.member, container, false);

        //
        TextView nameV = (TextView) view.findViewById(R.id.MemberNameView);
        TextView roleV = (TextView) view.findViewById(R.id.MemberRoleView);

        //Load img and Modify in the ImageView
        new DownloadImageTask((ImageView) view.findViewById(R.id.MemberImageView), getActivity()).execute(_img);

        //Can be changed for different view of the leaders
        if(_isLeader) {
            //view.setBackgroundColor(Color.RED);
        }
        else {
            //view.setBackgroundColor(Color.BLUE);
        }

        //
        Button closeB = (Button) view.findViewById(R.id.MemberButton);
        closeB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //Modify Name and Role
        nameV.setText(_name);
        roleV.setText(_role);

        return view;
    }

}
