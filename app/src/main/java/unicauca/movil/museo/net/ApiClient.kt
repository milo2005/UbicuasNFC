package unicauca.movil.museo.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{

    private val retrofit:Retrofit = Retrofit.Builder()
            //.baseUrl("http://10.0.2.2:3000") // Emulador
            .baseUrl("http://192.168.128.47:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val pinturas:PinturaApi = retrofit.create(PinturaApi::class.java)

}