package vn.dylanbui.android_core_kit.dialog_plus;

import android.view.View;

import androidx.annotation.NonNull;

public interface OnHolderListener {

  void onItemClick(@NonNull Object item, @NonNull View view, int position);

}
