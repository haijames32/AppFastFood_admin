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
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;

    private int currentFragment = FRAMENT_HOME;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.fragment);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawer);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFrag());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.icon_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }

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
//        }else if (id == R.id.nav_chat){
//            if (currentFragment!=FRAMENT_CHAT){
//                replaceFragment(new ChatFrag());
//                toolbar.setTitle("Box Chat");
//                currentFragment = FRAMENT_CHAT;
//            }
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
}