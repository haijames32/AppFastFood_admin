package hainb21127.poly.appfastfood_admin.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hainb21127.poly.appfastfood_admin.Activity.NewProducts;
import hainb21127.poly.appfastfood_admin.Activity.Tester;
import hainb21127.poly.appfastfood_admin.Adapter.CategoryAdapter;
import hainb21127.poly.appfastfood_admin.Adapter.ProdAdapter;
import hainb21127.poly.appfastfood_admin.DTO.Category;
import hainb21127.poly.appfastfood_admin.DTO.Products;
import hainb21127.poly.appfastfood_admin.R;
import hainb21127.poly.appfastfood_admin.database.FirebaseDB;

public class ProductsFrag extends Fragment {

    RecyclerView recyclerView;
    ProdAdapter adapter1;

    Context context;
    List<Products> mProduct;

    FloatingActionButton fab_sp;

    CategoryAdapter adapterCate;
    List<String>listCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_sp = view.findViewById(R.id.fab_sp);
        recyclerView = view.findViewById(R.id.rcv_product_fragment);
        mProduct = new ArrayList<>();
        adapter1 = new ProdAdapter(context);
        adapterCate = new CategoryAdapter(context);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(manager);
        getListProducts();

        fab_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), NewProducts.class));
                startActivity(new Intent(getActivity(), Tester.class));
            }
        });
    }
    private void getListProducts(){
        FirebaseDatabase database = FirebaseDB.getDatabaseInstance();
        DatabaseReference myRef = database.getReference("products");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mProduct.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Products product = dataSnapshot.getValue(Products.class);
                    mProduct.add(product);
                    adapter1.setData(mProduct);
                    recyclerView.setAdapter(adapter1);
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "faild", Toast.LENGTH_SHORT).show();
            }
        });
    }
}