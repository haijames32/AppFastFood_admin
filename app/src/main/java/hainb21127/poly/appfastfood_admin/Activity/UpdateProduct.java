package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class UpdateProduct extends AppCompatActivity {
    private List<String> listCat = new ArrayList<>();
    private Map<String, String> categoryToIdMap = new HashMap<>();
    private String selectedCategory;
    EditText nameSp, giaSp, anhSp, descSp;
    Button btnUd;
    ImageView imageUd, btnBack;
    Spinner spinner;
    String id_cat;
    private static final int PICK_IMAGE_REQUEST = 1;
    String productId;
    private Uri selectedImgUri; // Để lưu trữ đường dẫn hình ảnh đã chọn

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        nameSp = findViewById(R.id.update_sp_name);
        giaSp = findViewById(R.id.update_sp_gia);
        anhSp = findViewById(R.id.update_sp_img);
        descSp = findViewById(R.id.update_sp_mota);
        imageUd = findViewById(R.id.choose_up_sp);
        btnBack = findViewById(R.id.btn_back_Udsp);
        spinner = findViewById(R.id.spiner_update_sp_activyty);
        btnUd = findViewById(R.id.btnUpdate_sp);

        Intent intent = getIntent();
        productId = intent.getStringExtra("idProUd");
        Log.d("AAAAA", "onCreate: " + productId);
        String name = intent.getStringExtra("nameProUd");
        String category = intent.getStringExtra("categoryProUd");
        int gia = intent.getIntExtra("priceProUd", 0);
        String mota = intent.getStringExtra("motaProUd");
        String img = intent.getStringExtra("imagePorUd");

        showSpinner();

        nameSp.setText(name);
        giaSp.setText(Utilities.addDots(gia) + "đ");
        anhSp.setText(img);
        descSp.setText(mota);
        Picasso.get().load(img).into(imageUd);
        imageUd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = listCat.get(i);
                id_cat = categoryToIdMap.get(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Giá trị trong spinner chưa được chọn", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnUd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedName = nameSp.getText().toString();
                int updatedPrice = Integer.parseInt(giaSp.getText().toString());
                String updatedDescription = descSp.getText().toString();
                String updatedImage = anhSp.getText().toString();


                updateImageDB(updatedName,updatedPrice,selectedImgUri,updatedDescription,updatedImage);
            }
        });
    }

    private void updateImageDB( final String name, final Integer price,Uri imageUri, final String description, final String category) {
        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            String imageName = System.currentTimeMillis() + ".jpg";
            StorageReference imageRef = storageRef.child("images/" + imageName);

            imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Lấy URL của hình ảnh sau khi tải lên thành công
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            String imageUrl = downloadUrl.toString();

                            // Tiến hành lưu thông tin sản phẩm vào Firebase Realtime Database
                            UpdateSp(name, price, description, category, imageUrl);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateProduct.this, "Lỗi khi tải ảnh lên Firebase Storage: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void UpdateSp(String name, Integer price, String description, String category, String imageUrl){
        if (productId != null) {
            // Cập nhật sản phẩm trong Firebase Realtime Database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference productRef = database.getReference("products").child(productId);

            Products products = new Products( name, price, imageUrl, description);

            productRef.setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference reference1 = productRef.child("id_theloai");
                        DatabaseReference reference2 = reference1.child(id_cat);
                        DatabaseReference reference3 = reference2.child("nameCat");
                        reference3.setValue(selectedCategory).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProduct.this, "Update thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(UpdateProduct.this, "Update thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(UpdateProduct.this, "Update thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(UpdateProduct.this, "productID null", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSpinner() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCat.clear();
                categoryToIdMap.clear(); // Xóa danh sách danh mục và ánh xạ cũ

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    String categoryName = dataSnapshot.child("nameCat").getValue(String.class);
                    listCat.add(categoryName);
                    categoryToIdMap.put(categoryName, id);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), R.layout.style_spinner, listCat);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImgUri = data.getData();
            anhSp.setText(selectedImgUri.toString());
            // Hiển thị hình ảnh đã chọn nếu cần
            imageUd.setImageURI(selectedImgUri);
        }
    }

}