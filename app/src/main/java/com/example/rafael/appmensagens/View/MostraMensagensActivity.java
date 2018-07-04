package com.example.rafael.appmensagens.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    String idContato;
    EditText etCorpo;

    private List<Mensagem> listaMensagem;

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

        idContato = getIntent().getStringExtra("contato");
        etCorpo = (EditText) findViewById(R.id.edittext_chatbox);

        listaMensagem = new ArrayList<>();
        preencherAdapter(listaMensagem);

         final Handler handler = new Handler();
         Runnable runnable = new Runnable() {
            public void run() {
                trazerMensagens();


                handler.postDelayed(this, 100);
            }
        };
        runnable.run();

    }

    private void trazerMensagens() {
        MensageiroApi mensageiroApi1 = retrofit.create(MensageiroApi.class);
        mensageiroApi1.getRawMensagensByPath("1", UsuarioController.getInstance().getUsuarioLogado().getId(), idContato)
                .enqueue(new Callback<List<Mensagem>>() {
                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                        AdicionarMsgs(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                        //Toast.makeText(getApplicationContext(),"Deu Ruim",Toast.LENGTH_LONG).show();
                    }
                });

        MensageiroApi mensageiroApi2 = retrofit.create(MensageiroApi.class);
        mensageiroApi2.getRawMensagensByPath("1", idContato, UsuarioController.getInstance().getUsuarioLogado().getId())
                .enqueue(new Callback<List<Mensagem>>() {
                    @Override
                    public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {

                        AdicionarMsgs(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                       // Toast.makeText(getApplicationContext(),"Deu Ruim",Toast.LENGTH_LONG).show();
                    }
                });


    }

    private  void  AdicionarMsgs(List<Mensagem> msgs){
        boolean alterou = false;
        if(msgs.size() > 0) {

            for (Mensagem msg : msgs) {
                boolean existe = false;
                for (int i = 0; i < listaMensagem.size() ; i++) {
                    if(listaMensagem.get(i).getId().compareTo(msg.getId()) == 0) {
                        existe = true;
                        break;
                    }
                }

                if (existe == false) {
                    listaMensagem.add(msg);
                    alterou = true;
                }
            }

            if (alterou) {
                Collections.sort(listaMensagem);
                mMessageAdapter.notifyDataSetChanged();
            }
        }

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



    public void enviarMensagem(View view) {
        if(view.getId()==R.id.button_chatbox_send) {
            Mensagem mensagem = new Mensagem();

            mensagem.setOrigemId(UsuarioController.getInstance().getUsuarioLogado().getId());
            mensagem.setDestinoId(idContato);

            mensagem.setAssunto((new Date()).toString());
            mensagem.setCorpo(etCorpo.getText().toString());

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(mensagem));
            MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);

            mensageiroApi.postMensagem(requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {

                        Mensagem mensagemCadastrada = gson.fromJson(response.body().string(),Mensagem.class);

                        List<Mensagem> lAvulsa = new ArrayList<>();
                        lAvulsa.add(mensagemCadastrada);

                        AdicionarMsgs(lAvulsa);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Erro",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
