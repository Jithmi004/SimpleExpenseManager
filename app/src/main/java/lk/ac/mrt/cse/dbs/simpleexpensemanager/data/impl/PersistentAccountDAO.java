package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

/*This is a persistent implementation of the AccountDAO interface*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO extends SQLiteOpenHelper implements AccountDAO {
    //MATCHING CONSTRUCTOR -JNR
    public PersistentAccountDAO(@Nullable Context context) {
        super(context, "190496G.db", null, 1);
    }

    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ACCOUNT_NO = "ACCOUNT_NO";
    public static final String COLUMN_BANK_NAME = "BANK_NAME";
    public static final String COLUMN_ACCOUNT_HOLDER = "ACCOUNT_HOLDER";
    public static final String COLUMN_BALANCE = "BALANCE";
    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumbersList = new ArrayList<>();
        String query = "SELECT * FROM " + ACCOUNT_TABLE; //change this later
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                String accountNO = cursor.getString(1);
                accountNumbersList.add(accountNO);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountNumbersList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM " + ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                int accountID = cursor.getInt(0);
                String accountNO = cursor.getString(1);
                String bank = cursor.getString(2);
                String accountHolder = cursor.getString(3);
                Double balance = cursor.getDouble(4);

                Account newAccount = new Account(accountNO,bank,accountHolder,balance);
                accounts.add(newAccount);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String query = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE " + COLUMN_ACCOUNT_NO + " = " + accountNo;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        Account account;

        if (cursor.moveToFirst()){
            do {
                int accountID = cursor.getInt(0);
                String accountNO = cursor.getString(1);
                String bank = cursor.getString(2);
                String accountHolder = cursor.getString(3);
                Double balance = cursor.getDouble(4);

                account = new Account(accountNO,bank,accountHolder,balance);
            }while (cursor.moveToNext());
        }else {
            account = null;
        }
        cursor.close();
        db.close();
        return account;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ACCOUNT_NO,account.getAccountNo());
        cv.put(COLUMN_BANK_NAME,account.getBankName());
        cv.put(COLUMN_ACCOUNT_HOLDER,account.getAccountHolderName());
        cv.put(COLUMN_BALANCE,account.getBalance());

        long insert = db.insert(ACCOUNT_TABLE,null,cv);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCOUNT_TABLE, COLUMN_ACCOUNT_NO + "=" + accountNo, null);
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        double balance = getAccount(accountNo).getBalance();
        if (expenseType == ExpenseType.EXPENSE){
            balance-=amount;
        }else {
            balance+=amount;
        }
        String query = "UPDATE " + ACCOUNT_TABLE + " SET " + COLUMN_BALANCE + "="+ balance + " WHERE " + COLUMN_ACCOUNT_NO + "=" +accountNo;
        db.execSQL(query);
    }

    //overriding methods of sqlitehelper superclass - JNR
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + ACCOUNT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ACCOUNT_NO + " TEXT, " + COLUMN_BANK_NAME + " TEXT, " + COLUMN_ACCOUNT_HOLDER + " TEXT, " + COLUMN_BALANCE + " REAL)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
