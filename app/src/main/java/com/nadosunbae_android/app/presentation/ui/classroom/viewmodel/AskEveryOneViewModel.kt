package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.domain.model.main.SelectableData

class AskEveryOneViewModel : ViewModel(), DropDownSelectableViewModel, LoadableViewModel {

    override val onLoadingEnd = MutableLiveData<Boolean>()
    override var dropDownSelected = MutableLiveData<SelectableData>()
    //
}