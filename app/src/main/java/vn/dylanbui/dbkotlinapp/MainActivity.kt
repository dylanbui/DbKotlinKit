package vn.dylanbui.dbkotlinapp

import android.os.Bundle

import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router

import vn.dylanbui.android_core_kit.DbBaseActivity
import vn.dylanbui.dbkotlinapp.app_coordinator.AppCoordinator


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

    override fun onBackPressed()
    {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
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
