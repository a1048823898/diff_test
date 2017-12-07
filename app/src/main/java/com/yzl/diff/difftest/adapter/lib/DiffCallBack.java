package com.yzl.diff.difftest.adapter.lib;

import android.support.annotation.Nullable;

/**
 * Created by 沈小建 on 2017-12-07.
 */

public abstract class DiffCallBack<T> {

    public abstract boolean areItemsTheSame(T oldItem, T newItem);

    public abstract boolean areContentsTheSame(T oldItem, T newItemn);

    @Nullable
    public Object getChangePayload(T oldItem, T newItem) {
        return null;
    }
}
