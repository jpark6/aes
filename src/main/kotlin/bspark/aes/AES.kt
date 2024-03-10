package bspark.aes

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES {
  companion object {
    private const val ECB: String = "AES/ECB/PKCS5PADDING"
    private const val CBC: String = "AES/CBC/PKCS5PADDING"
    private val log: Logger = LoggerFactory.getLogger(AES::class.java)

    fun enc(key: String, iv: String, mode: String, keyLength: Int, str: String): String {
      if (str.isEmpty()) {
        log.error("str is required!")
        return str
      }
      if(keyLength != 128 && keyLength != 192 && keyLength != 256) {
        log.error("key length must in 128,192,256")
        return str
      }
      if(mode.uppercase() != "ECB" && mode.uppercase() != "CBC") {
        log.error("mode is must ECB or CDC")
        return str
      }

      val aesKey: String = when(keyLength) {
        128 -> key.substring(0, 16)
        192 -> key.substring(0, 24)
        256 -> key.substring(0, 32)
        else -> key
      }

      val keySpec = SecretKeySpec(aesKey.toByteArray(charset("UTF-8")), "AES")
      val ivSpec = IvParameterSpec(iv.toByteArray(charset("UTF-8")))

      val cipher: Cipher = Cipher.getInstance(if(mode.uppercase() == "CBC") CBC else ECB)
      if(mode.uppercase() == "CBC") {
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
      } else {
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
      }
      val encByteArr: ByteArray = cipher.doFinal(str.toByteArray(charset("UTF-8")))
      return String(Base64.getEncoder().encode(encByteArr))
    }

    fun dec(key: String, iv: String, mode: String, keyLength: Int, str: String): String {
      if (str.isEmpty()) {
        log.error("str is required!")
        return str
      }
      if(keyLength != 128 && keyLength != 192 && keyLength != 256) {
        log.error("key length must in 128,192,256")
        return str
      }
      if(mode.uppercase() != "ECB" && mode.uppercase() != "CBC") {
        log.error("mode is must ECB or CDC")
        return str
      }

      val aesKey: String = when(keyLength) {
        128 -> key.substring(0, 16)
        192 -> key.substring(0, 24)
        256 -> key.substring(0, 32)
        else -> key
      }

      val keySpec = SecretKeySpec(aesKey.toByteArray(charset("UTF-8")), "AES")
      val ivSpec = IvParameterSpec(iv.toByteArray(charset("UTF-8")))

      val cipher: Cipher = Cipher.getInstance(if(mode.uppercase() == "CBC") CBC else ECB)
      if(mode.uppercase() == "CBC") {
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
      } else {
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
      }
      val decByteArr: ByteArray
      try {
        decByteArr = cipher.doFinal(Base64.getDecoder().decode(str))
        return String(decByteArr)
      } catch(e: IllegalArgumentException) {
        log.error(e.message)
        return ""
      }
    }
  }
}