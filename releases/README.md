# 📦 手動發布 APK 使用說明

## 使用方式

當自動編譯失敗時，可以使用此目錄手動上傳 APK 進行發布：

1. 在本地編譯 APK：
   ```bash
   ./gradlew assembleProdRelease
   ```

2. 將編譯好的 APK 檔案複製到此目錄

3. 提交並推送到 GitHub：
   ```bash
   git add releases/
   git commit -m "Add APK for manual release"
   git push
   ```

4. 前往 GitHub Actions，手動觸發「📦 手動上傳 APK 發布」工作流程

## 檔案命名建議

建議 APK 檔案命名格式：`JetpackMovieCompose-{version}-release.apk`

例如：`JetpackMovieCompose-v1.0.0-release.apk`
