package hainb21127.poly.appfastfood_admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Adapter.OrderItemDetailAdapter;
import hainb21127.poly.appfastfood_admin.DTO.OrderItemDetail;
import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;

public class OrderDetail extends AppCompatActivity {
    RecyclerView rcvDetaiOrder;
    TextView fullname, sdt,emailUser,addressUser;
    List<OrderItemDetail> mDetail;
    OrderItemDetailAdapter detailAdapter;
    Context context;
    ImageView imgBack;
    Button btnSave;
//    String[] spnString = {"Chờ xác nhận","Đang giao hàng","Đã giao hàng","Đã hủy"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        rcvDetaiOrder = findViewById(R.id.rcv_order_detail);
        fullname = findViewById(R.id.tvFullname);
        sdt = findViewById(R.id.PhoneItemOrder);
        emailUser = findViewById(R.id.emailItemOrder);
        addressUser = findViewById(R.id.addressItemOrder);
        imgBack = findViewById(R.id.btnBack_lineItem);
        btnSave = findViewById(R.id.btnSave);
        mDetail = new ArrayList<>();
        detailAdapter = new OrderItemDetailAdapter(mDetail,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rcvDetaiOrder.setLayoutManager(manager);
        rcvDetaiOrder.setAdapter(detailAdapter);

        Intent intent = getIntent();
        String Id = intent.getStringExtra("idOrder");
        String textName = intent.getStringExtra("nameUser");
        String textEmail  = intent.getStringExtra("emailUser");
        String tvaddress = intent.getStringExtra("addressUser");
        int tvPhone = intent.getIntExtra("phoneUser",0);

        fullname.setText(textName);
        sdt.setText("0"+tvPhone);
        emailUser.setText(textEmail);
        addressUser.setText(tvaddress);

        getListDetail(Id);
        Log.d("TAG", "onCreate: "+Id);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void getListDetail(String idOrder){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("lineitems");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DatabaseReference referenceOd = dataSnapshot.child("id_order").getRef();
                    DatabaseReference referenceSp = dataSnapshot.child("id_sanpham").getRef();
                    Products product = new Products();
                    referenceSp.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshotsp : snapshot.getChildren()) {
                                product.setId(dataSnapshotsp.getKey());
                                product.setTensp(dataSnapshotsp.child("tensp").getValue(String.class));
                                product.setGiasp(dataSnapshotsp.child("giasp").getValue(Integer.class));
                                product.setImage(dataSnapshotsp.child("image").getValue(String.class));
                                product.setMota(dataSnapshotsp.child("mota").getValue(String.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.i("referenceSp", "onCancelled: " + error.toString());
                        }
                    });
                    referenceOd.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshotod : snapshot.getChildren()) {
                                String idOd = dataSnapshotod.getKey();
                                if (idOd.equals(idOrder)) {
                                    OrderItemDetail lineitem = new OrderItemDetail();
                                    lineitem.setId(dataSnapshot.getKey());
                                    lineitem.setId_order(idOd);
                                    lineitem.setId_products(product);
                                    lineitem.setSoluong(dataSnapshot.child("soluong").getValue(Integer.class));
                                    lineitem.setTongTien(dataSnapshot.child("tongmathang").getValue(Integer.class));
                                    mDetail.add(lineitem);

                                }
                            }
                            detailAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.i("referenceOd", "onCancelled: " + error.toString());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("lineitems", "onCancelled: " + error.toString());
            }
        });
    }
}