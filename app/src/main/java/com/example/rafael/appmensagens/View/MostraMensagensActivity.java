package com.example.rafael.appmensagens.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rafael.appmensagens.MainActivity;
import com.example.rafael.appmensagens.R;

import java.util.ArrayList;


public class MostraMensagensActivity extends AppCompatActivity {

    private ListView mostraMensagensLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_mensagens);

        mostraMensagensLV = findViewById(R.id.lv_mostra_mensagens);

        ArrayList<String> mensagensAL = getIntent().getStringArrayListExtra(BuscarMensagemActivity.MENSAGENS_STRING_ARRAY_EXTRA);

        if(mensagensAL!=null && mensagensAL.size()>0){

            ArrayAdapter<String > adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mensagensAL);

            mostraMensagensLV.setAdapter(adapter);

        }else {
            Toast.makeText(this,"Nao ha Mensagens",Toast.LENGTH_LONG).show();
        }
    }
}
