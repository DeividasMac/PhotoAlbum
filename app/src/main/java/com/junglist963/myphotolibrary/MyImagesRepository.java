package com.junglist963.myphotolibrary;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyImagesRepository {

    private MyImageDAO myImageDAO;
    private LiveData<List<MyImages>> imagesList;

    public MyImagesRepository(Application application){
        MyImagesDataBase dataBase = MyImagesDataBase.getInstance(application);
        myImageDAO = dataBase.myImageDAO();
        imagesList = myImageDAO.getAllImages();
    }
    public void insert(MyImages myImages){
        new InsertImageAsyncTask(myImageDAO).execute(myImages);
    }
    public void delete(MyImages myImages){
        new DeleteImageAsyncTask(myImageDAO).execute(myImages);
    }
    public void update(MyImages myImages){
        new UpdateImageAsyncTask(myImageDAO).execute(myImages);
    }
    public LiveData<List<MyImages>>getAllImages(){
        return imagesList;
    }

    public static class InsertImageAsyncTask extends AsyncTask<MyImages, Void, Void>{

        MyImageDAO imagesDAO;

        public InsertImageAsyncTask(MyImageDAO myImageDAO) {
            this.imagesDAO = myImageDAO;
        }

        @Override
        protected Void doInBackground(MyImages... myImages) {

            imagesDAO.Insert(myImages[0]);
            return null;
        }
    }
    public static class DeleteImageAsyncTask extends AsyncTask<MyImages, Void, Void>{

        MyImageDAO imagesDAO;

        public DeleteImageAsyncTask(MyImageDAO myImageDAO) {
            this.imagesDAO = myImageDAO;
        }

        @Override
        protected Void doInBackground(MyImages... myImages) {

            imagesDAO.Delete(myImages[0]);
            return null;
        }
    }
    public static class UpdateImageAsyncTask extends AsyncTask<MyImages, Void, Void>{

        MyImageDAO imagesDAO;

        public UpdateImageAsyncTask(MyImageDAO myImageDAO) {
            this.imagesDAO = myImageDAO;
        }

        @Override
        protected Void doInBackground(MyImages... myImages) {

            imagesDAO.Update(myImages[0]);
            return null;
        }
    }
}
