package com.nadosunbae_android.app.presentation.ui.classroom.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.app.util.DropDownSelectableViewModel
import com.nadosunbae_android.domain.model.main.SelectableData
import dagger.hilt.android.lifecycle.HiltViewModel


class InformationViewModel : ViewModel(), DropDownSelectableViewModel {

    override var dropDownSelected = MutableLiveData<SelectableData>()

}