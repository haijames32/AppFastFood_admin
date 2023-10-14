package hainb21127.poly.appfastfood_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import hainb21127.poly.appfastfood_admin.R;

public class Register extends AppCompatActivity {

    TextView tv_back;
    TextInputEditText ed_name, ed_email, ed_phone, ed_level, ed_passwd,ed_checkpw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv_back = findViewById(R.id.tv_back);

        Intent intent = new Intent(this, Login.class);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}