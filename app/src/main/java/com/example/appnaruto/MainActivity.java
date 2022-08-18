package com.example.appnaruto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            EditText edtemail= findViewById(R.id.edt_email_login);
            EditText edtpassword= findViewById(R.id.edt_password_login);
            HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
            @Override
            public void onClick(View v) {
                String email=edtemail.getText().toString().trim();
                String password=edtpassword.getText().toString().trim();

                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://tfkofgop.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiUser login=retrofit.create(ApiUser.class);
                Call<User> call=login.LOGIN_CALL(email,password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful() && response.body() !=null){
                            edtemail.getText().clear();
                            edtpassword.getText().clear();
                            String tokenInter=response.body().getToken();


                            Intent intent =new Intent(MainActivity.this,Logueado.class);
                            intent.putExtra("token",tokenInter);
                            startActivity(intent);

                            Toast.makeText(MainActivity.this,tokenInter.toString(),Toast.LENGTH_SHORT).show();
                        }else if(response.code()==401){
                            Toast.makeText(MainActivity.this,"Debes validar tu correo",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,ValidateAccount.class));
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this,"Error en las credenciales",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"LO SENTIMOS HUBO UN ERROR INTENTELO DE NUEVO", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void registerActivity(View view){
        startActivity(new Intent(MainActivity.this,Register.class));
        finish();
    }
}