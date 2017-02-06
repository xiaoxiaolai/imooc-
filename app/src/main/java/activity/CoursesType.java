package activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.xiao.cui.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import scroll.SwipeBackControllerLayout;
import ui.ViewPagerIndicator;

public class CoursesType extends AppCompatActivity {


    private SwipeBackControllerLayout layout;
    private ViewPager viewpager;
    private List<Fragment> fragmentsList = new ArrayList();
    private FragmentPagerAdapter adapter;
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("短信1", "收藏2", "推荐3", "推荐3", "推荐3");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raise);
        layout = (SwipeBackControllerLayout) findViewById(R.id.coursetype);
        layout.init(this);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        initDatas();
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentsList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentsList.size();
            }
        };
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(5);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mViewPagerIndicator.setVisibleTabCount(5);
        mViewPagerIndicator.setTabItemTitles(mTitles);
        mViewPagerIndicator.setViewPager(viewpager, 0);
    }

    private void initDatas() {
        for (int i = 0; i < 5; i++) {
            DummyFragment fragment = DummyFragment.getInstance(i + 1);
            fragmentsList.add(fragment);
        }
    }
}
