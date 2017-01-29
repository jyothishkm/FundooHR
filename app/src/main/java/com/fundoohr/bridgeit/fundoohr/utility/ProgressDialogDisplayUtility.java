package com.fundoohr.bridgeit.fundoohr.utility;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by bridgeit on 9/1/17.
 */

public class ProgressDialogDisplayUtility {
    ProgressDialog mDialog;

    public ProgressDialogDisplayUtility(Context context) {
        this.mDialog = new ProgressDialog(context);
    }

    //show the progress dialog
    public void showDialog(String message) {
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.show();
    }

    //close the progress dialog
    public void closeDialog(){
        mDialog.dismiss();
    }
}
