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
import hcmute.edu.vn.mssv18110361.model.Cart;
import hcmute.edu.vn.mssv18110361.model.User;

public class UserDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "User";
    public UserDAO(Context context) {
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

    //region USER
    public void addUser(User user) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("username", user.get_username());
        values.put("password", user.get_password());
        values.put("name", user.get_name());
        values.put("phone_number", user.get_phone_number());
        values.put("image", user.get_image());
        values.put("email", user.get_email());
        values.put("gender", user.get_gender());
        values.put("address", user.get_address());
        values.put("active", user.get_active());
        values.put("type", user.get_type());
        db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public void updateUser(User user) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("name", user.get_name());
        values.put("phone_number", user.get_phone_number());
        values.put("image", user.get_image());
        values.put("email", user.get_email());
        values.put("gender", user.get_gender());
        values.put("address", user.get_address());
        db.update(TABLE_NAME, values, " id= " + user.get_id(), null);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public void changePassword(int user_id , String newPassword) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        db.update(TABLE_NAME, values, " id= " + user_id, null);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public boolean isAccountExistInDB(String username , String password){
        openToRead();
        String query = "select username , password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query , null);
        String u_n, p_w = "";
        if(cursor.moveToFirst()){
            do {
                u_n = cursor.getString(0);
                p_w = cursor.getString(1);
                if(u_n.equals(username) && p_w.equals(password)){
                    Log.d("Check" , u_n + "   " + p_w);
                    close();
                    return true;
                }
            }while(cursor.moveToNext());
        }
        close();
        return false;
    }

    public boolean isAccountExistInDB(String username){
        openToRead();
        String query = "select username  from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query , null);
        String u_n;
        if(cursor.moveToFirst()){
            do {
                u_n = cursor.getString(0);
                if(u_n.equals(username)){
                    Log.d("Check" , u_n);
                    close();
                    return true;
                }
            }while(cursor.moveToNext());
        }
        close();
        return false;
    }
    public List<User> getAllUser() {
        openToRead();
        String query = "SELECT id, name , username , password , phone_number , image , gender , address ,active ,type FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        List<User> list = new ArrayList<User>();
        while(cursor.moveToNext()) {
            User user = new User();
            user.set_id(cursor.getInt(0));
            user.set_name(cursor.getString(1));
            user.set_username(cursor.getString(2));
            user.set_password(cursor.getString(3));
            user.set_phone_number(cursor.getString(4));
            user.set_image(cursor.getBlob(5));
            user.set_gender(cursor.getString(6));
            user.set_address(cursor.getString(7));
            user.set_active(cursor.getInt(8));
            user.set_type(cursor.getInt(9));
            list.add(user);
        }
        close();
        return list;
    }

    public User get(int id){
        openToRead();
        String query = "SELECT id, name , username , password , phone_number , image , gender , email , address ,active ,type FROM "+ TABLE_NAME + " WHERE id = " +id;
        Cursor cursor = db.rawQuery(query, null);
        User user = null;
        while(cursor.moveToFirst()) {
            user = new User();
            user.set_id(cursor.getInt(0));
            user.set_name(cursor.getString(1));
            user.set_username(cursor.getString(2));
            user.set_password(cursor.getString(3));
            user.set_phone_number(cursor.getString(4));
            user.set_image(cursor.getBlob(5));
            user.set_gender(cursor.getString(6));
            user.set_email(cursor.getString(7));
            user.set_address(cursor.getString(8));
            user.set_active(cursor.getInt(9));
            user.set_type(cursor.getInt(10));
            break;
        }
        close();
        return user;
    }

    public int getIdByUserName(String  username) {
        openToRead();
        String query = "SELECT id FROM "+ TABLE_NAME + " WHERE username = ?" ;
        Cursor cursor = db.rawQuery(query, new String[] {username});
        int id = -1;
        while(cursor.moveToFirst()) {
            id = cursor.getInt(0);
            break;
        }
        close();
        return id;
    }
    //endregion USER
}
