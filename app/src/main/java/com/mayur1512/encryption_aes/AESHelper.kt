package com.mayur1512.encryption_aes

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESHelper {

    private const val KEYSIZE = 256 // You can also use 128 or 192 or 256 bits

    fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(KEYSIZE)
        return keyGenerator.generateKey()
    }

    fun generateSecretKey(customSecretKey: String): SecretKeySpec {
        /*** The customSecretKey can be 16, 24 or 32 depending on algorithm you are using i.e AES-128, AES-192 or AES-256 ***/
        val secretKey = SecretKeySpec(customSecretKey.toByteArray(), "AES")
        return secretKey
    }

    fun generateIV(): IvParameterSpec {
        val iv = ByteArray(16)
        val secureRandom = SecureRandom()
        secureRandom.nextBytes(iv)
        return IvParameterSpec(iv)
    }

    fun generateIV(customIV: String): IvParameterSpec {
        return IvParameterSpec(customIV.toByteArray())
    }

    fun encrypt(
        textToEncrypt: String,
        secretKey: SecretKey,
        iv: IvParameterSpec
    ): String {
        val plainText = textToEncrypt.toByteArray()

        val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

        val encrypt = cipher.doFinal(plainText)
        return Base64.encodeToString(encrypt, Base64.DEFAULT)
    }

    fun decrypt(
        encryptedText: String,
        secretKey: SecretKey,
        iv: IvParameterSpec
    ): String {
        val textToDecrypt = Base64.decode(encryptedText, Base64.DEFAULT)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING")

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)

        val decrypt = cipher.doFinal(textToDecrypt)
        return String(decrypt)
    }
}