// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // 添加这一行来声明 Google Services 插件
    id("com.google.gms.google-services") version "4.4.3" apply false // 你可以检查并使用最新的插件版本
}
