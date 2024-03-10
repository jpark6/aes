package bspark.aes

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "secret")
data class SecretProperties(
  val aesKey: String,
  val iv: String,
  val encAesKey: String?,
  val encIv: String?
)