package hainb21127.poly.appfastfood_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import hainb21127.poly.appfastfood_admin.Activity.Login;
import hainb21127.poly.appfastfood_admin.Activity.Signout;
import hainb21127.poly.appfastfood_admin.Fragment.OrderFrag;
import hainb21127.poly.appfastfood_admin.Fragment.HomeFrag;
import hainb21127.poly.appfastfood_admin.Fragment.Profile;
import hainb21127.poly.appfastfood_admin.Fragment.UserFrag;
import hainb21127.poly.appfastfood_admin.Fragment.ProductsFrag;
import hainb21127.poly.appfastfood_admin.Fragment.ThongKeFrag;
import hainb21127.poly.appfastfood_admin.Fragment.MemberFrag;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final int FRAMENT_HOME =0;
    public static final int FRAMENT_SP =1;
    public static final int FRAMENT_CART =2;
    public static final int FRAMENT_USER =3;
    public static final int FRAMENT_MEMBER =4;
    public static final int FRAMENT_TKE =5;
    public static final int FRAMENT_GTHIEU =6;
    public static final int FRAMENT_CHAT =7;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;
    TextView name,email,Idroles;
    ImageView imageProfile;
    NavigationView navigationView;

    private int currentFragment = FRAMENT_HOME;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.nameProfile);
        email = headerView.findViewById(R.id.emailProfile);
        Idroles = headerView.findViewById(R.id.Idroles);
        imageProfile = headerView.findViewById(R.id.imageProfile);
        frameLayout = findViewById(R.id.fragment);
        drawerLayout = findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFrag());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        getDataProfile();
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id ==R.id.nav_home){
            if (currentFragment!=FRAMENT_HOME){
                replaceFragment(new HomeFrag());
                toolbar.setTitle("Home");
                currentFragment = FRAMENT_HOME;
            }
        }else if (id == R.id.nav_spham){
            if (currentFragment!=FRAMENT_SP){
                replaceFragment(new ProductsFrag());
                toolbar.setTitle("Products");
                currentFragment = FRAMENT_SP;
            }
        }else if (id == R.id.nav_cart){
            if (currentFragment!=FRAMENT_CART){
                replaceFragment(new OrderFrag());
                toolbar.setTitle("Cart");
                currentFragment = FRAMENT_CART;
            }

        }else if (id == R.id.nav_user){
            if (currentFragment!=FRAMENT_USER){
                replaceFragment(new MemberFrag());
                toolbar.setTitle("Staffs");
                currentFragment = FRAMENT_USER;
            }

        }else if (id == R.id.nav_member){
            if (currentFragment!=FRAMENT_MEMBER){
                replaceFragment(new UserFrag());
                toolbar.setTitle("Users");
                currentFragment = FRAMENT_MEMBER;
            }
        }else if (id == R.id.nav_thongke){
            if (currentFragment!=FRAMENT_TKE){
                replaceFragment(new ThongKeFrag());
                toolbar.setTitle("Thống kê");
                currentFragment = FRAMENT_TKE;
            }

        }else if (id == R.id.nav_gthieu){
            if (currentFragment!=FRAMENT_GTHIEU){
                toolbar.setTitle("Đổi mật khẩu");
                replaceFragment(new Profile());
                currentFragment = FRAMENT_GTHIEU;
            }

        }else if (id == R.id.nav_logout){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(getApplication(), Login.class));
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    private void getDataProfile(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("managers").child(userId);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int roles = snapshot.child("level").getValue(Integer.class);
                        Menu menu = navigationView.getMenu();
                        MenuItem menuItem = menu.findItem(R.id.nav_user);
                        MenuItem menuItem1 = menu.findItem(R.id.nav_member);
                        if (roles != 1){
                            menuItem.setVisible(false);
                            menuItem1.setVisible(false);
                        }else {
                            menuItem.setVisible(true);
                            menuItem1.setVisible(true);
                        }
                        String nameProfile = snapshot.child("name").getValue(String.class);
                        String emailProfile = snapshot.child("email").getValue(String.class);
                        String avater = snapshot.child("image").getValue(String.class);

                        name.setText(nameProfile);
                        email.setText(emailProfile);
                        Picasso.get().load(avater).into(imageProfile);
                        if (roles != 1){
                            Idroles.setText("Nhân viên");
                        }else {
                            Idroles.setText("Quản Lý");
                        }
                        Log.d("TAGD", "onDataChange: "+ roles);
                    Log.d("TAG", "onDataChange: "+ userId);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    private void getDataProfile1() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference managersRef = FirebaseDatabase.getInstance().getReference("managers");

            // Thêm sự kiện lắng nghe tại đường dẫn "managers"
            managersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Kiểm tra xem user có tồn tại trong danh sách managers hay không
                        if (snapshot.hasChild(userId)) {
                            DataSnapshot userSnapshot = snapshot.child(userId);
                            int roles = userSnapshot.child("level").getValue(Integer.class);

                            // ... Các dòng code xử lý dữ liệu khác
                            Menu menu = navigationView.getMenu();
                            MenuItem menuItem = menu.findItem(R.id.nav_user);
                            MenuItem menuItem1 = menu.findItem(R.id.nav_member);
                            if (roles != 1){
                                menuItem.setVisible(false);
                                menuItem1.setVisible(false);
                            }else {
                                menuItem.setVisible(true);
                                menuItem1.setVisible(true);
                            }
                            String nameProfile = userSnapshot.child("name").getValue(String.class);
                            String emailProfile = userSnapshot.child("email").getValue(String.class);
                            String avater = userSnapshot.child("image").getValue(String.class);

                            name.setText(nameProfile);
                            email.setText(emailProfile);
                            Picasso.get().load(avater).into(imageProfile);
                            if (roles != 1){
                                Idroles.setText("Nhân viên");
                            }else {
                                Idroles.setText("Admin");
                            }

                            Log.d("TAGD", "onDataChange: " + roles);
                        } else {
                            Log.d("TAGD", "User not found in managers");
                        }
                    }
                    Log.d("TAG", "onDataChange: " + userId);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý lỗi
                }
            });
        }
    }

}