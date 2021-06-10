package hcmute.edu.vn.mssv18110361;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import hcmute.edu.vn.mssv18110361.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110361.DAO.UserDAO;
import hcmute.edu.vn.mssv18110361.model.Category;
import hcmute.edu.vn.mssv18110361.model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirm;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtAddress;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private EditText edtEmail;
    private ImageView imageUser;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirm);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        imageUser = findViewById(R.id.imageUser);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDAO uDao = new UserDAO(v.getContext());

                String sUsername = edtUsername.getText().toString();
                String sPassword = edtPassword.getText().toString();
                String sConfirm = edtConfirm.getText().toString();
                String sEmail = edtEmail.getText().toString();
                String sPhone =  edtPhone.getText().toString();
                String sName = edtName.getText().toString();
                String sAddress = edtAddress.getText().toString();
                String sex = radioMale.isChecked() ? "Male" : "Female";
                Bitmap bitmap = null;
                byte[] img = null;
                try {
                    bitmap = ((BitmapDrawable)imageUser.getDrawable()).getBitmap();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
                    img = bos.toByteArray();
                }catch (Exception e){
                    Log.d("Fail", "onClick: Load image failure");
                }

                if(     bitmap == null||
                        uDao.isAccountExistInDB(edtUsername.getText().toString()) ||
                        "".equals(sUsername) ||
                        "".equals(sPassword) ||
                        "".equals(sConfirm)||
                        "".equals(sEmail) ||
                        "".equals(sPhone) ||
                        "".equals(sName) ||
                        "".equals(sAddress) ||
                        !sPassword.equals(sConfirm)){
                    Toast.makeText(RegisterActivity.this, "Invalid input ! Please try again !", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User( sUsername,
                            sPassword,
                            sName,
                            sPhone,
                            img,
                            sEmail,
                            sex,
                            sAddress);
                    uDao.addUser(user);

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Register ..")
                            .setMessage("Register completed !. let's login now?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            });
                    builder.show();

                }
            }
        });


        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v.getContext());
            }
        });

    }


    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

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
                    startActivityForResult(pickPhoto , 1);

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