package devendrn.ecb.client.data

import devendrn.ecb.client.database.model.ProfileEntity
import devendrn.ecb.client.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EcRepository(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) {
    fun getProfileDetails(): Flow<List<Pair<String, String>>> {
        return localDataSource.readProfile().map {
            it.map { item ->
                item.data to item.value
            }
        }
    }

    suspend fun updateProfileDetails() {
        val profileDetails = networkDataSource.getProfileDetails()
        localDataSource.updateProfile(
            profileDetails.map {
                ProfileEntity(it.first, it.second)
            }
        )
    }

}