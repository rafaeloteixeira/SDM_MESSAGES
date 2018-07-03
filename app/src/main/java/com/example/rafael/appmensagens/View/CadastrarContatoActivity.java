package com.example.rafael.appmensagens.View;

        import android.content.Intent;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.rafael.appmensagens.Controller.MensageiroApi;
        import com.example.rafael.appmensagens.Controller.UsuarioController;
        import com.example.rafael.appmensagens.MainActivity;
        import com.example.rafael.appmensagens.Model.Contato;
        import com.example.rafael.appmensagens.R;
        import com.google.gson.Gson;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;

        import okhttp3.MediaType;
        import okhttp3.RequestBody;
        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;

public class CadastrarContatoActivity extends AppCompatActivity {

    private static final String URL_BASE = "http://www.nobile.pro.br/sdm4/mensageiro/";

//    EditText idET;
    EditText nomeCompletoET;
    EditText apelidoET;

    private Retrofit retrofit;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_contato);

//        idET = findViewById(R.id.et_id);
        nomeCompletoET = findViewById(R.id.et_nome_completo);
        apelidoET = findViewById(R.id.et_apelido);

        gson = new Gson();

        retrofit = new Retrofit.Builder().baseUrl(URL_BASE).build();
    }

    public void cadastraNovoContato(View view){
        if(view.getId()==R.id.bt_cadastrar_novo_contato) {
            Contato contato = new Contato();

            contato.setNomeCompleto(nomeCompletoET.getText().toString());
            contato.setApelido(apelidoET.getText().toString());


            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(contato));

            MensageiroApi mensageiroApi = retrofit.create(MensageiroApi.class);

            mensageiroApi.postContato(requestBody).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Contato contatoCadastrado = gson.fromJson(response.body().string(),Contato.class);
                        UsuarioController.getInstance().SalvarUltimoID(Integer.parseInt(contatoCadastrado.getId()),getApplicationContext());

                        //idET.setText(contatoCadastrado.getId());

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

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
