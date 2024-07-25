package com.src.taipei_travel.data.local.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Singleton


@Singleton
class SettingsSerializer : Serializer<Settings> {
    override val defaultValue = Settings()

    override suspend fun readFrom(input: InputStream): Settings =
        try {
            Json.decodeFromString(
                Settings.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw SerializationException("Unable to read Settings", serialization)
        }

    override suspend fun writeTo(t: Settings, output: OutputStream) {
        output.write(
            Json.encodeToString(Settings.serializer(), t)
                .encodeToByteArray()
        )
    }
}