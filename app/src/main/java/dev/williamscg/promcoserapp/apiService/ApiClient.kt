package dev.williamscg.promcoserapp.apiService
import android.content.Context
import com.google.android.gms.common.api.Api.Client
import okhttp3.Interceptor
import dev.williamscg.promcoserapp.apiService.DetalleParteApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Response

class UnauthorizedException(message: String) : Exception(message)

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getTokenFromSharedPreferences(context)

        if (token.isNullOrEmpty()) {
            throw UnauthorizedException("Token is missing")
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }

    private fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userToken", null)
    }
}

object ApiClient {
    private const val BASE_URL = "https://aca5-38-25-111-46.ngrok-free.app/"

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    fun getDetalleParteApiService(context: Context): DetalleParteApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(context))
            .build()
            .create(DetalleParteApiService::class.java)
    }
}

object ApiClientLogIn {
    private const val BASE_URL = "https://aca5-38-25-111-46.ngrok-free.app/"

    val instance: LogInApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LogInApiService::class.java)
    }
}