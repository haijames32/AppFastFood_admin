package hainb21127.poly.appfastfood_admin.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Adapter.KhachAdapter;
import hainb21127.poly.appfastfood_admin.DTO.KhachHang;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.database.FirebaseDB;

public class UserFrag extends Fragment {

    RecyclerView rcv_kh;
    KhachAdapter khachAdapter;
    Context context;
    List<KhachHang> mKhang;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_kh = view.findViewById(R.id.rcv_dskh);
        mKhang = new ArrayList<>();
        khachAdapter = new KhachAdapter(context);

        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        rcv_kh.setLayoutManager(manager);
        getListKhang();
    }
    private void getListKhang() {
        FirebaseDatabase database = FirebaseDB.getDatabaseInstance();
        DatabaseReference reference = database.getReference("users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mKhang.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    KhachHang khachHang = new KhachHang();
                    khachHang.setName(dataSnapshot.child("fullname").getValue(String.class));
                    khachHang.setAddress(dataSnapshot.child("address").getValue(String.class));
                    khachHang.setPhonenumber(dataSnapshot.child("phone").getValue(Integer.class));

                    // Lấy UID của người dùng từ cơ sở dữ liệu
                    String userUID = dataSnapshot.getKey(); // Key ở đây chính là UID

                    // Sử dụng UID để lấy thông tin người dùng từ Firebase Authentication
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null && user.getUid().equals(userUID)) {
                        String userEmail = user.getEmail();
                        khachHang.setEmail(userEmail);
                    }

                    mKhang.add(khachHang);
                }

                khachAdapter.setDataKhang(mKhang);
                rcv_kh.setAdapter(khachAdapter);
                khachAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "Get List Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Get List Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}