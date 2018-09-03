package com.mvryan.kamus.utils.listener;

import android.view.View;

/**
 * Created by mvryan on 01/09/18.
 */

public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View child, int childAdapterPosition);
}
