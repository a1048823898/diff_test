package com.yzl.diff.difftest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.yzl.diff.difftest.adapter.MainAdapter;
import com.yzl.diff.difftest.adapter.lib.BaseAdapter;
import com.yzl.diff.difftest.adapter.lib.DiffCallBack;
import com.yzl.diff.difftest.bean.MockData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mBtAdd;
    private Button mBtSub;
    private Button mBtChange;
    private RecyclerView mRv;
    private BaseAdapter<MockData> mAdapter;
    private List<MockData> mMockDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initList();
        initListener();
    }

    private void initView() {
        mBtAdd = (Button) findViewById(R.id.bt_add);
        mBtSub = (Button) findViewById(R.id.bt_sub);
        mBtChange = (Button) findViewById(R.id.bt_change);
        mRv = (RecyclerView) findViewById(R.id.rv);
    }

    private void initList() {
        mMockDataList = new ArrayList<>();
        mMockDataList.add(new MockData(1, "一1"));
        mMockDataList.add(new MockData(2, "一2"));
        mMockDataList.add(new MockData(3, "一3"));
        mMockDataList.add(new MockData(4, "一4"));
        mMockDataList.add(new MockData(5, "一5"));
        mMockDataList.add(new MockData(6, "一6"));
        mMockDataList.add(new MockData(7, "一7"));
        mMockDataList.add(new MockData(8, "一8"));
        mMockDataList.add(new MockData(9, "一9"));
        mMockDataList.add(new MockData(10, "一10"));

        mMockDataList = BaseAdapter.getConvertList(mMockDataList);

        mAdapter = new MainAdapter(mMockDataList, R.layout.item_main, new DiffCallBack<MockData>() {
            @Override
            public boolean areItemsTheSame(MockData oldItem, MockData newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(MockData oldItem, MockData newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });

        mRv.setAdapter(mAdapter);
    }

    private void initListener() {
        mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                mMockDataList.add(random.nextInt(mMockDataList.size()), new MockData(mMockDataList.size() + 1, "一2"));
            }
        });

        mBtSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMockDataList.remove(mMockDataList.size() - 1);
            }
        });

        mBtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<MockData> data = new ArrayList<>();
                data.add(new MockData(1, "一8"));
                data.add(new MockData(2, "一2"));
                data.add(new MockData(3, "一3"));
                data.add(new MockData(8, "一1"));
                data.add(new MockData(9, "一9"));
                data.add(new MockData(10, "一10"));

                mMockDataList = BaseAdapter.getConvertList(data);

                mAdapter.setListData(mMockDataList);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.onDestroy();
    }
}
