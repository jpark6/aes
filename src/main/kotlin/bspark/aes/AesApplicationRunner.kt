package bspark.aes

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(SecretProperties::class)
class AesApplicationRunner(private val secretProperties: SecretProperties): ApplicationRunner {
  private final val log: Logger = LoggerFactory.getLogger(javaClass)
  private final val keyAesKey = "Q^hgA|PEs\"g=r,\$@uGLfS9zCmMK0LS*Y"
  private final val keyIv = ".AZR[}@EnXWpeMz]";
  private final val aesKey = AES.dec(keyAesKey, keyIv, "CBC", 256, secretProperties.aesKey)
  private final val iv = AES.dec(keyAesKey, keyIv, "CBC", 256, secretProperties.iv)
  override fun run(args: ApplicationArguments?) {
    val argsArr: Array<String> = args?.sourceArgs ?: arrayOf("")
    val argsLength = argsArr.size

    when(argsLength) {
      1 -> printArgsLength1(argsArr[0].uppercase())
      2 -> printArgsLength2(argsArr[0].uppercase(), argsArr[1].uppercase(), keyAesKey, keyIv)
      4 -> printArgsLength4(argsArr[0].uppercase(), argsArr[1].uppercase(), argsArr[2], argsArr[3])
      else -> printHelpText()
    }

  }

  fun printHelpText() {
    println(
      """
       ----------------------------------------------------------------------
       Encrypt / Decrypt Text
       - Arguments:
       - Encrypt or Decrypt : [enc/dec] (case insensitive)
       - Mode               : [cbc/ecb] (case insensitive)
       - Key Length         : [128/192/256]
       - Text               : ["text for Encrypt or Decrypt"]
       - ex) java -jar aes-0.0.1.jar enc cbc 256 [text]
       ----------------------------------------------------------------------
       Encrypt / Decrypt Key/IV
       - Arguments:
       - Encrypt or Decrypt : [enckey/deckey/enciv/deciv] (case insensitive)
       - Key or IV          : key must 32 Bytes, IV must 16 Bytes.
       - ex) java -jar aes-0.0.1.jar enckey [keyText]
       ----------------------------------------------------------------------
       Make Random key text(A-Z,a-z,0-9,SpecialSymbol(like !@#$....))
       - Arguments:
       - RandStr            : Make Rand Str : [randstr] (case insensitive)
       - Langth             : [Number > 0]
       - ex) java -jar aes-0.0.1.jar randstr 32
       ----------------------------------------------------------------------
      """.trimIndent()
    )
  }

  fun printArgsLength1(cmd: String) {
    when (cmd) {
      "help", "--help" -> printHelpText()
    }
  }

  fun printArgsLength2(cmd: String, text: String, keyAesKey: String, keyIv: String) {
    when (cmd){
      "ENCKEY" -> {
        if(text.length != 32) {
          log.error("AES Key length must 32 Bytes: input key length is ${text.length} Bytes")
          return;
        }
        val encKeyText = AES.enc(keyAesKey, keyIv, "CBC", 256, text)
        println("Encrypt Key AES 256 CBC: $encKeyText")
        return
      }
      "DECKEY" -> {
        val decKeyText = AES.dec(keyAesKey, keyIv, "CBC", 256, text)
        println("Decrypt Key AES 256 CBC: $decKeyText")
        return
      }
      "ENCIV" -> {
        if(text.length != 16) {
          log.error("AES IV length must 16 Bytes: input IV length is ${text.length} Bytes")
          return;
        }
        val encIvText = AES.enc(keyAesKey, keyIv, "CBC", 256, text)
        println("Encrypt IV AES 256 CBC: $encIvText")
        return
      }
      "DECIV" -> {
        val decIvText = AES.dec(keyAesKey, keyIv, "CBC", 256, text)
        println("Decrypt IV AES 256 CBC: $decIvText")
        return
      }
      "RANDSTR" -> {
        if(!text.matches("[0-9]+".toRegex())) {
          log.error("Length must number: input length is $text")
          return;
        }
        val length = Integer.valueOf(text)
        if(length < 0) {
          log.error("Length must larger then 0: input length is $text")
          return;
        }
        val randStr = Util.makeRandomStr(length)
        println("Random Text: $randStr, length: ${randStr.length}")
        return
      }
      else -> printHelpText();
    }
  }
  fun printArgsLength4(cmd: String, mode: String, keyLengthStr: String, text: String) {
    if (cmd != "ENC" && cmd != "DEC") {
      log.error("Type must in enc/dec. (case insensitive)")
      return
    }

    if (mode != "CBC" && mode != "ECB") {
      log.error("Mode must in cbc/ecb. (case insensitive)")
      return
    }

    if (!keyLengthStr.matches("[0-9]{3}".toRegex())){
      log.error("Key Length must Number")
      return
    }
    if(keyLengthStr != "128" && keyLengthStr != "192" && keyLengthStr != "256" ) {
      log.error("Key Length must in 128/192/256.")
      return
    }
    val keyLength: Int = Integer.valueOf(keyLengthStr)

    // dev 용, log level DEBUG
    log.info("key : $aesKey");
    log.info("iv  : $iv");

    if(cmd == "ENC") {
      println("Encrypt AES Mode: $mode, key length: $keyLength, Text  : $text")
      val result: String = AES.enc(aesKey, iv, mode, keyLength, text)
      println("Encrypted Text : $result")
    } else {
      println("Decrypted AES Mode: $mode, key length: $keyLength, Text  : $text")
      println("Encrypted text: $text")
      val result: String = AES.dec(aesKey, iv, mode, keyLength, text)
      println("Original Text : $result")
    }
  }
}