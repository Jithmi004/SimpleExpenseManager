package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String COLUMN_TRANSACTION_ID = "TRANSACTION_ID";
    public static final String COLUMN_DATE = "DATE";
    public static final String COLUMN_ACCOUNT_NO = "ACCOUNT_NO";
    public static final String COLUMN_EXPENSE_TYPE = "EXPENSE_TYPE";
    public static final String COLUMN_AMOUNT = "AMOUNT";

    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_ID = "ID";
    //public static final String COLUMN_ACCOUNT_NO = "ACCOUNT_NO";
    public static final String COLUMN_BANK_NAME = "BANK_NAME";
    public static final String COLUMN_ACCOUNT_HOLDER = "ACCOUNT_HOLDER";
    public static final String COLUMN_BALANCE = "BALANCE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "190496G.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TRANSACTION_TABLE + " (" + COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_ACCOUNT_NO + " TEXT, " + COLUMN_EXPENSE_TYPE + " TEXT, " + COLUMN_AMOUNT + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement);
        String createTableStatement2 = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NO + " TEXT, " + COLUMN_BANK_NAME + " TEXT, " + COLUMN_ACCOUNT_HOLDER + " TEXT, " + COLUMN_BALANCE + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
