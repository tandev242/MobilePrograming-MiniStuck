package hcmute.edu.vn.mssv18110361;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final static String TAG = "DatabaseHelper";
    private final Context myContext;
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 3;

    private String[] TABLE_NAME = {"User" , "Product" , "Bill" , "BillDetails" , "Category", "Cart" , "CartItem"};

    private static final String CREATE_TABLE_USER = "CREATE TABLE User ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT , name TEXT, phone_number TEXT , image BLOB , email TEXT not null UNIQUE,gender TEXT, address TEXT, active INTEGER default 0 not null, type INTEGER default 0 not null);";
    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE Product ( id INTEGER PRIMARY KEY AUTOINCREMENT,category_id INTEGER , name TEXT, image BLOB not null, date_of_manufacture TEXT default CURRENT_TIMESTAMP , expiration_date TEXT default CURRENT_TIMESTAMP , price INTEGER not null);";
    private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE Category ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT UNIQUE , image BLOB not null);";
    private static final String CREATE_TABLE_CART = "CREATE TABLE Cart ( id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER);";
    private static final String CREATE_TABLE_CART_ITEM = "CREATE TABLE CartItem ( id INTEGER PRIMARY KEY AUTOINCREMENT, cart_id INTEGER , product_Id INTEGER ,quantity INTEGER);";
    private static final String CREATE_TABLE_BILL = "CREATE TABLE Bill ( id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER , date TEXT default CURRENT_TIMESTAMP , total INTEGER , discount INTEGER );";
    private static final String CREATE_TABLE_BILL_DETAILS = "CREATE TABLE BillDetails ( id INTEGER PRIMARY KEY AUTOINCREMENT, bill_id INTEGER , product_id INTEGER , quantity INTEGER );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BILL);
        db.execSQL(CREATE_TABLE_BILL_DETAILS);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_CART_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        for(int i = 0 ; i < TABLE_NAME.length ; i++)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME[i]);
        }
        onCreate(db);
    }
}

