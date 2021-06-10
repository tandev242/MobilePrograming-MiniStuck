package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowBillActivity extends AppCompatActivity {

    private TextView tvTotal;
    private TextView tvBillId;
    private TextView tvDate;
    private Button btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bill);
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bill");

        tvTotal = findViewById(R.id.total_amount);
        tvBillId = findViewById(R.id.bill_id);
        tvDate = findViewById(R.id.payment_date);
        btnDone = (Button) findViewById(R.id.btnDone);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int bill_id =extras.getInt("bill_id");
            int total_amount = extras.getInt("total_amount");
            String date = extras.getString("date");

            tvTotal.setText(String.valueOf(total_amount));
            tvBillId.setText(String.valueOf(bill_id));
            tvDate.setText(date);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}