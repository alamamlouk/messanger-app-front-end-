package com.example.messasingchat.Shared;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;

public class EmailValidator implements TextWatcher , View.OnFocusChangeListener {
    private TextInputEditText editText;
    public EmailValidator(TextInputEditText editText) {
        this.editText = editText;
        this.editText.addTextChangedListener(this);
        this.editText.setOnFocusChangeListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if(!String.valueOf(editText.getText()).matches("")){
                String email = String.valueOf(editText.getText()).trim();
                if (!isValidEmail(email)) {
                    editText.setError("Invalid email");
                } else {
                    editText.setError(null); // Clear the error
                }
            }

        }
    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
