package hainb21127.poly.appfastfood_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import hainb21127.poly.appfastfood_admin.R;

public class Login extends AppCompatActivity {

    TextView create_account;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        create_account = findViewById(R.id.create_account);

        Intent intent = new Intent(this, Register.class);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

}