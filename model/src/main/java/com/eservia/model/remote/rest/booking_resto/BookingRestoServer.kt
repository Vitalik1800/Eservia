package com.eservia.model.remote.rest.booking_resto

import com.eservia.model.remote.rest.booking_resto.services.booking.BookingService
import com.eservia.model.remote.rest.booking_resto.services.booking_settings.BookingSettingsService
import com.eservia.model.remote.rest.booking_resto.services.general_booking.GeneralBookingService
import com.eservia.model.remote.rest.retrofit.CallAdapterFactory
import com.eservia.model.remote.rest.retrofit.RetrofitBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class BookingRestoServer(private val url: String, private val okHttpClient: OkHttpClient) {

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

    fun provideBookingService(): Observable<BookingService> {
        return provideService(BookingService::class.java)
    }

    fun provideBookingSettingsService(): Observable<BookingSettingsService> {
        return provideService(BookingSettingsService::class.java)
    }

    fun provideGeneralBokingsService(): Observable<GeneralBookingService> {
        return provideService(GeneralBookingService::class.java)
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
