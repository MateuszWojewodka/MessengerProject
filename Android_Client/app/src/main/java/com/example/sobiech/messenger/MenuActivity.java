package com.example.sobiech.messenger;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import Modules.AuthenticationModule;

public class MenuActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private AuthenticationModule authenticationModule = AuthenticationModule.getInstance();

//    Timer timer = new Timer();
//    FragmentConversiations fragmentConversiations;
//    FragmentSearchUsers fragmentSearchUsers;
//    FragmentInvitations fragmentInvitations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        ImageButton btLogout = findViewById(R.id.btLogout);
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryLogOut();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    FragmentConversiations fragmentConversiations = new FragmentConversiations();
                    return fragmentConversiations;
                case 1:
                    FragmentSearchUsers fragmentSearchUsers = new FragmentSearchUsers();
                    return fragmentSearchUsers;
                case 2:
                    FragmentInvitations fragmentInvitations = new FragmentInvitations();
                    return fragmentInvitations;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private void tryLogOut () {

        class LogOutAsyncTask extends AsyncTask<Void, Integer, Boolean> {

            @Override
            protected Boolean doInBackground(Void... voids) {
                return authenticationModule.logUserOut();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    Intent intent = new Intent (MenuActivity.this, LoginRegisterActivity.class);
                    startActivityForResult(intent, 0);
                    finish();
                }
        }
        }

        new LogOutAsyncTask().execute();
    }


}
