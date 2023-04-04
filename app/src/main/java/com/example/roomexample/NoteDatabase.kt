package com.example.roomexample

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    entities = [User::class],
//    autoMigrations = [
//        AutoMigration (from = 1, to = 2)
//    ],
    version = 4,
    exportSchema = true
)
@TypeConverters(NoteConverters::class)
// This is a Room Database class called notedatabase, which extends RoomDatabase class
// It is annotated with @TypeConverters to specify the NoteConverters class for type conversion

// The class is abstract and contains an abstract function to access the UserDao
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): UserDao

    companion object {
        @Volatile
        //This creates a singleton instance of the NoteDatabase class, which is a Room database. The function getDatabase() returns the instance of the database, creating it if necessary. The INSTANCE variable is marked as volatile to make sure that its value is visible to all threads. If the INSTANCE is already initialized, then getDatabase() simply returns it. If it is not initialized, then getDatabase() creates a new instance of the database using Room's databaseBuilder() method. The function is synchronized to prevent multiple threads from accessing it at the same time. Finally,
        // the INSTANCE variable is set to the newly created database, and the function returns the instance.
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // The following query will add a new column called lastUpdate to the notes database
                database.execSQL("ALTER TABLE notes ADD COLUMN lastUpdate INTEGER NOT NULL DEFAULT 0")
            }
        }

        private fun buildDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_database"
            )
                //.addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}