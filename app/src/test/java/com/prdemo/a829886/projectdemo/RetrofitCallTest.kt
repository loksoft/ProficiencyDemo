package com.prdemo.a829886.projectdemo

import com.prdemo.a829886.projectdemo.datasource.AppDataRepository
import com.prdemo.a829886.projectdemo.utilities.AppUtils
import com.prdemo.a829886.projectdemo.utilities.ServiceInterface
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RetrofitCallTest {

    @Test
    @Throws(IOException::class)
    fun testRetrofit() {
        val mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
                .baseUrl(mockWebServer.url(AppUtils.BASE_URL).toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val inputStream = this.javaClass.classLoader.getResourceAsStream("mock_response.json")
        val mockResponse = inputStream.bufferedReader().use { it.readText() }
        mockWebServer.enqueue(MockResponse().setBody(mockResponse))
        val service = retrofit.create(ServiceInterface::class.java)
        val call = service.getStateDemoGraphicData()
        assert(call.execute().isSuccessful)
        mockWebServer.shutdown()
    }
}