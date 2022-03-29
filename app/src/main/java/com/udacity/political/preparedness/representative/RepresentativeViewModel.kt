package com.udacity.political.preparedness.representative

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.political.preparedness.R
import com.udacity.political.preparedness.network.CivicsApi
import com.udacity.political.preparedness.network.models.Address
import com.udacity.political.preparedness.representative.model.Representative
import com.udacity.political.preparedness.util.NetworkStatus
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class RepresentativeViewModel(private val app: Application) : AndroidViewModel(app) {

    private val stateList = app.resources.getStringArray(R.array.states)

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    val selectedStatePosition = MutableLiveData<Int>()

    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    val hasRepresentativeData: LiveData<Boolean> = Transformations.map(representatives) {
        !it.isNullOrEmpty()
    }

    val representativesApiStatus = MutableLiveData(NetworkStatus.DONE)

    init {
        _address.value = Address.empty()
        selectedStatePosition.value = 0
    }

    fun setState(position: Int) {
        _address.value?.state = stateList[position]
    }

    fun setAddress(address: Address) {
        _address.value = address
    }

    fun fetchRepresentatives() {
        representativesApiStatus.value = NetworkStatus.LOADING
        viewModelScope.launch {
            try {
                val response = CivicsApi.retrofitService.getRepresentatives(
                    address.value!!.toFormattedString()
                )
                _representatives.value = response.body()?.representatives
            } catch (e: HttpException) {
                Toast.makeText(
                    app,
                    app.applicationContext.getString(R.string.representatives_network_error_toast),
                    Toast.LENGTH_SHORT
                ).show()
                Timber.e(e)
            }
            representativesApiStatus.value = NetworkStatus.DONE
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: Application) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (RepresentativeViewModel(app) as T)
    }
}
