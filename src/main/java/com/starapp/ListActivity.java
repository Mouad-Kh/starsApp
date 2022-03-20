package com.starapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.starapp.adapter.StarAdapter;
import com.starapp.beans.Star;
import com.starapp.service.StarService;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private StarService service;
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private static final String TAG = "StarAdapter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void init(){
        service.create(new Star("Lionel Messi", "https://image.shutterstock.com/image-photo/barcelona-feb-23-lionel-messi-600w-1900547713.jpg", 3.5f));
        service.create(new Star("Cristiano Ronaldo", "https://image.shutterstock.com/image-photo/25062018-saransk-russiancristiano-ronaldo-before-600w-1121013440.jpg", 3));
        service.create(new Star("Harry Kane", "https://image.shutterstock.com/image-photo/03072018-moscow-russiaharry-kane-action-600w-1126998179.jpg", 5));
        service.create(new Star("Mohamed Salah ", "https://image.shutterstock.com/image-photo/st-petersburg-russia-june-19-600w-1116584570.jpg", 1));
        service.create(new Star("Karim Benzema","https://image.shutterstock.com/image-photo/moscow-russia-02-octember-2018-600w-1223665645.jpg",3));
        service.create(new Star("Luis Suarez","https://image.shutterstock.com/image-photo/piraeus-greece-october-31-2017-600w-750375331.jpg",1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);


        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (starAdapter != null) {

                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String txt = "Stars";
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder.from(this).setType(mimeType).setChooserTitle("Stars").setText(txt).startChooser();
        }
        return super.onOptionsItemSelected(item);
    }
}