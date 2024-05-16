package com.voitash.contact_database.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {
    @Query("SELECT * FROM Contact")
    fun getContacts(): Flow<List<DbContact>>

    @Query("SELECT * FROM Contact WHERE id=:contactId")
    suspend fun getContactById(contactId: Int): DbContact?

    @Query("SELECT COUNT(*) FROM Contact")
    suspend fun contactCount(): Int

    @Update
    suspend fun update(contact: DbContact)

    @Insert
    suspend fun add(contact: DbContact)

    @Insert
    suspend fun addAll(contacts: List<DbContact>)

    @Query("DELETE FROM Contact WHERE id = (:contactId)")
    suspend fun deleteById(contactId: Int)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()
}