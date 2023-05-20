package com.example.triplan

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "2af358120f051fdcfe738ee3768e7d71")
    }
}