package com.example.rafael.appmensagens.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rafael.appmensagens.Controller.MensageiroApi;
import com.example.rafael.appmensagens.Controller.UsuarioController;
import com.example.rafael.appmensagens.Model.AdicionarContatoAdapter;
import com.example.rafael.appmensagens.Model.Contato;
import com.example.rafael.appmensagens.Model.ListaContatos;
import com.example.rafael.appmensagens.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContatosActivity extends AppCompatActivity implements AdicionarContatoAdapter.ItemClickListener {
    private static final String URL_BASE = "http://www.nobile.pro.br/sdm4/mensageiro/";
    private Retrofit retrofit;
    AdicionarContatoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL_BASE);
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        retrofit = builder.build();

        buscarContatos();
    }
    public void adicionarContato(View view) {
        if (view.getId() == R.id.fab) {
            Intent intent = new Intent(getApplicationContext(), AdicionarContatoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        buscarContatos();
    }

    private void buscarContatos() {
        ListaContatos contatos = new ListaContatos();
        contatos.setContatos(UsuarioController.getInstance().BuscarContatos(UsuarioController.getInstance().getUsuarioLogado().getId(), getApplicationContext()));
        setAdapter(contatos);
    }

    private void setAdapter(ListaContatos contatos){
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvContatos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AdicionarContatoAdapter(getApplicationContext(), contatos.getContatos());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Contato contatoSelecionado = adapter.getItem(position);
        Toast.makeText(getApplicationContext(), contatoSelecionado.getApelido(), Toast.LENGTH_LONG).show();
    }
}
