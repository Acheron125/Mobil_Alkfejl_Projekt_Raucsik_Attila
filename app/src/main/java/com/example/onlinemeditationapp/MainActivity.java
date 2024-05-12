package com.example.onlinemeditationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 13;

    EditText loginEmail;
    EditText loginPassword;

    Button loginButton;
    Button registerButton;
    Button anonymous;
    private SharedPreferences preferences;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        loginEmail.setText(preferences.getString("email", ""));
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        anonymous = findViewById(R.id.anonymousLoginButton);

        auth = FirebaseAuth.getInstance();
        Log.i(LOG_TAG, "onCreate");

        Animation animation_1 = AnimationUtils.loadAnimation(this, R.anim.side_slide_animation);
        loginButton.startAnimation(animation_1);

        Animation animation_2 = AnimationUtils.loadAnimation(this, R.anim.side_slide_animation);
        registerButton.startAnimation(animation_2);

        Animation animation_3 = AnimationUtils.loadAnimation(this, R.anim.slide_animation);
        anonymous.startAnimation(animation_3);


    }

    public void login(View view) {
        String emailStr = loginEmail.getText().toString();
        String passwordStr = loginPassword.getText().toString();
        if(emailStr == null || passwordStr == null || emailStr.isEmpty() || passwordStr.isEmpty()){
            return;
        }
        Log.i(LOG_TAG, "Bejelentkezett: " + emailStr + ", jelszo: " + passwordStr);
        auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "Bejelentkezés sikeres");
                    toMainPage();
                }else {
                    Log.d(LOG_TAG, "Bejelentkezés sikertelen");
                    Toast.makeText(MainActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void toMainPage(){
        Intent intent = new Intent(this, MainPageActivity.class);
//intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", 13);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", loginEmail.getText().toString());
        editor.apply();
    }

    public void anonymousLogin(View view) {
        auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "Anonymous login successful");
                    toMainPage();
                } else {
                    Log.d(LOG_TAG, "Failed to login anonymous");
                    Toast.makeText(MainActivity.this, "Failed to login anonymous: " + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}