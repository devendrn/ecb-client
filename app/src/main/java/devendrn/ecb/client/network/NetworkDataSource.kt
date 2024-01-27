package devendrn.ecb.client.network

import devendrn.ecb.client.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetworkDataSource(
    private val networkManager: NetworkManager
) {
    val userData: Flow<UserData> = flow {
        emit(UserData(false))
    }

    //private val scrapper: Scrapper = Scrapper(networkManager)

    fun getProfileDetails() {

    }

    fun getSubjectDetails() {

    }

    fun getAttendanceDetails() {

    }
}