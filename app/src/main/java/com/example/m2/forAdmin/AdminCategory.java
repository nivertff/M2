package com.example.m2.forAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.m2.R;

public class AdminCategory extends AppCompatActivity {

    private ImageView car, moto, boat,service;
    private ImageView dress, shoes, clothes,hat;
    private ImageView phone, computer, camera,fridge;
    private ImageView sport, book, collection,music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        init();



        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","car");
                startActivity(intent);
            }
        });
        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","moto");
                startActivity(intent);
            }
        });
        boat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","boat");
                startActivity(intent);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","service");
                startActivity(intent);
            }
        });
        dress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","dress");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });
        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","clothes");
                startActivity(intent);
            }
        });
        hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","hat");
                startActivity(intent);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","phone");
                startActivity(intent);
            }
        });
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","computer");
                startActivity(intent);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","camera");
                startActivity(intent);
            }
        });
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","fridge");
                startActivity(intent);
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","sport");
                startActivity(intent);
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","book");
                startActivity(intent);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","collection");
                startActivity(intent);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategory.this,AdminAddNewProduct.class);
                intent.putExtra("category","music");
                startActivity(intent);
            }
        });
    }

    private void init(){
        car = findViewById(R.id.car);
        moto = findViewById(R.id.motorcycle);
        boat = findViewById(R.id.boat);
        service = findViewById(R.id.service);

        dress = findViewById(R.id.dress);
        shoes = findViewById(R.id.shoes);
        clothes = findViewById(R.id.sweater);
        hat = findViewById(R.id.hats);

        phone = findViewById(R.id.smartphone);
        computer = findViewById(R.id.desktop);
        camera = findViewById(R.id.camera);
        fridge = findViewById(R.id.fridge);

        sport = findViewById(R.id.bicycle);
        book = findViewById(R.id.books);
        collection = findViewById(R.id.packaging);
        music = findViewById(R.id.guitar);
    }
}