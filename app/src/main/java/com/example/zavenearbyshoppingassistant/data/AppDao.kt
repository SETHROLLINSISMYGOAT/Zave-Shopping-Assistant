package com.example.zavenearbyshoppingassistant.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(q: SearchQuery)

    @Query("SELECT * FROM SearchQuery ORDER BY timestamp DESC LIMIT 5")
    suspend fun getRecentSearches(): List<SearchQuery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(s: CachedStore)

    @Query("SELECT * FROM CachedStore")
    suspend fun getCachedStores(): List<CachedStore>
}
