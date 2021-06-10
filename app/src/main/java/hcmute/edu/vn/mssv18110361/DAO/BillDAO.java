package hcmute.edu.vn.mssv18110361.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import hcmute.edu.vn.mssv18110361.DatabaseHelper;
import hcmute.edu.vn.mssv18110361.model.Bill;

public class BillDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "Bill";
    public BillDAO(Context context) {
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

    public int addBillAndReturnId(Bill Bill) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("user_id", Bill.get_user_id());
        values.put("date", Bill.get_date().toString());
        values.put("total", Bill.get_total());
        values.put("discount", Bill.get_discount());
        long id = db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
        return (int)id;
    }



    public boolean deleteBill(int id) {
        openToWrite();
        if(db.delete(TABLE_NAME, "id" + "=" + id , null) > 0){
            close();
            return true;
        };
        close();
        return false;
    }

    public int countBillById(int user_id) {
        openToRead();
        String query = "SELECT COUNT(id) FROM "+ TABLE_NAME + " WHERE user_id  = " + user_id;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while(cursor.moveToFirst()) {
           count = cursor.getInt(0);
           break;
        }
        close();
        return count;
    }


    public List<Integer> getListBillId(int user_id) {
        openToRead();
        String query = "SELECT id FROM "+ TABLE_NAME + " WHERE user_id  = " + user_id;
        Cursor cursor = db.rawQuery(query, null);
        List<Integer> list = new ArrayList<Integer>();
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            list.add(id);
        }
        close();
        return list;
    }

    public Bill get(int id) {
        openToRead();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        Bill bill = null;
        while(cursor.moveToFirst()) {
            bill = new Bill();
            bill.set_id(cursor.getInt(0));
            bill.set_user_id(cursor.getInt(1));
//            Bill.set_date(cursor.getInt(2));
            bill.set_total(cursor.getInt(3));
            bill.set_discount(cursor.getInt(4));
            break;
        }
        close();
        return bill;
    }

    public String getDate(int id){
        openToRead();
        String query = "SELECT date FROM "+ TABLE_NAME + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        String date = "";
        try {
            while(cursor.moveToFirst()) {
                date = cursor.getString(0);
                break;
            }
        }finally {
            cursor.close();
        }
        close();
        return date;
    }
}
