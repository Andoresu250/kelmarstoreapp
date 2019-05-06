package com.andoresu.kelmarstore.utils;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static com.andoresu.kelmarstore.utils.BaseFragment.BASE_TAG;

/**
 * Some note <br/>
 * <li>Always use locale US instead of default to make DecimalFormat work well in all language</li>
 */
public class CurrencyEditText extends android.support.v7.widget.AppCompatEditText {

    private static final String TAG = BASE_TAG + CurrencyEditText.class.getSimpleName();

    private static final int MAX_LENGTH = 20;
    private static final int MAX_DECIMAL = 3;
    private CurrencyTextWatcher currencyTextWatcher;
    public String prefix =  "$ ";

    public CurrencyEditText(Context context) {
        this(context, null);
    }

    public String getPrefix(){
        if(prefix != null){
            return prefix + " ";
        }
        if(getTag() != null && getTag() instanceof String){
            return (String) getTag();
        }
        return "$ ";
    }

    public void setPrefix(String prefix){
        this.prefix = prefix;
        currencyTextWatcher = new CurrencyTextWatcher(this, getPrefix());
    }

    public String getValue(){
        return getText().toString().replace(getPrefix(), "").replaceAll("[,]", "");
    }

    public Double getDoubleValue(){
        try{
            return Double.parseDouble(getValue());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CurrencyEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        this.setHint(getPrefix());
        this.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_LENGTH) });
        currencyTextWatcher = new CurrencyTextWatcher(this, getPrefix());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            this.addTextChangedListener(currencyTextWatcher);
        } else {
            this.removeTextChangedListener(currencyTextWatcher);
        }
        handleCaseCurrencyEmpty(focused);
    }

    public void setText(String txt){
        this.addTextChangedListener(currencyTextWatcher);
        this.setText((CharSequence) txt);
        this.removeTextChangedListener(currencyTextWatcher);
    }

    public void setValue(String value){
        transformTextToCurrency(value, value, prefix, this, currencyTextWatcher);
    }
    public void setValue(Double value){
        if(value == null){
            return;
        }
        transformTextToCurrency(value.toString(), value.toString(), prefix, this, currencyTextWatcher);
    }
    public void setValue(Integer value){
        if(value == null){
            return;
        }
        transformTextToCurrency(value.toString(), value.toString(), prefix, this, currencyTextWatcher);
    }

    private static void transformTextToCurrency(String str, String cleanString, String prefix, EditText editText, TextWatcher textWatcher){
        if(str == null){
            return;
        }
        if (str.length() < prefix.length()) {
            editText.setText(prefix);
            editText.setSelection(prefix.length());
            return;
        }
        if (str.equals(prefix)) {
            return;
        }

        String formattedString;
        if (cleanString.contains(".")) {
            formattedString = formatDecimal(cleanString, prefix);
        } else {
            formattedString = formatInteger(cleanString, prefix);
        }
        editText.removeTextChangedListener(textWatcher); // Remove listener
        editText.setText(formattedString);
        if (editText.getText().length() <= MAX_LENGTH) {
            editText.setSelection(editText.getText().length());
        } else {
            editText.setSelection(MAX_LENGTH);
        }
        editText.addTextChangedListener(textWatcher); // Add back the listener
    }

    private static String formatDecimal(String str, String prefix) {
        if (str.equals(".")) {
            return prefix + ".";
        }
        BigDecimal parsed = new BigDecimal(str);
        // example pattern VND #,###.00
        DecimalFormat formatter = new DecimalFormat(prefix + "#,###." + getDecimalPattern(str),
                new DecimalFormatSymbols(Locale.US));
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(parsed);
    }

    /**
     * It will return suitable pattern for format decimal
     * For example: 10.2 -> return 0 | 10.23 -> return 00, | 10.235 -> return 000
     */
    private static String getDecimalPattern(String str) {
        int decimalCount = str.length() - str.indexOf(".") - 1;
        StringBuilder decimalPattern = new StringBuilder();
        for (int i = 0; i < decimalCount && i < MAX_DECIMAL; i++) {
            decimalPattern.append("0");
        }
        return decimalPattern.toString();
    }

    private static String formatInteger(String str, String prefix) {
        BigDecimal parsed = new BigDecimal(str);
        DecimalFormat formatter =
                new DecimalFormat(prefix + "#,###", new DecimalFormatSymbols(Locale.US));
        return formatter.format(parsed);
    }

    /**
     * When currency empty <br/>
     * + When focus EditText, set the default text = prefix (ex: VND) <br/>
     * + When EditText lose focus, set the default text = "", EditText will display hint (ex:VND)
     */
    private void handleCaseCurrencyEmpty(boolean focused) {
        if (focused) {
            if (getText().toString().isEmpty()) {
                setText(getPrefix());
            }
        } else {
            if (getText().toString().equals(getPrefix())) {
                setText("");
            }
        }
    }

    private static class CurrencyTextWatcher implements TextWatcher {
        private final EditText editText;
        private String previousCleanString;
        private String prefix;

        CurrencyTextWatcher(EditText editText, String prefix) {
            this.editText = editText;
            this.prefix = prefix;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // do nothing
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.i("CurrencyEditText", "afterTextChanged: ");
            String str = editable.toString();
//            if (str.length() < prefix.length()) {
//                editText.setText(prefix);
//                editText.setSelection(prefix.length());
//                return;
//            }
//            if (str.equals(prefix)) {
//                return;
//            }
//            // cleanString this the string which not contain prefix and ,
            String cleanString = str.replace(prefix, "").replaceAll("[,]", "");
            // for prevent afterTextChanged recursive call
            if (cleanString.equals(previousCleanString) || cleanString.isEmpty()) {
                return;
            }
            previousCleanString = cleanString;
//
//            String formattedString;
//            if (cleanString.contains(".")) {
//                formattedString = formatDecimal(cleanString, prefix);
//            } else {
//                formattedString = formatInteger(cleanString, prefix);
//            }
//            editText.removeTextChangedListener(this); // Remove listener
//            editText.setText(formattedString);
//            handleSelection();
//            editText.addTextChangedListener(this); // Add back the listener
            transformTextToCurrency(str, previousCleanString, prefix, editText, this);
        }



        private void handleSelection() {
            if (editText.getText().length() <= MAX_LENGTH) {
                editText.setSelection(editText.getText().length());
            } else {
                editText.setSelection(MAX_LENGTH);
            }
        }
    }
}