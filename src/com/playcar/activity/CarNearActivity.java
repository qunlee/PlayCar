package com.playcar.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.playcar.R;
import com.playcar.fragment.CarNearFriendsFragment;
import com.playcar.fragment.CarNearGroupFragment;
import com.playcar.fragment.CarNearTrandsFragment;
import com.playcar.view.MainViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Aure on 15/9/23.
 */
public class CarNearActivity extends CarBaseActivity {

    public MainViewPager viewPager;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private ImageView mTabImg;

    private int zero = 0;// 动画图片偏移量
    private int one;//单个水平动画位移
    private int two;
//    private int three;

    private FragmentManager fragmentManager;
//	private int displayWidth, displayHeight;

    // 底部线条移动初始位置
    private int mUnderLineFromX = 0;

    private int mUnderLineWidth;

    private int mScreenWidth;

    private RelativeLayout mTab1,mTab2,mTab3;

//    private NormalTopBar titleBar;

    private String activityName = "CarNearActivity";

    private LinearLayout fartherView;

    private int moreWidth = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_near_activity);


//        three = one * 3;



        initView();

    }

    private void initView () {
//        titleBar = (NormalTopBar) findViewById(R.id.order_list_top_bar);
//        titleBar.setTitle("订单管理");
//        titleBar.setBackTextVisibility(false);
//        titleBar.setBtSettingVisibility(false);
//        titleBar.setOnBackListener(this);
        moreWidth = Dp2Px(this,180);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        int tuW = mScreenWidth - moreWidth;
        fartherView = (LinearLayout) findViewById(R.id.tab_farther);
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        fartherView.measure(w, h);
        int height =fartherView.getMeasuredHeight();
        int width =fartherView.getMeasuredWidth();

        mUnderLineWidth = tuW / 3;
        one = mUnderLineWidth; //设置水平动画平移大小
        two = one * 2;




        mTab1 = (RelativeLayout) findViewById(R.id.all_order);
        mTab2 = (RelativeLayout) findViewById(R.id.payed_order);
        mTab3 = (RelativeLayout) findViewById(R.id.checked_order);
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        mTab1.setOnClickListener(new MyOnClickListener(0));
        mTab2.setOnClickListener(new MyOnClickListener(1));
        mTab3.setOnClickListener(new MyOnClickListener(2));

        ViewGroup.LayoutParams laParams=(ViewGroup.LayoutParams)mTabImg.getLayoutParams();
        laParams.height=4;
        laParams.width=mUnderLineWidth;
        mTabImg.setLayoutParams(laParams);
        mTabImg.setVisibility(View.VISIBLE);

//        int[] location = new int[2];
//        fartherView.getLocationOnScreen(location);
//        int x = location[0];
//        int y = location[1];
//        mUnderLineFromX = x;


        fragments.add(new CarNearTrandsFragment());
        fragments.add(new CarNearFriendsFragment());
        fragments.add(new CarNearGroupFragment());
//        fragments.add(new FinishOrderListFragment());

        fragmentManager = this.getSupportFragmentManager();

        viewPager = (MainViewPager) findViewById(R.id.viewPager);
        //viewPager.setSlipping(false);//设置ViewPager是否可以滑动
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setCurrentItem(1);
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);

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
                case 2:
                    doUnderLineAnimation(2);
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

    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    };

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
    protected void onResume() {
        super.onResume();
        //友盟页面统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        //友盟页面统计

    }
}
