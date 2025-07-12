package com.shang.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shang.common.exception.NetworkException

/**
 * 錯誤畫面組件
 *
 * @param modifier 修飾符
 * @param onRetry 重試回調函式
 * @param errorText 錯誤文字資源 ID
 * @param retryText 重試按鈕文字資源 ID
 * @param throwable 當前異常，若為 null 則使用預設錯誤文字
 */
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    throwable: Throwable? = null,
    onRetry: () -> Unit = {},
    errorText: Int = throwable?.toErrorText() ?: R.string.default_error_text,
    retryText: Int = R.string.retry_button_text,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = stringResource(errorText),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(horizontal = 32.dp),
        ) {
            Text(stringResource(retryText))
        }
    }
}

/**
 * 將異常轉換為對應的錯誤文字資源 ID
 *
 * 根據不同的異常類型返回相應的錯誤文字，提供使用者友善的錯誤訊息
 */
fun Throwable.toErrorText(): Int {
    return when (this) {
        // NetworkException 處理
        is NetworkException.HttpError -> when (this.httpCode) {
            in 400..499 -> R.string.server_error_text // 客戶端錯誤
            in 500..599 -> R.string.server_error_text // 伺服器錯誤
            else -> R.string.default_error_text
        }
        is NetworkException.ConnectionError -> R.string.network_error_text
        is NetworkException.TimeoutError -> R.string.timeout_error_text
        is NetworkException.ParseError -> R.string.server_error_text
        is NetworkException.UnknownError -> R.string.unknown_error_text

        // 其他未知錯誤
        else -> R.string.default_error_text
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(
        onRetry = { },
    )
}
