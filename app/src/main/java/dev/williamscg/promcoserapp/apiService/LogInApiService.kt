package dev.williamscg.promcoserapp.apiService

import dev.williamscg.promcoserapp.model.PromcoserUser
import dev.williamscg.promcoserapp.model.UserRequestModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LogInApiService {
    @POST("api/Personal/LogIn")
    fun signIn(@Body user: UserRequestModel): Call<PromcoserUser>
}