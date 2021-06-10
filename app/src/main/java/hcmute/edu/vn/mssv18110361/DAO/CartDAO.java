package hcmute.edu.vn.mssv18110361.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import hcmute.edu.vn.mssv18110361.DatabaseHelper;
import hcmute.edu.vn.mssv18110361.model.Cart;

public class CartDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "Cart";
    public CartDAO(Context context) {
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

    public void addCart(Cart cart) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("user_id", cart.get_user_id());
        db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public boolean deleteCart(int id) {
        openToWrite();
        if(db.delete(TABLE_NAME, "id" + "=" + id , null) > 0){
            close();
            return true;
        };
        close();
        return false;
    }

    public Cart get(int user_id) {
        openToRead();
        String query = "SELECT id, user_id FROM "+ TABLE_NAME + " WHERE user_id = " + user_id;
        Cursor cursor = db.rawQuery(query, null);
        Cart cart = null;
        while(cursor.moveToFirst()) {
            cart = new Cart();
            cart.set_id(cursor.getInt(0));
            cart.set_user_id(cursor.getInt(1));
            break;
        }
        close();
        return cart;
    }
}
