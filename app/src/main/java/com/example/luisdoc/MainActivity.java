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
            finish();
        }
        btnLogin.setOnClickListener( view -> {
            String email = inputEmail.getText().toString();
            String senha = inputSenha.getText().toString();
            if (validarPreenchimento()){
                login(email, senha, view);
            }
        });
    }
    private void login(String email, String password,View view) {
        objAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(view.getContext(), Usuario.class);
                        startActivity(intent);
                    } else {
                        exibirMensagem("Login ou senha inválidos !");
                        inputEmail.setText("");
                        inputSenha.setText("");
                        inputEmail.requestFocus();

                    }
                });
    }
    private void exibirMensagem(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private boolean validarPreenchimento(){
        boolean preenchido = true;
        if(inputEmail.getText().toString().isEmpty()
                || inputSenha.getText().toString().isEmpty()  ){
            preenchido = false;
            exibirMensagem("Preencha todos os campos !");


        }
        return preenchido;
    }
}