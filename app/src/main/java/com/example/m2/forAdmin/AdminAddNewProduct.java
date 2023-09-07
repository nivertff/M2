package com.example.m2.forAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.m2.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminAddNewProduct extends AppCompatActivity {

    private String categoryName, description,
            price, name,saveCurrentDate,saveCurrentTime,productRandomKey, downloadImageUri;
    private ImageView productImage;
    private EditText nameProduct, descriptionProduct, priceProduct;
    private Button addProduct;
    private static final int GALLARYPICK = 1;
    private Uri imageUri;
    private StorageReference productImageRef;
    private DatabaseReference productRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        init();

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValideteProductData();
            }
        });

       }

    private void ValideteProductData() {
        description = descriptionProduct.getText().toString();
        price = priceProduct.getText().toString();
        name = nameProduct.getText().toString();

        if(imageUri == null){
            Toast.makeText(this,"Pls add Image",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(description) || TextUtils.isEmpty(name) || TextUtils.isEmpty(price)){
            Toast.makeText(this,"Pls do not leave blank fields", Toast.LENGTH_LONG).show();
        }else{
            StoreProductInform();
        }
    }

    private void StoreProductInform() {
        loadingBar.setTitle("Loading");
        loadingBar.setMessage("Pls stay...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;
        StorageReference filePath = productImageRef.child(imageUri.getPathSegments() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewProduct.this,"Some problem " + message,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProduct.this,"Is success", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminAddNewProduct.this,"Photo is saved ",Toast.LENGTH_LONG).show();
                            saveProductInfoToDataBase();
                        }
                    }
                });
            }

            private void saveProductInfoToDataBase() {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("pid",productRandomKey);
                productMap.put("date",saveCurrentDate);
                productMap.put("time",saveCurrentTime);
                productMap.put("description",description);
                productMap.put("image",downloadImageUri);
                productMap.put("category", categoryName);
                productMap.put("price",price);
                productMap.put("name",name);

                productRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProduct.this,"Product add..", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminAddNewProduct.this,AdminCategory.class);
                            startActivity(intent);
                        }else{
                            String message = task.getException().toString();
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProduct.this,"Exception : "+  message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void OpenGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,GALLARYPICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLARYPICK && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            productImage.setImageURI(imageUri);
        }
    }



    private void init(){
           categoryName = getIntent().getExtras().get("category").toString();
           productImage = findViewById(R.id.select_product);
           nameProduct = findViewById(R.id.product_name);
           descriptionProduct = findViewById(R.id.product_description);
           priceProduct = findViewById(R.id.product_price);
           addProduct = findViewById(R.id.btn_add_product);
           productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
           productRef = FirebaseDatabase.getInstance().getReference().child("Products");
           loadingBar = new ProgressDialog(this);
    }
}