package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hainb21127.poly.appfastfood_admin.R;

public class Register extends AppCompatActivity {


    TextView tv_back;
    Button btn_signin;
    TextInputEditText ed_name, ed_email, ed_phone, ed_level, ed_passwd,ed_checkpw;

    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_phone = findViewById(R.id.ed_phone);
        ed_level = findViewById(R.id.ed_level);
        ed_passwd = findViewById(R.id.ed_passwd);
        ed_checkpw = findViewById(R.id.ed_checkpw);
        tv_back = findViewById(R.id.tv_back);
        btn_signin= findViewById(R.id.btn_signin);
        progressBar = findViewById(R.id.progress_res);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = ed_name.getText().toString();
                String textEmail = ed_email.getText().toString();
                String textPhone = ed_phone.getText().toString();
                Integer textLevel = Integer.valueOf(ed_level.getText().toString());
                String textPasswd = ed_passwd.getText().toString();
                String textConfirmPwd = ed_checkpw.getText().toString();
//                startActivity(intent);

                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(Register.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    ed_name.setError("Name is required");
                    ed_name.requestFocus();
                }else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    ed_email.setError("Email is required");
                    ed_email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(Register.this, "Please re-enter your name", Toast.LENGTH_SHORT).show();
                    ed_email.setError("Vaild Email is required");
                    ed_email.requestFocus();
                }else if (TextUtils.isEmpty(textPhone)){
                    Toast.makeText(Register.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    ed_phone.setError("Phone number is required");
                    ed_phone.requestFocus();
                }else if (textPhone.length() !=10) {
                    Toast.makeText(Register.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
                    ed_phone.setError("Phone number should be 10 digits");
                    ed_phone.requestFocus();
                }else if (TextUtils.isEmpty(textLevel+"")){
                    Toast.makeText(Register.this, "Please enter level qtv", Toast.LENGTH_SHORT).show();
                    ed_level.setError("Level is required");
                    ed_level.requestFocus();
                }else if (TextUtils.isEmpty(textPasswd)){
                    Toast.makeText(Register.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    ed_passwd.setError("Password is required");
                    ed_passwd.requestFocus();
                }else if (textPasswd.length() <6){
                    Toast.makeText(Register.this, "Password should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    ed_passwd.setError("Password too weak");
                    ed_passwd.requestFocus();
                }else if (TextUtils.isEmpty(textConfirmPwd)){
                    Toast.makeText(Register.this, "Please confirm your Password", Toast.LENGTH_SHORT).show();
                    ed_checkpw.setError("Password Confirmation is required");
                    ed_checkpw.requestFocus();
                }else if (!textPasswd.equals(textConfirmPwd)){
                    Toast.makeText(Register.this, "Please same same Password", Toast.LENGTH_SHORT).show();
                    ed_checkpw.setError("Password Confirmation is required");
                    ed_checkpw.requestFocus();

                    ed_passwd.clearComposingText();
                    ed_checkpw.clearComposingText();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textName, textEmail,textPhone,textLevel, textPasswd);
                }
            }
        });
    }

    private void registerUser(String textName, String textEmail, String textPhone, Integer textLevel, String textPasswd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPasswd).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    firebaseUser.sendEmailVerification();
                    Intent intent = new Intent(Register.this, Login.class);
//                  intent
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Register.this, "Register faild: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}