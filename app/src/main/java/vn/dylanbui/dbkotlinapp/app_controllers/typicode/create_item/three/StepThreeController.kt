package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.three

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModelController
import vn.dylanbui.dbkotlinapp.R


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */


class StepThreeController: DbViewModelController<StepThreeViewModel>(
    StepThreeViewModel::class.java)
{


    override fun setTitle(): String = "Step 3"



    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_step_three, container, false)
    }

    override fun onPreAttach() {

    }

    override fun onViewBound(view: View)
    {

    }
}