package com.udacity.political.preparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.political.preparedness.network.models.Election
import com.udacity.political.preparedness.network.models.FollowedElection
import java.time.Instant
import java.util.*

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(election: Election): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(election: Election)

    @Transaction
    suspend fun upsert(election: Election) {
        val id = insert(election)
        if (id == -1L) {
            update(election)
        }
    }

    @Query("SELECT * FROM elections")
    fun getAllElections(): LiveData<List<Election>>

    @Query("SELECT * FROM elections WHERE id IN followed_elections")
    fun getAllFollowedElections(): LiveData<List<Election>>

    @Query("SELECT * FROM elections WHERE id = :id")
    fun getElectionById(id: Int): LiveData<Election>

    @Query("SELECT * FROM elections WHERE id = :id AND :id IN followed_elections")
    suspend fun getFollowedElectionById(id: Int): Election?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun followElection(election: FollowedElection)

    suspend fun followElection(election: Election) =
        followElection(FollowedElection(electionId = election.id))

    @Query("DELETE FROM followed_elections WHERE electionId = :id")
    suspend fun unfollowElection(id: Int)

    suspend fun unfollowElection(election: Election) = unfollowElection(election.id)

    @Query("DELETE FROM elections WHERE Date(electionDay) < Date(:date)")
    suspend fun deletePastElections(date: Date = Date.from(Instant.now()))
}
