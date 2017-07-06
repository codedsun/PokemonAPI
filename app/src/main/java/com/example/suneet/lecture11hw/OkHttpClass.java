package com.example.suneet.lecture11hw;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.widget.Toast.makeText;

/**
 * Created by suneet on 6/7/17.
 */

public class OkHttpClass {
    public static String BASE="http://pokeapi.co/api/v2/pokemon/";
    OkHttpClient okHttpClient=new OkHttpClient();
    Context c;
    Pokemon pokemon;
    int counter=0;
    public OkHttpClass(Context c) {
        this.c = c;
    }

    public Pokemon getResult(String no)
    {
        BASE=BASE+no;

        Request request=new Request.Builder().url(BASE).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mess=e.getLocalizedMessage();
                Toast.makeText(c,"Kindly Check the URL"+mess,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                pokemon=gson.fromJson(response.body().string(),Pokemon.class);
                counter=1;
                Log.e("TAG", "onResponse: "+pokemon.getBase_experience() );


            }
        });
        if(counter==0)
            return null;
            else
                return pokemon;

    }



}
