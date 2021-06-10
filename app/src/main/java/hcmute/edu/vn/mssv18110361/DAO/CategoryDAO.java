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
import hcmute.edu.vn.mssv18110361.model.Category;

public class CategoryDAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "Category";
    public CategoryDAO(Context context) {
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

    public void addCategory(Category category) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("name", category.get_name());
        values.put("image", category.get_image());
        db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public boolean deleteCategory(int id) {
        openToWrite();
        if(db.delete(TABLE_NAME, "id" + "=" + id , null) > 0){
            close();
            return true;
        };
        close();
        return false;
    }

    public void updateCategory(Category category) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("name", category.get_name());
        values.put("image", category.get_image());
        db.update(TABLE_NAME, values , "id = " + category.get_id() , null);
        close();
        Log.d("Updated!!", "Updated to DB");
    }

    public Category get(int id) {
        openToRead();
        String query = "SELECT id, name , image FROM "+ TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        Category category = new Category();
        try {
            while(cursor.moveToFirst()) {
                category.set_id(cursor.getInt(0));
                category.set_image(cursor.getBlob(2));
                category.set_name(cursor.getString(1));
                break;
            }
        }finally {
            cursor.close();
        }
        close();
        return category;
    }

    public List<Category> getAllCategory() {
        openToRead();
        String query = "SELECT id, name , image FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        List<Category> list = new ArrayList<Category>();
        try {
            while(cursor.moveToNext()) {
                Category category = new Category();
                category.set_id(cursor.getInt(0));
                category.set_name(cursor.getString(1));
                category.set_image(cursor.getBlob(2));
                list.add(category);
            }
        }finally {
            cursor.close();
        }
        close();
        return list;
    }

}
