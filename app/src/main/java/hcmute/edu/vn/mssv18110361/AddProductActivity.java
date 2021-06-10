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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hcmute.edu.vn.mssv18110361.DAO.CategoryDAO;
import hcmute.edu.vn.mssv18110361.DAO.ProductDAO;
import hcmute.edu.vn.mssv18110361.model.Category;
import hcmute.edu.vn.mssv18110361.model.Product;

public class AddProductActivity extends AppCompatActivity {
    ImageView imageView = null;
    Button upload = null;
    Spinner spinner_category = null;
    Button add = null;
    EditText name = null;
    EditText price = null;
    Integer category_id_selected = 0;
    List<Category> listCategory = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        name = (EditText)findViewById(R.id.product_name);
        price = (EditText) findViewById(R.id.editTextPrice);
        add = (Button) findViewById(R.id.btnAddProduct);
        upload = (Button) findViewById(R.id.btnCamera);
        imageView = (ImageView) findViewById(R.id.product_img);

        CategoryDAO categoryDAO = new CategoryDAO(this);
        listCategory = categoryDAO.getAllCategory();
        getLabel(listCategory);
        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getLabel(listCategory));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                // Get id by  index of spinner
                category_id_selected = listCategory.get(spinner_category.getSelectedItemPosition()).get_id();
//                Toast.makeText(AddProductActivity.this, Integer.toString(category_id_selected), Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v.getContext());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product_name = name.getText().toString();
                category_id_selected = listCategory.get(spinner_category.getSelectedItemPosition()).get_id();
                int product_price = -1;
                if(price.getText().toString() != "" && price.getText().toString() != null ){
                    product_price = Integer.parseInt(price.getText().toString());
                }
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap .compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] img = bos.toByteArray();
                Date date1 = null;
                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2020");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Product product = new Product(category_id_selected , product_name , img , date1, date1 , product_price );
                addProduct(product);
            }
        });

    }
    
    private List<String> getLabel(List<Category> list){
        List<String> label = new ArrayList<String>();
        for (Category item: list) {
            label.add(item.get_name());
        }
        return label;
    }

    private void addProduct(Product product){

        if(product.get_name() != "" && product.get_price() > 0 && product.get_image() != null){
            ProductDAO productDAO = new ProductDAO(AddProductActivity.this);
            productDAO.addProduct(product);
            Toast.makeText(AddProductActivity.this, "Add Product completed! ", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(AddProductActivity.this, "Add Product failed ! ", Toast.LENGTH_SHORT).show();
        }
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
                        imageView.setImageBitmap(selectedImage);
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
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}