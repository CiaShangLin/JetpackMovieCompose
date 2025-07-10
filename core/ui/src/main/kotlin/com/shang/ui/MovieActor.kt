package com.shang.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.shang.designsystem.component.JMAsyncImage

/**
 *
 */
@Composable
fun MovieActor(
    model: Any,
    modifier: Modifier = Modifier,
) {
    JMAsyncImage(
        model = model,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
            .padding(2.dp),
        clipToBounds = true,
    )
}
