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

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btnregister=findViewById(R.id.btn_register);
        btnregister.setOnClickListener(new View.OnClickListener() {
            EditText edtName= findViewById(R.id.edt_name);
            EditText edtLastNameF= findViewById(R.id.edt_last_name_f);
            EditText edtLastNameM= findViewById(R.id.edt_last_name_m);
            EditText edtDateBorn= findViewById(R.id.edt_date_born);
            EditText edtEmail= findViewById(R.id.edt_email_register);
            EditText edtPassword= findViewById(R.id.edt_password_register);
            EditText edtVerifyPassword= findViewById(R.id.edt_verify_password);
            HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
            @Override
            public void onClick(View view) {
                String name=edtName.getText().toString().trim();
                String last_name_f=edtLastNameF.getText().toString().trim();
                String last_name_m=edtLastNameM.getText().toString().trim();
                String date_born=edtDateBorn.getText().toString().trim();
                String email=edtEmail.getText().toString().trim();
                String password=edtPassword.getText().toString().trim();
                String verify_password=edtVerifyPassword.getText().toString().trim();

                if(password.length()<8){
                    edtPassword.setError("la contraseña debe ser mayotr a 8 caracteres");
                    edtPassword.setFocusable(true);
                }
                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient=new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl("https://tfkofgop.lucusvirtual.es/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();

                ApiUser register=retrofit.create(ApiUser.class);
                Call<User> call=register.REGISTER_CALL(name,last_name_f,last_name_m,date_born,email,password,verify_password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                            if(response.isSuccessful() && response.body() != null){
                                edtName.getText().clear();
                                edtLastNameF.getText().clear();
                                edtLastNameM.getText().clear();
                                edtDateBorn.getText().clear();
                                edtEmail.getText().clear();
                                edtPassword.getText().clear();
                                edtVerifyPassword.getText().clear();
                                startActivity(new Intent(Register.this,MainActivity.class));
                                finish();
                                Toast.makeText(Register.this,"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Register.this,"ERROR VERIFICA TUS CREDENCIALES", Toast.LENGTH_SHORT).show();
                            }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Register.this,"LO SENTIMOS HUBO UN ERROR INTENTELO DE NUEVO", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public void registerUser(){

    }
}