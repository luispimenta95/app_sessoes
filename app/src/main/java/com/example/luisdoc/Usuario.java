package com.example.luisdoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Usuario extends AppCompatActivity {
    FirebaseAuth objAuth = FirebaseAuth.getInstance();
    private TextView textoTimer;
    private static final long MINUTOS = 10;
    private static final long CONTADOR_REGRESSIVO = 1;
    private Button btnLogout;
    private long TEMPO_RESTANTE = (MINUTOS *60 *1000);
    private boolean contadorAtivo;
    private  static final String TAG = "LOG";
    private CountDownTimer contador;
    private  View view;
    FirebaseUser user = objAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        TextView nome = findViewById(R.id.nomeUser);
        textoTimer = findViewById(R.id.textoTempo);
        btnLogout = findViewById(R.id.btnLogout);
        iniciarTempoSessao();
        nome.setText(user.getUid());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }


    private void exibirMensagem(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void iniciarTempoSessao() {
        contador = new CountDownTimer(TEMPO_RESTANTE, (CONTADOR_REGRESSIVO *1000)) {
            @Override
            public void onTick(long millisUntilFinished) {
                TEMPO_RESTANTE = millisUntilFinished;
                exibirTempoContador();
            }

            @Override
            public void onFinish() {
                contadorAtivo = false;

            }
        }.start();

        contadorAtivo = true;
    }


    private void exibirTempoContador() {
        int minutes = (int) (TEMPO_RESTANTE / 1000) / 60;
        int seconds = (int) (TEMPO_RESTANTE / 1000) % 60;
        int minutesInSec = (int) (TEMPO_RESTANTE / 1000);

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textoTimer.setText(timeLeftFormatted);
        if(minutesInSec ==300){
            exibirMensagem("Restam menos de 5 minutos para sua sessão ser desativada");
        }
        else if(minutesInSec ==120){
            exibirMensagem("Restam menos de 2 minutos para sua sessão ser desativada");
        }
        else if(minutesInSec ==30){
            exibirMensagem("Restam menos de 30 segundos para sua sessão ser desativada");
        }
        else if (minutesInSec == 0){
            contadorAtivo = false;

            AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
            diaBuilder.setMessage("Deseja manter a sessao ativa ?");
            diaBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    contadorAtivo = true;
                    contador.start();

                }
            });
            diaBuilder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   logout();
                }
            });
            diaBuilder.show();
        }
        btnLogout.setOnClickListener( view -> {
           logout();
        });
    }
    private void logout(){
        objAuth.signOut();
        Intent intent = new Intent(Usuario.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}