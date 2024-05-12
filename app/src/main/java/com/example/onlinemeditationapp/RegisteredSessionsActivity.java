package com.example.onlinemeditationapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RegisteredSessionsActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainPageActivity.class.getName();
    private RecyclerView recyclerView;
    private ArrayList<SessionItem> SessionItemList;
    private SessionItemAdapter adapter;
    private int gridNumber = 1;

    private FirebaseUser user;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(LOG_TAG, "Authenticated user");
        }else {
            Log.d(LOG_TAG, "Login Error");
            finish();
        }


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        SessionItemList = new ArrayList<>();
        adapter = new SessionItemAdapter(this, SessionItemList);
        recyclerView.setAdapter(adapter);

        initializeData();
    }
    private void initializeData(){
        String[] sessionsList;
        String[] sessionsDesc;
        String[] sessionsPrice;
        //TODO SETUP DATABASE HERE
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.registered_sessions_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(LOG_TAG, newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logout_button) {
            Log.d(LOG_TAG, "Logout pressed");
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        }else if(item.getItemId() == R.id.all_sessions) {
            Log.d(LOG_TAG, "Registered sessions pressed");
            finish();
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }
}