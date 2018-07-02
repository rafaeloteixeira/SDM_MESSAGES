package com.example.rafael.appmensagens;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

        import com.example.rafael.appmensagens.View.BuscarMensagemActivity;
        import com.example.rafael.appmensagens.View.CadastrarContatoActivity;
        import com.example.rafael.appmensagens.View.MostraMensagensActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirBuscaMensagem(View view) {
        if (view.getId() == R.id.bt_nova_mensagem) {
            Intent intent = new Intent(getApplicationContext(), BuscarMensagemActivity.class);
            startActivity(intent);

            startActivity(intent);
        }
    }

    public void abrirCadastrarContato(View view) {
        if (view.getId() == R.id.bt_cadastrar_contato) {
            Intent intent = new Intent(getApplicationContext(), CadastrarContatoActivity.class);
            startActivity(intent);
        }
    }
}
