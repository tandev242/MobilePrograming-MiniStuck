package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110361.DAO.BillDAO;
import hcmute.edu.vn.mssv18110361.DAO.BillDetailsDAO;
import hcmute.edu.vn.mssv18110361.DAO.UserDAO;
import hcmute.edu.vn.mssv18110361.model.User;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvTotalBill;
    private TextView tvTotalProduct;
    private Button btnLogout;
    private Button btnViewDetail;
    private Button btnEditProfile;
    private ImageView imageUser;
    private TextView tvName;
    SharedPreferences sp;

    public static final String PREFS = "PREFS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        tvTotalBill = findViewById(R.id.total_bill);
        tvTotalProduct = findViewById(R.id.total_product);
        btnLogout = findViewById(R.id.btnLogout);
        btnViewDetail = findViewById(R.id.btnViewDetail);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        imageUser = findViewById(R.id.imageUser);
        tvName = findViewById(R.id.tvName);
        showInfo(this, sp.getInt("user_id" , 0));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed(); // Implemented by activity
            }
        });

        int user_id = sp.getInt("user_id", 0);
        try {
            BillDAO bDao = new BillDAO(this);
            tvTotalBill.setText(String.valueOf(bDao.countBillById(user_id)));
            List<Integer> listBillId = bDao.getListBillId(user_id);
            BillDetailsDAO bdtDao = new BillDetailsDAO(this);

            int total_product = 0;
            for (Integer id: listBillId) {
                total_product += bdtDao.countBillDetailsById(id);
            }
            tvTotalProduct.setText(String.valueOf(total_product));
        }catch (Exception e){
            Log.d("Fail", e.getMessage());
        }


        btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().apply();
                Toast.makeText(ProfileActivity.this, "Logout completed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this , UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showInfo(Context context , int user_id){
        UserDAO uDao = new UserDAO(context);
        User user = uDao.get(user_id);
        byte[] img = user.get_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        imageUser.setImageBitmap(bitmap);
        tvName.setText(user.get_name());
    }
}