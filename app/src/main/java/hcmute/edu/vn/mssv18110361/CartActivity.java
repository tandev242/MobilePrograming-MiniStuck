package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.mssv18110361.Adapter.CartItemAdapter;
import hcmute.edu.vn.mssv18110361.DAO.BillDAO;
import hcmute.edu.vn.mssv18110361.DAO.BillDetailsDAO;
import hcmute.edu.vn.mssv18110361.DAO.CartItemDAO;
import hcmute.edu.vn.mssv18110361.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110361.model.Bill;
import hcmute.edu.vn.mssv18110361.model.BillDetails;
import hcmute.edu.vn.mssv18110361.model.CartItem;

public class CartActivity extends AppCompatActivity {
    public static final String PREFS = "PREFS";
    SharedPreferences sp;
    int cart_id;
    int user_id;
    private ProgressBar mProgressBar;
    private String[] voucher = {"GIAMNGAY15K", "TANDZ", "BETRUCUTE", "YEUTRUC", "GIAMGIA"};
    private int[] discount = {15000, 30000, 30000, 18000, 80000};

    private EditText edtVoucher;
    private Button btnSubmit;
    private TextView total_discount;
    private TextView total_amount;
    private int total = 0;
    private int disc = 0;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        edtVoucher = (EditText) findViewById(R.id.edtVoucher);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        total_discount = (TextView) findViewById(R.id.total_discount);
        total_amount = (TextView) findViewById(R.id.total_amount);
        btnPay = (Button) findViewById(R.id.btnPay);

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        try {
            user_id = sp.getInt("user_id" , 0);
            cart_id = sp.getInt("cart_id", 0);
            CartItemDAO cartItemDAO = new CartItemDAO(this);
            final List<CartItem> cartItemList = cartItemDAO.getAllCartItem(cart_id);
            if(cartItemList.size() == 0){
                LinearLayout layout = (LinearLayout) findViewById(R.id.empty_cart);
                layout.setVisibility(View.VISIBLE);
            }else{
                RecyclerView cart_item_recyclerview = (RecyclerView) findViewById(R.id.recyclerview_item_products);
                cart_item_recyclerview.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                cart_item_recyclerview.setAdapter(new CartItemAdapter(cartItemList, CartActivity.this));
                total = totalAmount(this, cartItemList);
                total_amount.setText(String.valueOf(total));

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        disc = getDiscount(edtVoucher.getText().toString(), total);
                        if(disc > 0){
                            Toast.makeText(CartActivity.this, "Voucher  has been applied !", Toast.LENGTH_SHORT).show();
                            total_discount.setText(String.valueOf(disc));
                            total_amount.setText(String.valueOf(total - disc));
                            total -= disc;
                        }else{
                            Toast.makeText(CartActivity.this, "Voucher cannot be applied !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handlePayment(v.getContext(), cartItemList, total , disc);
                    }
                });
            }
        } catch (Exception e) {
            Log.d("....HEY", "Can't find cart id");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });
    }
    
    public void handlePayment(Context context , List<CartItem> cartItemList , int total , int discount ){
        if(cartItemList != null && total >= 0){
            BillDAO bDao = new BillDAO(context);
            Date now = java.util.Calendar.getInstance().getTime();
            int bill_id = bDao.addBillAndReturnId(new Bill(user_id, now , total , discount));
            if(bill_id != -1) {
                BillDetailsDAO bdtDAO = new BillDetailsDAO(context);
                CartItemDAO cDAO = new CartItemDAO(context);
                for (CartItem item : cartItemList) {
                    bdtDAO.addBillDetails(new BillDetails(bill_id, item.get_product_id(), item.get_quantity()));
                    cDAO.deleteCartItem(item.get_id());
                }
                Intent intent = new Intent(getApplicationContext(),
                        ShowBillActivity.class);
                finish();
                intent.putExtra("bill_id" , bill_id);
                intent.putExtra("total_amount" , total);
                intent.putExtra("date" ,  now.toString());
                startActivity(intent);
            }else{
                Toast.makeText(context, "Payment failure !", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    public int getDiscount(String vc, int total) {
        for (int i = 0; i < voucher.length; i++) {
            if (vc.equals(voucher[i])) {
                int disc = discount[i];
                if (disc <= total) {
                    return disc;
                }
            }
        }
        return 0;
    }

    public int totalAmount(Context context, List<CartItem> cartItemList) {
        ProductDAO dao = new ProductDAO(context);
        int total = 0;
        for (CartItem item : cartItemList) {
            total += dao.get(item.get_product_id()).get_price() * item.get_quantity();
        }
        return total;
    }
}