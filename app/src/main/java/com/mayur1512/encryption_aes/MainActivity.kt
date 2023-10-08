package com.mayur1512.encryption_aes

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity() {

    private lateinit var secretKey: SecretKey
    private lateinit var iv: IvParameterSpec

    private lateinit var edtEncrypt: EditText
    private lateinit var txtEncrypt: TextView
    private lateinit var txtDecrypt: TextView
    private lateinit var btnEncrypt: Button
    private lateinit var btnDecrypt: Button

    private var encryptedText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aes)

        edtEncrypt = findViewById(R.id.edt_encrypt)
        txtEncrypt = findViewById(R.id.txt_encrypted)
        txtDecrypt = findViewById(R.id.txt_decrypted)
        btnEncrypt = findViewById(R.id.btn_encrypt)
        btnDecrypt = findViewById(R.id.btn_decrypt)

        secretKey = AESHelper.generateSecretKey()
        iv = AESHelper.generateIV()

        /*** If you want to generate custom SecretKey and IV use below code
        secretKey = AESHelper.generateSecretKey("abcdefghabcdefghabcdefghabcdefgh")
        iv = AESHelper.generateIV("abcdefghabcdefgh") ***/

        btnEncrypt.setOnClickListener {
            val textToEncrypt = edtEncrypt.text.toString().trim()

            if (textToEncrypt.isNotEmpty()) {
                encryptedText = AESHelper.encrypt(
                    textToEncrypt = textToEncrypt,
                    secretKey = secretKey,
                    iv = iv
                )

                txtEncrypt.text = encryptedText
            } else {
                showToast("Please enter text to encrypt")
            }
        }

        btnDecrypt.setOnClickListener {
            if (encryptedText.isNotEmpty()) {
                val decryptedText = AESHelper.decrypt(
                    encryptedText = encryptedText,
                    secretKey = secretKey,
                    iv = iv
                )

                txtDecrypt.text = decryptedText
            } else {
                showToast("Please encrypt a text first")
            }
        }
    }
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}