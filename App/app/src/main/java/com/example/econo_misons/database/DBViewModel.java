package com.example.econo_misons.database;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.econo_misons.database.models.Budget;
import com.example.econo_misons.database.models.Category;
import com.example.econo_misons.database.models.User;
import com.example.econo_misons.database.repositories.BudgetDataRepository;
import com.example.econo_misons.database.repositories.CategoryDataRepository;
import com.example.econo_misons.database.repositories.UserDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class DBViewModel extends ViewModel {

    /*TODO: Rework a bit the database:
            -Link Budget and previsional budget ?
            -Add a color to category ?
            -Add a prev column to Transaction ?
            -Add a recurrent column to transaction ?
      TODO: Add the methods (on split DAOs and Repositories):
            -addCategory(Budget budget, String name)
            -getCatList(User user, Budget bud, String Prev_Date) return Cat list with name and sum
            -getCatExp(Category cat)
            -addExp(User user, Budget bud, Category cat, String name, float amount, bool exp, String date)
            -editExp(Transaction exp)
            -deleteExp(Transaction exp)
    */

    private final UserDataRepository userDataSource;
    private final BudgetDataRepository budgetDataSource;
    private final CategoryDataRepository categoryDataSource;

    private final Executor executor;

    @Nullable
    private LiveData<User> currentUser;

    public  DBViewModel(UserDataRepository userDataSource, BudgetDataRepository budgetDataSource, CategoryDataRepository categoryDataSource,Executor executor){
        this.userDataSource = userDataSource;
        this.budgetDataSource = budgetDataSource;
        this.categoryDataSource = categoryDataSource;
        this.executor = executor;
    }

    //USER

    public LiveData<List<User>> getAllUsers() {return userDataSource.getAllUsers();}

    public void newUser(User user) {
        executor.execute(() -> userDataSource.newUser(user));
    }

    public void updateUser(User user) {
        executor.execute(() -> userDataSource.updateUser(user));
    }

    public void deleteUser(User user) {
        executor.execute(() -> userDataSource.deleteUser(user));
    }

    public LiveData<List<User>> getUser(int id){ return userDataSource.getUser(id);}

    //BUDGET

    public void addBudget(Budget budget, User user){ executor.execute(() -> budgetDataSource.addBudget(budget, user));}

    public LiveData<List<Budget>> getAllBudgets(){ return budgetDataSource.getAllBudgets();}

    public LiveData<List<Budget>> getUserBudgets(int userID){ return budgetDataSource.getUserBudgets(userID);}

    public void addUserToBudget(Budget budget, User user){
        executor.execute(() -> budgetDataSource.addUserToBudget(budget, user));
    }

    //CATEGORY

    public void addCategory(Category cat){executor.execute(() -> categoryDataSource.addCategory(cat));}

    public void updateCategory(Category cat){executor.execute(() -> categoryDataSource.updateCategory(cat));}

    public void deleteCategory(Category cat){executor.execute(() -> categoryDataSource.deleteCategory(cat));}

    public LiveData<List<Category>> getAllCategories(){return categoryDataSource.getAllCategories();}

    public LiveData<List<Category>> getCategoryByID(int id){return categoryDataSource.getCategoryByID(id);}
}
