package com.glodanif.bluetoothchat.data.service.message

import android.util.Base64
import com.glodanif.bluetoothchat.utils.isNumber
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.Security
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec

class Message() {

    var type: Contract.MessageType = Contract.MessageType.MESSAGE
    var uid: Long = 0
    var flag: Boolean = false
    var body: String = ""

    constructor(message: String) : this() {

        var messageText = message

        val parsedType = messageText.substring(0, messageText.indexOf(Contract.DIVIDER))
        if (!parsedType.isNumber()) {
            type = Contract.MessageType.UNEXPECTED
            return
        }

        type = Contract.MessageType.from(parsedType.toInt())
        messageText = messageText.substring(messageText.indexOf(Contract.DIVIDER) + 1, messageText.length)

        val parsedUid = messageText.substring(0, messageText.indexOf(Contract.DIVIDER))
        uid = if (parsedType.isNumber()) parsedUid.toLong() else 0
        messageText = messageText.substring(messageText.indexOf(Contract.DIVIDER) + 1, messageText.length)

        flag = messageText.substring(0, messageText.indexOf(Contract.DIVIDER)).toInt() == 1
        messageText = messageText.substring(messageText.indexOf(Contract.DIVIDER) + 1, messageText.length)

        body = messageText
    }

    constructor(uid: Long, body: String, flag: Boolean, type: Contract.MessageType) : this() {
        this.uid = uid
        this.body = body
        this.type = type
        this.flag = flag
    }

    constructor(body: String, flag: Boolean, type: Contract.MessageType) : this() {
        this.body = body
        this.type = type
        this.flag = flag
    }

    constructor(uid: Long, body: String, type: Contract.MessageType) : this() {
        this.uid = uid
        this.body = body
        this.type = type
    }

    constructor(uid: Long, flag: Boolean, type: Contract.MessageType) : this() {
        this.uid = uid
        this.flag = flag
        this.type = type
    }

    constructor(flag: Boolean, type: Contract.MessageType) : this() {
        this.flag = flag
        this.type = type
    }

    fun getDecodedMessage(): String {
        val flag = if (this.flag) 1 else 0
        return "${type.value}${Contract.DIVIDER}$uid${Contract.DIVIDER}$flag${Contract.DIVIDER}${encrypt(body,"1234")}"
    }

    fun encrypt(strToEncrypt: String, secret_key: String): String? {
        Security.addProvider((BouncyCastleProvider()))
        var keyBytes: ByteArray

        try {
            keyBytes = secret_key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = strToEncrypt.toByteArray(charset("UTF8"))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.ENCRYPT_MODE, skey)

                val cipherText = ByteArray(cipher.getOutputSize(input.size))
                var ctLength = cipher.update(
                        input, 0, input.size,
                        cipherText, 0
                )
                ctLength += cipher.doFinal(cipherText, ctLength)
                return cipherText.toString();
            }
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        } catch (ibse: IllegalBlockSizeException) {
            ibse.printStackTrace()
        } catch (bpe: BadPaddingException) {
            bpe.printStackTrace()
        } catch (ike: InvalidKeyException) {
            ike.printStackTrace()
        } catch (nspe: NoSuchPaddingException) {
            nspe.printStackTrace()
        } catch (nsae: NoSuchAlgorithmException) {
            nsae.printStackTrace()
        } catch (e: ShortBufferException) {
            e.printStackTrace()
        }

        return null
    }

    fun decryptWithAES(key: String, strToDecrypt: String?): String? {
        Security.addProvider(BouncyCastleProvider())
        var keyBytes: ByteArray

        try {
            keyBytes = key.toByteArray(charset("UTF8"))
            val skey = SecretKeySpec(keyBytes, "AES")
            val input = org.bouncycastle.util.encoders.Base64
                    .decode(strToDecrypt?.trim { it <= ' ' }?.toByteArray(charset("UTF8")))

            synchronized(Cipher::class.java) {
                val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
                cipher.init(Cipher.DECRYPT_MODE, skey)

                val plainText = ByteArray(cipher.getOutputSize(input.size))
                var ptLength = cipher.update(input, 0, input.size, plainText, 0)
                ptLength += cipher.doFinal(plainText, ptLength)
                val decryptedString = String(plainText)
                return decryptedString.trim { it <= ' ' }
            }
        } catch (uee: UnsupportedEncodingException) {
            uee.printStackTrace()
        } catch (ibse: IllegalBlockSizeException) {
            ibse.printStackTrace()
        } catch (bpe: BadPaddingException) {
            bpe.printStackTrace()
        } catch (ike: InvalidKeyException) {
            ike.printStackTrace()
        } catch (nspe: NoSuchPaddingException) {
            nspe.printStackTrace()
        } catch (nsae: NoSuchAlgorithmException) {
            nsae.printStackTrace()
        } catch (e: ShortBufferException) {
            e.printStackTrace()
        }

        return null
    }


}
