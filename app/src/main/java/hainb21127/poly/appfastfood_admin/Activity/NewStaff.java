package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import hainb21127.poly.appfastfood_admin.DTO.User;
import hainb21127.poly.appfastfood_admin.R;

public class NewStaff extends AppCompatActivity {
    TextInputEditText ed_name, ed_email, ed_phone, ed_level, ed_passwd, ed_confirmpw;
    Button btn_add_member;
    Context context;
    private static final int IMAGE_PICK_CODE = 1001;
    ImageView chooseImage, btnPrevStaff;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_staff);
        btn_add_member = findViewById(R.id.btn_add_member);
        ed_name = findViewById(R.id.ed_name_member);
        ed_email = findViewById(R.id.ed_email_member);
        ed_phone = findViewById(R.id.ed_phone_member);
        ed_level = findViewById(R.id.ed_roles_member);
        chooseImage = findViewById(R.id.avatar_newNV);
        ed_passwd = findViewById(R.id.ed_passwd_member);
        ed_confirmpw = findViewById(R.id.ed_checkpw_member);
        btnPrevStaff =findViewById(R.id.btnStaff);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở cửa sổ chọn ảnh từ thiết bị
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });
        btnPrevStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = ed_name.getText().toString().trim();
                String textEmail = ed_email.getText().toString().trim();
                Integer textLevel = Integer.valueOf(ed_level.getText().toString());
                String textPhone = ed_phone.getText().toString().trim();
                String textPasswd = ed_passwd.getText().toString().trim();
                String textConfirmPwd = ed_confirmpw.getText().toString().trim();

                String phoneNumberPattern = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

                if (chooseImage.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Please choose an image for the member.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(textName)) {
                    ed_name.setError("Name is required");
                    ed_name.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(textEmail)) {
                    ed_email.setError("Email is required");
                    ed_email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    ed_email.setError("Invalid email address");
                    ed_email.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(textPhone)) {
                    ed_phone.setError("Phone number is required");
                    ed_phone.requestFocus();
                    return;
                }

                if (!textPhone.matches(phoneNumberPattern)) {
                    ed_phone.setError("Invalid phone number format");
                    ed_phone.requestFocus();
                    return;
                }

                if (textPhone.length() != 10) {
                    ed_phone.setError("Phone number should be 10 digits");
                    ed_phone.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(textPasswd)) {
                    ed_passwd.setError("Password is required");
                    ed_passwd.requestFocus();
                    return;
                }

                if (textPasswd.length() < 6) {
                    ed_passwd.setError("Password should be at least 6 digits");
                    ed_passwd.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(textConfirmPwd)) {
                    ed_confirmpw.setError("Password Confirmation is required");
                    ed_confirmpw.requestFocus();
                    return;
                }

                if (!textPasswd.equals(textConfirmPwd)) {
                    ed_confirmpw.setError("Password does not match");
                    ed_confirmpw.requestFocus();
                    return;
                }

                // Lấy URI của ảnh đã chọn
                Uri imageUri = (Uri) chooseImage.getTag();
                if (imageUri == null) {
                    Toast.makeText(getApplicationContext(), "Failed to get image URI", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một StorageReference để đại diện cho đường dẫn nơi ảnh sẽ được lưu trữ
//                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUri.getLastPathSegment());
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                String imageName = System.currentTimeMillis() + ".jpg";
                StorageReference imageRef = storageRef.child("images/" + imageName);

                // Tải lên ảnh lên Firebase Storage
                imageRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Lấy URL của ảnh đã tải lên
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {
                                        String imageURL = downloadUrl.toString();
                                        newStaff(imageURL, textName, textEmail, textPhone,textLevel, textPasswd);
                                        ed_name.setText("");
                                        ed_email.setText("");
                                        ed_phone.setText("");
                                        ed_passwd.setText("");
                                        ed_confirmpw.setText("");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Failed to get image URL", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    private void newStaff(String textImage, String textName, String textEmail, String textPhone, Integer textLevel, String textPasswd){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        auth.createUserWithEmailAndPassword(textEmail,textPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String msg = task.getResult().getUser().getUid();
                    User user = new User(textName, textPhone, textLevel, textImage, textEmail, textPasswd);
                    DatabaseReference reference = database.getReference("managers").child(msg);
                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("user", "complete: " + user);
                                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Thêm thất bại" , Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "New member Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            // Lấy URI của ảnh đã chọn
            Uri imageUri = data.getData();

            // Hiển thị ảnh đã chọn lên ImageView
            chooseImage.setImageURI(imageUri);
            // Lưu trữ URI của ảnh vào tag của ImageView
            chooseImage.setTag(imageUri);
        }
    }
}