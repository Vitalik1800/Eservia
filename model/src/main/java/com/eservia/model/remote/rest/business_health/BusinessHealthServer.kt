package com.eservia.model.remote.rest.business_health

import com.eservia.model.remote.rest.business_health.services.service.ServiceService
import com.eservia.model.remote.rest.business_health.services.service_group.ServiceGroupService
import com.eservia.model.remote.rest.business_health.services.staff.StaffService
import com.eservia.model.remote.rest.business_health.services.working_day.WorkingDayService
import com.eservia.model.remote.rest.retrofit.CallAdapterFactory
import com.eservia.model.remote.rest.retrofit.RetrofitBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class BusinessHealthServer(private val url: String, private val okHttpClient: OkHttpClient) {

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

    fun provideStaffService(): Observable<StaffService> {
        return provideService(StaffService::class.java)
    }

    fun provideServiceGroupService(): Observable<ServiceGroupService> {
        return provideService(ServiceGroupService::class.java)
    }

    fun provideServiceService(): Observable<ServiceService> {
        return provideService(ServiceService::class.java)
    }

    fun provideWorkingDayService(): Observable<WorkingDayService> {
        return provideService(WorkingDayService::class.java)
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
