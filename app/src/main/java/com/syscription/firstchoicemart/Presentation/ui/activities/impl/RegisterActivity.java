package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.syscription.firstchoicemart.Network.response.RegistrationResponse;
import com.syscription.firstchoicemart.Presentation.presenters.RegisterPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.RegisterView;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

public class RegisterActivity extends BaseActivity implements RegisterView {

    private TextView input_name, input_email, input_password, input_confirm_password, already_sign_in;
    private AppCompatButton btn_signUp;
    private Boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initializeActionBar();
        // setTitle("My Account");

        initviews();

        already_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = true;

                if(input_name.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_name_layout);
                    til.setError("Name is required");
                    isValid = false;
                }

                if(input_email.getText().toString().length() <= 0){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_email_layout);
                    til.setError("Email is required");
                    isValid = false;
                }

                if(input_password.getText().toString().length() <= 5){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_password_layout);
                    til.setError("Password is required and must be 6 characters at least");
                    isValid = false;
                }

                //Log.d("Test", String.valueOf(input_password.getText()));

                if(!input_confirm_password.getText().toString().equals(input_password.getText().toString())){
                    TextInputLayout til = (TextInputLayout) findViewById(R.id.input_confirm_password_layout);
                    til.setError("Confirm password mismatch with password");
                    isValid = false;
                }

                if (isValid){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", input_name.getText().toString());
                    jsonObject.addProperty("email", input_email.getText().toString());
                    jsonObject.addProperty("password", input_password.getText().toString());
                    // jsonObject.addProperty("passowrd_confirmation", input_confirm_password.getText().toString());

                    new RegisterPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), RegisterActivity.this).validateRegistration(jsonObject);
                }
            }
        });
    }

    private void initviews(){
        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_confirm_password = findViewById(R.id.input_confirm_password);
        btn_signUp = findViewById(R.id.btn_signUp);
        already_sign_in = findViewById(R.id.already_sign_in);
    }

    @Override
    public void setRegistrationResponse(RegistrationResponse registrationResponse) {
        if (registrationResponse != null){
            Toast.makeText(this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
