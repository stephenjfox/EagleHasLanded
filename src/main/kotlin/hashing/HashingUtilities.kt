package hashing

import java.security.MessageDigest
import kotlin.experimental.and

/**
 * Created by stephen on 11/14/15.
 */
object HashingUtilities {
    private const val SHA256 = "SHA-256"

    fun getMessageDigest(): MessageDigest {
        return MessageDigest.getInstance(SHA256)
    }

    fun getPreppedMessageDigest(prepString: String): MessageDigest {
        // NOTE: might need UTF_16
        return getMessageDigest().apply {
            update(prepString.toByteArray())
        }
    }

    fun getStringBuilderFromMessageDigest(someHashGuy: MessageDigest): StringBuilder? {
        val builder = StringBuilder()
        for (b in someHashGuy.digest()) {
            builder.append((b.and(0xFF.toByte()) + 0x100).toString(16).substring(1))
        }
        return builder
    }

}