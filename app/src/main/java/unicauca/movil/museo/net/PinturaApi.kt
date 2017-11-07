package unicauca.movil.museo.net

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import unicauca.movil.museo.model.Pintura

interface PinturaApi{

    @GET("/pinturas/{id}")
    fun getPictureInfo(@Path("id") id:String):Call<Pintura>

    @POST("/pinturas")
    fun insert(@Body pintura:Pintura):Call<SuccessRes>

}

data class SuccessRes(val success:Boolean)