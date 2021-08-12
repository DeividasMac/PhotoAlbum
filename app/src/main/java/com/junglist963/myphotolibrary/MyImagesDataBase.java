package com.junglist963.myphotolibrary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MyImages.class, version = 1)
public abstract class MyImagesDataBase extends RoomDatabase {

    private static MyImagesDataBase instance;
    public abstract MyImageDAO myImageDAO();

    public static synchronized MyImagesDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    ,MyImagesDataBase.class, "my_images_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
