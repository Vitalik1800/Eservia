package com.eservia.model.remote.rest.file

import com.eservia.model.remote.rest.file.services.photo.PhotoService
import com.eservia.model.remote.rest.retrofit.CallAdapterFactory
import com.eservia.model.remote.rest.retrofit.RetrofitBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class FileServer(private val url: String, private val okHttpClient: OkHttpClient) {

    private var retrofit: Retrofit? = null
    private val services: MutableMap<String, Any> = hashMapOf()

    private fun provideRetrofit(): Observable<Retrofit> {
        return Observable.fromCallable {
            synchronized(this) {
                if (retrofit == null) {
                    retrofit = RetrofitBuilder.createRetrofit(
                            url,
                            okHttpClient,
                            CallAdapterFactory.create()
                    )
                }
                retrofit!!
            }
        }
    }

    fun providePhotoService(): Observable<PhotoService> {
        return provideService(PhotoService::class.java)
    }

    private fun <T : Any> addService(key: String, service: T) {
        synchronized(this) {
            services[key] = service
        }
    }

    private fun <T : Any> provideService(clazz: Class<T>): Observable<T> {
        synchronized(this) {
            val key = clazz.simpleName
            val value = services[key] as? T
            if (value != null) {
                return Observable.just(value)
            }
            return provideRetrofit()
                    .map { retrofit -> retrofit.create(clazz) }
                    .doOnNext { service -> addService(key, service as T) }
        }
    }
}
