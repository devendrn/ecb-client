package devendrn.ecb.client.network

import devendrn.ecb.client.network.scrapper.parser.getProfileData

class NetworkDataSource(
    private val networkManager: NetworkManager
) {
    fun getProfileDetails(): List<Pair<String, String>> {
        return networkManager.getProfileData().map { it.toPair() }
    }

    fun getSubjectDetails() {

    }

    fun getAttendanceDetails() {

    }
}