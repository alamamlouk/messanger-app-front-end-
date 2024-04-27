package com.example.messasingchat.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.messasingchat.R;
import com.example.messasingchat.Services.AuthServices.AuthServices;
import com.example.messasingchat.Shared.EmailValidator;
import com.example.messasingchat.Shared.SharedPreferenceManager;
import com.example.messasingchat.home_pages.HomePagesActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AuthActivity extends AppCompatActivity{
    private AuthServices authServices;
    private TextInputEditText loginEmail, loginPassword,signInEmail,signInPassword,signInName;
    TextView switchToSign,switchToLogin;

    private CardView loginCard;
    private CardView signInCard;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        if(SharedPreferenceManager.getInstance(getApplicationContext()).getLoginRememver()){
            Intent intent = new Intent(AuthActivity.this, HomePagesActivity.class);
            startActivity(intent);
            finish();
        }
        loginEmail =findViewById(R.id.email_text_field);
        loginEmail.addTextChangedListener(new EmailValidator(loginEmail));
        loginPassword =findViewById(R.id.password_text_field);
        loginCard = findViewById(R.id.loginCard);
        signInCard = findViewById(R.id.signInCard);
        signInEmail=findViewById(R.id.signUp_email_text_field);
        signInPassword=findViewById(R.id.signUp_password_text_field);
        signInEmail.addTextChangedListener(new EmailValidator(signInEmail));
        signInName=findViewById(R.id.signUp_Name_text_field);
        checkBox=findViewById(R.id.remeberMe);
        this.authServices =new AuthServices(AuthActivity.this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialButton login_BTN = findViewById(R.id.login_btn);
        login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailField=String.valueOf(loginEmail.getText());
                String passwordField=String.valueOf(loginPassword.getText());

                if(emailField.matches("") || passwordField.matches("") ) {
                    Toast.makeText(getApplicationContext(),"Field must not be empty",Toast.LENGTH_SHORT).show();
                }

                else {

                    authServices.loginRequest(emailField, passwordField, new AuthCallback() {
                        @Override
                        public void onSuccess() {
                            Log.d("TAG", "onSuccess: "+checkBox.toString());
                            if(checkBox.isChecked()){
                                SharedPreferenceManager.getInstance(getApplicationContext()).saveLogin(true);
                            }
                            Intent intent = new Intent(AuthActivity.this, HomePagesActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(AuthActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });
        MaterialButton sign_upBTN = findViewById(R.id.SignUpBTN);
        sign_upBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailField=String.valueOf(signInEmail.getText());
                String passwordField=String.valueOf(signInPassword.getText());
                String nameField = String.valueOf(signInName.getText());
                if(emailField.matches("") || passwordField.matches("") || nameField.matches("") ) {
                    Toast.makeText(getApplicationContext(),"Field must not be empty",Toast.LENGTH_SHORT).show();
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailField).matches()){
                    Toast.makeText(getApplicationContext(),"invalid email",Toast.LENGTH_SHORT).show();

                }
                else {
                    authServices.SignUpRequest(emailField, passwordField, nameField, new AuthCallback() {
                        @Override
                        public void onSuccess() {
                            signInEmail.setText("");
                            signInPassword.setText("");
                            signInName.setText("");
                            switchCards(signInCard,loginCard);

                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            Toast.makeText(AuthActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
        switchToLogin=findViewById(R.id.Switch_to_login);
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCards(signInCard,loginCard);

            }
        });
        switchToSign = findViewById(R.id.switch_to_sign_in);
        switchToSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCards(loginCard,signInCard);
            }
        });
    }
    private void switchCards(CardView cardToGo,CardView cardToShow){
        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slilde_down);
        cardToGo.startAnimation(slideDown);
        cardToGo.setVisibility(View.GONE);
        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        cardToShow.startAnimation(slideUp);
        cardToShow.setVisibility(View.VISIBLE);
    }
}