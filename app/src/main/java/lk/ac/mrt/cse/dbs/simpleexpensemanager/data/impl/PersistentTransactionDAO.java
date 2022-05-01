package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.R;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO extends SQLiteOpenHelper implements TransactionDAO {
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

    public PersistentTransactionDAO(@Nullable Context context) {
        super(context, "190496G.db", null, 1);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = formatter.format(date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_DATE,date.toString());
        cv.put(COLUMN_DATE,strDate);
        cv.put(COLUMN_ACCOUNT_NO,accountNo);
        cv.put(COLUMN_EXPENSE_TYPE,expenseType.toString());
        cv.put(COLUMN_AMOUNT,amount);

        long insert = db.insert(TRANSACTION_TABLE,null,cv);
        //db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM "+ TRANSACTION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                //int transactionID = cursor.getInt(0);
                String dateString = cursor.getString(1);
                String accountNo = cursor.getString(2);
                String expenseTypeString = cursor.getString(3);
                Double amount = cursor.getDouble(4);

                ExpenseType expenseType = ExpenseType.valueOf(expenseTypeString);
                //Date date = null;
                Date date = new Date();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    //String formattedDate = sdf.format(transaction.getDate());
                    date = sdf.parse(dateString);
                    //date = dateString.
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction newTransaction = new Transaction(date,accountNo,expenseType,amount);
                transactionList.add(newTransaction);
            }while (cursor.moveToNext());
        }
        return transactionList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> paginatedTransactionList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT * FROM " +TRANSACTION_TABLE+" ORDER BY " +COLUMN_TRANSACTION_ID +" DESC LIMIT " + limit +" ) ORDER BY " +COLUMN_TRANSACTION_ID+" ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                int transactionID = cursor.getInt(0);
                String dateString = cursor.getString(1);
                String accountNo = cursor.getString(2);
                String expenseTypeString = cursor.getString(3);
                Double amount = cursor.getDouble(4);

                ExpenseType expenseType = ExpenseType.valueOf(expenseTypeString);
                //Date date = null;
                Date date = new Date();
                try {
                    //date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    //String formattedDate = sdf.format(transaction.getDate());
                    date = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Transaction newTransaction = new Transaction(date,accountNo,expenseType,amount);
                paginatedTransactionList.add(newTransaction);
            }while (cursor.moveToNext());
        }
        return paginatedTransactionList;
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
