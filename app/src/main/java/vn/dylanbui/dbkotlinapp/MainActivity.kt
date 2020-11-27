package vn.dylanbui.dbkotlinapp

import android.os.Bundle
import android.os.Handler

import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router

import vn.dylanbui.dbkotlinapp.app_coordinator.AppCoordinator
import vn.propzy.android_core_kit.DbBaseActivity


class MainActivity : DbBaseActivity() {

    private lateinit var container: ViewGroup
    private lateinit var router: Router
    private lateinit var appCoordinator: AppCoordinator

    override fun getActivityLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        container = findViewById(R.id.controller_container)
        router = Conductor.attachRouter(this, container, savedInstanceState)

        appCoordinator = AppCoordinator(router)
        appCoordinator.start()
    }

    override fun onBackPressed() {
        val backStackList = router.backstack
        if (backStackList.isNotEmpty()) {
            if (backStackList.size <= 1) {
                return handleDoubleBackPressExitApp()
            } else {
                return if (!router.handleBack()) super.onBackPressed() else return
            }
        }
    }

    private var doubleBackToExitPressedOnce = false
    private fun handleDoubleBackPressExitApp() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.common_press_back_again_to_exit), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
