package com.shang.datastore.mapper

import com.shang.model.ConfigurationBean
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo

/**
 * ConfigurationMapper 的單元測試
 *
 * 驗證 ConfigurationBean 與 ConfigurationProto 之間的雙向轉換
 */
@DisplayName("ConfigurationMapper Tests")
class ConfigurationMapperTest {

    @Test
    @DisplayName("ConfigurationBean 轉換為 Proto 後，欄位應完整保留")
    fun `toProto should preserve all fields`() {
        // Given
        val bean = ConfigurationBean(
            changeKeys = listOf("images", "adult"),
            images = ConfigurationBean.Images(
                backdropSizes = listOf("w300", "w780"),
                baseUrl = "http://image.tmdb.org/t/p/",
                logoSizes = listOf("w45"),
                posterSizes = listOf("w92"),
                profileSizes = listOf("w45"),
                secureBaseUrl = "https://image.tmdb.org/t/p/",
                stillSizes = listOf("w92"),
            ),
        )

        // When
        val proto = bean.toProto()

        // Then
        expectThat(proto.changeKeysList).containsExactly("images", "adult")
        expectThat(proto.images.baseUrl).isEqualTo("http://image.tmdb.org/t/p/")
        expectThat(proto.images.secureBaseUrl).isEqualTo("https://image.tmdb.org/t/p/")
        expectThat(proto.images.backdropSizesList).containsExactly("w300", "w780")
        expectThat(proto.images.logoSizesList).containsExactly("w45")
        expectThat(proto.images.posterSizesList).containsExactly("w92")
        expectThat(proto.images.profileSizesList).containsExactly("w45")
        expectThat(proto.images.stillSizesList).containsExactly("w92")
    }

    @Test
    @DisplayName("Proto 轉換回 ConfigurationBean 後，欄位應與原始資料相等")
    fun `toModel should round trip back to the original bean`() {
        // Given
        val original = ConfigurationBean(
            changeKeys = listOf("images"),
            images = ConfigurationBean.Images(
                backdropSizes = listOf("w300"),
                baseUrl = "http://image.tmdb.org/t/p/",
                logoSizes = listOf("w45"),
                posterSizes = listOf("w92"),
                profileSizes = listOf("w45"),
                secureBaseUrl = "https://image.tmdb.org/t/p/",
                stillSizes = listOf("w92"),
            ),
        )

        // When
        val roundTripped = original.toProto().toModel()

        // Then
        expectThat(roundTripped).isEqualTo(original)
    }

    @Test
    @DisplayName("空的 ConfigurationBean 轉換時應保持空清單與空字串")
    fun `toProto should handle empty configuration`() {
        // Given
        val bean = ConfigurationBean()

        // When
        val proto = bean.toProto()

        // Then
        expectThat(proto.changeKeysList).isEqualTo(emptyList())
        expectThat(proto.images.baseUrl).isEqualTo("")
    }
}
