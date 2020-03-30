package com.example.wikitea.Tables.Admin;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wikitea.BaseApp;
import com.example.wikitea.Tables.Admin.Admin;
import com.example.wikitea.Tables.Admin.AdminDao;
import com.example.wikitea.Tables.Admin.AdminDatabase;

import java.util.List;

public class AdminRepository {

    private AdminDao adminDao;
    private LiveData<List<Admin>> allAdmins;
    private static AdminRepository instance;

    //Constructor
    public AdminRepository(Application application){

        AdminDatabase database = AdminDatabase.getInstance(application);
        //adminDao = database.admindao();
        allAdmins = adminDao.getAllAdmins();
    }


    private AdminRepository()
    {
    }

    public static AdminRepository getInstance() {
        if (instance == null) {
            synchronized (AdminRepository.class) {
                if (instance == null) {
                    instance = new AdminRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Admin> getAdmin(final String adminId, Application application) {
        return ((BaseApp) application).getDatabase().adminDao().getById(adminId);
    }




    //Unused method to modify,delete admins

    public void insert(Admin admin){

        new InsertAdminAsyncTask(adminDao).execute(admin);

    }

    public void update(Admin admin){

        new UpdateAdminAsyncTask(adminDao).execute(admin);

    }

    public void delete(Admin admin){

        new DeleteAdminAsyncTask(adminDao).execute(admin);

    }

    public LiveData<List<Admin>> getAllAdmins(){

        return allAdmins;
    }

    private static class InsertAdminAsyncTask extends AsyncTask<Admin, Void, Void>{

        private AdminDao adminDao;
        private InsertAdminAsyncTask(AdminDao adminDao){
            this.adminDao = adminDao;
        }

        @Override
        protected Void doInBackground(Admin... admins) {

            adminDao.insert(admins[0]);

            return null;
        }
    }


    private static class UpdateAdminAsyncTask extends AsyncTask<Admin, Void, Void>{

        private AdminDao adminDao;

        private UpdateAdminAsyncTask(AdminDao adminDao){
            this.adminDao = adminDao;
        }

        @Override
        protected Void doInBackground(Admin... admins) {

            adminDao.update(admins[0]);

            return null;

        }
    }

    private static class DeleteAdminAsyncTask extends AsyncTask<Admin, Void, Void>{

        private AdminDao adminDao;

        private DeleteAdminAsyncTask(AdminDao adminDao){
                this.adminDao = adminDao;
        }

        @Override
        protected Void doInBackground(Admin... admins) {

            adminDao.delete(admins[0]);

            return null;

        }
    }

}
