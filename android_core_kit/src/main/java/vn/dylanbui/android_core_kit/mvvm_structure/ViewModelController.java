package vn.dylanbui.android_core_kit.mvvm_structure;

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/8/20
 * To change this template use File | Settings | File and Code Templates.
 */
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.bluelinelabs.conductor.Controller;

public abstract class ViewModelController extends Controller implements LifecycleOwner {

    private final ViewModelStore viewModelStore = new ViewModelStore();
    private final ControllerLifecycleOwner lifecycleOwner = new ControllerLifecycleOwner(this);

    public ViewModelController() {
        super();
    }

    public ViewModelController(Bundle bundle) {
        super(bundle);
    }

    public ViewModelProvider viewModelProvider() {
        return viewModelProvider(new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()));
    }

    public ViewModelProvider viewModelProvider(ViewModelProvider.Factory factory) {
        return new ViewModelProvider(viewModelStore, factory);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModelStore.clear();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleOwner.getLifecycle();
    }
}