package com.eservia.model.remote.rest.business

import com.eservia.model.remote.rest.business.services.address.AddressService
import com.eservia.model.remote.rest.business.services.business.BusinessService
import com.eservia.model.remote.rest.business.services.category.CategoryService
import com.eservia.model.remote.rest.business.services.cities.CitiesService
import com.eservia.model.remote.rest.business.services.comment.CommentService
import com.eservia.model.remote.rest.business.services.contact.ContactService
import com.eservia.model.remote.rest.business.services.favorite.FavoriteService
import com.eservia.model.remote.rest.business.services.gallery.GalleryService
import com.eservia.model.remote.rest.business.services.sector.SectorService
import com.eservia.model.remote.rest.business.services.working_day.WorkingDayService
import com.eservia.model.remote.rest.retrofit.CallAdapterFactory
import com.eservia.model.remote.rest.retrofit.RetrofitBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class BusinessServer(private val url: String, private val okHttpClient: OkHttpClient) {

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

    fun provideSectorService(): Observable<SectorService> {
        return provideService(SectorService::class.java)
    }

    fun provideCategoryService(): Observable<CategoryService> {
        return provideService(CategoryService::class.java)
    }

    fun provideBusinessService(): Observable<BusinessService> {
        return provideService(BusinessService::class.java)
    }

    fun provideAddressService(): Observable<AddressService> {
        return provideService(AddressService::class.java)
    }

    fun provideCitiesService(): Observable<CitiesService> {
        return provideService(CitiesService::class.java)
    }

    fun provideGalleryService(): Observable<GalleryService> {
        return provideService(GalleryService::class.java)
    }

    fun provideCommentService(): Observable<CommentService> {
        return provideService(CommentService::class.java)
    }

    fun provideFavoriteService(): Observable<FavoriteService> {
        return provideService(FavoriteService::class.java)
    }

    fun provideWorkingDayService(): Observable<WorkingDayService> {
        return provideService(WorkingDayService::class.java)
    }

    fun provideContactService(): Observable<ContactService> {
        return provideService(ContactService::class.java)
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
