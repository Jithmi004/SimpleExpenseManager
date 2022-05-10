package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentExpenseManager extends ExpenseManager{
    private Context mycontext;

    public PersistentExpenseManager(Context context) throws ExpenseManagerException {
        this.mycontext = context;
        setup();
    }
    @Override
    public void setup() throws ExpenseManagerException {
        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(mycontext);
        setTransactionsDAO(persistentTransactionDAO);

        AccountDAO persistentAccountDAO = new PersistentAccountDAO(mycontext);
        setAccountsDAO(persistentAccountDAO);

        //adding dummy data
        Account dummyAccount1 = new Account("1000q","HDFC","Jane Doe",100000);
        Account dummyAccount2 = new Account("2000r","NDB","John Doe",35000);

        getAccountsDAO().addAccount(dummyAccount1);
        getAccountsDAO().addAccount(dummyAccount2);
    }

}
