package hainb21127.poly.appfastfood_admin.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Adapter.OrderAdapter;
import hainb21127.poly.appfastfood_admin.DTO.KhachHang;
import hainb21127.poly.appfastfood_admin.DTO.Order;
import hainb21127.poly.appfastfood_admin.R;

public class OrderFrag extends Fragment {

    RecyclerView rcvOrder;
    List<Order> mOrder;
    OrderAdapter adapterOrder;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvOrder = view.findViewById(R.id.rcv_order);
        mOrder = new ArrayList<>();
        adapterOrder = new OrderAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rcvOrder.setLayoutManager(manager);
        getListOrder();

    }
    private void getListOrder(){
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

                            khachHang.setName(dataSnapshot2.child("fullname").getValue(String.class));

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
                            adapterOrder.setDataOrder(mOrder);
                            rcvOrder.setAdapter(adapterOrder);
                        }
                    }

                }

                adapterOrder.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Load data success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Load data Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}