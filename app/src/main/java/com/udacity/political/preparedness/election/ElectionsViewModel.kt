package com.udacity.political.preparedness.election

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.database.ElectionDatabase
import com.udacity.political.preparedness.network.models.Election
import com.udacity.political.preparedness.repository.ElectionRepository
import com.udacity.political.preparedness.util.NetworkStatus
import kotlinx.coroutines.launch
import timber.log.Timber


class ElectionsViewModel(app: Application) : AndroidViewModel(app) {

    private val database = ElectionDatabase.getInstance(app)
    private val electionRepository = ElectionRepository(database.electionDao)

    val electionApiStatus = electionRepository.apiStatus

    val elections
        get() = electionRepository.getAllElections()
    val followedElections
        get() = electionRepository.getAllFollowedElections()

    private val _navigateToSelectedElection = MutableLiveData<Election?>()
    val navigateToSelectedElection: LiveData<Election?>
        get() = _navigateToSelectedElection


    init {
        viewModelScope.launch {
            try {
                electionRepository.refreshElections()
            } catch (e: Exception) {
                Toast.makeText(
                    app,
                    app.applicationContext.getString(R.string.elections_network_error_toast),
                    Toast.LENGTH_SHORT
                ).show()
                Timber.e(e)
                electionApiStatus.value = NetworkStatus.DONE
            }
        }
    }

    fun displayElectionDetails(election: Election) {
        _navigateToSelectedElection.value = election
    }

    fun displayElectionDetailsCompleted() {
        _navigateToSelectedElection.value = null
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>) = (ElectionsViewModel(app) as T)
    }

}
