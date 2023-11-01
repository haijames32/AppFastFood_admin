package hainb21127.poly.appfastfood_admin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class ProductsDetail extends AppCompatActivity {
    EditText tvName, tvPrice, tvSoluong, tvMota, tvTongtien;
    ImageView btnBack, imgSp, minus, plus;
    Button btnSave;
    private int tong=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        tvName = findViewById(R.id.ed_name_detailsp);
        tvPrice = findViewById(R.id.ed_gia_detailsp);
        tvMota = findViewById(R.id.ed_mota_detailsp);
        imgSp = findViewById(R.id.img_detailsp);
        btnSave = findViewById(R.id.btn_update_detailsp);
        btnBack = findViewById(R.id.btn_back_sp_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("idPro");
        String name = intent.getStringExtra("namePro");
        int gia = intent.getIntExtra("pricePro",0);
        String mota = intent.getStringExtra("motaPro");
        String img = intent.getStringExtra("imagePro");

        tvName.setText(name);
        tvPrice.setText(Utilities.addDots(gia)+"đ");
        tvMota.setText(mota);
        Picasso.get().load(img).into(imgSp);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}