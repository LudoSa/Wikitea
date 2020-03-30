package com.example.wikitea.Tables.Admin;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wikitea.Tables.Tea.TeaDatabase;

public class AdminRepository {

    private AdminDao adminDao;
    private LiveData<Admin> adminByName;
    String name;


    //Constructor
    public AdminRepository(Application application){

        TeaDatabase database = TeaDatabase.getInstance(application);
        adminDao = database.adminDao();
        adminByName = adminDao.getAdminByName(name);
    }


    public LiveData<Admin> getAdminByName(String adminName)
    {
        return adminDao.getAdminByName(adminName);
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
