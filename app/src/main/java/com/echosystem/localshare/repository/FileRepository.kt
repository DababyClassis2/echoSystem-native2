package com.echosystem.localshare.repository

import android.content.Context
import android.webkit.MimeTypeMap
import com.echosystem.localshare.model.SharedFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val rootDir = File(context.getExternalFilesDir(null), "LocalShare/received").apply { mkdirs() }
    private val tempDir = File(context.cacheDir, "uploads_tmp").apply { mkdirs() }
    private val _files = MutableStateFlow<List<SharedFile>>(emptyList())
    val files = _files.asStateFlow()

    fun listFiles(): List<SharedFile> = _files.value

    fun getFile(id: String): File? = _files.value.find { it.id == id }?.let { File(it.absolutePath) }

    fun saveFile(name: String, stream: java.io.InputStream, mimeType: String, deviceId: String): SharedFile {
        val id = UUID.randomUUID().toString()
        val file = File(rootDir, name)
        stream.use { input -> file.outputStream().use { output -> input.copyTo(output) } }
        val sharedFile = SharedFile(id, name, file.length(), mimeType, file.absolutePath, deviceId)
        _files.value = _files.value + sharedFile
        return sharedFile
    }

    fun finalizeChunkedFile(name: String, path: String, mimeType: String, deviceId: String): SharedFile {
        val file = File(path)
        val id = UUID.randomUUID().toString()
        val sharedFile = SharedFile(id, name, file.length(), mimeType, file.absolutePath, deviceId)
        _files.value = _files.value + sharedFile
        return sharedFile
    }

    fun deleteFile(id: String) {
        val file = _files.value.find { it.id == id }
        file?.let {
            File(it.absolutePath).delete()
            _files.value = _files.value.filter { f -> f.id != id }
        }
    }
}
