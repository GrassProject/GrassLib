package com.github.grassproject.grassLib.api.utilities

import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class Encryption {
    companion object {
        private const val s = "GrassProject"
        private const val s1 = "RandomSaltValue"
        private const val s2 = "AES/CBC/PKCS5Padding"
        private const val s3 = "PBKDF2WithHmacSHA256"
        private val base64Decoder = Base64.getDecoder()
        private val base64Encoder = Base64.getEncoder()

        private fun fun2(): SecretKeySpec {
            val sk1 = SecretKeyFactory.getInstance(s3)
            val spec = PBEKeySpec(s.toCharArray(), s1.toByteArray(), 65536, 256)
            val secret = sk1.generateSecret(spec)
            return SecretKeySpec(secret.encoded, "AES")
        }

        private fun fun1(): IvParameterSpec {
            val iv = ByteArray(16)
            SecureRandom().nextBytes(iv)
            return IvParameterSpec(iv)
        }

        @JvmStatic
        fun encrypt(input: String): String {
            val cipher = Cipher.getInstance(s2)
            val ivParameterSpec = fun1()
            cipher.init(Cipher.ENCRYPT_MODE, fun2(), ivParameterSpec)
            val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
            val combined = ivParameterSpec.iv + encryptedBytes
            return base64Encoder.encodeToString(combined)
        }

        @JvmStatic
        fun decrypt(input: String): String {
            val decodedBytes = base64Decoder.decode(input)
            val ivBytes = decodedBytes.copyOfRange(0, 16)
            val encryptedBytes = decodedBytes.copyOfRange(16, decodedBytes.size)
            val cipher = Cipher.getInstance(s2)
            cipher.init(Cipher.DECRYPT_MODE, fun2(), IvParameterSpec(ivBytes))
            return String(cipher.doFinal(encryptedBytes), Charsets.UTF_8)
        }
    }
}
