package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Adapter.ProdAdapter;
import hainb21127.poly.appfastfood_admin.DTO.Category;
import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;

public class NewProducts extends AppCompatActivity {
    Spinner spinner;
    private List<Category> listCat = new ArrayList<>();
    List<String> arrList = new ArrayList<>();
    private TextInputEditText ed_name_newsp, ed_gia_newsp, ed_mota_newsp,ed_img_newsp;
    Button btn_newsp;
    ImageView btn_back;
    ProdAdapter adapter;
    Context context;
    List<Products> listNEwProduct;

    String id_cat, id_name;

    private String selectedCategory;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_products);
        anhXa();
        showSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedCategory = listCat.get(i);
                id_cat = listCat.get(i).getId();
                Log.d("TAG", "onItemSelected: "+id_cat);

                id_name = listCat.get(i).getNameCat();
                Log.d("TAGww", "onItemSelected: "+id_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Giá trị trong spinner chưa được chọn", Toast.LENGTH_SHORT).show();
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

                id_name = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(intPrice + "") || TextUtils.isEmpty(textImage) || TextUtils.isEmpty(textDesr)
                        || TextUtils.isEmpty(textName) || TextUtils.isEmpty(id_name)) {
                    Toast.makeText(NewProducts.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    NEwSanPham(textName, intPrice, textImage, textDesr, id_name);
                }
            }
        });


    }
    private void NEwSanPham(String textName, Integer intPrice, String textImage, String textDesr, String selectedCategory) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("products").push();

        Products products = new Products(textName, intPrice, textImage, textDesr);

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
                            if (task.isSuccessful()){
                                Toast.makeText(NewProducts.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(NewProducts.this, "Failed"+task.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(NewProducts.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewProducts.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhXa(){
        spinner = findViewById(R.id.spiner_dialog_sp_activyty);
        ed_gia_newsp = findViewById(R.id.dialog_sp_gia);
        ed_img_newsp = findViewById(R.id.dialog_sp_img);
        ed_mota_newsp = findViewById(R.id.dialog_sp_mota);
        ed_name_newsp = findViewById(R.id.dialog_sp_name);
        btn_newsp = findViewById(R.id.btn_them_sp);
        btn_back = findViewById(R.id.btn_back_new_sp);
        adapter = new ProdAdapter(context);
        listNEwProduct = new ArrayList<>();
    }
    private void showSpinner(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category1 = new Category();
                    category1.setId(dataSnapshot.getKey());
                    category1.setNameCat(dataSnapshot.child("nameCat").getValue(String.class));
                    listCat.add(category1);
                    id_cat = listCat.get(0).getId();

                    for (Category cat : listCat){
                        arrList.add(cat.getNameCat());
                    }
//                    listCat.add(dataSnapshot.child("nameCat").getValue(String.class));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewProducts.this,R.layout.style_spinner, arrList);
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewProducts.this,R.layout.style_spinner, listCat);
                spinner.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//private void showSpinner() {
//    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("category");
//    reference.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            ArrayList<String> arrList = new ArrayList<>();
//            ArrayList<Category> listCat = new ArrayList<>();
//
//            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                Category category = new Category();
//                category.setId(dataSnapshot.getKey());
//                category.setNameCat(dataSnapshot.child("nameCat").getValue(String.class));
//                listCat.add(category);
//                arrList.add(category.getNameCat());
//            }
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewProducts.this, R.layout.style_spinner, arrList);
//            spinner.setAdapter(adapter);
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            // Xử lý lỗi nếu cần
//        }
//    });
//}


    private void showSpinner2() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("category");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> arrList = new ArrayList<>();
                ArrayList<Category> listCat = new ArrayList<>();

                // Xóa danh sách cũ
                arrList.clear();
                listCat.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Category category = new Category();
                    category.setId(dataSnapshot.getKey());
                    category.setNameCat(dataSnapshot.child("nameCat").getValue(String.class));
                    listCat.add(category);
                    arrList.add(category.getNameCat());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewProducts.this, R.layout.style_spinner, arrList);
                spinner.setAdapter(adapter);

                if (!listCat.isEmpty()) {
                    id_cat = listCat.get(0).getId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }


}