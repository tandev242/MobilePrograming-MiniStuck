package hcmute.edu.vn.mssv18110361;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorWindow;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import hcmute.edu.vn.mssv18110361.Adapter.CategoryAdapter;
import hcmute.edu.vn.mssv18110361.DAO.CartItemDAO;
import hcmute.edu.vn.mssv18110361.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110361.DAO.UserDAO;
import hcmute.edu.vn.mssv18110361.model.Category;
import hcmute.edu.vn.mssv18110361.model.Product;
import hcmute.edu.vn.mssv18110361.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static int cart_count = 0;
    private ProgressBar mProgressBar;
    private ActionBar bottom_toobar;
    private int seletedItem = R.id.navigation_home;
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    private static int cart_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Nang cap Cursor len thi moi load len duoc nhieu item
        upgradeCursor();

        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        mProgressBar = findViewById(R.id.progressBar);
        bottom_toobar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(seletedItem);
        // Set cart_count
        setCartCount();
        bottom_toobar.setTitle("Home");
        loadFragment(new HomeFragment());
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //Hide navigation when on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Intent myIntent;
            mProgressBar = findViewById(R.id.progressBar);
            if(item.getItemId() != seletedItem){
                mProgressBar.setVisibility(View.VISIBLE);
                switch (item.getItemId()) {
                    case R.id.navigation_product:
                        bottom_toobar.setTitle("Product");
                        fragment = new ProductFragment();
                        loadFragment(fragment);
                        handleProgress();
                        seletedItem = item.getItemId();
                        return true;
                    case R.id.navigation_home:
                        bottom_toobar.setTitle("Home");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        handleProgress();
                        seletedItem = item.getItemId();
                        return true;
                    case R.id.navigation_profile:
                        bottom_toobar.setTitle("Profile");
                        if(cart_id == 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Heyy..")
                                    .setMessage("You need to login first. Do you want to login ?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setCancelable(false);
                            builder.show();
                        }else {
                            myIntent = new Intent(MainActivity.this ,ProfileActivity.class);
                            startActivityForResult(myIntent, 0);
                        }
                        handleProgress();
                        seletedItem = item.getItemId();
                        return true;
                }
            }
            return false;
        }
    };

    private void setCartCount(){
        try{
            cart_id = sp.getInt("cart_id" , 0);
            CartItemDAO cid = new CartItemDAO(MainActivity.this);
            cart_count = cid.getAllCartItem(cart_id).size();
        }
        catch (Exception e){
            Log.d(TAG, "getCartCount: NULL");
        }
    }

    private void handleProgress(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        }, 100);
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        final MenuItem menuItem = menu.findItem(R.id.cart);
        menuItem.setIcon(Converter.convertLayoutToImage(MainActivity.this, cart_count, R.drawable.ic_shopping_cart_white));

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            bottom_toobar.setTitle("Search");
            Fragment fragment = ProductFragment.newInstance(query);
            loadFragment(fragment);
            handleProgress();
            //use the query to search your data somehow
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cart) {
            boolean isLogin = false;
            try {
                int i = sp.getInt("user_id" , 0);
                if (sp.getInt("user_id" , 0) > 0 ){
                    isLogin = true;
                }
            }
            catch (Exception e){
                Log.d(TAG, "onOptionsItemSelected: Can't find id in storage");
            }
            if (isLogin) {
                Intent i = new Intent(this, CartActivity.class);
                startActivity(i);
                return true;
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Heyy..")
                        .setMessage("To see your cart you have to login first. Do you want to login ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No Just Continue ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setCancelable(false);
                builder.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void upgradeCursor(){
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 102400* 1024); //the 100MB is the new size
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

}