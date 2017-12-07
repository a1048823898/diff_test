package com.yzl.diff.difftest.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.yzl.diff.difftest.R;
import com.yzl.diff.difftest.adapter.lib.BaseAdapter;
import com.yzl.diff.difftest.adapter.lib.BaseViewHolder;
import com.yzl.diff.difftest.adapter.lib.DiffCallBack;
import com.yzl.diff.difftest.adapter.lib.PagedList;
import com.yzl.diff.difftest.bean.MockData;

import java.util.List;

/**
 * Created by 沈小建 on 2017-12-07.
 */

public class MainAdapter extends BaseAdapter<MockData> {

    public MainAdapter(@NonNull List<MockData> listData, int layoutId, DiffCallBack<MockData> callBack) {
        super(listData, layoutId, callBack);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TextView tvId = holder.getView(R.id.tv_id);
        TextView tvName = holder.getView(R.id.tv_name);

        MockData mockData = mListData.get(position);
        tvId.setText(mockData.getId() + "");
        tvName.setText(mockData.getName());
    }
}
