package com.phissy.restaurantplug.repository.repo.user

import androidx.lifecycle.LiveData

import com.phissy.restaurantplug.repository.api.ApiServices
import com.phissy.restaurantplug.repository.api.network.NetworkResource
import com.phissy.restaurantplug.repository.api.network.Resource
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiServices: ApiServices
) {

//    fun login(
//        email: String,
//        password: String,
//        deviceName: String,
//        deviceModel: String,
//        deviceToken: String
//    ): LiveData<Resource<UserResponse>> {
//        return object : NetworkResource<UserResponse>() {
//            override fun createCall(): LiveData<Resource<UserResponse>> {
//                return apiServices.login(
//                    createJsonRequestBody(
//                        "email" to email,
//                        "password" to password,
//                        "device_name" to deviceName,
//                        "device_model" to deviceModel,
//                        "operating_system" to "Android",
//                        "device_token" to deviceToken
//                    )
//                )
//            }
//        }.asLiveData()
//    }
//
//    fun checkEmailPhoneAvailable(
//        email: String,
//        phone: String,
//    ): LiveData<Resource<UserResponse>> {
//        return object : NetworkResource<UserResponse>() {
//            override fun createCall(): LiveData<Resource<UserResponse>> {
//                return apiServices.checkEmail(
//                    createJsonRequestBody(
//                        "email" to email,
//                        "phone" to phone
//                    )
//                )
//            }
//        }.asLiveData()
//    }
//
//    fun register(
//        name: String,
//        email: String,
//        password: String,
//        dateOfBirth: String,
//        isNewsletter: Int,
//        rentingType: String,
//        phone: String,
//        deviceName: String,
//        deviceModel: String,
//        deviceToken: String
//    ): LiveData<Resource<UserResponse>> {
//        return object : NetworkResource<UserResponse>() {
//            override fun createCall(): LiveData<Resource<UserResponse>> {
//                return apiServices.register(
//                    createJsonRequestBody(
//                        "name" to name,
//                        "email" to email,
//                        "phone" to phone,
//                        "password" to password,
//                        "birthdate" to dateOfBirth,
//                        "is_newsletter" to isNewsletter.toString(),
//                        "type" to rentingType,
//                        "device_name" to deviceName,
//                        "device_model" to deviceModel,
//                        "operating_system" to "Android",
//                        "device_token" to deviceToken
//                    )
//                )
//            }
//        }.asLiveData()
//    }


    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(mapOf(*params)).toString()
        )

}