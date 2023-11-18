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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class OrderDetail extends AppCompatActivity {
    RecyclerView rcvDetaiOrder;
    TextView fullname, sdt,emailUser,addressUser, tongHoaDon,spnStatus;
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
        tongHoaDon = findViewById(R.id.tongHoaDon);
        spnStatus = findViewById(R.id.spnStatus);
        btnSave = findViewById(R.id.btnSave);
        mDetail = new ArrayList<>();
        detailAdapter = new OrderItemDetailAdapter(mDetail,getApplicationContext());
        // abc
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rcvDetaiOrder.setLayoutManager(manager);
        rcvDetaiOrder.setAdapter(detailAdapter);

        Intent intent = getIntent();
        String Id = intent.getStringExtra("idOrder");
        String textName = intent.getStringExtra("nameUser");
        String textEmail  = intent.getStringExtra("emailUser");
        String tvaddress = intent.getStringExtra("addressUser");
        String status = intent.getStringExtra("Status");
        int textTong = intent.getIntExtra("tongHoaDon",0);
        int tvPhone = intent.getIntExtra("phoneUser",0);

        fullname.setText(textName);
        sdt.setText("0"+tvPhone);
        emailUser.setText(textEmail);
        addressUser.setText(tvaddress);
        tongHoaDon.setText(Utilities.addDots(textTong)+"đ");
        spnStatus.setText(status);
        if (status.equalsIgnoreCase("Chờ xác nhận")
        || status.equalsIgnoreCase("Đã thanh toán và chờ xác nhận")){
            btnSave.setText("Xác nhận");
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("orders").child(Id);

                    String text = "Đang giao hàng";

                    reference.child("trangthai").setValue(text).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(view.getContext(), "Xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                btnSave.setText("Đã xác nhận");
                                btnSave.setEnabled(false);
                            }else {
                                Toast.makeText(view.getContext(), "Xác nhận đơn hàng không thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });
        }else if(status.equalsIgnoreCase("Đang giao hàng")){
            btnSave.setText("Đơn hàng đang được giao");
            btnSave.setEnabled(false);
        }else if(status.equalsIgnoreCase("Đã giao hàng")){
            btnSave.setText("Đơn hàng đã được giao");
            btnSave.setEnabled(false);
        }else {
            btnSave.setText("Đã hủy đơn hàng");
            btnSave.setEnabled(false);
        }

        getListDetail(Id);
        Log.d("TAG", "onCreate: "+Id);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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