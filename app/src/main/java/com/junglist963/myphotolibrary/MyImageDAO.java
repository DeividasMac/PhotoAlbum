package com.junglist963.myphotolibrary;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyImageDAO {

    @Insert
    void Insert(MyImages myImages);

    @Delete
    void Delete(MyImages myImages);

    @Update
    void Update(MyImages myImages);

    @Query("Select * FROM my_images ORDER BY image_id ASC ")
    LiveData<List<MyImages>>getAllImages();
}
