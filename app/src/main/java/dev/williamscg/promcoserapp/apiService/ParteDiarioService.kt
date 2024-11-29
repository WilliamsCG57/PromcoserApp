package dev.williamscg.promcoserapp.apiService

import dev.williamscg.promcoserapp.model.RequestNewParteDiario
import dev.williamscg.promcoserapp.model.TarjetaParteDiarioModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ParteDiarioService {
    @GET("api/ParteDiario/GetAllActivePending/{userId}")
    suspend fun getParteDiario(@Path("userId") userId: Int): List<TarjetaParteDiarioModel>

    @POST("api/ParteDiario/Create")
    suspend fun crearParteDiario(@Body newParteDiario: RequestNewParteDiario): Response<Unit>
}
