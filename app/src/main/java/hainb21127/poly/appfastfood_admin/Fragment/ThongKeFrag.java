package hainb21127.poly.appfastfood_admin.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Adapter.ThongKeAdapter;
import hainb21127.poly.appfastfood_admin.DTO.KhachHang;
import hainb21127.poly.appfastfood_admin.DTO.Order;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.config.Utilities;

public class ThongKeFrag extends Fragment {
    ListView rcvTke;
    List<Order> mOrder;
    ThongKeAdapter thongKeAdapter;
    Context context;
    TextView tvTongDon, tvSoDon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTke = view.findViewById(R.id.rcvThongKe);
        tvTongDon = view.findViewById(R.id.tongDoanhThu);
        tvSoDon = view.findViewById(R.id.soDon);
        mOrder = new ArrayList<>();
        thongKeAdapter = new ThongKeAdapter(getContext(),mOrder);

        getListTke();
    }
    private void getListTke(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("orders");


        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mOrder.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()){
                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                            String userId = dataSnapshot2.getKey();
                            KhachHang khachHang = new KhachHang();
                            khachHang.setId(userId);

                            khachHang.setFullname(dataSnapshot2.child("fullname").getValue(String.class));

                            khachHang.setEmail(dataSnapshot2.child("email").getValue(String.class));

                            khachHang.setImage(dataSnapshot2.child("image").getValue(String.class));

                            khachHang.setAddress(dataSnapshot2.child("address").getValue(String.class));

                            khachHang.setPhonenumber(dataSnapshot2.child("phone").getValue(Integer.class));

                            Order order = new Order();
                            order.setId(dataSnapshot1.getKey());
                            order.setDate(dataSnapshot1.child("date").getValue(String.class));

                            order.setStatus(dataSnapshot1.child("trangthai").getValue(String.class));

                            order.setTong(dataSnapshot1.child("tongdonhang").getValue(Integer.class));

                            order.setId_user(khachHang);
                            mOrder.add(order);

                        }
                    }

                }
                int total = 0;
                for (int i = 0; i < thongKeAdapter.getCount(); i++) {
                    Order order = (Order) thongKeAdapter.getItem(i);
                    if (order.getStatus().equalsIgnoreCase("Đã giao hàng")) {
                        total += order.getTong();
                    }else {
                        mOrder.remove(order);
                        i--;
                    }
                }
                rcvTke.setAdapter(thongKeAdapter);
                thongKeAdapter.notifyDataSetChanged();


//                for (int i = 0; i < thongKeAdapter.getCount(); i++) {
//                    Order cart = (Order) thongKeAdapter.getItem(i);
//                    if (cart.getStatus().equalsIgnoreCase("Đã giao hàng")) {
//                        total += cart.getTong();
//
//                    }
//                }
                tvTongDon.setText(Utilities.addDots(total) + "đ");
                int count = 0;
                for (int i = 0; i < thongKeAdapter.getCount(); i++) {
                    Order cart = (Order) thongKeAdapter.getItem(i);
                    if (cart.getStatus().equalsIgnoreCase("Đã giao hàng")) {
                        count++;
                    }
                }
                tvSoDon.setText(String.valueOf(count));
                Toast.makeText(getActivity(), "Lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}