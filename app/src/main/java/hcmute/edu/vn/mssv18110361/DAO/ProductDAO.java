package hcmute.edu.vn.mssv18110361.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110361.DatabaseHelper;
import hcmute.edu.vn.mssv18110361.model.Category;
import hcmute.edu.vn.mssv18110361.model.Product;

public class ProductDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "Product";

    SimpleDateFormat StringToDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");


    public ProductDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void openToWrite() throws SQLiteException {
        db = dbHelper.getWritableDatabase();
    }

    public void openToRead() throws SQLiteException {
        db = dbHelper.getReadableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    //region Product
    public void addProduct(Product product) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("name", product.get_name());
        values.put("category_id", product.get_category_id());
        values.put("image", product.get_image());
        values.put("price", product.get_price());
        values.put("date_of_manufacture", product.get_date_of_manufacture().toString());
        values.put("expiration_date", product.get_expiration_date().toString());
        db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public Product get(int id){
        openToRead();
        String query = "SELECT id, category_id ,name, image , date_of_manufacture ,expiration_date, price FROM " + TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        Product product = null;
        try {
            while (cursor.moveToFirst()) {
                product = new Product();
                product.set_id(cursor.getInt(0));
                product.set_category_id(cursor.getInt(1));
                product.set_name(cursor.getString(2));
                product.set_image(cursor.getBlob(3));
                product.set_price(cursor.getInt(6));
                break;
            }
        }finally {
            cursor.close();
        }

        close();
        return product;
    }

    public List<Product> getProductsByName(String name) {
        openToRead();
        String query = "SELECT id, category_id ,name, image , price  FROM " + TABLE_NAME + " WHERE name like '%"+ name +"%' ";
        Cursor cursor = db.rawQuery(query, null);
        List<Product> list = new ArrayList<Product>();
        try{
            while (cursor.moveToNext()) {
                Product product = new Product();
                product.set_id(cursor.getInt(0));
                product.set_category_id(cursor.getInt(1));
                product.set_name(cursor.getString(2));
                product.set_image(cursor.getBlob(3));
                product.set_price(cursor.getInt(4));
                list.add(product);
            }
        }finally {
            cursor.close();
        }
        close();
        return list;
    }

    public List<Product> getAllProduct() {
        openToRead();
        String query = "SELECT id, category_id ,name, image , price  FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        List<Product> list = new ArrayList<Product>();
        try {
            while (cursor.moveToNext()) {
                Product product = new Product();
                product.set_id(cursor.getInt(0));
                product.set_category_id(cursor.getInt(1));
                product.set_name(cursor.getString(2));
                product.set_image(cursor.getBlob(3));
                product.set_price(cursor.getInt(4));
                list.add(product);
            }
        }finally {
            cursor.close();
        }
        close();
        return list;
    }

    public List<Product> getProductsByCategoryId(int id) {
        openToRead();
        String query = "SELECT id, category_id ,name, image , price  FROM " + TABLE_NAME + " WHERE category_id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        List<Product> list = new ArrayList<Product>();
        try {
            while (cursor.moveToNext()) {
                Product product = new Product();
                product.set_id(cursor.getInt(0));
                product.set_category_id(cursor.getInt(1));
                product.set_name(cursor.getString(2));
                product.set_image(cursor.getBlob(3));
                product.set_price(cursor.getInt(4));
                list.add(product);
            }
        }finally {
            cursor.close();
        }
        close();
        return list;
    }
    //endregion
}
