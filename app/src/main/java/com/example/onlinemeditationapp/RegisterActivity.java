package com.example.onlinemeditationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 13;

    EditText registerEmail;
    EditText registerPassword;
    EditText registerPasswordRepeat;

    private SharedPreferences preferences;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if(secret_key != 13){
            finish();
        }

        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerPasswordRepeat = findViewById(R.id.passwordRepeat);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String emailStr = preferences.getString("email", "");
        String passwordStr = preferences.getString("password", "");


        registerEmail.setText(emailStr);
        registerPassword.setText(passwordStr);
        registerPasswordRepeat.setText(passwordStr);

        auth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG, "onCreate");
    }
    public void register(View view){
        String emailStr = registerEmail.getText().toString();
        String passwordStr = registerPassword.getText().toString();
        String passwordRepeatStr = registerPasswordRepeat.getText().toString();

        if(!passwordStr.equals(passwordRepeatStr)){
            Log.e(LOG_TAG, "A megadott jelszavak nem egyeznek!");
            return;
        }
        if(emailStr == null || passwordStr == null || emailStr.isEmpty() || passwordStr.isEmpty() || passwordRepeatStr == null || passwordRepeatStr.isEmpty()){
            return;
        }
        Log.i(LOG_TAG, "Regisztrált: " + emailStr + ", jelszo: " + passwordStr);
        auth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "Felhasználó sikeresen létrehozva");
                    toMainPage();
                }else{
                    Log.d(LOG_TAG, "Regisztráció sikertelen");
                    Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view){
        finish();
    }

    public void toMainPage(){
        Intent intent = new Intent(this, MainPageActivity.class);
//intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }
}