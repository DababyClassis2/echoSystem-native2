package com.echosystem.localshare.server.routes

import com.echosystem.localshare.model.SharedFile
import com.echosystem.localshare.repository.FileRepository
import com.echosystem.localshare.server.ServerEventBus
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRoutes @Inject constructor(
    private val fileRepository: FileRepository,
    private val serverEventBus: ServerEventBus
) {
    private val sessionStore = ConcurrentHashMap<String, RandomAccessFile>()

    fun register(route: Route) {
        route.route("/files") {
            get {
                call.respond(fileRepository.listFiles())
            }
            get("/{id}/download") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val file = fileRepository.getFile(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.response.header(HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, file.name).toString())
                call.respondFile(file)
            }
            post("/upload") {
                val multipart = call.receiveMultipart()
                val savedFiles = mutableListOf<SharedFile>()
                multipart.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        // Simplified saving, real implementation needs proper stream handling
                        val file = fileRepository.saveFile(part.originalFileName ?: "unknown", part.streamProvider(), part.contentType?.toString() ?: "application/octet-stream", "unknown")
                        savedFiles.add(file)
                        serverEventBus.emit(com.echosystem.localshare.model.ServerEvent.FileReceived(file))
                    }
                    part.dispose()
                }
                call.respond(HttpStatusCode.Created, savedFiles)
            }
        }
    }
}
