package devendrn.ecb.client.data

import android.content.Context
import devendrn.ecb.client.database.EcDatabase
import devendrn.ecb.client.network.NetworkDataSource
import devendrn.ecb.client.network.NetworkManager

class AppContainer(private val context: Context) {

    val networkManager: NetworkManager by lazy {
        NetworkManager(
            userDao = EcDatabase.getDatabase(context).userDao()
        )
    }

    private val localDataSource: LocalDataSource by lazy {
        LocalDataSource(
            profileDao = EcDatabase.getDatabase(context).profileDao(),
            subjectDao = EcDatabase.getDatabase(context).subjectDao()
        )
    }

    private val networkDataSource: NetworkDataSource = NetworkDataSource(networkManager)

    val ecRepository: EcRepository = EcRepository(localDataSource, networkDataSource)
}