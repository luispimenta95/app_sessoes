package com.example.luisdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView inputEmail,inputSenha;
    private  static final String TAG = "LOG";
    private  static final String TAG_SENHA = "SENHA";
    private  static final String TAG_EMAIL = "EMAIL";
    private  static final String TAG_LOGIN = "LOGIN";
    private FirebaseAuth objAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        inputEmail =  findViewById(R.id.inputEmail);
        inputSenha =  findViewById(R.id.inputSenha);
        btnLogin =  findViewById(R.id.btnLogin);
        objAuth = FirebaseAuth.getInstance();
        FirebaseUser userLogado = objAuth.getCurrentUser();

        if(userLogado!=null){
            Intent intent = new Intent(MainActivity.this, Usuario.class);
            startActivity(intent);
            Log.d(TAG, "Algum usuário está logado");
            finish();
        }else{
            Log.d(TAG, "Nenhum usuário está logado");
        }
        btnLogin.setOnClickListener( view -> {
            String email = "luisfelipearaujopimenta@gmail.com";
            String password = "13151319";
            login(email, password, view);

        });
    }
    private void login(String email, String password,View view) {
        objAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(view.getContext(), Usuario.class);
                        startActivity(intent);

                        // Do something with the logged-in user
                    } else {
                        // Login failed
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}