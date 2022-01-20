package com.nadosunbae_android.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.sign.SelectableData

fun Activity.showCustomDropDown(
    view: View, width: Int, selectedItem: Int, dataList: MutableList<SelectableData>
) = showCustomDropDownByContext(this, layoutInflater, view, width, selectedItem, dataList)


fun Fragment.showCustomDropDown(
    view: View, width: Int, selectedItem: Int, dataList: MutableList<SelectableData>
) = showCustomDropDownByContext(requireContext(), layoutInflater, view, width, selectedItem, dataList)


private fun showCustomDropDownByContext(context: Context, layoutInflater: LayoutInflater,
    view: View, width: Int, selectedItem: Int, dataList: MutableList<SelectableData>) {

    val inflater = layoutInflater.inflate(R.layout.view_drop_down, null, false)
    val popup = PopupWindow(inflater, width, RelativeLayout.LayoutParams.WRAP_CONTENT, true)

    val recyclerView: RecyclerView = inflater.findViewById(R.id.rv_drop_down)
    recyclerView.addItemDecoration(
        CustomDecoration(1.dpToPxF, 0f, context.getColor(R.color.gray_1))
    )

    val adapter = CustomDropDownAdapter()
    recyclerView.adapter = adapter

    adapter.dataList.addAll(dataList)
    adapter.notifyDataSetChanged()

    popup.showAsDropDown(view, 0, 0)
}
