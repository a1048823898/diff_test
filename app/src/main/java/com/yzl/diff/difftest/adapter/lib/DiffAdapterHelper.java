package com.yzl.diff.difftest.adapter.lib;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 沈小建 on 2017-12-06.
 */

public class DiffAdapterHelper<T> {

    /**
     * 线程池(固定2个).
     */
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(2);

    /**
     * diff计算.
     */
    private BaseCallBack<T> mDiffCallback;

    /**
     * 对应的adapter.
     */
    private BaseAdapter<T> mBaseAdapter;

    /**
     * 数据.
     */
    private List<T> mListData;

    /**
     * 切换主线程用.
     */
    private Handler mHandler = new Handler();

    public DiffAdapterHelper(List<T> listData, BaseAdapter<T> baseAdapter, DiffCallBack<T> diffCallback) {
        mListData = listData;
        mBaseAdapter = baseAdapter;

        createNewDiff(diffCallback);
        setPagedCallback();
    }

    /**
     * 设置pagedList的回调.
     */
    private void setPagedCallback() {
        PagedList list = (PagedList) mListData;
        list.setCallback(new PagedList.Callback() {
            @Override
            public void onInserted(int position, int count) {
                mBaseAdapter.notifyItemRangeInserted(position, count);
                mBaseAdapter.notifyItemRangeChanged(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                mBaseAdapter.notifyItemRangeRemoved(position, count);
                mBaseAdapter.notifyItemRangeChanged(position, count);
            }
        });
    }

    /**
     * 创建真正的diffUtilCallback.
     */
    private void createNewDiff(final DiffCallBack<T> diffCallback) {
        mDiffCallback = new BaseCallBack<T>() {
            @Override
            public boolean areItemsTheSame(T oldItem, T newItem) {
                return diffCallback.areItemsTheSame(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(T oldItem, T newItem) {
                return diffCallback.areContentsTheSame(oldItem, newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return diffCallback.getChangePayload(mOldData.get(oldItemPosition), mNewData.get(newItemPosition));
            }
        };
    }

    /**
     * 设置新值.
     * <p>
     * 通过新旧数据对比计算,进入线程池计算
     *
     * @param listData 列表数据
     */
    void setListData(final List<T> listData) {
        List<T> oldData = mListData;

        this.mListData = listData;
        setPagedCallback();

        mDiffCallback.setOldData(oldData);
        mDiffCallback.setNewData(mListData);

        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mDiffCallback, true);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBaseAdapter.setRealListData(listData);
                        diffResult.dispatchUpdatesTo(mBaseAdapter);
                    }
                });
            }
        });
    }

    /**
     * 销毁资源.
     */
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        mExecutorService.shutdownNow();
    }
}
