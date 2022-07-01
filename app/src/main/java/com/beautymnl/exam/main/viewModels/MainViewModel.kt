package com.beautymnl.exam.main.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beautymnl.exam.core.entities.TaskStatus
import com.beautymnl.exam.core.extensions.SingleLiveEvent
import com.beautymnl.exam.core.extensions.launch
import com.beautymnl.exam.core.networking.services.ApiService
import com.beautymnl.exam.main.entities.DeveloperListItem
import com.beautymnl.exam.main.factories.MainFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val developersListEvent = SingleLiveEvent<TaskStatus<List<DeveloperListItem>>>()

    fun getDevelopers() {
        viewModelScope.launch(
            work = {
                developersListEvent.postValue(TaskStatus.loading(true))
                delay(300)
                val developers = apiService.getDevelopers()
                MainFactory.convertToDeveloperListItem(developers)
            }, onSuccess = {
                developersListEvent.value = TaskStatus.success(it!!)
            }, onError = {
                developersListEvent.value = TaskStatus.failure(it)
            }
        )
    }
}