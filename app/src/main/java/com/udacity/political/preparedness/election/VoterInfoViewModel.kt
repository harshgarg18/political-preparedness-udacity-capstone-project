package com.udacity.political.preparedness.election

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.database.ElectionDatabase
import com.udacity.political.preparedness.network.CivicsApi
import com.udacity.political.preparedness.network.models.Election
import com.udacity.political.preparedness.network.models.State
import com.udacity.political.preparedness.repository.ElectionRepository
import com.udacity.political.preparedness.util.NetworkStatus
import com.udacity.political.preparedness.util.combineWith
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class VoterInfoViewModel(private val app: Application, private val election: Election) :
    AndroidViewModel(app) {

    private val database = ElectionDatabase.getInstance(app)
    private val electionRepository = ElectionRepository(database.electionDao)

    private val followString = app.getString(R.string.follow_election)
    private val unfollowString = app.getString(R.string.unfollow_election)

    private val _buttonText = MutableLiveData<String>()
    val buttonText: LiveData<String>
        get() = _buttonText

    private val voterInfo = MutableLiveData<State>()
    val voterInfoApiStatus = MutableLiveData(NetworkStatus.DONE)

    private val votingLocationFinderUrl: LiveData<String> = Transformations.map(voterInfo) {
        it?.electionAdministrationBody?.votingLocationFinderUrl
    }

    private val balletInfoUrl: LiveData<String> = Transformations.map(voterInfo) {
        it?.electionAdministrationBody?.ballotInfoUrl
    }

    val hasElectionInfo: LiveData<Boolean> =
        votingLocationFinderUrl.combineWith(balletInfoUrl, false) { url1, url2 ->
            !url1.isNullOrBlank() && !url2.isNullOrBlank()
        }

    val mailingAddress: LiveData<String> = Transformations.map(voterInfo) {
        it?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
    }

    val hasMailingAddress: LiveData<Boolean> = Transformations.map(mailingAddress) {
        it != null
    }

    private val _urlToBeOpened = MutableLiveData<String?>()
    val urlToBeOpened: LiveData<String?>
        get() = _urlToBeOpened

    init {
        viewModelScope.launch {
            fetchVoterInformation()
            setButtonState(electionRepository.isElectionFollowed(election))
        }
    }

    private suspend fun fetchVoterInformation() {
        voterInfoApiStatus.value = NetworkStatus.LOADING
        try {
            val response = CivicsApi.retrofitService.getVoterInfo(
                address = election.division.format(),
                electionId = election.id
            )
            voterInfo.value = response.body()?.state?.firstOrNull()
            Timber.w("election states: ${voterInfo.value}")
        } catch (e: HttpException) {
            Toast.makeText(
                app,
                app.applicationContext.getString(R.string.voter_info_network_error_toast),
                Toast.LENGTH_SHORT
            ).show()
            Timber.e(e)
        }
        voterInfoApiStatus.value = NetworkStatus.DONE
    }

    private suspend fun setButtonState(isFollowed: Boolean) {
        _buttonText.value = if (isFollowed) {
            unfollowString
        } else {
            followString
        }
    }

    fun onVotingLocationClicked() {
        _urlToBeOpened.value = votingLocationFinderUrl.value
    }

    fun onBallotInformationClicked() {
        _urlToBeOpened.value = balletInfoUrl.value
    }

    fun onUrlLoaded() {
        _urlToBeOpened.value = null
    }

    fun onElectionFollowingChanged() {
        viewModelScope.launch {
            val isFollowed = electionRepository.isElectionFollowed(election)
            if (isFollowed) {
                electionRepository.unfollowElection(election)
            } else {
                electionRepository.followElection(election)
            }
            setButtonState(!isFollowed)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: Application, private val election: Election) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (VoterInfoViewModel(app, election) as T)
    }

}
