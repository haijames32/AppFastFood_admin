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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView name,email;
    ImageView imageProfile;

    private int currentFragment = FRAMENT_HOME;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.nameProfile);
        email = headerView.findViewById(R.id.emailProfile);
        imageProfile = headerView.findViewById(R.id.imageProfile);
        frameLayout = findViewById(R.id.fragment);
        drawerLayout = findViewById(R.id.drawer);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("managers").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
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
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFrag());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

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
                toolbar.setTitle("User");
                currentFragment = FRAMENT_USER;
            }

        }else if (id == R.id.nav_member){
            if (currentFragment!=FRAMENT_MEMBER){
                replaceFragment(new UserFrag());
                toolbar.setTitle("Member");
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
                replaceFragment(new Profile());
                currentFragment = FRAMENT_GTHIEU;
            }

        }else if (id == R.id.nav_logout){
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            startActivity(new Intent(getApplication(), Signout.class));
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

    }
}