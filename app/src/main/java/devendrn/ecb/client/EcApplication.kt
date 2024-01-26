package devendrn.ecb.client

import android.app.Application
import devendrn.ecb.client.data.AppContainer

class EcApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}