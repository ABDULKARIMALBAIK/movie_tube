package com.abdalkarimalbiekdev.movietube.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.R;

public class DialogRating {

    public TextView txtTitle , txtMessage , btnYES , btnNO;
    public ImageView imgDialog;
    public RatingBar ratingEntertainmentDialog , ratingPerformingArtistsDialog , ratingResolutionDialog;
    public Dialog dialog;

    public DialogRating(Activity activity, int drawable, String message, String title, String textYES, String textNO , float ratingEntertainment, float ratingPerformingArtists , float ratingResolution) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_rating);

        initViews(dialog);

        setViews(drawable , message , title , textYES , textNO , ratingEntertainment, ratingPerformingArtists , ratingResolution);

        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void showDialog(){
        dialog.show();
    }

    private void setViews(int drawable, String message, String title, String textYES, String textNO, float ratingEntertainment, float ratingPerformingArtists , float ratingResolution) {

        imgDialog.setImageResource(drawable);
        txtTitle.setText(title);
        txtMessage.setText(message);
        btnYES.setText(textYES);
        btnNO.setText(textNO);
        ratingEntertainmentDialog.setRating(ratingEntertainment);
        ratingPerformingArtistsDialog.setRating(ratingPerformingArtists);
        ratingResolutionDialog.setRating(ratingResolution);
    }

    private void initViews(Dialog dialog) {

        txtTitle = dialog.findViewById(R.id.title_dialog);
        txtMessage = dialog.findViewById(R.id.message_dialog);
        imgDialog = dialog.findViewById(R.id.img_dialog);
        btnYES = dialog.findViewById(R.id.btnYES);
        btnNO = dialog.findViewById(R.id.btnNO);
        ratingEntertainmentDialog = dialog.findViewById(R.id.ratingEntertainmentDialog);
        ratingPerformingArtistsDialog = dialog.findViewById(R.id.ratingPerformingArtistsDialog);
        ratingResolutionDialog = dialog.findViewById(R.id.ratingResolutionDialog);
    }

}
