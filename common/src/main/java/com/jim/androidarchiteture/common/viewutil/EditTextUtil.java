package com.jim.androidarchiteture.common.viewutil;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.jim.androidarchiteture.common.NumberUtil;

/**
 * Created by JimGong on 2016/3/5.
 */
public final class EditTextUtil {
    public static void isMoneyText(final EditText pEditText) {
        pEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NumberUtil.formatDot(s, pEditText);
            }

            @Override
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                int lastDot = temp.lastIndexOf(".");
                if (posDot != lastDot) {
                    edt.delete(posDot, posDot + 1);
                    return;
                }
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }
}
