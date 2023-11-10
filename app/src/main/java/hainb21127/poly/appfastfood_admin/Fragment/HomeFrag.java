package hainb21127.poly.appfastfood_admin.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Activity.ChatBot;
import hainb21127.poly.appfastfood_admin.Adapter.CategoryAdapter;
import hainb21127.poly.appfastfood_admin.Adapter.ProdAdapter;
import hainb21127.poly.appfastfood_admin.DTO.Category;
import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.database.FirebaseDB;


public class HomeFrag extends Fragment {

    RecyclerView recyclerView, rcv_category;
    ProdAdapter adapter;
    CategoryAdapter adapterCategory;
    Context context;
    List<Products> mpProducts;
    List<Category> mCategory;
    TextView tv_add;
    FloatingActionButton floating;
    private static final int IMAGE_PICK_CODE = 1001;
    ImageView chooseImage;
    TextInputEditText img_lsp, ed_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_lsp, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floating = view.findViewById(R.id.fabChatBot);
        tv_add = view.findViewById(R.id.tv_add_home);
        recyclerView = view.findViewById(R.id.rcv_products);
        rcv_category = view.findViewById(R.id.rcv_category);
        mpProducts = new ArrayList<>();
        adapter = new ProdAdapter(context);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(manager);
        getListProduct();

        // getListCategory
        mCategory = new ArrayList<>();
        adapterCategory = new CategoryAdapter(context);
        LinearLayoutManager linearManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        rcv_category.setLayoutManager(linearManager);
        getListCategory();

        oninVisible();

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChatBot.class));
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory();
            }
        });
    }

    private void getListCategory() {

        FirebaseDatabase dataCate = FirebaseDB.getDatabaseInstance();
        DatabaseReference RefCate = dataCate.getReference("category");
        
        RefCate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mCategory.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    category.setId(dataSnapshot.getKey());
                    mCategory.add(category);
                    adapterCategory.setDataCatogory(mCategory);
                    rcv_category.setAdapter(adapterCategory);
                }
                adapterCategory.notifyDataSetChanged();
                getListProduct();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "GetList Data Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListProduct(){
        FirebaseDatabase database = FirebaseDB.getDatabaseInstance();
        DatabaseReference myRef = database.getReference("products");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mpProducts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products product = dataSnapshot.getValue(Products.class);
                    product.setId(dataSnapshot.getKey());
                    mpProducts.add(product);
                    adapter.setData(mpProducts);
                    recyclerView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "faild", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void newCategory(String imageSelected, String textName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("category").push();

        Category category = new Category( imageSelected,textName);
        reference.setValue(category).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void oninVisible(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("managers").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int roles = snapshot.child("level").getValue(Integer.class);
                if (roles != 1){
                    tv_add.setVisibility(View.INVISIBLE);
                }else {
                    tv_add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addCategory() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_loaisp, null);

        ed_name = view.findViewById(R.id.dialog_lsp_name);
        chooseImage = view.findViewById(R.id.imageCategory_dialog);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở cửa sổ chọn ảnh từ thiết bị
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });

        dialog.setView(view);
        dialog.setTitle("New Category");

        dialog.setPositiveButton("New", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String textName = ed_name.getText().toString();

                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(getActivity(), "Please enter data", Toast.LENGTH_SHORT).show();
                    ed_name.setError("Name is required");
                } else if (chooseImage.getDrawable() == null) {
                    Toast.makeText(getActivity(), "Please choose an image", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy URI của ảnh đã chọn
                    Uri imageUri = (Uri) chooseImage.getTag();
                    // Tạo một StorageReference để đại diện cho đường dẫn nơi ảnh sẽ được lưu trữ
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUri.getLastPathSegment());
                    // Tải lên ảnh lên Firebase Storage
                    storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy URL của ảnh đã tải lên
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUrl) {
                                    // Khi đã có URL của ảnh, bạn có thể lưu nó vào cơ sở dữ liệu Firebase
                                    String imageUrl = downloadUrl.toString();
                                    newCategory(imageUrl, textName);
                                }
                            });
                        }
                    });
                }
            }
        });
        dialog.setNegativeButton("Done", null);
        AlertDialog builderDialog = dialog.create();
        builderDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            // Lấy URI của ảnh đã chọn
            Uri imageUri = data.getData();

            // Hiển thị ảnh đã chọn lên ImageView
            chooseImage.setImageURI(imageUri);
            // Lưu trữ URI của ảnh vào tag của ImageView
            chooseImage.setTag(imageUri);
        }
    }
}