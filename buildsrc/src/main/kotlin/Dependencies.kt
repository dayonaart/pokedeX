object Dependencies {
    const val activityCompose =
        "androidx.activity:activity-compose:${Versions.composeActivityVersion}" /* compose and material */
    const val composeBoomPlatform =
        "androidx.compose:compose-bom:${Versions.composeBoomVersion}" /* compose and material */
    const val composeTestImpl =
        "androidx.compose:compose-bom:${Versions.composeBoomVersion}" /* compose and material */
    const val composeUi = "androidx.compose.ui:ui" /* compose and material */
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics" /* compose and material */
    const val composeToolingPreview =
        "androidx.compose.ui:ui-tooling-preview" /* compose and material */
    const val composeMaterial3 = "androidx.compose.material3:material3" /* compose and material */
    const val composeMaterial = "androidx.compose.material:material" /* compose and material */
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4" /* compose and material */
    const val composeUiToolingDebugImpl =
        "androidx.compose.ui:ui-tooling" /* compose and material */
    const val composeUiTestManifestDebugImpl =
        "androidx.compose.ui:ui-test-manifest" /* compose and material */
    const val hilt =
        "com.google.dagger:hilt-android:${Versions.daggerHiltVersion}"   /* dagger hilt */
    const val hiltKapt =
        "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltVersion}" /* dagger hilt */
    const val room = "androidx.room:room-runtime:${Versions.roomVersion}" /* Room Database */
    const val roomAnnotationProcessor =
        "androidx.room:room-compiler:${Versions.roomVersion}"  /* Room Database */
    const val roomKsp = "androidx.room:room-compiler:${Versions.roomVersion}" /* Room Database */
    const val navCompose =
        "androidx.navigation:navigation-compose:${Versions.navVersion}" /* Nav Compose */
    const val retrofit =
        "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"  /* Network */
    const val gsonRetrofit =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonVersion}"  /* Network */
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"  /* GSON */
    const val okHttpPlatform =
        "com.squareup.okhttp3:okhttp-bom:${Versions.okHttpBoomVersion}"  /* Network */
    const val okHttp = "com.squareup.okhttp3:okhttp"  /* Network */
    const val okHttpLogInterceptor = "com.squareup.okhttp3:logging-interceptor"  /* Network */
    const val coilCompose =
        "io.coil-kt:coil-compose:${Versions.coilComposeVersion}" /* COIL COMPOSE*/
    const val coilGif = "io.coil-kt:coil-gif:${Versions.coilGifVersion}" /* COIL GIF*/
}