package com.junglist963.myphotolibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyImagesViewModel myImagesViewModel;
    private RecyclerView rv;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);

        MyImagesAdapter adapter = new MyImagesAdapter();
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        myImagesViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(MyImagesViewModel.class);
        myImagesViewModel.getAllImages().observe(MainActivity.this, new Observer<List<MyImages>>() {
            @Override
            public void onChanged(List<MyImages> myImages) {
                adapter.setMyImages(myImages);

            }
        });

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, AddImageActivity.class);
               startActivityForResult(intent, 3);
           }
       });
       new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
           @Override
           public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
               return false;
           }

           @Override
           public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {

               myImagesViewModel.delete(adapter.getPosition(viewHolder.getAdapterPosition()));

           }
       }).attachToRecyclerView(rv);

       adapter.setListener(new MyImagesAdapter.onImageClickListener() {
           @Override
           public void onImageClicked(MyImages myImages) {
               Intent intent = new Intent(MainActivity.this, UpdateImageActivity.class);
               intent.putExtra("id", myImages.getImage_id());
               intent.putExtra("title",myImages.getImage_title());
               intent.putExtra("description",myImages.getImage_description());
               intent.putExtra("image", myImages.getImage());
               startActivityForResult(intent, 4);
           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == RESULT_OK && data != null){
        String title = data.getStringExtra("title");
        String description = data.getStringExtra("description");
        byte[] image = data.getByteArrayExtra("image");

        MyImages myImages = new MyImages(title, description, image);
        myImagesViewModel.insert(myImages);
        }

        if (requestCode == 4 && resultCode == RESULT_OK && data != null){
            String title = data.getStringExtra("updateTitle");
            String description = data.getStringExtra("updateDescription");
            byte[] image = data.getByteArrayExtra("image");
            int id = data.getIntExtra("id", -1);

            MyImages myImages = new MyImages(title, description, image);
            myImages.setImage_id(id);
            myImagesViewModel.update(myImages);
        }
    }
}