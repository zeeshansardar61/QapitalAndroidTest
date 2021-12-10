package com.example.qapitaltest.network

import com.example.qapitaltest.entity.ActivitiesResponse
import com.example.qapitaltest.entity.UserResponse
import com.google.gson.*
import io.reactivex.Observable
import org.joda.time.DateTime
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Type


interface ApiInterface {

    @GET("activities")
    fun getActivities(
        @Query("from") from: String,
        @Query("to") to: String
    ): Observable<ActivitiesResponse>


    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Observable<UserResponse>

    @GET("users")
    fun getUsers(): Observable<ArrayList<UserResponse>>

}


class DateTimeTypeConverter : JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    override fun serialize(src: DateTime, srcType: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.toString())
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext): DateTime {
        return DateTime(json.asString)
    }
}