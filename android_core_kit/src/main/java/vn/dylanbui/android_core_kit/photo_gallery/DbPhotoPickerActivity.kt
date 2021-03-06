package vn.dylanbui.android_core_kit.photo_gallery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_photo_picker.*
import vn.dylanbui.android_core_kit.R
import vn.dylanbui.android_core_kit.permission_manager.DbPermissionManager
import vn.dylanbui.android_core_kit.permission_manager.DbPermissionManagerImpl

class DbPhotoPickerActivity: MvpActivity<DbPhotoPickerViewAction, DbPhotoPickerPresenter>(), DbPhotoPickerViewAction,
    DbPhotoPickerAdapter.DbChoosePhotoListener {

    private lateinit var adapter: DbPhotoPickerAdapter

    private lateinit var btnDone: Button
    private lateinit var rvImage: RecyclerView

    private val TAKE_PHOTO_CODE = 100
    private val REQUEST_CAMERA = 101

    private val managePermissions: DbPermissionManager = DbPermissionManagerImpl

    override fun createPresenter(): DbPhotoPickerPresenter = DbPhotoPickerPresenter()

    open override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_picker)

        toolbar?.setNavigationIcon(R.mipmap.ic_arrow_left)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
        tvTitle?.text = "Chọn hình ảnh"

        btnDone = findViewById(R.id.btnDone)
        rvImage = findViewById(R.id.rvImage)

        // Chay se co loi ve adapter chua duoc tao
        this.initView()
    }

    private fun initView() {
        presenter.getDataIntent(intent.extras)
        this.scanImage()
        presenter.updateNumberPhotoChoosedInHeader()

        btnDone.setOnClickListener {
            presenter.finishChoosePhoto()
        }
    }

    override fun showButtonDone(isShow: Boolean) {
        btnDone.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun scanImage() {
        managePermissions.checkPermissions(
            activity = this,
            permissions = arrayOf(Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE),
            onPermissionResult = { permissionResult ->
                // handle permission result
                if (permissionResult.areAllGranted()) {
                    getPresenter().queryImage(this)
                } else {
                    getPresenter().finishChoosePhoto()
                }
            }, requestCode = REQUEST_CAMERA)
    }

    override fun finishChoosePhoto(result: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("result", result)
        returnIntent.putParcelableArrayListExtra("results", presenter.photoResult)
        setResult(Activity.RESULT_OK, returnIntent)
        onBackPressed()
    }

    override fun setDataAdapter(myImages: MutableList<DbPhoto>, numImageCanChoose: Int) {
        adapter = DbPhotoPickerAdapter(myImages)
        adapter.setChooseImageListener(this)
        adapter.setNumImageCanChoose(numImageCanChoose)
        rvImage.layoutManager = GridLayoutManager(this@DbPhotoPickerActivity, 3)
        rvImage.adapter = adapter
    }

    override fun addImageToFirstList(myImage: DbPhoto) {
        adapter?.addItemInfirst(myImage)
        adapter?.notifyDataSetChanged()
    }

    // update lại số tự thự chọn ảnh trên từng item
    override fun updateNumberOfPositionCheckedItem(position: Int, newIndex: Int) {
        val viewHolder = rvImage.findViewHolderForAdapterPosition(position) as DbPhotoPickerAdapter.ImageViewHolder
        viewHolder.tvNumber.text = "$newIndex"
    }

    // update lại số ảnh đã chọn trên thanh header
    override fun updateNumberPhotoChoosedInHeader(numImageChoosed: Int) {
        // tvNumberImageChoosed.text = if (numImageChoosed > 0) "($numImageChoosed)" else ""
        tvTitle?.text = "Chọn hình ảnh " + if (numImageChoosed > 0) "($numImageChoosed)" else ""
        btnDone.visibility = if (numImageChoosed > 0) View.VISIBLE else View.GONE
    }

    override fun openCameraIntent(outputFileUri: Uri) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE)
    }

    override fun onOpenCamera() {
        managePermissions.checkPermissions(
            activity = this,
            permissions = arrayOf(Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE),
            onPermissionResult = { permissionResult ->
                // handle permission result
                if (permissionResult.areAllGranted()) {
                    getPresenter().openCamera(this)
                }
            }, requestCode = REQUEST_CAMERA)
    }

    override fun onChecked(
        isChecked: Boolean,
        position: Int,
        myImage: DbPhoto,
        imageViewHolder: DbPhotoPickerAdapter.ImageViewHolder
    ) {
        presenter.onItemImageChecked(isChecked, position, myImage)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        managePermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
            ?: super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CAMERA) {
//            managePermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAKE_PHOTO_CODE && resultCode == Activity.RESULT_OK) {
            presenter.onCapturePhotoResult(this, requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
