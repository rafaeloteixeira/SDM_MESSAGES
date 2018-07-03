package com.example.rafael.appmensagens.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafael.appmensagens.Controller.MensageiroApi;
import com.example.rafael.appmensagens.Controller.MessageListAdapter;
import com.example.rafael.appmensagens.Controller.UsuarioController;
import com.example.rafael.appmensagens.MainActivity;
import com.example.rafael.appmensagens.Model.Contato;
import com.example.rafael.appmensagens.Model.Mensagem;
import com.example.rafael.appmensagens.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MostraMensagensActivity extends AppCompatActivity {

    private ListView mostraMensagensLV;
    private Gson gson;
    private Retrofit retrofit;
    private static final String URL_BASE = "http://www.nobile.pro.br/sdm4/mensageiro/";
    MensageiroApi mensageiroApi;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_mensagens);

        gson = new GsonBuilder().setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL_BASE);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);

        String mensagem = getIntent().getStringExtra(BuscarMensagemActivity.MENSAGENS_STRING_EXTRA);

        trazerMensagens("0", "958255", "958257");
    }

    private void trazerMensagens(String idUltima, String idRemetente, String idDestinatario) {

        MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);

        ArrayList<Mensagem> mensagensAL;

        mensageiroApi.getRawMensagensByPath(idUltima, idRemetente, idDestinatario)
           .enqueue(new Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                List<Mensagem> listaMensagem = response.body();


                preencherAdapter(listaMensagem);
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Deu Ruim",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void preencherAdapter(List<Mensagem> listaMensagem){

        try{
            mMessageRecycler = findViewById(R.id.reyclerview_message_list);
            mMessageAdapter = new MessageListAdapter(getApplicationContext(), listaMensagem);
            mMessageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            mMessageRecycler.setAdapter(mMessageAdapter);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }



    }



















    /*private void mostrarMensagens(){
        mostraMensagensLV = findViewById(R.id.lv_mostra_mensagens);

        ArrayList<String> mensagensAL = getIntent().getStringArrayListExtra(BuscarMensagemActivity.MENSAGENS_STRING_ARRAY_EXTRA);
        if(mensagensAL!=null && mensagensAL.size()>0){
            ArrayAdapter<String > adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mensagensAL);
            mostraMensagensLV.setAdapter(adapter);
        }else {
            Toast.makeText(this,"Nao ha Mensagens",Toast.LENGTH_LONG).show();
        }
    }*/

    public void enviarMensagem(View view) {
       /* if(view.getId()==R.id.button_chatbox_send) {
            Mensagem mensagem = new Mensagem();

            mensagem.setOrigemId("958255");
            mensagem.setDestinoId("958257");

            mensagem.setAssunto("Teste Assunto");
            mensagem.setCorpo("Teste Corpo teste teste");


            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(mensagem));
            MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);

            mensageiroApi.postMensagem(requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mostrarMensagens();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Erro",Toast.LENGTH_LONG).show();
                }
            });
        }*/
    }
}
