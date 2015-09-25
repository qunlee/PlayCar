package com.playcar.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.playcar.R;
import com.playcar.activity.CarNearActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aure on 15/9/23.
 */
public class CarNearTrandsFragment extends Fragment {


    private Activity mActivity;
    private TextView mMsgTv;
    private String mMsgName;

//    private TodayOrderListAdapter adapter;

    private String orderStatus = "";

//    private PullToRefreshListView orderListView;

//    private List<OrderData> orderList;

    private int flag;

    private int pagenum = 1;
    private boolean isPullUp = false;
    //表示是否继续加载下一页
    private boolean notLoadNext = false;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.car_near_trands_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//		initViews(view);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CarNearActivity activity = ((CarNearActivity) getActivity());
        View fragmentView = activity.fragments.get(0).getView();
//        adapter = new TodayOrderListAdapter(mActivity,this);
//        orderListView = (PullToRefreshListView) fragmentView.findViewById(R.id.all_order_list_view);
//        orderListView.getRefreshableView().setAdapter(adapter);
//        orderList = new ArrayList<OrderData>();
//		OrderData d1 = new OrderData();
//		d1.setOrderNumber("12121212");
//
//		OrderData d2 = new OrderData();
//		d2.setOrderNumber("12121212");
//
//		OrderData d3 = new OrderData();
//		d3.setOrderNumber("12121212");
//
//		orderList.add(d1);
//		orderList.add(d2);
//		orderList.add(d3);



//        orderListView.setOnLastItemVisibleListener(lastItemVisibleListener);
//        orderListView.getRefreshableView().setOnItemClickListener(itemClickListener);
//
//        adapter.addAll(orderList);
//        adapter.notifyDataSetChanged();
//        orderListView.onRefreshComplete();
//        initDisplay();
    }



//    private void initDisplay() {
//
//
//		下拉刷新
//        orderListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                pagenum = 1;
//                isPullUp = false;
//                //
//                getOrderList(orderStatus);
//
//            }
//        });
//        //上拉刷新
////		orderListView.setOnLastItemVisibleListener(lastItemVisibleListener);
////		orderListView.getRefreshableView().setOnItemClickListener(itemClickListener);
//        getOrderList(orderStatus);
//
//
//    }


    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            String orderId  = orderList.get(position - 1).getOrderId();
//            Intent jumpIntent = new Intent(mActivity,OrderInfoActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("orderId", orderId);
////			bundle.putSerializable("order",orderList.get(position - 1));
//            jumpIntent.putExtras(bundle);
//            mActivity.startActivity(jumpIntent);


        }

//        }
    };
//    private PullToRefreshBase.OnLastItemVisibleListener lastItemVisibleListener = new PullToRefreshBase.OnLastItemVisibleListener() {
//        @Override
//        public void onLastItemVisible() {
//            if (notLoadNext || orderList.size() < Constants.PAGESIZE) {
//                return;
//            }
//            isPullUp = true;
//            //加载下一页
//            pagenum++;
//            //获取数据
//            getOrderList(orderStatus);
//        }
//    };



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }







}
