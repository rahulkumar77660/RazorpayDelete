package com.example.razorpaydeleteupdate

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemInterfaceApi {

    @POST("items")
    fun postItemData(@HeaderMap reqHeader : Map<String,String?>, @Body reqBody : ItemDataForPost) : Call<ItemDataForPostResponse>

    @GET("items")
    fun getItemData(@HeaderMap reqHeader : Map<String,String?>): Call<ItemDataForGet>

    @DELETE("items/{item_id}")
    fun delteItem(@HeaderMap reqHeader : Map<String,String?>, @Path("item_id") itemId: String):Call<Void>
}