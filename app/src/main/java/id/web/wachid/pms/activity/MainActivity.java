package id.web.wachid.pms.activity;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import id.web.wachid.pms.R;
import id.web.wachid.pms.fragment.FavoriteFragment;
import id.web.wachid.pms.fragment.HomeFragment;
import id.web.wachid.pms.fragment.ProfileFragment;
import id.web.wachid.pms.fragment.ServerFragment;
import id.web.wachid.pms.fragment.ServerFragment_backup;
import id.web.wachid.pms.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupBottomNavigation();

        if (savedInstanceState == null) {

            loadHomeFragment();
        }

    }

    private void setupBottomNavigation() {

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:
                        loadHomeFragment();
                        return true;
                    case R.id.action_favorite:
                        loadServerFragment();
                        return true;
                    case R.id.action_profile:
                        loadServerFragmentBack();
                        return true;
                    case R.id.action_settings:
                        loadSettingsFragment();
                        return true;
                }
                return false;
            }
        });
    }

    private void loadHomeFragment() {

        HomeFragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadServerFragment() {

        ServerFragment fragment = ServerFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadServerFragmentBack() {

        ServerFragment_backup fragment = ServerFragment_backup.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadFavoriteFragment() {

        FavoriteFragment fragment = FavoriteFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadProfileFragment() {

        ProfileFragment fragment = ProfileFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadSettingsFragment() {

        SettingFragment fragment = SettingFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }
}