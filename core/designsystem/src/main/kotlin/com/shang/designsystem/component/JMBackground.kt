package com.shang.designsystem.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shang.designsystem.theme.Surface

@Composable
fun JMBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { },
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        content.invoke()
    }
}
