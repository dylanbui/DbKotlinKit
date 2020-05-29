package vn.dylanbui.android_core_kit.utils_adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface OnDbAdapterListener<in T> {

    fun onSelectedItemListener(model: T, position: Int, view: View? = null)

    fun onSelectedItemLongClickListener(model: T, position: Int, view: View? = null) { }

    fun onBottomReachedListener(model: T, position: Int) { }

}


abstract class DbBaseRecyclerAdapter<T>(var dataSet: ArrayList<T> = arrayListOf()) : RecyclerView.Adapter<DbBaseRecyclerAdapter.BaseViewHolder>() {

    protected abstract fun onCreateView(parent: ViewGroup, viewType: Int) : BaseViewHolder
    protected abstract fun onBindView(item: T, position: Int, viewHolder: BaseViewHolder)

    override fun getItemCount() = dataSet.size

    lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        // val v = LayoutInflater.from(parent.context).inflate(toBeInflated, parent, false)
        mContext = parent.context
        return onCreateView(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBindView(dataSet[position], position, holder)
    }

    fun replaceData(data: ArrayList<T>) {
        // dataSet = data// .toMutableList()
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: ArrayList<T>) {
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    fun get(position: Int): T {
        return dataSet[position]
    }

    fun add(item: T) {
        dataSet.add(item)
        notifyItemInserted(dataSet.size - 1)
    }

    fun removeAt(index: Int) {
        dataSet.removeAt(index)
        notifyItemRemoved(index)
    }

    fun removeAll() {
        val size = dataSet.size
        dataSet.clear()
        notifyItemRangeRemoved(0, size)
    }


    open class BaseViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

}