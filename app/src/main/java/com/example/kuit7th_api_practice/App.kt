package com.example.kuit7th_api_practice

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp                 //hilt를 앱 전체에서 사용할 수 있도록 초기화
class App : Application()       //앱이 실행될때 가장 먼저 실행되는 클래스
