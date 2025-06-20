package com.shang.buildsrc.deps

object TestDependencies {
    const val JUNIT = "junit:junit:${DependenciesVersions.JUNIT}"

    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:${DependenciesVersions.ANDROIDX_TEST_JUNIT}"
    const val ANDROIDX_ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${DependenciesVersions.ANDROIDX_ESPRESSO_CORE}"

    const val ANDROIDX_COMPOSE_UI_TEST = "androidx.compose.ui:ui-test-junit4:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${DependenciesVersions.COMPOSE}"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:${DependenciesVersions.COMPOSE}"
}