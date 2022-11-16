package com.od.moviesapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.od.moviesapp.R;
import com.od.moviesapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigationFav:
                    navController.popBackStack(R.id.nav_graph,true);
                    navController.navigate(R.id.favouriteMovieFragment);
                    return true;
                case R.id.navigationExplore:
                    navController.popBackStack(R.id.nav_graph,true);
                    navController.navigate(R.id.mainFragment);
                    return true;
                case R.id.navigationContact:
                    navController.popBackStack(R.id.nav_graph,true);
                    navController.navigate(R.id.settingsFragment);
                    return true;
            }
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigation.setOnItemSelectedListener(onItemSelectedListener);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        setSelectedItem();
    }
    private void setSelectedItem(){
        MenuItem item = binding.navigation.getMenu().findItem(R.id.navigationExplore);
        item.setChecked(true);
    }
}