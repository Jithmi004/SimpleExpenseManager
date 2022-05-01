package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentExpenseManager extends ExpenseManager{
    private static Context mycontext;

    public PersistentExpenseManager(Context context){
        //super(context);
        this.mycontext = context;
        //System.out.println(context.toString() + "JIMIYA");
    }
    @Override
    public void setup() throws ExpenseManagerException {
        //TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(getMycontext());
        //setTransactionsDAO(persistentTransactionDAO);

        //AccountDAO persistentAccountDAO = new PersistentAccountDAO(getMycontext());
        //setAccountsDAO(persistentAccountDAO);
    }
    public static Context getMycontext(){
        return mycontext;
    }
}
