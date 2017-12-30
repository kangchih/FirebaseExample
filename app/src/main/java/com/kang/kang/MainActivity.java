package com.kang.kang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //user already signed in
            Log.d("AUTH", auth.getCurrentUser().getUid());
            Log.d("AUTH", auth.getCurrentUser().getEmail());
        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(
                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())).build(), RC_SIGN_IN);
        }
        findViewById(R.id.log_out_button).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {

        super.onActivityResult(requestCode, resultcode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultcode == RESULT_OK) {
                Log.d("AUTH", "Result OK UserId= " + auth.getCurrentUser().getUid());
                Log.d("AUTH", "Result OK Email= " + auth.getCurrentUser().getEmail());
            } else {
                //User not authenticated
                Log.d("AUTH", auth.getCurrentUser().getUid());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.log_out_button) {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("AUTH", "USER LOGGED OUT");
                            finish();
                        }
                    });
        }
    }

}
