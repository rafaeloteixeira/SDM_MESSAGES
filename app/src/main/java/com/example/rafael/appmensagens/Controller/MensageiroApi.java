package com.example.rafael.appmensagens.Controller;


import com.example.rafael.appmensagens.Model.Contato;
import com.example.rafael.appmensagens.Model.ListaContatos;
import com.example.rafael.appmensagens.Model.ListaMensagem;
import com.example.rafael.appmensagens.Model.Mensagem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by rafael on 02/07/2018.
 */

public interface MensageiroApi {
    //"http://www.nobile.pro.br/sdm/mensageiro/mensagem/100"
    //http://www.nobile.pro.br/sdm/mensageiro/mensagem?id=100

    @POST("contato")
    Call<ResponseBody> postContato(@Body RequestBody novoContato);

    @GET("rawcontatos")
    Call<List<Contato>> getContatos();

    @GET("contato/{id}")
    Call<ResponseBody> getContatoByPathId(@Path("id") String contatoId);

    @GET("mensagem/{mensagemId}")
    Call<ResponseBody> getMensagemByPathId(@Path("mensagemId") String mensagemId);

    @GET("mensagem")
    Call<ResponseBody> getMensagemByQueryId(@Query("mensagemId") String id);

    @GET("mensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<ListaMensagem> getMensagensByPath(@Path("ultimaMensagemId")String id, @Path("origemId") String origemId, @Path("destinoId") String destinoId);

    @GET("mensagens")
    Call<ListaMensagem> getMensagensByQuery(@Query("id") String id, @Query("origem") String origemId, @Query("destino") String destinoId);

    @GET("rawmensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<List<Mensagem>> getRawMensagensByPath(@Path("ultimaMensagemId")String id, @Path("origemId") String origemId, @Path("destinoId") String destinoId);

}
