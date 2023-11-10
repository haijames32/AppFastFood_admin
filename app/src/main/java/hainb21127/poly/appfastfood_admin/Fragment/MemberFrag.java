package hainb21127.poly.appfastfood_admin.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
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

import hainb21127.poly.appfastfood_admin.Activity.Login;
import hainb21127.poly.appfastfood_admin.Activity.Register;
import hainb21127.poly.appfastfood_admin.Adapter.UserAdapter;
import hainb21127.poly.appfastfood_admin.DTO.User;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.database.FirebaseDB;


public class MemberFrag extends Fragment {

    FloatingActionButton btn_add;

    UserAdapter adapter;

    Context context;
    List<User> mUser;
    RecyclerView rcView;
    private static final int IMAGE_PICK_CODE = 1001;
    ImageView chooseImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_add = view.findViewById(R.id.fab_user);
        rcView = view.findViewById(R.id.rcv_user);
        mUser = new ArrayList<>();
        adapter = new UserAdapter(context);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rcView.setLayoutManager(manager);
        getListUser();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember();
            }
        });
    }

    private void getListUser() {
        FirebaseDatabase database = FirebaseDB.getDatabaseInstance();
        DatabaseReference myRef = database.getReference("managers");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    user.setId(dataSnapshot.getKey());
                    mUser.add(user);
                    adapter.setDataUser(mUser);
                    rcView.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @SuppressLint("MissingInflatedId")
//    public void addMember2() {
//        TextInputEditText ed_name, ed_email, ed_phone,
//                ed_level, ed_passwd, ed_confirmpw,ed_image;
//        Button btn_add_member;
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_add_member, null);
//
//        btn_add_member = view.findViewById(R.id.btn_add_dialog_member);
//        ed_name = view.findViewById(R.id.ed_name_dialog_member);
//        ed_email = view.findViewById(R.id.ed_email_dialog_member);
//        ed_phone = view.findViewById(R.id.ed_phone_dialog_member);
//        chooseImage = view.findViewById(R.id.avatar_newNV_dialog);
////        ed_level = view.findViewById(R.id.ed_level_dialog_member);
//        ed_passwd = view.findViewById(R.id.ed_passwd_dialog_member);
//        ed_confirmpw = view.findViewById(R.id.ed_checkpw_dialog_member);
//        ed_image = view.findViewById(R.id.ed_img_member_dialog);
//
//        builder.setView(view);
//        builder.setTitle("New User");
//        btn_add_member.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String textName = ed_name.getText().toString();
//                String textEmail = ed_email.getText().toString();
//                String textPhone = ed_phone.getText().toString();
////                int textLevel = Integer.valueOf(ed_level.getText().toString());
//                String textPasswd = ed_passwd.getText().toString();
//                String textConfirmPwd = ed_confirmpw.getText().toString();
//                String textImage = ed_image.getText().toString();
//
//
//
//                String phoneNumberPattern = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
//                if (TextUtils.isEmpty(textImage)) {
//                    ed_image.setError("Image is required");
//                    ed_image.requestFocus();
//                } else if (TextUtils.isEmpty(textName)) {
//                    ed_name.setError("Name is required");
//                    ed_name.requestFocus();
//                } else if (TextUtils.isEmpty(textEmail)) {
//                    ed_email.setError("Email is required");
//                    ed_email.requestFocus();
//                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
//                    Toast.makeText(getContext(), "Please re-enter your name", Toast.LENGTH_SHORT).show();
//                    ed_email.setError("Vaild Email is required");
//                    ed_email.requestFocus();
//                } else if (TextUtils.isEmpty(textPhone)) {
//                    ed_phone.setError("Phone number is required");
//                    ed_phone.requestFocus();
//                } else if (!textPhone.matches(phoneNumberPattern)) {
//                    Toast.makeText(getContext(), "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
//                    ed_phone.setError("Invalid phone number format");
//                    ed_phone.requestFocus();
//                } else if (textPhone.length() != 10) {
//                    Toast.makeText(getContext(), "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
//                    ed_phone.setError("Phone number should be 10 digits");
//                    ed_phone.requestFocus();
////                } else if (TextUtils.isEmpty(textLevel + "")) {
////                    ed_level.setError("Level is required");
////                    ed_level.requestFocus();
//                } else if (TextUtils.isEmpty(textPasswd)) {
//                    ed_passwd.setError("Password is required");
//                    ed_passwd.requestFocus();
//                } else if (textPasswd.length() < 6) {
//                    Toast.makeText(getContext(), "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
//                    ed_passwd.setError("Password too weak");
//                    ed_passwd.requestFocus();
//                } else if (TextUtils.isEmpty(textConfirmPwd)) {
//                    ed_confirmpw.setError("Password Confirmation is required");
//                    ed_confirmpw.requestFocus();
//                } else if (!textPasswd.equals(textConfirmPwd)) {
//                    Toast.makeText(getContext(), "Please same same Password", Toast.LENGTH_SHORT).show();
//                    ed_confirmpw.setError("Password not ");
//                    ed_confirmpw.requestFocus();
//
//                } else {
//                    registerUser(textImage, textName, textEmail, textPhone, textPasswd);
//                    ed_image.setText("");
//                    ed_name.setText("");
//                    ed_email.setText("");
//                    ed_phone.setText("");
//                    ed_passwd.setText("");
//                    ed_confirmpw.setText("");
//                }
//            }
//        });
//
//        builder.setNegativeButton("Done", null);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }


    private void registerUser(String textImage, String textName, String textEmail, String textPhone, String textPasswd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        auth.createUserWithEmailAndPassword(textEmail, textPasswd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    int roles = 2;
                    User user = new User(textName, textPhone, roles, textImage, textEmail,textPasswd);
                    String uid = task.getResult().getUser().getUid();
                    DatabaseReference reference = database.getReference("managers").child(uid);
                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("user", "complete: " + task.toString());
                                Toast.makeText(getActivity(), "New member Success" + task.toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Error save memebr" + task.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "New member Failed "+task.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addMember3() {
        TextInputEditText ed_name, ed_email, ed_phone,
                ed_level, ed_passwd, ed_confirmpw;
        Button btn_add_member;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_member, null);

        btn_add_member = view.findViewById(R.id.btn_add_dialog_member);
        ed_name = view.findViewById(R.id.ed_name_dialog_member);
        ed_email = view.findViewById(R.id.ed_email_dialog_member);
        ed_phone = view.findViewById(R.id.ed_phone_dialog_member);
        chooseImage = view.findViewById(R.id.avatar_newNV_dialog);
        ed_passwd = view.findViewById(R.id.ed_passwd_dialog_member);
        ed_confirmpw = view.findViewById(R.id.ed_checkpw_dialog_member);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở cửa sổ chọn ảnh từ thiết bị
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });

        builder.setView(view);
        builder.setTitle("New User");
        btn_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = ed_name.getText().toString();
                String textEmail = ed_email.getText().toString();
                String textPhone = ed_phone.getText().toString();
                String textPasswd = ed_passwd.getText().toString();
                String textConfirmPwd = ed_confirmpw.getText().toString();

                String phoneNumberPattern = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";
                if (chooseImage.getDrawable() == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setTitle("No Image Selected");
                    alertDialogBuilder.setMessage("Please choose an image for the member.");
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "Nhớ chọn ảnh", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else if (TextUtils.isEmpty(textName)) {
                    ed_name.setError("Name is required");
                    ed_name.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    ed_email.setError("Email is required");
                    ed_email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(getContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    ed_email.setError("Valid Email is required");
                    ed_email.requestFocus();
                } else if (TextUtils.isEmpty(textPhone)) {
                    ed_phone.setError("Phone number is required");
                    ed_phone.requestFocus();
                } else if (!textPhone.matches(phoneNumberPattern)) {
                    Toast.makeText(getContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    ed_phone.setError("Invalid phone number format");
                    ed_phone.requestFocus();
                } else if (textPhone.length() != 10) {
                    Toast.makeText(getContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    ed_phone.setError("Phone number should be 10 digits");
                    ed_phone.requestFocus();
                } else if (TextUtils.isEmpty(textPasswd)) {
                    ed_passwd.setError("Password is required");
                    ed_passwd.requestFocus();
                } else if (textPasswd.length() < 6) {
                    Toast.makeText(getContext(), "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    ed_passwd.setError("Weak password");
                    ed_passwd.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    ed_confirmpw.setError("Password Confirmation is required");
                    ed_confirmpw.requestFocus();
                } else if (!textPasswd.equals(textConfirmPwd)) {
                    Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    ed_confirmpw.setError("Password does not match");
                    ed_confirmpw.requestFocus();
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
                                    String imageURL = downloadUrl.toString();
                                    registerUser(imageURL, textName, textEmail, textPhone, textPasswd);
                                    ed_name.setText("");
                                    ed_email.setText("");
                                    ed_phone.setText("");
                                    ed_passwd.setText("");
                                    ed_confirmpw.setText("");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Failed to get image URL", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        builder.setNegativeButton("Done", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void addMember() {
        TextInputEditText ed_name, ed_email, ed_phone,
                ed_level, ed_passwd, ed_confirmpw;
        Button btn_add_member;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_member, null);

        btn_add_member = view.findViewById(R.id.btn_add_dialog_member);
        ed_name = view.findViewById(R.id.ed_name_dialog_member);
        ed_email = view.findViewById(R.id.ed_email_dialog_member);
        ed_phone = view.findViewById(R.id.ed_phone_dialog_member);
        chooseImage = view.findViewById(R.id.avatar_newNV_dialog);
        ed_passwd = view.findViewById(R.id.ed_passwd_dialog_member);
        ed_confirmpw = view.findViewById(R.id.ed_checkpw_dialog_member);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở cửa sổ chọn ảnh từ thiết bị
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        });

        builder.setView(view);
        builder.setTitle("New User");

        btn_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = ed_name.getText().toString().trim();
                String textEmail = ed_email.getText().toString().trim();
                String textPhone = ed_phone.getText().toString().trim();
                String textPasswd = ed_passwd.getText().toString().trim();
                String textConfirmPwd = ed_confirmpw.getText().toString().trim();

                String phoneNumberPattern = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

                if (chooseImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "Please choose an image for the member.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Failed to get image URI", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một StorageReference để đại diện cho đường dẫn nơi ảnh sẽ được lưu trữ
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUri.getLastPathSegment());

                // Tải lên ảnh lên Firebase Storage
                storageRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Lấy URL của ảnh đã tải lên
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {
                                        String imageURL = downloadUrl.toString();
                                        registerUser(imageURL, textName, textEmail, textPhone, textPasswd);
                                        ed_name.setText("");
                                        ed_email.setText("");
                                        ed_phone.setText("");
                                        ed_passwd.setText("");
                                        ed_confirmpw.setText("");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Failed to get image URL", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        builder.setNegativeButton("Done", null);
        AlertDialog dialog = builder.create();
        dialog.show();
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