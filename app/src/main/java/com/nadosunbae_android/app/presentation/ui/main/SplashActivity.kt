package com.nadosunbae_android.app.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivitySplashBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.main.viewmodel.SplashViewModel
import com.nadosunbae_android.app.presentation.ui.onboarding.OnBoardingActivity
import com.nadosunbae_android.app.presentation.ui.sign.SignInActivity
import com.nadosunbae_android.app.util.FirebaseAnalyticsUtil
import com.nadosunbae_android.app.util.NadoSunBaeSharedPreference
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel : SplashViewModel by viewModel()

    private var loginSuccess = false
    private var notification = -1
    private lateinit var appUpdateManager: AppUpdateManager

    //업데이트 상태 확인인
    var installStateUpdatedListener: InstallStateUpdatedListener = object : InstallStateUpdatedListener {
        override fun onStateUpdate(state: InstallState) {

            if (state.installStatus() == InstallStatus.DOWNLOADED) {


            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (appUpdateManager != null) {
                    appUpdateManager?.unregisterListener(this)

                }
            } else {
                // 예외 처리
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseAnalyticsUtil.initFirebaseAnalytics(Firebase.analytics)
        setupTimber()
        observeSignIn()
        autoLogin()
        startLoading()
        updateVersion()
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            Timber.d("푸쉬 알림 백그라운드에서")
            notification = 6
        }


    }

    //버전 업데이트
    private fun updateVersion() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo

        appUpdateManager?.registerListener(installStateUpdatedListener)

        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->

            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)

                    requestUpdate(appUpdateInfo)
                }

                else -> {

                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener{
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // 인 앱 업데이트가 실행된 상태라면 계속 업데이트 진행
                try {
                    appUpdateManager?.startUpdateFlowForResult(it, AppUpdateType.IMMEDIATE, this, UPDATE)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }

    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        when(resultCode){
            UPDATE ->{
                if(resultCode != Activity.RESULT_OK){
                    Toast.makeText(this,"업데이트가 취소 되었습니다.",Toast.LENGTH_LONG).show()
                    finishAffinity()
                }
            }else->{

        }
        }
    }

    private fun requestUpdate(appUpdateInfo: AppUpdateInfo){
        try{
            appUpdateManager?.startUpdateFlowForResult (
                appUpdateInfo,
                AppUpdateType.FLEXIBLE, // or AppUpdateType.IMMEDIATE
                this,
                UPDATE)
        }catch (e: IntentSender.SendIntentException){
            e.printStackTrace()
        }
    }

    //snackbar
    fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            binding.clSplash,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            setActionTextColor(ResourcesCompat.getColor(resources,R.color.mint,null))
            show()
        }
    }


    //Timber 초기화
    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun observeSignIn() {
        splashViewModel.signIn.observe(this) {
            if (it.success) {
                loginSuccess = true
                NadoSunBaeSharedPreference.setAccessToken(this, it.accessToken)
                NadoSunBaeSharedPreference.setRefreshToken(this, it.refreshToken)
            }
        }
    }

    private fun autoLogin() {
        val refreshToken = NadoSunBaeSharedPreference.getRefreshToken(this)

        if (refreshToken != null && refreshToken.isNotEmpty())
            splashViewModel.postRenewalToken(refreshToken)
    }


    private fun startLoading() {
        val pref = getSharedPreferences("isFirst", MODE_PRIVATE)
        val first = pref.getBoolean("isFirst", false)

        //앱 최초 실행일 때
        if (first == false) {
            Log.d("FirstTimeCheck", ": true")
            val editor = pref.edit()
            editor.putBoolean("isFirst", true)
            editor.commit()
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        } //앱 최초 실행 아닐 때
        else {
            Log.d("FirstTimeCheck", " : false")
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = if (loginSuccess) {
                    Intent(this, MainActivity::class.java).apply {
                        putExtra("signData", splashViewModel.signIn.value?.user)
                        putExtra("bottomNavItem", notification)
                    }
                } else {
                    Intent(this, SignInActivity::class.java)
                }


                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }
    }

    companion object {
        private const val DURATION: Long = 2000
        const val UPDATE = 225
    }
}
