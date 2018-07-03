package com.example.rafael.appmensagens.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rafael.appmensagens.Controller.MensageiroApi;
import com.example.rafael.appmensagens.Model.Mensagem;
import com.example.rafael.appmensagens.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscarMensagemActivity extends AppCompatActivity {

    private EditText remetenteET;
    private EditText destinatarioET;
    private EditText ultimaMensagemET;
    //private Button buscarMensageBT;

    private static final String URL_BASE = "http://www.nobile.pro.br/sdm4/mensageiro/";
    public static final String MENSAGENS_STRING_EXTRA  = "MENSAGENS_STRING_EXTRA";
    public static final String MENSAGENS_OBJECT_ARRAY_EXTRA  = "MENSAGENS_OBJECT_ARRAY_EXTRA";

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mensagem);

        remetenteET=findViewById(R.id.et_remetente);
        destinatarioET=findViewById(R.id.et_destinatario);
        ultimaMensagemET=findViewById(R.id.et_ultima_mensagem);
        //buscarMensageBT=findViewById(R.id.bt_buscar_mensagens);

        //Habilita o modo "pegaleve" | ignora alguns erros basicos
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL_BASE);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
    }

    public void buscarMensagens(View view) {
        if(view.getId()==R.id.bt_buscar_mensagens){


            Intent intent = new Intent(getApplicationContext(),MostraMensagensActivity.class);
            intent.putExtra(MENSAGENS_STRING_EXTRA, ultimaMensagemET.getText().toString() + "|" + remetenteET.getText().toString() + "|" + destinatarioET.getText().toString());
            startActivity(intent);
            limparCampos();
        }
    }

    private void limparCampos() {
        destinatarioET.setText("");
        ultimaMensagemET.setText("");
        remetenteET.setText("");
    }
}
