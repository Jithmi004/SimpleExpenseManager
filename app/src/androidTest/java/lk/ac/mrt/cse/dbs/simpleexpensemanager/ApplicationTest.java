/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class ApplicationTest {
    private ExpenseManager expenseManager;
    @Before
    public void setUpBeforeTests() throws ExpenseManagerException {
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }
    @Test
    public void addAccountTest(){
        expenseManager.addAccount("56864rt","NDB","Nawoda",100000);
        assertTrue(expenseManager.getAccountNumbersList().contains("56864rt"));
    }
    @Test
    public void addTransactionTest() throws InvalidAccountException {
        int transactionCount = expenseManager.getTransactionsDAO().getAllTransactionLogs().size();
        /*for the account number, put the same account number used in addAccountTest. Otherwise the testcase may not pass
        * since the account that you try to update the balance won't exist on the system
        * */
        expenseManager.updateAccountBalance("56864rt",10,11,2022, ExpenseType.INCOME,"201");
        int newTransactionCount = expenseManager.getTransactionsDAO().getAllTransactionLogs().size();
        assertTrue(transactionCount+1==newTransactionCount);
    }
}
