plugins {
    //https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources
    //這裡不需要寫是因為buildSrc這個資料夾是Gradle特殊保留的資料夾，它會自動引入
    id("com.google.dagger.hilt.android") version deps.DependenciesVersions.HILT apply false
}