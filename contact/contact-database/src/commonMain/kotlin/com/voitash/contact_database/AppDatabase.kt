package com.voitash.contact_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.voitash.contact_database.contacts.ContactsDao
import com.voitash.contact_database.contacts.DbContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [DbContact::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactsDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}