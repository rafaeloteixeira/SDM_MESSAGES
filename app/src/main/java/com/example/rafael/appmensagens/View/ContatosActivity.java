package com.example.rafael.appmensagens.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rafael.appmensagens.R;

public class ContatosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
    }
    public void adicionarContato(View view) {
        if (view.getId() == R.id.fab) {
            Intent intent = new Intent(getApplicationContext(), AdicionarContatoActivity.class);
            startActivity(intent);
        }
    }
}
