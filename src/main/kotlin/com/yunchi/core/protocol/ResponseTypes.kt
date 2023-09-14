package com.yunchi.core.protocol

import com.yunchi.core.protocol.orm.GoodsTable
import com.yunchi.core.protocol.orm.IOType
import com.yunchi.core.protocol.orm.UserType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.ktorm.dsl.QueryRowSet
import java.time.Instant

typealias SigninResponse = AutoSignInArgument

@Serializable
data class UserInfoResponse(
    val name: String,
    val type: String
)

@Serializable
data class GoodsDetail(
    val name: String,
    val price: Int,
    val publisherType: UserType,
    val ioType: IOType
){companion object{
    fun of(row: QueryRowSet): GoodsDetail{
        return GoodsDetail(
            row[GoodsTable.name]!!,
            row[GoodsTable.money]!!,
            row[GoodsTable.publisherType]!!,
            row[GoodsTable.ioType]!!
        )
    }
}}


@Serializable
data class MessageChunk(
    val senderId: Long,
    val content: String,
    val time: Long
)

@Serializable
data class ErrResponse(
    val reason: String
)


suspend inline fun <reified T> ApplicationCall.respondJson(
    response: T,
    status: HttpStatusCode? = null
){
    this.respondText(
        Json.encodeToString(response),
        ContentType.parse("application/json"),
        status
    )
}

suspend inline fun ApplicationCall.respondErr(
    reason: String,
    status: HttpStatusCode = HttpStatusCode.BadRequest
){
    this.respondText(
        Json.encodeToString(ErrResponse(reason)),
        ContentType.parse("application/json"),
        status
    )
}