package devendrn.ecb.client.data

import devendrn.ecb.client.model.UserData
import devendrn.ecb.client.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow

class EcRepository(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: NetworkDataSource
) {
    val userData: Flow<UserData> = networkDataSource.userData
}