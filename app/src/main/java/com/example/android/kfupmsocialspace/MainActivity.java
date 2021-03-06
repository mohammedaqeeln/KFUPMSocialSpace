package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    //Bottom Navigation bar Listener
    public BottomNavigationView.OnNavigationItemSelectedListener botNavListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case (R.id.navigation_chats):
                            selectedFragment = new ChatsFragment();
                            break;
                        case (R.id.navigation_blogs):
                            selectedFragment = new BlogsFragment();
                            break;
                        case (R.id.navigation_news):
                            selectedFragment = new NewsFragment();
                            break;
                        case (R.id.navigation_roommate):
                            selectedFragment = new RoommateFragment();
                            break;
                        case (R.id.navigation_market):
                            selectedFragment = new MarketFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Since we removed the defult action bar we have to tell the app to use the toolbar we created and select the 7th Toolbar
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        //Here is for the fragments after creating them to listen to the button clicks
        drawer = findViewById(R.id.drawer_layout);

        /*
        This is to show the action bar sliding. before that we add 2 strings in strings.xml
        the navigation_drawer_open And navigation_drawer_close for Blind people so it can be read by the device.
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        NavigationView navigationview = findViewById(R.id.nav_view);
        //https://www.youtube.com/watch?v=bjYstsO1PgI 5:00 we implements the interface
        navigationview.setNavigationItemSelectedListener(this);

        //adding the Listener to the bottom navigation bar
        BottomNavigationViewEx bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(botNavListener);

        //removes the bad animation using the BottomNavigationViewEx https://github.com/ittianyu/BottomNavigationViewEx
        for (int i = 0; i < bottomNav.getMenu().size(); i++) {
            bottomNav.enableShiftingMode(false);
        }

        //This is the first things you see when you open the app before clicking on any activity from the toolbar
        // https://www.youtube.com/watch?v=bjYstsO1PgI 9:20 when device rotates we don't call the method again
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NewsFragment()).commit();
            bottomNav.setSelectedItemId(R.id.navigation_news);
        }

        //just rotates the three line hamburger symbol with the device
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case (R.id.nav_profile):
                selectedFragment = new ProfileFragment();
                break;
            case (R.id.nav_utilities):
                selectedFragment = new UtilitiesFragment();
                break;
            case (R.id.nav_files):
                selectedFragment = new FilesFragment();
                break;
            case (R.id.nav_logout):
                setContentView(R.layout.activity_sign_in);
                Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                break;
        }
        if(selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
        }

        //After clicking an item we need to close the drawer so we can get to the page and see it
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //if back is pressed it closes the toolbar first
    //START is for the left side
    //END is for the right side toolbars
    @Override
    public void onBackPressed() {
        //if the toolbar is open close it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //closes the activity
            super.onBackPressed();
        }
    }
}
