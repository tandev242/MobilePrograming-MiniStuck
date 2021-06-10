package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import hcmute.edu.vn.mssv18110361.DAO.UserDAO;
import hcmute.edu.vn.mssv18110361.model.User;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtOldPassword;
    private EditText edtNewPassword;
    private EditText edtConfirm;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtAddress;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private EditText edtEmail;
    private ImageView imageUser;
    private Button btnEditProfile;
    private Button btnChange;
    SharedPreferences sp;
    public static final String PREFS = "PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        sp = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.mytoolbar);


        edtUsername = findViewById(R.id.edtUsername);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        imageUser = findViewById(R.id.imageUser);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnChange = findViewById(R.id.btnChangePassword);
        final int user_id = sp.getInt("user_id", 0);
        loadProfile(user_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Profile");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed(); // Implemented by activity
            }
        });
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v.getContext());
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO uDao = new UserDAO(v.getContext());
                User user = uDao.get(user_id);
                if (edtOldPassword.getText().toString().equals(user.get_password())) {
                    String newPassword = edtNewPassword.getText().toString();
                    String confirm = edtConfirm.getText().toString();
                    if (newPassword.equals(confirm) && !"".equals(newPassword)) {
                        uDao.changePassword(user_id, newPassword);
                        Toast.makeText(UpdateProfileActivity.this, "Changed password !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UpdateProfileActivity.this, "New password and Confirm password is not same !", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UpdateProfileActivity.this, "Old password incorrect !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDAO uDao = new UserDAO(v.getContext());

                String sEmail = edtEmail.getText().toString();
                String sPhone = edtPhone.getText().toString();
                String sName = edtName.getText().toString();
                String sAddress = edtAddress.getText().toString();
                String sex = radioMale.isChecked() ? "Male" : "Female";
                Bitmap bitmap = null;
                byte[] img = null;
                try {
                    bitmap = ((BitmapDrawable) imageUser.getDrawable()).getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    img = bos.toByteArray();
                } catch (Exception e) {
                    Log.d("Fail", "onClick: Load image failure");
                }
                if (bitmap == null ||
                        "".equals(sEmail) ||
                        "".equals(sPhone) ||
                        "".equals(sName) ||
                        "".equals(sAddress)) {
                    Toast.makeText(UpdateProfileActivity.this, "Input can't be blank! Please check again !", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(user_id, sName,
                            sPhone,
                            img,
                            sEmail,
                            sex,
                            sAddress);
                    uDao.updateUser(user);
                    Toast.makeText(UpdateProfileActivity.this, "Updated profile !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadProfile(int user_id) {
        UserDAO dao = new UserDAO(UpdateProfileActivity.this);
        User user = dao.get(user_id);
        edtName.setText(user.get_name());
        edtPhone.setText(user.get_phone_number());
        edtEmail.setText(user.get_email());
        edtAddress.setText(user.get_address());
        String gender = user.get_gender();
        if ("Female".equals(gender)) {
            radioFemale.setChecked(true);
        }

        byte[] img = user.get_image();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        imageUser.setImageBitmap(bitmap);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageUser.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imageUser.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}