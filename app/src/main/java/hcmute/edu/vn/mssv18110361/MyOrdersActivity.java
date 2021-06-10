package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110361.Adapter.CartItemAdapter;
import hcmute.edu.vn.mssv18110361.Adapter.OrdersAdapter;
import hcmute.edu.vn.mssv18110361.Adapter.ProductAdapter;
import hcmute.edu.vn.mssv18110361.DAO.BillDAO;
import hcmute.edu.vn.mssv18110361.DAO.BillDetailsDAO;
import hcmute.edu.vn.mssv18110361.DAO.CartItemDAO;
import hcmute.edu.vn.mssv18110361.model.BillDetails;
import hcmute.edu.vn.mssv18110361.model.CartItem;

public class MyOrdersActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    int user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Orders");


        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        try {
            user_id = sp.getInt("user_id", 0);
            BillDAO bDao = new BillDAO(this);
            List<Integer> listBillId = bDao.getListBillId(user_id);
            BillDetailsDAO bdtDao = new BillDetailsDAO(this);
            List<BillDetails> bdtList = new ArrayList<>();
            for (int i = listBillId.size() - 1; i >= 0 ; i--) {
                List<BillDetails> bdt = bdtDao.getAllBillDetails(listBillId.get(i));
                if(bdt.size() != 0){
                    bdtList.addAll(bdt);
                }
            }
            RecyclerView recyclerView = findViewById(R.id.recyclerview_item_orders);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new OrdersAdapter(bdtList, this));
        } catch (Exception e) {
            Log.d("....HEY", "Can't find orders id");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

    }


}