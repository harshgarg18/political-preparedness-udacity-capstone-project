package com.udacity.political.preparedness.repository

import androidx.lifecycle.MutableLiveData
import com.udacity.political.preparedness.database.ElectionDao
import com.udacity.political.preparedness.network.CivicsApi
import com.udacity.political.preparedness.network.models.Election
import com.udacity.political.preparedness.util.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionRepository(private val dao: ElectionDao) {

    val apiStatus = MutableLiveData(NetworkStatus.DONE)

    fun getAllElections() = dao.getAllElections()

    fun getAllFollowedElections() = dao.getAllFollowedElections()

    suspend fun followElection(election: Election) = withContext(Dispatchers.IO) {
        dao.followElection(election)
    }

    suspend fun unfollowElection(election: Election) = withContext(Dispatchers.IO) {
        dao.unfollowElection(election)
    }

    suspend fun isElectionFollowed(election: Election): Boolean = withContext(Dispatchers.IO) {
        dao.getFollowedElectionById(election.id) == election
    }

    suspend fun refreshElections() {
        apiStatus.value = NetworkStatus.LOADING
        withContext(Dispatchers.IO) {
            dao.deletePastElections()
            val response = CivicsApi.retrofitService.getElectionsInfo()
            val elections = response.body()?.elections
            elections?.forEach {
                dao.upsert(it)
            }
        }
        apiStatus.value = NetworkStatus.DONE
    }
}
