package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.mssv18110361.DAO.CartDAO;
import hcmute.edu.vn.mssv18110361.DAO.UserDAO;
import hcmute.edu.vn.mssv18110361.model.Cart;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUsername, edtPassword;
    TextView tvRegister;
    TextView tvForgotPwd;
    SharedPreferences sp;
    public static final String PREFS = "PREFS";
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getApplicationContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = sp.edit();

        edtUsername = findViewById(R.id.editTextUserName);
        edtPassword = findViewById(R.id.editTextPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            // Event Login
            public void onClick(View v) {
                login(v);
            }
        });

        tvRegister = findViewById(R.id.gotoRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), RegisterActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
        tvForgotPwd = findViewById(R.id.forgotPassword);
        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), ForgotPasswordActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    private void login(View v) {
        DatabaseHelper db = new DatabaseHelper(v.getContext());
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        UserDAO userDAO = new UserDAO(this);
        if (userDAO.isAccountExistInDB(username, password)) {
            // luu username vao PREFS
            int id = userDAO.getIdByUserName(username);
            int cart_id = getCartId(id);
            editor.putInt("user_id", id);
            editor.putInt("cart_id", cart_id);
            editor.apply();
            Intent myIntent = new Intent(v.getContext(), MainActivity.class);
            startActivity(myIntent);
            Toast.makeText(v.getContext(), "Login successful !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(v.getContext(), "Login failed !", Toast.LENGTH_SHORT).show();
        }
    }

    private int getCartId(int user_id) {
        CartDAO cartDao = new CartDAO(LoginActivity.this);
        if (cartDao.get(user_id) == null) {
            cartDao.addCart(new Cart(user_id));
        }
        int cart_id = cartDao.get(user_id).get_id();
        return cart_id;
    }
}