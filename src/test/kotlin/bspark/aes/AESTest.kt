package bspark.aes

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@EnableConfigurationProperties(SecretProperties::class)
class AESTest(
 @Autowired private val secretProperties: SecretProperties
){

  @Test
  @DisplayName("AES 암호화 테스트")
  fun enc() {
    // val key = secretProperties.aesKey
    val key = "Q^hgA|PEs\"g=r,\$@uGLfS9zCmMK0LS*Y"
    val iv = ".AZR[}@EnXWpeMz]"
   // val iv = secretProperties.iv
    val str = secretProperties.aesKey// "hello world"//"1111111111111111"
    var encStr = ""
    var decStr = ""
    
    /*
    for (i in 0..16*16*16 step(16)) {
      if(i == 0) continue
      for(j in 0..15) {
        str += "1"
      }
      encStr = AES.enc(key, iv, "CBC", 256, str)

      println("$i, $str, $encStr")
    }
    */

    //*
    println("-------------------------------------------------")
    println("AES 암호화 테스트")
    println("-------------------------------------------------")
    println("원본: $str")
    println("-------------------------------------------------")
    encStr = AES.enc(key, iv, "ECB", 128, str)
    println("AES128 ECB 암호화: $encStr")
    decStr = AES.dec(key, iv, "ECB", 128, encStr)
    println("AES128 ECB 복호화: $decStr")
    println("-------------------------------------------------")

    encStr = AES.enc(key, iv, "ECB", 192, str)
    println("AES192 ECB 암호화: $encStr")
    decStr = AES.dec(key, iv, "ECB", 192, encStr)
    println("AES192 ECB 복호화: $decStr")
    println("-------------------------------------------------")
    encStr = AES.enc(key, iv, "ECB", 256, str)
    println("AES256 ECB 암호화: $encStr")
    decStr = AES.dec(key, iv, "ECB", 256, encStr)
    println("AES256 ECB 복호화: $decStr")
    println("-------------------------------------------------")

    println("-------------------------------------------------")
    encStr = AES.enc(key, iv, "CBC", 128, str)
    println("AES128 CBC 암호화: $encStr")
    decStr = AES.dec(key, iv, "CBC", 128, encStr)
    println("AES128 CBC 복호화: $decStr")
    println("-------------------------------------------------")
    encStr = AES.enc(key, iv, "CBC", 192, str)
    println("AES192 CBC 암호화: $encStr")
    decStr = AES.dec(key, iv, "CBC", 192, encStr)
    println("AES192 CBC 복호화: $decStr")
    println("-------------------------------------------------")
    encStr = AES.enc(key, iv, "CBC", 256, str)
    println("AES256 CBC 암호화: $encStr")
    decStr = AES.dec(key, iv, "CBC", 256, encStr)
    println("AES256 CBC 복호화: $decStr")
    println("-------------------------------------------------")
    /**/
    Assertions.assertThat(encStr).isNotEmpty()
  }
}
