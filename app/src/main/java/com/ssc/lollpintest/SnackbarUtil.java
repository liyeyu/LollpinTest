package com.ssc.lollpintest;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Administrator on 2016/5/9.
 */
public class SnackbarUtil {

    private static Snackbar mSnackbar;

    public static void show(View view, String msg, int flag) {

        mSnackbar = Snackbar.make(view, msg, flag==0?Snackbar.LENGTH_SHORT:Snackbar.LENGTH_LONG);

        mSnackbar.show();

        mSnackbar.setAction("close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSnackbar.dismiss();
            }
        });
    }

}
