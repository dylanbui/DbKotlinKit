package vn.dylanbui.android_core_kit.dialog_plus;

import androidx.annotation.NonNull;

/**
 * Invoked when dialog is completely dismissed. This listener takes the animation into account and waits for it.
 *
 * <p>It is invoked after animation is completed</p>
 */
public interface OnDismissListener {
  void onDismiss(@NonNull DialogPlus dialog);
}
