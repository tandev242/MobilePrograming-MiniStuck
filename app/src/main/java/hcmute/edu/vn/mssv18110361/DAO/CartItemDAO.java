package hcmute.edu.vn.mssv18110361.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.mssv18110361.DatabaseHelper;
import hcmute.edu.vn.mssv18110361.model.CartItem;
import hcmute.edu.vn.mssv18110361.model.Product;

public class CartItemDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "CartItem";
    public CartItemDAO(Context context) {
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

    public void addCartItem(CartItem item) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("cart_id", item.get_cart_id());
        values.put("product_id", item.get_product_id());
       values.put("quantity" , item.get_quantity());
        db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public CartItem getExistCartItem(int product_id){
        openToRead();
        String query = "SELECT id , cart_id , product_id ,  quantity FROM "+ TABLE_NAME + " WHERE product_id = " + product_id ;
        Cursor cursor = db.rawQuery(query, null);
        CartItem item = null;
        while(cursor.moveToFirst()) {
            item =  new CartItem();
            item.set_id(cursor.getInt(0));
            item.set_cart_id(cursor.getInt(1));
            item.set_product_id(cursor.getInt(2));
            item.set_quantity(cursor.getInt(3));
            break;
        }
        close();
        return item;
    }



    public void updateCartItem(int id , int quantity) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("quantity" , quantity);
        db.update(TABLE_NAME, values, " id =" + id , null);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public boolean deleteCartItem(int id) {
        openToWrite();
        if(db.delete(TABLE_NAME, "id" + "=" + id , null) > 0){
            close();
            return true;
        };
        close();
        return false;
    }

    public List<CartItem> getAllCartItem(int cart_id) {
        openToRead();
        String query = "SELECT id, cart_id, product_id , quantity FROM " + TABLE_NAME + " WHERE cart_id = " + cart_id;
        Cursor cursor = db.rawQuery(query, null);
        List<CartItem> list = new ArrayList<CartItem>();
        while (cursor.moveToNext()) {
            CartItem item = new CartItem();
            item.set_id(cursor.getInt(0));
            item.set_cart_id(cursor.getInt(1));
            item.set_product_id(cursor.getInt(2));
            item.set_quantity(cursor.getInt(3));
            list.add(item);
        }
        close();
        return list;
    }
}
