package com.yzl.diff.difftest.adapter.lib;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author 沈小建 on 2017/1/6 0006.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;

    /**
     * 集合.
     */
    protected List<T> mListData;

    /**
     * 布局.
     */
    protected int layoutId;

    /**
     * 辅助计算.
     */
    protected DiffAdapterHelper<T> mAdapterHelper;

    public BaseAdapter(@NonNull List<T> listData, @LayoutRes int layoutId, DiffCallBack<T> callBack) {
        this.mListData = listData;
        this.layoutId = layoutId;

        if (mListData instanceof PagedList) {
            mAdapterHelper = new DiffAdapterHelper<>(mListData, this, callBack);
        }
    }

    public static <T> List<T> getConvertList(List<T> listData) {
        PagedList<T> list = new PagedList<>();
        list.addAll(listData);
        return list;
    }

    public final void setListData(List<T> listData) {
        if (mAdapterHelper != null) {
            mAdapterHelper.setListData(listData);
        } else {
            setRealListData(listData);
        }
    }

    protected final void setRealListData(List<T> listData) {
        mListData = listData;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        return new BaseViewHolder(LayoutInflater.from(mContext)
                .inflate(layoutId, parent, false));
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        onDestroy();
    }

    public void onDestroy() {
        mAdapterHelper.onDestroy();
    }
}
