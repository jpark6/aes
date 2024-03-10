package bspark.aes

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class AesApplicationRunner : ApplicationRunner {
  override fun run(args: ApplicationArguments?) {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    val argsArr: Array<String> = args?.sourceArgs ?: arrayOf("")
    if (argsArr.size < 4) {
      log.error(
        """
       Arguments required:
       Encrypt or Decrypt : [enc/dec] (case insensitive)
       Mode               : [cbc/ecb] (case insensitive)
       Key Length         : [128/192/256]
       Text               : ["text for Encrypt or Decrypt"]
       ex) java -jar aes-0.0.1.jar enc cbc 256 text
      """.trimIndent()
      )
      return
    }

    val type: String = argsArr[0].uppercase()
    val mode: String = argsArr[1].uppercase()
    val keyLengthStr: String = argsArr[2]
    val keyLength: Int = Integer.valueOf(keyLengthStr)
    val text: String = argsArr[3]

    if (type != "ENC" && type != "DEC") {
      log.error("Type must in enc/dec. (case insensitive)")
      return
    }

    if (mode != "CBC" && mode != "ECB") {
      log.error("Mode must in cbc/ecb. (case insensitive)")
      return
    }

    if (!keyLengthStr.matches("[0-9]{3}".toRegex())
      && keyLengthStr != "128"
      && keyLengthStr != "192"
      && keyLengthStr != "256"
    ) {
      log.error("Key Length must in 128/192/256.")
      return
    }

    if(type == "ENC") {
      println("Encrypt AES Mode: $mode, key length: $keyLength, Text  : $text")
      val result: String = AES.enc(mode, keyLength, text)
      println("Encrypted Text : $result")
    } else {
      println("Decrypted AES Mode: $mode, key length: $keyLength, Text  : $text")
      println("Encrypted text: $text")
      val result: String = AES.dec(mode, keyLength, text)
      println("Original Text : $result")
    }
  }
}