package com.udacity.political.preparedness.election

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(app: Application) : ViewModel() {

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: Application) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>) =
            (ElectionsViewModel(app) as T)
    }

}
