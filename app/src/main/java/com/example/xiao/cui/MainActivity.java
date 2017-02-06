package com.example.xiao.cui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.example.xiao.cui.Fragment.FirstFragment;
import com.example.xiao.cui.Fragment.ThridFragment;
import com.example.xiao.cui.Fragment.ThridFragment1;
import com.example.xiao.cui.interface_.ToolBar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import second.SecondFragment;
import ui.ChangeColorIconWithText;
import ui.MyViewPager;

;

public class MainActivity extends AppCompatActivity implements OnClickListener,
		OnPageChangeListener ,ToolBar,SecondFragment.intercept
{

	private MyViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private String[] mTitles = new String[]
	{ "First Fragment !", "Second Fragment !", "Third Fragment !",
			"Fourth Fragment !" };
	private FragmentPagerAdapter mAdapter;

	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);
		initEvent();
		initRegister();
//		android.app.FragmentManager


	}

	@Override
	protected void onStart() {
		super.onStart();
//		setOverflowButtonAlways();
	}

	@Override
	public void initToolbar(Toolbar toolbar) {

		if (toolbar != null) {
			setSupportActionBar(toolbar);
		}



//		getActionBar().setDisplayShowHomeEnabled(false);
	}

	/**
	 * 初始化所有事件
	 */
	private void initEvent()
	{

		mViewPager.addOnPageChangeListener(this);

	}

	private void initDatas()
	{
		FirstFragment tab = new FirstFragment();
		mTabs.add(tab);
		SecondFragment tab2 = new SecondFragment();
		mTabs.add(tab2);
		ThridFragment tab3 = new ThridFragment();
		mTabs.add(tab3);
		ThridFragment1 tab4 = new ThridFragment1();
		mTabs.add(tab4);


		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position)
			{
				return mTabs.get(position);
			}
		};
	}

	private void initView()
	{
		mViewPager = (MyViewPager) findViewById(R.id.id_viewpager);
//		pager.setOffscreenPageLimit(2);
		mViewPager.setOffscreenPageLimit(3);

		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);
		ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);
		mTabIndicators.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		one.setIconAlpha(1.0f);

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	private void setOverflowButtonAlways()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 设置menu显示icon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{

		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View v)
	{
		clickTab(v);

	}

	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	private void clickTab(View v)
	{
		resetOtherTabs();

		switch (v.getId())
		{
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicators.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}
	}

	/**
	 * 重置其他的TabIndicator的颜色
	 */
	private void resetOtherTabs()
	{
		for (int i = 0; i < mTabIndicators.size(); i++)
		{
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{
		// Log.e("TAG", "position = " + position + " ,positionOffset =  "
		// + positionOffset);
		if (positionOffset > 0)
		{
			ChangeColorIconWithText left = mTabIndicators.get(position);
			ChangeColorIconWithText right = mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageSelected(int position)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int state)
	{
		// TODO Auto-generated method stub

	}

private Boolean isLast=false;
	@Override
	public Boolean isLast(Boolean boolen) {
		isLast=!boolen;
		return !boolen;
	}

	private void initRegister() {
		//注册广播接收器
		IntentFilter filter = new IntentFilter();
		filter.addAction(ThridFragment1.UPDATAE);
		registerReceiver(mReceiver, filter);
	}

	upDataList upDataList;

	public void setUpDataList(MainActivity.upDataList upDataList) {
		this.upDataList = upDataList;
	}

	/**
	 * 更新UI的广播接收器
	 */
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (ThridFragment1.UPDATAE.equals(intent.getAction())) {
				Log.d("tag", "onReceive: ------------sssssssssssss---------------");
				upDataList.upDataListItem();
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}
	public interface upDataList{
		public void upDataListItem();
	}
}
