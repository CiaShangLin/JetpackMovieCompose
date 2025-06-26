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
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.9.0")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.4")
}