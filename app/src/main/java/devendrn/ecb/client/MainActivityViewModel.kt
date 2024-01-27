package devendrn.ecb.client

import androidx.lifecycle.ViewModel
import devendrn.ecb.client.data.EcRepository
import devendrn.ecb.client.network.NetworkStatus

class MainActivityViewModel (
    networkStatus: NetworkStatus,
    ecRepository: EcRepository
): ViewModel() {

}
