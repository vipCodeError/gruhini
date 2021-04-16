package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Presentation.presenters.LoginPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.LoginView;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.CustomToast;
import com.syscription.firstchoicemart.Utils.UserPrefs;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private TextView phoneEditText;
    private TextView tvPassword, link_signUp, link_forgotPassword;
    private AppCompatButton bLogin;
    private CallbackManager callbackManager;
    private Button fb, google;
    private LoginButton loginButton;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton signInButton;
    private LottieAnimationView lottieAnimationView;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        lottieAnimationView = findViewById(R.id.lottie_login_loading);

        initviews();

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString();
                Intent intent = new Intent(LoginActivity.this, OtpVerficationActivity.class);
                intent.putExtra("phoneNumber", phone);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 200) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initviews(){
        phoneEditText = findViewById(R.id.input_email);
        tvPassword = findViewById(R.id.input_password);
        bLogin = findViewById(R.id.btn_login);
//        link_signUp = findViewById(R.id.link_signUp);
   //     link_forgotPassword = findViewById(R.id.link_forgotPassword);
        fb = (Button) findViewById(R.id.fb);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                loginButton.performClick();
            }
        });

        google = findViewById(R.id.google);
        signInButton = findViewById(R.id.sign_in_button);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 200);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 200);

            }
        });
    }

}
