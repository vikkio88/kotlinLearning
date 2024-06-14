package org.vikkio.org.vikkio.libs

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.text.SimpleDateFormat
import java.util.*

object DateToTimestampSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeLong(value.time)
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeLong())
    }
}

object NullableDateToTimestampSerializer : KSerializer<Date?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date?", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: Date?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            encoder.encodeLong(value.time)
        }
    }

    override fun deserialize(decoder: Decoder): Date? {
        return if (decoder.decodeNotNullMark()) {
            Date(decoder.decodeLong())
        } else {
            decoder.decodeNull()
            null
        }
    }
}

object DateSerializer : KSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        val dateString = dateFormat.format(value)
        encoder.encodeString(dateString)
    }

    override fun deserialize(decoder: Decoder): Date {
        val dateString = decoder.decodeString()
        return dateFormat.parse(dateString)!!
    }
}

object NullableDateSerializer : KSerializer<Date?> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Date?", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            val dateString = dateFormat.format(value)
            encoder.encodeString(dateString)
        }
    }

    override fun deserialize(decoder: Decoder): Date? {
        return if (decoder.decodeNotNullMark()) {
            val dateString = decoder.decodeString()
            dateFormat.parse(dateString)
        } else {
            decoder.decodeNull()
            null
        }
    }
}
