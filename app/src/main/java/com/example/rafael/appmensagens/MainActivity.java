package com.example.rafael.appmensagens;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.rafael.appmensagens.Controller.MensageiroApi;
        import com.example.rafael.appmensagens.Controller.UsuarioController;
        import com.example.rafael.appmensagens.Model.Contato;
        import com.example.rafael.appmensagens.Model.Mensagem;
        import com.example.rafael.appmensagens.View.BuscarMensagemActivity;
        import com.example.rafael.appmensagens.View.CadastrarContatoActivity;
        import com.example.rafael.appmensagens.View.ContatosActivity;
        import com.example.rafael.appmensagens.View.MostraMensagensActivity;
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


public class MainActivity extends AppCompatActivity {

    private EditText idUsuarioET;
    private static final String URL_BASE = "http://www.nobile.pro.br/sdm4/mensageiro/";
    public static final String MENSAGENS_STRING_ARRAY_EXTRA = "MENSAGENS_STRING_ARRAY_EXTRA";

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idUsuarioET = findViewById(R.id.et_id);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(URL_BASE);
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        retrofit = builder.build();
    }

    @Override
    protected void onStart() {
        super.onStart();

        carregarDadosAnteriores();
    }

    private void carregarDadosAnteriores() {
        int ultimoID = UsuarioController.getInstance().BuscarUltimoID(getApplicationContext());
        if (ultimoID > 0)
            idUsuarioET.setText(String.valueOf(ultimoID));
    }



    public void efetuarLogin(View view) {
        if (view.getId() == R.id.bt_entrar) {
            if (idUsuarioET.getText().toString().trim().length() == 0) {
                Toast.makeText(getApplicationContext(), R.string.usuario_invalido, Toast.LENGTH_LONG).show();
                return;
            }

            buscarContato();
        }
    }

    public void abrirCadastrarContato(View view) {
        if (view.getId() == R.id.bt_cadastrar) {
            Intent intent = new Intent(getApplicationContext(), CadastrarContatoActivity.class);
            startActivity(intent);
        }
    }

    private void buscarContato() {
        MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);
        mensageiroApi.getContatoByPathId(idUsuarioET.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject contatoJson = new JSONObject( response.body().string() );

                    if(contatoJson!=null) {
                        Contato contato = new Contato();
                        contato.setId(contatoJson.getString("id"));
                        contato.setNomeCompleto(contatoJson.getString("nome_completo"));
                        contato.setApelido(contatoJson.getString("apelido"));
                        UsuarioController.getInstance().setUsuarioLogado(contato);

                        Intent intent = new Intent(getApplicationContext(), ContatosActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.usuario_invalido,Toast.LENGTH_LONG).show();
                    UsuarioController.getInstance().setUsuarioLogado(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.problema_login,Toast.LENGTH_LONG).show();
            }
        });
    }
}
