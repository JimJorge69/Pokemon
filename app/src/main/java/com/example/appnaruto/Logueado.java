package com.example.appnaruto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Logueado extends AppCompatActivity {

    TextView txttoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueado);

        txttoken=findViewById(R.id.txt_token);
        Bundle intet=getIntent().getExtras();
        String tokenI=intet.getString("token");

        txttoken.setText(tokenI);
        Button btnLogout=findViewById(R.id.btn_logout);
        Button btnPokemons=findViewById(R.id.btn_pokemones);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            HttpLoggingInterceptor loggin=new HttpLoggingInterceptor();
            @Override
            public void onClick(View v) {
                String token=tokenI;

                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://tfkofgop.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiUser logout=retrofit.create(ApiUser.class);
                Call<User> call=logout.LOGOUT_CALL(token);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()&&response.body()!=null){
                            Toast.makeText(Logueado.this, "Has cerrado sesion", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Logueado.this,MainActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Logueado.this,"LO SENTIMOS HUBO UN ERROR INTENTELO DE NUEVO", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnPokemons.setOnClickListener(new View.OnClickListener() {
            Intent intent=new Intent(Logueado.this,ShowCharacters.class);
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });


    }

}