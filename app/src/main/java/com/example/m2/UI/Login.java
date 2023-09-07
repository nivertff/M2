package com.example.m2.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m2.Model.User;
import com.example.m2.Prevalent.Prevalent;
import com.example.m2.R;
import com.example.m2.forAdmin.AdminCategory;
import com.example.m2.forUser.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {

    private Button loginBtn;
    private EditText loginPhoneInput;
    private EditText passwordInput;
    private ProgressDialog loadingBar;
    private CheckBox checkBox;
    private TextView admin,user;

    private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_btn);
        loginPhoneInput = findViewById(R.id.login_phone);
        passwordInput = findViewById(R.id.login_password);
        loadingBar = new ProgressDialog(this);
        checkBox = findViewById(R.id.login_check);
        admin = findViewById(R.id.admin_panel);
        user = findViewById(R.id.not_admin_panel);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.setVisibility(View.INVISIBLE);
                user.setVisibility(View.VISIBLE);
                loginBtn.setText("Only for admin");
                parentDbName = "Admins";
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin.setVisibility(View.VISIBLE);
                user.setVisibility(View.INVISIBLE);
                loginBtn.setText("Login");
                parentDbName = "Users";
            }
        });

        Paper.init(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String phone = loginPhoneInput.getText().toString();
        String password = passwordInput.getText().toString();
        if(TextUtils.isEmpty(password)|| TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Some empty", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.setTitle("Login account");
            loadingBar.setMessage("Pls stay...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(phone,password);
        }
    }

    private void ValidateUser(String phone, String password) {
        if (checkBox.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDbName).child(phone).exists()){
                    User user = snapshot.child(parentDbName).child(phone).getValue(User.class);
                    if(user.getPhone().equals(phone) && user.getPassword().equals(password)){
                       if(parentDbName.equals("Users")){
                           loadingBar.dismiss();
                           Toast.makeText(Login.this,"Success", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(Login.this, Home.class);
                           startActivity(intent);
                       }else{
                           loadingBar.dismiss();
                           Toast.makeText(Login.this,"Success", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(Login.this, AdminCategory.class);
                           startActivity(intent);
                       }
                    }else{
                        loadingBar.dismiss();
                        Toast.makeText(Login.this,"Not success",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    loadingBar.dismiss();
                    Toast.makeText(Login.this,"Account not found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}