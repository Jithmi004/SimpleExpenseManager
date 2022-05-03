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

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.R;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO extends DatabaseHelper implements TransactionDAO {

    public PersistentTransactionDAO(Context context) {
        super(context);
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = formatter.format(date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_DATE,strDate);
        cv.put(Constants.COLUMN_ACCOUNT_NO,accountNo);
        cv.put(Constants.COLUMN_EXPENSE_TYPE,expenseType.toString());
        cv.put(Constants.COLUMN_AMOUNT,amount);

        long insert = db.insert(Constants.TRANSACTION_TABLE,null,cv);
        //db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM "+ Constants.TRANSACTION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                String dateString = cursor.getString(1);
                String accountNo = cursor.getString(2);
                String expenseTypeString = cursor.getString(3);
                Double amount = cursor.getDouble(4);

                ExpenseType expenseType = ExpenseType.valueOf(expenseTypeString);
                Date date = new Date();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    date = sdf.parse(dateString);
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
        String query = "SELECT * FROM (SELECT * FROM " +Constants.TRANSACTION_TABLE+" ORDER BY " +Constants.COLUMN_TRANSACTION_ID +" DESC LIMIT " + limit +" ) ORDER BY " +Constants.COLUMN_TRANSACTION_ID+" ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                String dateString = cursor.getString(1);
                String accountNo = cursor.getString(2);
                String expenseTypeString = cursor.getString(3);
                Double amount = cursor.getDouble(4);

                ExpenseType expenseType = ExpenseType.valueOf(expenseTypeString);
                Date date = new Date();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
}
