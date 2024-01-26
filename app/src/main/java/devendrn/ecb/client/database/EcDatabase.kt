package devendrn.ecb.client.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import devendrn.ecb.client.database.dao.ProfileDao
import devendrn.ecb.client.database.dao.SubjectDao
import devendrn.ecb.client.database.dao.UserDao
import devendrn.ecb.client.database.model.ProfileEntity
import devendrn.ecb.client.database.model.SubjectEntity
import devendrn.ecb.client.database.model.UserEntity

@Database(
    entities = [UserEntity::class, SubjectEntity::class, ProfileEntity::class],
    exportSchema = false,
    version = 2
)
abstract class EcDatabase: RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun profileDao(): ProfileDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: EcDatabase? = null

        fun getDatabase(context: Context): EcDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EcDatabase::class.java, "ec_db")
                    .fallbackToDestructiveMigration()
                    //.allowMainThreadQueries()  // TODO: avoid this if possible
                    .build()
                    .also { Instance = it }
            }
        }
    }
}