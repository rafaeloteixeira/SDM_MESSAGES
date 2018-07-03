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
    public static final String MENSAGENS_STRING_ARRAY_EXTRA  = "MENSAGENS_STRING_ARRAY_EXTRA";

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

        //retrofit = new Retrofit.Builder().baseUrl(URL_BASE).build();

    }

    public void buscarMensagens(View view) {
        if(view.getId()==R.id.bt_buscar_mensagens){
            MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);


            mensageiroApi.getRawMensagensByPath(ultimaMensagemET.getText().toString(),remetenteET.getText().toString(),destinatarioET.getText().toString())
                    .enqueue(new Callback<List<Mensagem>>() {
                        @Override
                        public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                            List<Mensagem> listaMensagem = response.body();

                            ArrayList<String> mensagensAL = new ArrayList<>();

                            for(Mensagem mensagem:listaMensagem){
                                mensagensAL.add(mensagem.getCorpo());
                            }

                            Intent intent = new Intent(getApplicationContext(),MostraMensagensActivity.class);
                            intent.putExtra(MENSAGENS_STRING_ARRAY_EXTRA,mensagensAL);

                            startActivity(intent);

                            limparCampos();


                        }

                        @Override
                        public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Deu Ruim",Toast.LENGTH_LONG).show();
                        }
                    });

            /*mensageiroApi.getMensagensByPath(ultimaMensagemET.getText().toString(),remetenteET.getText().toString(),destinatarioET.getText().toString())
                    .enqueue(new Callback<ListaMensagem>() {
                        @Override
                        public void onResponse(Call<ListaMensagem> call, Response<ListaMensagem> response) {
                            ListaMensagem listaMensagem = response.body();

                            ArrayList<String> mensagensAL = new ArrayList<>();

                            for(Mensagem mensagem:listaMensagem.getMensagens()){
                                mensagensAL.add(mensagem.getCorpo());
                            }

                            Intent intent = new Intent(getApplicationContext(),MostraMensagensActivity.class);
                            intent.putExtra(MENSAGENS_STRING_ARRAY_EXTRA,mensagensAL);

                            startActivity(intent);

                            limparCampos();


                        }

                        @Override
                        public void onFailure(Call<ListaMensagem> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Deu Ruim",Toast.LENGTH_LONG).show();
                        }
                    });*/




            /*
            mensageiroApi.getMensagemByPathId(ultimaMensagemET.getText().toString()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject mensagemJson = new JSONObject( response.body().string() );

                        ArrayList<String> mensagensArrayList = new ArrayList<>();

                        if(mensagemJson!=null) {

                            mensagensArrayList.add(mensagemJson.getString(CORPO_MENSAGEM_JSON));

                            Intent intent = new Intent(getApplicationContext(),MostraMensagensActivity.class);
                            intent.putExtra(MENSAGENS_STRING_ARRAY_EXTRA,mensagensArrayList);

                            startActivity(intent);

                                  limparCampos();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Deu Ruim",Toast.LENGTH_LONG).show();
                }
            });
            */

            //limparCampos();

        }
    }

    private void limparCampos() {
        destinatarioET.setText("");
        ultimaMensagemET.setText("");
        remetenteET.setText("");
    }
}
