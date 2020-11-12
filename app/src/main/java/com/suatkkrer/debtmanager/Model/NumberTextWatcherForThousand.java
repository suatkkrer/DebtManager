package com.suatkkrer.debtmanager.Model;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.StringTokenizer;

public class NumberTextWatcherForThousand implements TextWatcher {

    EditText editText;


    public NumberTextWatcherForThousand(EditText editText) {
        this.editText = editText;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try
        {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();


            if (value != null && !value.equals(""))
            {

                if(value.startsWith(".")){ //adds "0." when only "." is pressed on begining of writting
                    editText.setText("");
                }

                if (value.contains(".")){
                    String text = editText.getText().toString();
                    String result = text.substring(0,text.indexOf("."));
                    editText.setText(result);
                }

                if(value.startsWith("0") && !value.startsWith("0.")){
                    editText.setText(""); //Prevents "0" while starting but not "0."

                }



                String str = editText.getText().toString().replaceAll(",", "");
                if (!value.equals(""))
//                    Double.valueOf(str).doubleValue();
                    editText.setText(getDecimalFormat(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    public static String getDecimalFormat(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }


    //Trims all the comma of the string and returns
    public static String trimCommaOfString(String string) {
//        String returnString;
        if(string.contains(",")){
            return string.replace(",","");}
        else {
            return string;
        }

    }
}