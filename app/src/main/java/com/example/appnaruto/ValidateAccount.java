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

public class ValidateAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_account);

        Button btnValidar=findViewById(R.id.btn_validate);
        btnValidar.setOnClickListener(new View.OnClickListener() {
            EditText edtEmail=findViewById(R.id.edt_email_validate);
            EditText edtCode=findViewById(R.id.edt_codigo_validate);
            HttpLoggingInterceptor logging=new HttpLoggingInterceptor();
            @Override
            public void onClick(View v) {
                String email=edtEmail.getText().toString().trim();
                String code=edtCode.getText().toString().trim();

                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);
                Retrofit retrofit =new Retrofit.Builder()
                        .baseUrl("https://tfkofgop.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                ApiUser validateAccount=retrofit.create(ApiUser.class);
                Call<User> call=validateAccount.VALIDATE_CALL(email,code);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful() && response.body() != null){
                            edtEmail.getText().clear();
                            edtCode.getText().clear();
                            Toast.makeText(ValidateAccount.this, "SE HA VALIDADO CORRECTAMENTE EL CORREO", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ValidateAccount.this,MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(ValidateAccount.this, "NO SE HA PORIDO VERIFICAR CORRECTAMENRE", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ValidateAccount.this,"LO SENTIMOS HUBO UN ERROR INTENTELO DE NUEVO", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}