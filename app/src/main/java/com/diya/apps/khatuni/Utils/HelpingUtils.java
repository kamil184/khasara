package com.diya.apps.khatuni.Utils;

import android.content.Context;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.EditText;

public class HelpingUtils {

    private Context context;

    public HelpingUtils(Context context) {
        this.context = context;
    }

    public void textUtilEmptyError(EditText editText, int error){
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError(context.getString(error));
            editText.requestFocus();
            return;
        }
    }

    public void vibrate() {
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibe != null) {
            vibe.vibrate(15);
        }
    }

    public  boolean isNumeric(String str)
    {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
