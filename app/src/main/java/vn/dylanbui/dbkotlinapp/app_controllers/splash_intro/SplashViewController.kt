package vn.dylanbui.dbkotlinapp.app_controllers.splash_intro

import android.animation.Animator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.controller_splash.view.*
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModelController
import vn.dylanbui.android_core_kit.utils.dLog

import vn.dylanbui.dbkotlinapp.R
import vn.dylanbui.dbkotlinapp.app_coordinator.ApplicationRoute

//internal class AnimatorListenerAdapter(
//    val onStart: ((Animator) -> Unit)? = null,
//    val onRepeat: ((Animator) -> Unit)? = null,
//    val onEnd: ((Animator) -> Unit)? = null,
//    val onCancel: ((Animator) -> Unit)? = null
//): Animator.AnimatorListener {
//
//    override fun onAnimationStart(animation: Animator) = onStart?.invoke(animation) ?: Unit
//    override fun onAnimationRepeat(animation: Animator) = onRepeat?.invoke(animation) ?: Unit
//    override fun onAnimationEnd(animation: Animator) = onEnd?.invoke(animation) ?: Unit
//    override fun onAnimationCancel(animation: Animator) = onCancel?.invoke(animation) ?: Unit
//}
// Example: animationView.addAnimatorListener(AnimatorListenerAdapter(onEnd = { _ ->  }))


class SplashViewController: DbViewModelController<SplashViewModel>(SplashViewModel::class.java), Animator.AnimatorListener
{
    lateinit var animationView: LottieAnimationView

    override fun setTitle(): String = "Title first"

    private var updateDone: Boolean = false

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_splash, container, false)
    }

    override fun onPreAttach() {

    }

    override fun onViewBound(view: View)
    {
        animationView = view.findViewById(R.id.lottieAnimationView)
        animationView.addAnimatorListener(this)

        this.viewModel.getLiveData().observe(this, Observer<String> {
            this.view?.run {
                // text.text = "ViewModel created at: $it"
                dLog("ViewModel created at: $it")
                tvAppVersion.text = "ViewModel created at: $it"
            }
        })

    }

    override fun onAttach(view: View)
    {
        super.onAttach(view)

//        var mainActivity = activity as? MainActivity
//        mainActivity?.let {
//            it.setToolBarTitle(setTitle())
//            it.enableUpArrow(router.backstackSize > 1)
//        }

        // -- Begin play LottieAnimationView --
        animationView.playAnimation()

//        dLog("Chuyen vao thread")
//        Utils.delayFunc(1500) {
//            dLog("-- jobUpdate --- ${Thread.currentThread()} has run.")
//        }

//        prensenter.executeTwoTasksParallel {
//            Log.d("TAG", "Chay dong bo 1 luc 2 Task")
//        }

//        prensenter.executeTwoTasksSequentially {
//            Log.d("TAG", "Chay tuan tu tung task trong 2 Task")
//        }

//        prensenter.getAllDataForApp {
//            Log.d("TAG", "Da chay choi xong, kiem chung thoi")
//            this.updateDone = true
//        }
    }

    override fun onAnimationStart(animation: Animator?) {
        Log.d("TAG", "onAnimationStart")
    }

    override fun onAnimationRepeat(animation: Animator?) {
        Log.d("TAG", "onAnimationRepeat")
    }

    override fun onAnimationEnd(animation: Animator?) {
        Log.d("TAG", "onAnimationEnd")
        // activity?.toast("onAnimationEnd")
        nav?.navigate(ApplicationRoute.SplashPageComplete())
    }

    override fun onAnimationCancel(animation: Animator?) {
        Log.d("TAG", "onAnimationCancel")

    }

}