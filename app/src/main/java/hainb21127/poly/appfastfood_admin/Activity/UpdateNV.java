package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.squareup.picasso.Picasso;

import hainb21127.poly.appfastfood_admin.DTO.User;
import hainb21127.poly.appfastfood_admin.R;

public class UpdateNV extends AppCompatActivity {

    TextInputEditText image, name, email, phone,roles,passwd;
    String userId;
    ImageView avatarsto, btnBack;
    Button btnUpdate;

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nv);
        avatarsto = findViewById(R.id.avatar);
        image = findViewById(R.id.update_nv_img);
        name = findViewById(R.id.name_update_nv);
        email = findViewById(R.id.email_update_nv);
        phone = findViewById(R.id.phone_update_nv);
        roles = findViewById(R.id.roles_update_nv);
        btnUpdate = findViewById(R.id.btnUpdate_nv);
        passwd = findViewById(R.id.passwd_edit_update_nv);
        btnBack =findViewById(R.id.btnBack_nv);

        listData();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = name.getText().toString();
                String textPhone = phone.getText().toString();
                int textRoles = Integer.parseInt(roles.getText().toString());
                String textImg = image.getText().toString();
                String textEmail = email.getText().toString();
                String textPasswd = passwd.getText().toString();
                UserUpdate(textName,textPhone,textRoles,textImg,textEmail,textPasswd);
                onBackPressed();
            }
        });
    }
    private void UserUpdate(String name,String phone,Integer level, String image, String email,String passwd){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("managers").child(userId);
            User user = new User(name, phone,level,image,email,passwd);

            myRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(UpdateNV.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateNV.this, "Cập nhật thông tin không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }

    private void listData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("nvId");
        String avatar = intent.getStringExtra("avatar");
        String textName = intent.getStringExtra("nvName");
        String textEmail = intent.getStringExtra("nvEmail");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        int lv = intent.getIntExtra("roles",0);
        String textPass = intent.getStringExtra("passwd");

        image.setText(avatar);
        name.setText(textName);
        email.setText(textEmail);
        phone.setText(phoneNumber);
        roles.setText(lv+"");
        passwd.setText(textPass);
        Picasso.get().load(avatar).into(avatarsto);
    }
}