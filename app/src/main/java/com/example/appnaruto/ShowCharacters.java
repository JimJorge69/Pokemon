package com.example.appnaruto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ServiceConfigurationError;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowCharacters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_characters);
        Button btnShow=findViewById(R.id.btn_show);
        EditText edtNumber=findViewById(R.id.edt_number);
        btnShow.setOnClickListener(new View.OnClickListener() {
            TextView txtCharacter=findViewById(R.id.txt_name);
            HttpLoggingInterceptor loggin=new HttpLoggingInterceptor();
            @Override
            public void onClick(View v) {
                String id=edtNumber.getText().toString();
                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);
                Retrofit retrofit =new Retrofit.Builder()
                        .baseUrl("https://pokeapi.co/api/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiUser character=retrofit.create(ApiUser.class);
                Call<Character> call=character.FIND(id);
                call.enqueue(new Callback<Character>() {
                   @Override
                   public void onResponse(Call<Character> call, Response<Character> response) {
                       if(response.isSuccessful()){
                           txtCharacter.setText("nombre: "+response.body().getName()+"\n"+" Experiencia base: "+ response.body().getBase_experience());
                       }else{
                           Toast.makeText(ShowCharacters.this,"ERROR", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<Character> call, Throwable t) {

                   }
               });

            }
        });
    }
}