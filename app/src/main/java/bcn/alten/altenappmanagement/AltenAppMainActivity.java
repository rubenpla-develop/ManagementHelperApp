package bcn.alten.altenappmanagement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import bcn.alten.altenappmanagement.adapter.ViewPagerAdapter;
import bcn.alten.altenappmanagement.mvp.view.IMainActivityView;
import bcn.alten.altenappmanagement.ui.fragment.FollowUpFragment;
import bcn.alten.altenappmanagement.ui.fragment.HomeFragment;
import bcn.alten.altenappmanagement.ui.fragment.QMFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AltenAppMainActivity extends AppCompatActivity implements IMainActivityView,
        ViewPager.OnPageChangeListener {

    private final String TAG = AltenAppMainActivity.class.getSimpleName();

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    private ViewPagerAdapter adapter;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alten_app_main);
        ButterKnife.bind(this);

        setupViewPager();

        viewPager.addOnPageChangeListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HomeFragment homeFragment = new HomeFragment();
        FollowUpFragment followupFragment = new FollowUpFragment();
        QMFragment QMFragment = new QMFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(followupFragment);
        adapter.addFragment(QMFragment);

        viewPager.setAdapter(adapter);
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
