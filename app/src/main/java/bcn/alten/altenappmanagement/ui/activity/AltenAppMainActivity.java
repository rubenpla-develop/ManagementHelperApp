package bcn.alten.altenappmanagement.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import bcn.alten.altenappmanagement.R;
import bcn.alten.altenappmanagement.ui.adapter.ViewPagerAdapter;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
import bcn.alten.altenappmanagement.ui.fragment.FollowUpFragment;
import bcn.alten.altenappmanagement.ui.fragment.HomeFragment;
import bcn.alten.altenappmanagement.ui.fragment.QMFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AltenAppMainActivity extends AppCompatActivity implements IMainActivityView,
        ViewPager.OnPageChangeListener {

    private final String TAG = AltenAppMainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    private ViewPagerAdapter viewPagerAdapter;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alten_app_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPager();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_toolbar_main_about:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setupViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        HomeFragment homeFragment = new HomeFragment();
        FollowUpFragment followupFragment = FollowUpFragment.newInstance();
        QMFragment QMFragment = new QMFragment();

        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(followupFragment);
        viewPagerAdapter.addFragment(QMFragment);

        viewPager.setAdapter(viewPagerAdapter);
    }

    private void setListeners() {
        viewPager.addOnPageChangeListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_follow_up:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_qm:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        bottomNavigationView.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottomNavigationView.getMenu().getItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
