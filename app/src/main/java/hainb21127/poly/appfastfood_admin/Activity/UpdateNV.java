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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

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
        passwd = findViewById(R.id.passwd_update_nv);
        btnUpdate = findViewById(R.id.btnUpdate_nv);
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
                String passwdUp = passwd.getText().toString();


            }
        });
    }
    private void UserUpdate(String passwd){
        if (userId!=null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser fbuser = auth.getCurrentUser();
            DatabaseReference userRef = database.getReference("managers").child(userId);

        }
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