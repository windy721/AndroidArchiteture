package com.jim.androidarchiteture.common;

import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by zhangxiliang on 2016/1/28.
 */
public class NumberUtil {
    /**
     * onTextChanged
     * @param sequence  (CharSequenc s
     * @param editText
     */
    public static void formatDot(CharSequence sequence,EditText editText){
        String s = sequence.toString();
        if (s.contains(".")){
            /**
             * 如果小数点位数大于两位 截取后两位
             */
            if (s.length()-1-s.indexOf(".")>2){
                s = s.substring(0, (s.indexOf(".") + 3));
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }
        /**
         * 如果第一个输入为小数点 ，自动补零
         */
        if(s.trim().substring(0).equals(".")){
            s ="0"+s;
            editText.setText(s);
            editText.setSelection(s.length());
        }
        /**
         * 如果第一个第二个均为0
         */
        if(s.startsWith("0")&&s.trim().length()>1){
            if (!s.substring(1,2).equals(".")){
                editText.setText(s.substring(0,1));
                editText.setSelection(1);
                return;
            }
        }
    }

    public static String formatAmount(String pSource) {
        if (StringUtils.isBlank(pSource)) {
            return pSource;
        }
        double value;
        try {
            value = Double.valueOf(pSource);
        } catch (NumberFormatException e) {
            return null;
        }

        return formatAmount(value);
    }

    public static String formatAmount(double pSource) {
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        return fmt.format(pSource);
    }
}
