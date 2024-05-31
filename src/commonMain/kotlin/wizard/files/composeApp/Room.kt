package wizard.files.composeApp

import wizard.ProjectFile
import wizard.ProjectInfo
import wizard.packagePath

internal const val ROOM_PATH = "database/Database.kt"

class RoomDataBase(info : ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/commonMain/kotlin/${info.packagePath}/$ROOM_PATH"
    override val content: String = """
        package ${info.packageId}.database

        import androidx.room.Dao
        import androidx.room.Database
        import androidx.room.Entity
        import androidx.room.Insert
        import androidx.room.PrimaryKey
        import androidx.room.Query
        import androidx.room.RoomDatabase
        import kotlinx.coroutines.Dispatchers
        import kotlinx.coroutines.flow.Flow
        
        //TODO make Your Entity and Dao
        //See : https://developer.android.com/kotlin/multiplatform/room#creating-database
        @Database(entities = [TodoEntity::class], version = 1)
        abstract class AppDatabase : RoomDatabase() {
            abstract fun getDao(): TodoDao
        }
        
        @Dao
        interface TodoDao {
            @Insert
            suspend fun insert(item: TodoEntity)
        
            @Query("SELECT count(*) FROM TodoEntity")
            suspend fun count(): Int
        
            @Query("SELECT * FROM TodoEntity")
            fun getAllAsFlow(): Flow<List<TodoEntity>>
        }
        
        @Entity
        data class TodoEntity(
            @PrimaryKey(autoGenerate = true) val id: Long = 0,
            val title: String,
            val content: String
        )
        
        fun getRoomDatabase(
            builder: RoomDatabase.Builder<AppDatabase>
        ): AppDatabase {
            return builder
        //        .addMigrations(MIGRATIONS)
        //        .fallbackToDestructiveMigrationOnDowngrade()
        //        .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }
        
        //TODO Change Room database Name
        internal const val ROOM_DB_NAME = "my_room.db"
    """.trimIndent()
}

class AndroidRoomProvider(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/androidMain/kotlin/${info.packagePath}/$ROOM_PATH"
    override val content: String = """
        package ${info.packageId}.database

        import android.content.Context
        import androidx.room.Room
        import androidx.room.RoomDatabase
        
        fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
            val appContext = ctx.applicationContext
            val dbFile = appContext.getDatabasePath(ROOM_DB_NAME)
            return Room.databaseBuilder<AppDatabase>(
                context = appContext,
                name = dbFile.absolutePath
            )
        }
    """.trimIndent()
}

class JVMRoomProvider(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/jvmMain/kotlin/${info.packagePath}/$ROOM_PATH"
    override val content: String = """
        package ${info.packageId}.database

        import androidx.room.Room
        import androidx.room.RoomDatabase
        import java.io.File
        
        fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
            val dbFile = File(System.getProperty("java.io.tmpdir"), ROOM_DB_NAME)
            return Room.databaseBuilder<AppDatabase>(
                name = dbFile.absolutePath,
            )
        }
    """.trimIndent()
}

class IOSRoomProvider(info: ProjectInfo) : ProjectFile {
    override val path = "${info.moduleName}/src/iosMain/kotlin/${info.packagePath}/$ROOM_PATH"
    override val content: String = """
        package ${info.packageId}.database

        import kotlinx.cinterop.ExperimentalForeignApi
        import platform.Foundation.NSFileManager
        import platform.Foundation.NSLibraryDirectory
        import platform.Foundation.NSURL
        import platform.Foundation.NSUserDomainMask
        
        
        @OptIn(ExperimentalForeignApi::class)
        internal fun providePath(): String {
            val baseDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSLibraryDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            val dbFilePath = requireNotNull(baseDirectory).path + "/" + ROOM_DB_NAME
            return dbFilePath
        }
    """.trimIndent()
}

open class IOSSubRoomProvider(info: ProjectInfo) : ProjectFile{
    open val middlePath = "iosMain"
    override val path by lazy { "${info.moduleName}/src/${this.middlePath}/kotlin/${info.packagePath}/$ROOM_PATH" }
    override val content: String = """
        package ${info.packageId}.database

        import androidx.room.Room
        import androidx.room.RoomDatabase
        
        fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
            return Room.databaseBuilder<AppDatabase>(
                name = providePath(),
                factory =  { AppDatabase::class.instantiateImpl() }
            )
        }
    """.trimIndent()
}

class IOSX64RoomProvider(info: ProjectInfo) : IOSSubRoomProvider(info){
    override val middlePath = "iosX64Main"
}

class IOSArm64RoomProvider(info: ProjectInfo) : IOSSubRoomProvider(info) {
    override val middlePath: String = "iosArm64Main"
}

class IOSSimulatorArm64RoomProvider(info: ProjectInfo) : IOSSubRoomProvider(info) {
    override val middlePath: String = "iosSimulatorArm64Main"
}