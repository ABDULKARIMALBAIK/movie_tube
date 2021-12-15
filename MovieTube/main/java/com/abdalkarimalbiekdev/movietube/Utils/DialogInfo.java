package com.abdalkarimalbiekdev.movietube.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdalkarimalbiekdev.movietube.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class DialogInfo {

    public TextView txtTitle , txtMessage , btnYES , btnNO;
    public ImageView imgDialog;
    public MaterialEditText edtInfo;
    public Dialog dialog;

    public DialogInfo(Activity activity, int drawable, String message, String title, String textYES, String textNO , String hintEdit) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_info);

        initViews(dialog);

        setViews(drawable , message , title , textYES , textNO , hintEdit);

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

    private void setViews(int drawable, String message, String title, String textYES, String textNO, String hintEdit) {

        imgDialog.setImageResource(drawable);
        txtTitle.setText(title);
        txtMessage.setText(message);
        btnYES.setText(textYES);
        btnNO.setText(textNO);
        edtInfo.setHint(hintEdit);
    }

    private void initViews(Dialog dialog) {

        txtTitle = dialog.findViewById(R.id.title_dialog);
        txtMessage = dialog.findViewById(R.id.message_dialog);
        imgDialog = dialog.findViewById(R.id.img_dialog);
        btnYES = dialog.findViewById(R.id.btnYES);
        btnNO = dialog.findViewById(R.id.btnNO);
        edtInfo = dialog.findViewById(R.id.edtInfo);
    }


}
