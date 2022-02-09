package com.nadosunbae_android.app.presentation.ui.sign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignViewModel : ViewModel() {


 var text = MutableLiveData<String>()
 var firstMajor = MutableLiveData<String>()
 var firstMajorPeriod =  MutableLiveData<String>()
 var secondMajor =  MutableLiveData<String>()
 var secondMajorPeriod =  MutableLiveData<String>()
 var deviceToken = MutableLiveData<String>()
}
