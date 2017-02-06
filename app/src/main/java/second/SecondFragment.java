package second;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiao.cui.R;
import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

import ui.MyViewPager;

import static com.example.xiao.cui.R.id.id_tv_chat;

public class SecondFragment extends Fragment implements View.OnClickListener{
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;

    private TextView mChatTextView;
    private TextView mFriendTextView;
    private TextView mContactTextView;
    private LinearLayout mChatLinearLayout;
    private BadgeView mBadgeView;

    private ImageView mTabline;
    private int mScreen1_3;

    private int mCurrentPageIndex = 0;
    private String CurrentPageIndex="CurrentPageIndex";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e("onSaveInstanceState", "onSaveInstanceState: "+mViewPager.getCurrentItem());

        outState.putInt(CurrentPageIndex, mViewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        if (null != savedInstanceState)
//            mCurrentPageIndex = savedInstanceState.getInt(CurrentPageIndex);
        super.onViewStateRestored(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState)
            mCurrentPageIndex = savedInstanceState.getInt(CurrentPageIndex);

        View view = inflater.inflate(R.layout.activity_main_second, container, false);
        initTabLine(view);
        initView(view);

        return view;
    }



//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
////		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_main_second);
//
//		initTabLine();
//		initView();
//	}

    private void initTabLine(View view) {
        mTabline = (ImageView) view.findViewById(R.id.id_iv_tabline);
        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3 = outMetrics.widthPixels / 3;
//        LayoutParams lp = mTabline.getLayoutParams();


        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabline
                .getLayoutParams();
        lp.width = mScreen1_3;

        lp.leftMargin = mCurrentPageIndex
                * mScreen1_3;
        mTabline.setLayoutParams(lp);
    }


    private void initView(View view) {
        intercept = (MyViewPager) getActivity().findViewById(R.id.id_viewpager);
        mViewPager = (ViewPager) view.findViewById(R.id.id_viewpager1);
        mViewPager.setOffscreenPageLimit(2);

        mChatTextView = (TextView) view.findViewById(id_tv_chat);
        mChatTextView.setOnClickListener(this);
        mFriendTextView = (TextView) view.findViewById(R.id.id_tv_friend);
        mFriendTextView.setOnClickListener(this);
        mContactTextView = (TextView) view.findViewById(R.id.id_tv_contact);
        mContactTextView.setOnClickListener(this);
        mChatLinearLayout = (LinearLayout) view.findViewById(R.id.id_ll_chat);

        mDatas = new ArrayList<Fragment>();

        SecondFragmentChild tab01 = new SecondFragmentChild();
        FriendMainTabFragment tab02 = new FriendMainTabFragment();
        ContactMainTabFragment tab03 = new ContactMainTabFragment();

        mDatas.add(tab01);
        mDatas.add(tab02);
        mDatas.add(tab03);

        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mDatas.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetTextView();
//                intercept.isLast(position == 0 || position == 2);
                intercept.isLast(position == 0);

                switch (position) {
                    case 0:
                        if (mBadgeView != null) {
                            mChatLinearLayout.removeView(mBadgeView);
                        }
//                        mBadgeView = new BadgeView(SecondFragment.this);
                        mBadgeView = new BadgeView(getActivity());
                        mBadgeView.setBadgeCount(7);
                        mChatLinearLayout.addView(mBadgeView);

                        mChatTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        mFriendTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        mContactTextView.setTextColor(Color.parseColor("#008000"));
                        break;

                }



            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPx) {
                Log.e("TAG", position + " , " + positionOffset + " , "
                        + positionOffsetPx);
                mCurrentPageIndex = position;

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabline
                        .getLayoutParams();

                if (mCurrentPageIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex
                            * mScreen1_3);
                } else if (mCurrentPageIndex == 1 && position == 0)// 1->0
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1)
                            * mScreen1_3);
                } else if (mCurrentPageIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + positionOffset
                            * mScreen1_3);
                } else if (mCurrentPageIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1)
                            * mScreen1_3);
                }
                mTabline.setLayoutParams(lp);

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    protected void resetTextView() {
        mChatTextView.setTextColor(Color.BLACK);
        mFriendTextView.setTextColor(Color.BLACK);
        mContactTextView.setTextColor(Color.BLACK);
    }

    private intercept intercept;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tv_chat:
                mViewPager.setCurrentItem(0);
                resetTextView();
                mChatTextView.setTextColor(Color.parseColor("#008000"));
                break;
            case R.id.id_tv_friend:
                mViewPager.setCurrentItem(1);
                resetTextView();
                mFriendTextView.setTextColor(Color.parseColor("#008000"));

                break;
            case R.id.id_tv_contact:
                mViewPager.setCurrentItem(2);
                resetTextView();
                mContactTextView.setTextColor(Color.parseColor("#008000"));
                break;

        }

    }

    public interface intercept {

        Boolean isLast(Boolean boolen);

    }

}
