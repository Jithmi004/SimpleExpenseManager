package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/*This is a persistent implementation of the AccountDAO interface*/

public class PersistentAccountDAO extends DatabaseHelper implements AccountDAO {

    public PersistentAccountDAO(Context context) {
        super(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> accountNumbersList = new ArrayList<>();
        String query = "SELECT " + Constants.COLUMN_ACCOUNT_NO + " FROM " + Constants.ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {

                String accountNO = cursor.getString(0);
                accountNumbersList.add(accountNO);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountNumbersList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM " + Constants.ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                String accountNO = cursor.getString(0);
                String bank = cursor.getString(1);
                String accountHolder = cursor.getString(2);
                Double balance = cursor.getDouble(3);

                Account newAccount = new Account(accountNO,bank,accountHolder,balance);
                accountList.add(newAccount);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String query = "SELECT * FROM " + Constants.ACCOUNT_TABLE + " WHERE " + Constants.COLUMN_ACCOUNT_NO + " = " + "'"+accountNo+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        Account account;

        if (cursor.moveToFirst()){
            String accountNO = cursor.getString(0);
            String bank = cursor.getString(1);
            String accountHolder = cursor.getString(2);
            Double balance = cursor.getDouble(3);

            account = new Account(accountNO,bank,accountHolder,balance);
        }else {
            account = null;
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);

        }
        cursor.close();
        db.close();
        return account;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_ACCOUNT_NO,account.getAccountNo());
        cv.put(Constants.COLUMN_BANK_NAME,account.getBankName());
        cv.put(Constants.COLUMN_ACCOUNT_HOLDER,account.getAccountHolderName());
        cv.put(Constants.COLUMN_BALANCE,account.getBalance());

        long insert = db.insert(Constants.ACCOUNT_TABLE,null,cv);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.ACCOUNT_TABLE, Constants.COLUMN_ACCOUNT_NO + "=" + "'"+accountNo+"'", null);
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        try {
            Account account = getAccount(accountNo);
            double balance = account.getBalance();
            if (expenseType == ExpenseType.EXPENSE){
                balance-=amount;
            }else {
                balance+=amount;
            }
            String query = "UPDATE " + Constants.ACCOUNT_TABLE + " SET " + Constants.COLUMN_BALANCE + "="+ balance + " WHERE " + Constants.COLUMN_ACCOUNT_NO + " = " + "'"+accountNo+"'";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query);
            db.close();
        }catch (InvalidAccountException e){
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);

        }

    }
}
