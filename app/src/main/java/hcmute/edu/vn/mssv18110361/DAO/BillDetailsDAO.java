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
import hcmute.edu.vn.mssv18110361.model.Bill;
import hcmute.edu.vn.mssv18110361.model.BillDetails;

public class BillDetailsDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private String TABLE_NAME = "BillDetails";
    public BillDetailsDAO(Context context) {
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

    public void addBillDetails(BillDetails bdt) {
        openToWrite();
        ContentValues values = new ContentValues();
        values.put("bill_id", bdt.get_bill_id());
        values.put("product_id", bdt.get_product_id());
        values.put("quantity", bdt.get_quantity());
        db.insert(TABLE_NAME, null, values);
        close();
        Log.d("Saved!!", "Saved to DB");
    }

    public int countBillDetailsById(int bill_id) {
        openToRead();
        String query = "SELECT SUM(quantity)  FROM "+ TABLE_NAME + " WHERE bill_id  = " + bill_id;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while(cursor.moveToFirst()) {
            count = cursor.getInt(0);
            break;
        }
        close();
        return count;
    }

    public List<BillDetails> getAllBillDetails(int bill_id) {
        openToRead();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE bill_id  = " + bill_id;
        Cursor cursor = db.rawQuery(query, null);
        List<BillDetails> list = new ArrayList<>();
        try {
        while(cursor.moveToNext()) {
            BillDetails bdt = new BillDetails();
            bdt.set_id(cursor.getInt(0));
            bdt.set_bill_id(cursor.getInt(1));
            bdt.set_product_id(cursor.getInt(2));
            bdt.set_quantity(cursor.getInt(3));
            list.add(bdt);
        }
        } finally {
            cursor.close();
        }
        close();
        return list;
    }
}
