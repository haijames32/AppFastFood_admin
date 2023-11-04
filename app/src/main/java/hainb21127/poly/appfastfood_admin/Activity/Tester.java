package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hainb21127.poly.appfastfood_admin.Adapter.ProdAdapter;
import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;

public class Tester extends AppCompatActivity {
    Spinner spinner;
    private List<String> listCat = new ArrayList<>();
    private TextInputEditText ed_name_newsp, ed_gia_newsp, ed_mota_newsp, ed_img_newsp;
    Button btn_newsp;
    ImageView btn_back;
    ProdAdapter adapter;
    Context context;
    ImageView btnChooseImg;
    List<Products> listNEwProduct;

    String id_cat;

    private Map<String, String> categoryToIdMap = new HashMap<>();


    private String selectedCategory;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri; // Để lưu trữ đường dẫn hình ảnh đã chọn

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);
        anhXa();
        showSpinner();
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

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_newsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = ed_name_newsp.getText().toString();
                Integer intPrice = Integer.valueOf(ed_gia_newsp.getText().toString());
                String textImage = ed_img_newsp.getText().toString();
                String textDesr = ed_mota_newsp.getText().toString();

                selectedCategory = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(intPrice + "") || TextUtils.isEmpty(textImage) || TextUtils.isEmpty(textDesr)
                        || TextUtils.isEmpty(textName) || TextUtils.isEmpty(selectedCategory)) {
                    Toast.makeText(Tester.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
//                    NEwSanPham(textName, intPrice, textImage, textDesr, selectedCategory);
                    uploadImageToFirebase(selectedImageUri, textName, intPrice, textDesr, selectedCategory);
                    ed_name_newsp.setText("");
                    ed_gia_newsp.setText("");
                    ed_img_newsp.setText("");
                    ed_mota_newsp.setText("");
                }
            }
        });
    }


    private void uploadImageToFirebase(Uri imageUri, final String name, final Integer price, final String description, final String category) {
        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            String imageName = System.currentTimeMillis() + ".jpg";
            StorageReference imageRef = storageRef.child("images/" + imageName);

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy URL của hình ảnh sau khi tải lên thành công
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUrl) {
                                    String imageUrl = downloadUrl.toString();

                                    // Tiến hành lưu thông tin sản phẩm vào Firebase Realtime Database
                                    saveProductToDatabase(name, price, description, category, imageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Tester.this, "Lỗi khi tải ảnh lên Firebase Storage: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void saveProductToDatabase(String name, Integer price, String description, String category, String imageUrl) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("products").push();

        Products products = new Products(name, price, imageUrl, description);

        reference.setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference reference1 = reference.child("id_theloai");
                    DatabaseReference reference2 = reference1.child(id_cat);
                    DatabaseReference reference3 = reference2.child("nameCat");
                    reference3.setValue(selectedCategory).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Tester.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Tester.this, "Thêm thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Tester.this, "Thêm thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhXa() {
        spinner = findViewById(R.id.spiner_dialog_sp_activyty);
        ed_gia_newsp = findViewById(R.id.dialog_sp_gia);
        ed_img_newsp = findViewById(R.id.dialog_sp_img);
        ed_mota_newsp = findViewById(R.id.dialog_sp_mota);
        ed_name_newsp = findViewById(R.id.dialog_sp_name);
        btn_newsp = findViewById(R.id.btn_them_sp);
        btn_back = findViewById(R.id.btn_back_new_sp);
        btnChooseImg = findViewById(R.id.img_dialog_sp);
        adapter = new ProdAdapter(context);
        listNEwProduct = new ArrayList<>();
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

                ArrayAdapter<String> adapter = new ArrayAdapter<>(Tester.this, R.layout.style_spinner, listCat);
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
            selectedImageUri = data.getData();
            ed_img_newsp.setText(selectedImageUri.toString());
            // Hiển thị hình ảnh đã chọn nếu cần
            btnChooseImg.setImageURI(selectedImageUri);
        }
    }
}