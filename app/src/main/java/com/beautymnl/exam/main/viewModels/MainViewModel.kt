package com.beautymnl.exam.main.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beautymnl.exam.core.entities.DeveloperListItem
import com.beautymnl.exam.core.entities.TaskStatus
import com.beautymnl.exam.core.extensions.SingleLiveEvent
import com.beautymnl.exam.core.extensions.launch
import com.beautymnl.exam.core.networking.services.ApiService
import com.beautymnl.exam.main.factories.MainFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val getDevelopersListEvent = SingleLiveEvent<TaskStatus<List<DeveloperListItem>>>()
    val deleteDeveloperEvent = SingleLiveEvent<TaskStatus<Int>>()

    fun getDevelopers() {
        viewModelScope.launch(
            work = {
                getDevelopersListEvent.postValue(TaskStatus.loading(true))
                delay(300)
                val developers = apiService.getDevelopers()
                MainFactory.convertToDeveloperListItem(developers.toList())
            }, onSuccess = {
                getDevelopersListEvent.value = TaskStatus.success(it!!)
            }, onError = {
                getDevelopersListEvent.value = TaskStatus.failure(it)
            }
        )
    }

    fun deleteDeveloper(id: Int) {
        viewModelScope.launch(
            work = {
                deleteDeveloperEvent.postValue(TaskStatus.loading(true))
                apiService.deleteDeveloper(id)
                delay(3000)
            }, onSuccess = {
                deleteDeveloperEvent.value = TaskStatus.success(id)
            }, onError = {
                deleteDeveloperEvent.value = TaskStatus.failure(it)
            }
        )
    }
}