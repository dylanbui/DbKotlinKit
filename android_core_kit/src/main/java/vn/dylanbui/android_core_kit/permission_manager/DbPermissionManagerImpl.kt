package vn.dylanbui.android_core_kit.permission_manager

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import vn.dylanbui.android_core_kit.R

/**
 * <p>PermissionManagerImpl is based on PermissionManager and contains all logic for handling permission handling</p>
 *
 * @since    1.0.0
 * @version  1.0.0
 * @author   grumpyshoe
 *
 */
object DbPermissionManagerImpl : DbPermissionManager {

    private var usedRequestCode = DbPermissionManager.DEFAULT_PERMISSION_REQUEST_CODE
    private var onPermissionResult: ((DbPermissionResult) -> Unit)? = null


    /**
     * handle permission request result
     *
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray): Boolean? {
        if (requestCode == usedRequestCode) {
            if (grantResults.isNotEmpty()) {
                val result = mutableMapOf<String, Int>()
                permissions.forEachIndexed { index, permission ->
                    result.put(permission, grantResults[index])
                }
                onPermissionResult?.invoke(DbPermissionResult(result, requestCode))

            } else {
                val result = mutableMapOf<String, Int>()
                permissions.forEachIndexed { index, permission ->
                    result.put(permission, grantResults[index])
                }
                onPermissionResult?.invoke(DbPermissionResult(result, requestCode))
            }
            return true
        }
        return false
    }


    /**
     * check for required permissions
     *
     */

    override fun checkPermissions(activity: Activity, permissions: Array<out String>,
                                  onPermissionResult: ((DbPermissionResult) -> Unit)?,
                                  permissionRequestPreExecuteExplanation: DbPermissionRequestExplanation?,
                                  permissionRequestRetryExplanation: DbPermissionRequestExplanation?,
                                  requestCode: Int?): Boolean {

        // check if new permission handling need to be respected (API >= 23)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            this.onPermissionResult = onPermissionResult
            usedRequestCode = requestCode ?: DbPermissionManager.DEFAULT_PERMISSION_REQUEST_CODE

            val notGrantedPermissionList = mutableListOf<String>()
            permissions.forEach { permission ->
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    notGrantedPermissionList.add(permission)
                }
            }

            // check if there is any permission that had not been granted yet
            if (notGrantedPermissionList.isNotEmpty()) {

                // check if we should show an explanation ('true' if the request has been denied before)?
                var showExplanation = false
                notGrantedPermissionList.forEach {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it)) {
                        showExplanation = true
                    }
                }

                // if no explanation should be shown, show pre-information before requesting permission
                if (!showExplanation && permissionRequestPreExecuteExplanation != null) {
                    showPermissionRequestExplanationHint(activity, permissionRequestPreExecuteExplanation, notGrantedPermissionList.toTypedArray(), usedRequestCode)
                    return true
                }

                // show explanation if the values have been set to 'permissionRequestRetryExplanation'
                if (showExplanation && permissionRequestRetryExplanation != null) {

                    showPermissionRequestExplanationHint(activity, permissionRequestRetryExplanation, notGrantedPermissionList.toTypedArray(), usedRequestCode)

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(activity, permissions, usedRequestCode)

                }

                return false
            } else {
                // all permissions has already been granted
                val result = mutableMapOf<String, Int>()
                permissions.forEachIndexed { _, permission ->
                    result.put(permission, PackageManager.PERMISSION_GRANTED)
                }
                onPermissionResult?.invoke(DbPermissionResult(result, usedRequestCode))
                return true

            }
        } else {

            // the sdk is lower then API 23 so no permission handling needs to be used
            val result = mutableMapOf<String, Int>()
            permissions.forEach { permission ->
                result.put(permission, PackageManager.PERMISSION_GRANTED)
            }
            onPermissionResult?.invoke(DbPermissionResult(result, usedRequestCode))
            return true
        }

    }


    /**
     * show alert dialog with some text
     *
     */
    private fun showPermissionRequestExplanationHint(activity: Activity,
                                                     permissionRequestExplanation: DbPermissionRequestExplanation,
                                                     permissions: Array<String>,
                                                     requestCode: Int,
                                                     hintOnly: Boolean = true) {
        val alert = AlertDialog.Builder(activity)
            .setTitle(permissionRequestExplanation.title)
            .setMessage(permissionRequestExplanation.message)

        if (hintOnly) {
            alert.setPositiveButton(activity.getString(R.string.permission_request_explanation_btn_ok_text)) { dialog, _ ->
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
                dialog.dismiss()
            }
        } else {
            alert.setPositiveButton(activity.getString(R.string.permission_request_explanation_btn_grant_text)) { dialog, _ ->
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
                dialog.dismiss()
            }
            alert.setNegativeButton(activity.getString(R.string.permission_request_explanation_btn_deny_text), null)
        }

        val dialog = alert.create()
        dialog.show()
    }

}