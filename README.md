# JetpackMovieCompose

### JetpackMovie改用Compose寫寫看。

https://developer.themoviedb.org/reference/account-details

##### Library
1. DB:Room
2. DI:Hilt
3. Network:retrofit+coroutine+moshi+sandwich
4. UI:Material3+Compose

##### 架構 : MVVM
##### 主題 : Light，Dark

## [Demo APK](https://github.com/CiaShangLin/JetpackMovieCompose/blob/master/app/release/app-release.apk "Demo APK")


## 心得
- Hilt<br>
  Google爸爸出品前身是Dagger可能是因為學習曲線確實太難太複雜,所以Jetpack的時候就推出Hilt取代他,在使用上確實是比Koin相對複雜一點,但是感覺支援的範圍也比較廣,因為是Google本身出的所以也會跟其他的Library結合,例如:Compose就有hiltViewModel,navigation也有,但整體來說使用上確實比較複雜一點,有時候看錯誤的Log可能會看不懂,小專案可以用Koin如果是大的專案建議用Hilt
  <br>

- Theme<br>
  Compose的Theme用起來相對xml感覺清楚簡化一點,只不過用Material的調色盤真的痛苦,那些預設的參數名稱根本看起來就沒感覺,還不如自己自定義比較實在一點,因為從xml變成kotlin code的操作感覺就像Gradle進化到DSL那邊感覺
  <br>

- Compose<br>
  先說在使用上確實比xml方便一點點,理論上compose在畫ui可能可以比xml快一點,整體的學習難度我覺得還好,如果有Flutter或是網頁的經驗,可能都比第一次接觸Hilt還簡單一點,因為之前學過Flutter所以寫Compose上沒什麼感覺到太痛苦,而且因為Compose是比較for Android所以在很多東西上都有簡化,像是有constraintlayout-compose可以用,在真的很複雜的UI的時候就很方便
  但是由於變成聲明式UI所以每個UI都是一個@Composable註解的function不能繼承,所以要共用一個UI的時候在設計上可能要考慮傳入的UI參數怎麼設定,對於之前都是使用xml在寫可能會很不習慣
  整體來說如果是寫寫自己的小專案可能可以用,公司的大專案就省省比較實在,光說上面的不能繼承的問題就不能存在兩種不同的寫法,例如:BaseXXXViewHolder,另一個fun BaseXXXViewHolder,第三方的UI類型的Library有沒有支援Compose也是問題,例如:播放器之類的,舊專案xml混compose不現實,新專案純compose變成可能有些第三方Library沒得用,而且compose對於UI的順暢度頂多跟xml五五開也沒比較順暢,效能也沒比較省實在沒理由去用compose,Google推出的聲明式UI寫法,其他語言也都推聲明式UI寫法所以Google也想推吧,
  <br>


