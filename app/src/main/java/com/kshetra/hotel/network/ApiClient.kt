package com.kshetra.hotel.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.kshetra.hotel.BuildConfig
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.android.gms.common.util.CollectionUtils

object ApiClient {
    var retrofitCustom: Retrofit? = null
    private var API_INSTANCE: API? = null
    val BASE_URL = "http://bookingapp.cgtec.in/api/api/"


    val BASE_URL_TWELIO = "http://docall.io:3000/"

    val BASE_URL_COUNTRY_CODE = "http://ip-api.com/"


    private val TIMEOUT_SECONDS: Long = 45

    private var retrofit: Retrofit? = null
    private var dispatcher: Dispatcher? = null
    private var client: OkHttpClient? = null


    fun cancelAllConnections() {
        okHttpClient?.dispatcher?.cancelAll()
    }

    fun reset() {
        cancelAllConnections()
        client = null
        dispatcher = null
        retrofit = null
    }

    /**
     * Retrofit singleton instance used to call all the APIs throughout the project
     *
     * @return
     */
    fun getClient(): Retrofit {
        if (retrofit == null) {
            val client = okHttpClient
            val gson = gson
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }

    fun getClientWithCustomBaseUrl(baseUrl: String): Retrofit {
        if (retrofitCustom == null
            || retrofitCustom!!.baseUrl().toUri().toString() != baseUrl
        ) {
            val client = okHttpClient
            val gson = gson
            retrofitCustom = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client!!)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofitCustom!!
    }

    private//						return f.getName().toLowerCase().contains("SerializedName".toLowerCase());
    //						return f.getName().toLowerCase().contains("SerializedName".toLowerCase());
    val gson: Gson
        get() = GsonBuilder()
            .setLenient()
            .addSerializationExclusionStrategy(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {

                    val annotations = f.annotations
                    if (!CollectionUtils.isEmpty(annotations)) {
                        for (annotation in annotations) {
                            if (annotation is SerializedName) {
                                return false
                            }
                        }
                    }
                    return true

                }

                override fun shouldSkipClass(aClass: Class<*>): Boolean {
                    return false
                }
            })
            .addDeserializationExclusionStrategy(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    val annotations = f.annotations
                    if (!CollectionUtils.isEmpty(annotations)) {
                        for (annotation in annotations) {
                            if (annotation is SerializedName) {
                                return false
                            }
                        }
                    }
                    return true
                }

                override fun shouldSkipClass(aClass: Class<*>): Boolean {
                    return false
                }
            })
            .create()

    /*.addInterceptor(new CurlInterceptor())
                        .addInterceptor(interceptor)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                okhttp3.Request.Builder requestBuilder = chain.request().newBuilder();
                                requestBuilder.header("Content-Type", "application/json");
                                return chain.proceed(requestBuilder.build());
                            }
                        })*///Logger
    private var okHttpClient: OkHttpClient? = null
        get() {
            if (client == null) {
                val dispatcher = getDispatcher()
                if (!BuildConfig.DEBUG) {
//                    val interceptor = HttpLoggingInterceptor()
//                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    client =
                        OkHttpClient.Builder().readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                            .dispatcher(dispatcher)
                            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                            .addInterceptor { chain ->
                                val requestBuilder = chain.request().newBuilder()
                                requestBuilder.header("Content-Type", "application/json")
//                                requestBuilder.header("OSVERSION","1")
//                                requestBuilder.header("APPVERSION","234")
                                chain.proceed(requestBuilder.build())
                            }
//                            .addInterceptor(interceptor)
                            .build()
                } else {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    client =
                        OkHttpClient.Builder().readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                            .dispatcher(dispatcher)
                            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                            .addInterceptor { chain ->
                                val requestBuilder = chain.request().newBuilder()
                                requestBuilder.header("Content-Type", "application/json")
//                                requestBuilder.header("OSVERSION","1")
//                                requestBuilder.header("APPVERSION","234")
                                chain.proceed(requestBuilder.build())
                            }
                            .addInterceptor(interceptor)
                            .build()

                }
            }
            return client!!
        }

    private fun getDispatcher(): Dispatcher {
        if (dispatcher == null) {
            dispatcher = Dispatcher()
            dispatcher!!.maxRequests = 150
            dispatcher!!.maxRequestsPerHost = 20
        }
        return dispatcher!!
    }

    fun getAPI(): API {
        if (API_INSTANCE == null) {
            API_INSTANCE = getClient().create(API::class.java)
        }
        return API_INSTANCE!!
    }


}