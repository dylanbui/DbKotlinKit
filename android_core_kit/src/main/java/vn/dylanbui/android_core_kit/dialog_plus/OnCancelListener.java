package vn.dylanbui.android_core_kit.dialog_plus;

import androidx.annotation.NonNull;

/**
 * DialogPlus will use this listener to propagate cancel events when back button is pressed.
 */
public interface OnCancelListener {

  void onCancel(@NonNull DialogPlus dialog);
}
