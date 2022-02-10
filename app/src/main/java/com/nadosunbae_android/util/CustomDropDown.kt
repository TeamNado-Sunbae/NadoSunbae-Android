package com.nadosunbae_android.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.nadosunbae_android.R
import com.nadosunbae_android.model.response.sign.SelectableData


/*
    viewModel: DropDownSelectableViewModel 인터페이스를 상속한 뷰 모델
    view : 해당 뷰 아래로 드롭다운 생김
    width : 드롭 다운 너비
    selectedItem : 선택 디폴트 값 (index 아니라 id 값)
    dataList : 드롭다운에 표시될 데이터 목록

    선택한 결과를 viewModel의 dropDownSelected.value에 저장합니다 -> observe해서 적용할 것
 */
fun Activity.showCustomDropDown(
                                viewModel: DropDownSelectableViewModel,
                                view: View, width: Int, selectedItem: Int,
                                dataList: MutableList<SelectableData>) = showCustomDropDownByContext(viewModel, this, layoutInflater, view, width, selectedItem, dataList)


fun Fragment.showCustomDropDown(
                                viewModel: DropDownSelectableViewModel,
                                view: View, width: Int, selectedItem: Int,
                                dataList: MutableList<SelectableData>) = showCustomDropDownByContext(viewModel, requireContext(), layoutInflater, view, width, selectedItem, dataList)


private fun showCustomDropDownByContext(viewModel: DropDownSelectableViewModel, context: Context, layoutInflater: LayoutInflater,
                                        view: View, width: Int, selectedItemId: Int, dataList: MutableList<SelectableData>) {

    val inflater = layoutInflater.inflate(R.layout.view_drop_down, null, false)
    val popup = PopupWindow(inflater, width, RelativeLayout.LayoutParams.WRAP_CONTENT, true)

    val recyclerView: RecyclerView = inflater.findViewById(R.id.rv_drop_down)
    recyclerView.addItemDecoration(
        CustomDecoration(1.dpToPxF, 0f, context.getColor(R.color.gray_1))
    )

    // 리사이클러 뷰 어댑터
    val adapter = CustomDropDownAdapter(viewModel, selectedItemId)

    // 어댑터 리스너
    adapter.setItemClickListener(object : CustomDropDownAdapter.ItemClickListener {
        override fun onClick(view: View, position: Int) {
            popup.dismiss()
        }
    })

    recyclerView.adapter = adapter

    adapter.setMenuList(dataList)
    adapter.notifyDataSetChanged()

    popup.showAsDropDown(view, 0, 0)
}

interface DropDownSelectableViewModel {
    var dropDownSelected: MutableLiveData<SelectableData>
}