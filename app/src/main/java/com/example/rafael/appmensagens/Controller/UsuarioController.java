package com.example.rafael.appmensagens.Controller;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.rafael.appmensagens.Model.Contato;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
            editor.clear();

            editor.putInt("id_user", id_user); // Storing integer
            editor.commit();
        }
        catch (Exception ex){

        }
    }

    public void SalvarContatos(String id_user, List<Contato> contatos, Context context){
        try {
            SharedPreferences pref = context.getSharedPreferences(id_user, 0);
            SharedPreferences.Editor editor = pref.edit();

            editor.clear();
            Gson gson = new Gson();
            String jsonText = gson.toJson(contatos);
            editor.putString("contatos", jsonText);
            editor.commit();
        }
        catch (Exception ex){

        }
    }
    public List<Contato> BuscarContatos(String id_user,  Context context){
        List<Contato> contatos = null;
        try {
            SharedPreferences pref = context.getSharedPreferences(id_user, 0);
            SharedPreferences.Editor editor = pref.edit();

            Gson gson = new Gson();
            String jsonText = pref.getString("contatos", null);

            Type listType = new TypeToken<ArrayList<Contato>>(){}.getType();

            contatos = gson.fromJson(jsonText, listType);
        }
        catch (Exception ex){

        }

        if(contatos == null)
            contatos = new ArrayList<>();

        return contatos;
    }

    public Contato getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Contato usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
