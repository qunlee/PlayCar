package com.playcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.playcar.R;
import com.playcar.activity.trands.CarAddFriendActivity;
import com.playcar.fragment.CarFollowsFragment;
import com.playcar.fragment.CarFriendsFragment;
import com.playcar.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;


public class CarFriendsActivity extends FragmentActivity implements View.OnClickListener {
    public MainViewPager viewPager;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private ImageView mTabImg;
    private FragmentManager fragmentManager;

    // 底部线条移动初始位置
    private int mUnderLineFromX = 0;

    private int mUnderLineWidth;

    private int mScreenWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity_friends);
        initView();
    }
    private void initView () {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mUnderLineWidth = mScreenWidth / 2;
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        ViewGroup.LayoutParams laParams=(ViewGroup.LayoutParams)mTabImg.getLayoutParams();
        laParams.height=4;
        laParams.width=mUnderLineWidth;
        mTabImg.setLayoutParams(laParams);
        mTabImg.setVisibility(View.VISIBLE);
        fragments.add(new CarFriendsFragment());
        fragments.add(new CarFollowsFragment());
        fragmentManager = this.getSupportFragmentManager();

        viewPager = (MainViewPager) findViewById(R.id.viewPager);
        //viewPager.setSlipping(false);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(new MyPagerAdapter());





    }

    private void doUnderLineAnimation(int index) {
        TranslateAnimation animation = new TranslateAnimation(mUnderLineFromX,
                index * mUnderLineWidth, 0, 0);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        animation.setDuration(150);
        mTabImg.startAnimation(animation);
        mUnderLineFromX = index * mUnderLineWidth;
    }




    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    doUnderLineAnimation(0);
                    break;
                case 1:
                    doUnderLineAnimation(1);
                    break;
            }


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(fragments.get(position)
                    .getView());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = fragments.get(position);
            if (!fragment.isAdded()) { // 如果fragment还没有added
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(fragment, fragment.getClass().getSimpleName());
                ft.commit();
                /**
                 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
                 * 会在进程的主线程中,用异步的方式来执行。
                 * 如果想要立即执行这个等待中的操作,就要调用这个方法(只能在主线程中调用)。
                 * 要注意的是,所有的回调和相关的行为都会在这个调用中被执行完成,因此要仔细确认这个方法的调用位置。
                 */
                fragmentManager.executePendingTransactions();
            }

            if (fragment.getView().getParent() == null) {
                container.addView(fragment.getView()); // 为viewpager增加布局
            }
            return fragment.getView();
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.go_back_tv:
                finish();
                break;

            case R.id.setting:
                intent.setClass(this, CarSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.friends_count:
                intent.setClass(this, CarFriendsActivity.class);
                startActivity(intent);
                break;
            case R.id.friends_tab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.follow_tab:
                viewPager.setCurrentItem(1);
                break;
            case R.id.add_friend:
                intent.setClass(this, CarAddFriendActivity.class);
                startActivity(intent);
                break;
        }
    }
}
