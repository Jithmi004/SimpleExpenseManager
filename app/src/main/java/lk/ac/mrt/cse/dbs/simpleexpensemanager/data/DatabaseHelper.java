package lk.ac.mrt.cse.dbs.simpleexpensemanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "190496G.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement1 = "CREATE TABLE " + Constants.ACCOUNT_TABLE + " (" + Constants.COLUMN_ACCOUNT_NO + " TEXT PRIMARY KEY, " + Constants.COLUMN_BANK_NAME + " TEXT, " + Constants.COLUMN_ACCOUNT_HOLDER + " TEXT, " + Constants.COLUMN_BALANCE + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement1);
        String createTableStatement2 = "CREATE TABLE " + Constants.TRANSACTION_TABLE + " (" + Constants.COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.COLUMN_DATE + " TEXT, " + Constants.COLUMN_ACCOUNT_NO + " TEXT REFERENCES " +Constants.ACCOUNT_TABLE +"("+Constants.COLUMN_ACCOUNT_NO +"), " + Constants.COLUMN_EXPENSE_TYPE + " TEXT, " + Constants.COLUMN_AMOUNT + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
