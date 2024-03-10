package bspark.aes

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class UtilTest {
  @Test
  @DisplayName("랜덤 문자열 생성 테스트")
  fun makeRandStrTest() {
    val length = 100
    val randStr = Util.makeRandomStr(100)
    println("randStr: $randStr, length: ${randStr.length}")
    Assertions.assertThat(randStr.length).isEqualTo(length)
  }
}
