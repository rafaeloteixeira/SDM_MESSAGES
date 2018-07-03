package com.example.rafael.appmensagens.Controller;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.rafael.appmensagens.Model.Contato;

public class UsuarioController{
    private static UsuarioController instance;

    public static UsuarioController getInstance() {
        if(instance == null)
            instance= new UsuarioController();

        return instance;
    }

    private Contato usuarioLogado;

    private UsuarioController(){
    }


    public int BuscarUltimoID(Context context){
        int id = 0;

        try {
            SharedPreferences pref = context.getSharedPreferences("UserPreferences", 0);
            id = pref.getInt("id_user",0 ); // getting Integer
        }
        catch (Exception ex){

        }

        return id;
    }

    public void SalvarUltimoID(int id_user, Context context){
        try {
            SharedPreferences pref = context.getSharedPreferences("UserPreferences", 0);

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("id_user", id_user); // Storing integer
            editor.commit();
        }
        catch (Exception ex){

        }
    }

    public Contato getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Contato usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
