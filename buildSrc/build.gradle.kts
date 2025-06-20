plugins {
    `kotlin-dsl`
}

repositories{
    google()
    mavenCentral()
    gradlePluginPortal()
}


dependencies{
    api(kotlin("gradle-plugin:2.1.21"))
    implementation("com.android.tools.build:gradle:8.9.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.21")
}