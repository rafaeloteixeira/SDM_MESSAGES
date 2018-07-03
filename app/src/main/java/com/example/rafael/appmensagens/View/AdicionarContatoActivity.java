package com.example.rafael.appmensagens.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rafael.appmensagens.Controller.MensageiroApi;
import com.example.rafael.appmensagens.Controller.UsuarioController;
import com.example.rafael.appmensagens.Model.Contato;
import com.example.rafael.appmensagens.Model.ListaContatos;
import com.example.rafael.appmensagens.Model.ListaMensagem;
import com.example.rafael.appmensagens.Model.Mensagem;
import com.example.rafael.appmensagens.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdicionarContatoActivity extends AppCompatActivity {

    private static final String URL_BASE = "http://www.nobile.pro.br/sdm4/mensageiro/";
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_contato2);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL_BASE);
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        retrofit = builder.build();

        buscarContatos();
    }

    private void buscarContatos() {
        MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);
        mensageiroApi.getContatos().enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                try {
                    ListaContatos listaContatos = new ListaContatos();
                    listaContatos.setContatos(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.erro_busca_contatos, Toast.LENGTH_LONG).show();
                    UsuarioController.getInstance().setUsuarioLogado(null);
                }
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.erro_busca_contatos, Toast.LENGTH_LONG).show();
            }
        });
    }
}