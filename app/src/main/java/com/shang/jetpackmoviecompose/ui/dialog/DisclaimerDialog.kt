package com.shang.jetpackmoviecompose.ui.dialog

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DisclaimerDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .padding(0.dp)
                .size(300.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp, horizontal = 8.dp)) {
                Text(
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    text = "1、“火箭電影”部分文章資訊來源於網路轉載是出於傳遞更多資訊之目的，並不意味著贊同其觀點或證實其內容的真實性。如其他媒體、APP或個人從本網下載使用，必須保留本網註明的“稿件來源”，並自負版權等法律責任。如對稿件內容有疑議，請及時與我們聯絡。\n" +
                            "\n2、“火箭電影”致力於提供合理、準確、完整的資訊資訊，但不保證資訊的合理性、準確性和完整性，且不對因資訊的不合理、不準確或遺漏導致的任何損失或損害承擔責任。本APP所有資訊僅供參考，不做交易和服務的根據， 如自行使用本網資料發生偏差，本站概不負責，亦不負任何法律責任。\n" +
                            "\n3、任何由於黑客攻擊、計算機病毒侵入或發作、因政府管制而造成的暫時性關閉等影響網路正常經營的不可抗力而造成的損失，本APP均得免責。由於與本APP連結的其它APP所造成之個人資料洩露及由此而導致的任何法律爭議和後果，本APP均得免責。\n" +
                            "\n4、本APP如因系統維護或升級而需暫停服務時，將事先公告。若因線路及非本公司控制範圍外的硬體故障或其它不可抗力而導致暫停服務，於暫停服務期間造成的一切不便與損失，本APP不負任何責任。\n" +
                            "\n5、本APP使用者因為違反本宣告的規定而觸犯中華人民共和國法律的，一切後果自己負責，本APP不承擔任何責任。\n" +
                            "\n6、凡以任何方式登陸本APP或直接、間接使用本APP資料者，視為自願接受本APP宣告的約束。\n" +
                            "\n7、本宣告未涉及的問題參見國家有關法律法規，當本宣告與國家法律法規衝突時，以國家法律法規為準。\n" +
                            "\n8、本APP如無意中侵犯了哪個媒體或個人的智慧財產權，請來信或來電告之，APP將立即給予刪除。 \"\n"
                )
            }
        }
    }
}