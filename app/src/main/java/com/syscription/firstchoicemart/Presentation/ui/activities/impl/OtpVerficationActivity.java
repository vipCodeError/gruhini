package com.syscription.firstchoicemart.Presentation.ui.activities.impl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;
import com.syscription.firstchoicemart.Network.response.AuthResponse;
import com.syscription.firstchoicemart.Presentation.presenters.LoginPresenter;
import com.syscription.firstchoicemart.Presentation.ui.activities.LoginView;
import com.syscription.firstchoicemart.R;
import com.syscription.firstchoicemart.Threading.MainThreadImpl;
import com.syscription.firstchoicemart.Utils.UserPrefs;
import com.syscription.firstchoicemart.domain.executor.impl.ThreadExecutor;

import java.util.concurrent.TimeUnit;

import carbon.dialog.ProgressDialog;

public class OtpVerficationActivity extends AppCompatActivity implements LoginView {

    private static final String TAG = OtpVerficationActivity.class.getName();
    private FirebaseAuth mAuth;

    private OtpView otpView;

    String mToken = "";
    String mVerificationId = "";

    String phoneNumber = "";

    ProgressDialog progress;

    TextView phoneNumTxt;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_accept_lay);
        otpView = findViewById(R.id.otp_view);
        mAuth = FirebaseAuth.getInstance();
        phoneNumTxt = findViewById(R.id.phoneNumber);


        //progress bar
        progress = new ProgressDialog(this);
        progress.setTitle("");
        progress.setText("Wait while you are logged ...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog


        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, token);

                Log.d("onCodeSent", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mToken = token.toString();
                Toast.makeText(OtpVerficationActivity.this, "Token ::"+ mVerificationId, Toast.LENGTH_SHORT).show();
            }
        };


        if (getIntent() != null){
            if (getIntent().getStringExtra("phoneNumber") != null){
                phoneNumber = getIntent().getStringExtra("phoneNumber");

                phoneNumTxt.setText("+91 "+ phoneNumber);

                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);


            }

        }


        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                verifyPhoneNumber(credential);
            }
        });
    }

    public void verifyPhoneNumber(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            new LoginPresenter(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), OtpVerficationActivity.this).validLogin(getIntent().getStringExtra("phoneNumber"));
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void setLoginResponse(AuthResponse authResponse) {
        if (authResponse != null){
            UserPrefs userPrefs = new UserPrefs(getApplicationContext());
            userPrefs.setAuthPreferenceObject(authResponse, "auth_response");
            Toast.makeText(OtpVerficationActivity.this, "Hey "+ userPrefs.getAuthPreferenceObjectJson("auth_response").getUser().getPhone(), Toast.LENGTH_SHORT).show();
            refresh();
        }else{
            Toast.makeText(OtpVerficationActivity.this, "Your Email or Password may be wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    public void refresh() {
        Intent mIntent = new Intent(OtpVerficationActivity.this, DrawerActivityNew.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(mIntent);
    }
}
