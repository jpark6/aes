package bspark.aes

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.regex.Pattern
import kotlin.math.floor

class Util {
  companion object {
    private val log: Logger = LoggerFactory.getLogger(Util::class.java)
    fun makeRandomStr(length: Int): String {
      if(length < 0) {
        log.error("length must >0")
        return ""
      }
      val charArr: Array<String> = Pattern.compile("").split("01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/")
      var result = ""
      for(i in 0 until length) {
        result += charArr[floor(Math.random()*charArr.size).toInt()]
      }
      return result
    }
  }
}
