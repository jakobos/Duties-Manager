package com.dreamsfactory.dutiesmanager.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamsfactory.dutiesmanager.R;
import com.dreamsfactory.dutiesmanager.database.DbManager;
import com.dreamsfactory.dutiesmanager.database.entities.Flat;
import com.dreamsfactory.dutiesmanager.database.entities.User;
import com.dreamsfactory.dutiesmanager.fragments.FreeTasksFragment;
import com.dreamsfactory.dutiesmanager.fragments.HomeFragment;
import com.dreamsfactory.dutiesmanager.fragments.MyTasksFragment;
import com.dreamsfactory.dutiesmanager.managers.LogManager;
import com.dreamsfactory.dutiesmanager.services.SyncService;
import com.dreamsfactory.dutiesmanager.settings.Settings;

public class MainActivity extends AppCompatActivity {


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtEmail, txtFlat;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    //index to identify current nac menu item
    public static int navItemIndex = 0;

    //tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_MY_TASKS = "my_tasks";
    private static final String TAG_FREE_TASKS =  "free_tasks";
    private static final String TAG_LOGOUT = "logout";
    public static String CURRENT_TAG = TAG_HOME;

    //toolbar items respected to selected nav menu item
    private String[] activity_titles;

    private boolean shouldLoadHomeFragOnBackPress = true;

    private Handler mHandler;

    private static final String KEY_INDEX = "index";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //nav header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtEmail = (TextView) navHeader.findViewById(R.id.email);
        txtFlat = (TextView) navHeader.findViewById(R.id.flat);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);

        //load toolbar titles from string resources
        activity_titles = getResources().getStringArray(R.array.nav_item_activity_titles);

        loadNavHeader();

        setUpNavigationView();

        startSyncService();

        if(savedInstanceState == null){
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        //DbManager.getInstance(this).init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogManager.logInfo("onSaveInstanceState()");
        outState.putInt(KEY_INDEX, navItemIndex);
        LogManager.logInfo("onSaveInstanceState - navItemIndex = "+navItemIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        navItemIndex = savedInstanceState.getInt(KEY_INDEX);
        LogManager.logInfo("onRestoreInstanceState - navItemIndex = "+navItemIndex);
        setToolbarTitle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadNavHeader(){
        User user = DbManager.getInstance(this).getUserService().getUser();
        Flat flat = DbManager.getInstance(this).getFlatService().getFlat();
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtFlat.setText(flat.getAddress());

    }

    private void loadHomeFragment(){
        //select nav menu item
        selectNavMenu();

        setToolbarTitle();

        if(getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null){
            drawer.closeDrawers();
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if(mPendingRunnable != null){
            mHandler.post(mPendingRunnable);
        }

        toggleFab();

        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment(){
        switch(navItemIndex){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                MyTasksFragment myTasksFragment = new MyTasksFragment();
                return myTasksFragment;
            case 2:
                FreeTasksFragment freeTasksFragment = new FreeTasksFragment();
                return freeTasksFragment;
            default:
                return new HomeFragment();
        }
    }
    private void setToolbarTitle(){
        getSupportActionBar().setTitle(activity_titles[navItemIndex]);
        LogManager.logInfo("setToolbarTitle - navItemIndex = "+ navItemIndex);
    }
    private void selectNavMenu(){
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void startSyncService(){
        Intent alarmIntent = new Intent(MainActivity.this, SyncService.class);
        // Pending Intent Object
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Alarm Manager Object
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        // Alarm Manager calls BroadCast for every Ten seconds (10 * 1000), BroadCase further calls service to check if new records are inserted in
        // Remote MySQL DB
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                5*1000,
                30*1000, pendingIntent);
    }

    private void logout(){

        Settings.getInstance(this).set(Settings.USER_IS_LOGGED_IN, false);
        Settings.getInstance(this).set(Settings.FLAT_IS_LOGGED_IN, false);
        Settings.getInstance(this).set(Settings.USER_ID, 0);
        Settings.getInstance(this).set(Settings.FLAT_ID, 0);
        Settings.getInstance(this).set(Settings.LAST_SYNC_TASK, 0);
        Settings.getInstance(this).set(Settings.LAST_SYNC_FRIEND, 0);

        DbManager.getInstance(this).getUserService().deleteAllUsers();
        DbManager.getInstance(this).getFlatService().deleteAllFlats();
        DbManager.getInstance(this).getFriendService().deleteAllFriends();
        DbManager.getInstance(this).getTaskService().deleteAllTasks();

        Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
        startActivity(intent);
        finish();


    }
    private void setUpNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_my_tasks:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_MY_TASKS;
                        break;
                    case R.id.nav_free_tasks:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_FREE_TASKS;
                        break;
                    case R.id.nav_logout:
                        logout();
                        break;
                    default:
                        navItemIndex = 0;

                }
                if(item.isChecked()){
                    item.setChecked(false);
                }else{
                    item.setChecked(true);
                }
                item.setChecked(true);
                loadHomeFragment();

                return true;
            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawers();
            return;
        }
        if(shouldLoadHomeFragOnBackPress){
            if(navItemIndex != 0){
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }


        super.onBackPressed();
    }

    private void toggleFab(){
        if(navItemIndex == 0){
            fab.show();
        }else{
            fab.hide();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }

        return super.onOptionsItemSelected(item);
    }
}
