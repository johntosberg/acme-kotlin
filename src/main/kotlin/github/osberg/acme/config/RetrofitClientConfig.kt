package github.osberg.acme.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import github.osberg.acme.api.OpenWeatherApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

class RetrofitClientConfig {

    val openWeatherApi: OpenWeatherApi = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(buildHttpClient())
            .addConverterFactory(
                    JacksonConverterFactory.create(
                            ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    )
            )
            .build()
            .create(OpenWeatherApi::class.java)

    private fun buildHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build()
    }

    class ApiKeyInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalUrl = original.url()
            val newUrl = originalUrl.newBuilder()
                    .addQueryParameter("appid", "eb039d246f6f68ac492986f532d96630")

                    .build()
            return chain.proceed(original.newBuilder().url(newUrl).build())
        }
    }
}
