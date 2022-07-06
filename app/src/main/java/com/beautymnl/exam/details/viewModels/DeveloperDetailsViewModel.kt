package com.beautymnl.exam.details.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beautymnl.exam.core.entities.TaskStatus
import com.beautymnl.exam.core.extensions.SingleLiveEvent
import com.beautymnl.exam.core.extensions.launch
import com.beautymnl.exam.core.networking.entities.Developer
import com.beautymnl.exam.core.networking.services.ApiService
import com.beautymnl.exam.core.utils.DEFAULT_DIALOG_ANIMATION_DELAY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DeveloperDetailsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val updateDeveloperEvent = SingleLiveEvent<TaskStatus<Developer>>()
    val addDeveloperEvent = SingleLiveEvent<TaskStatus<Developer>>()

    fun updateDetails(developer: Developer) {
        viewModelScope.launch(
            work = {
                updateDeveloperEvent.postValue(TaskStatus.loading(true))
                delay(DEFAULT_DIALOG_ANIMATION_DELAY)
                apiService.updateDeveloper(developer)
            }, onSuccess = {
                updateDeveloperEvent.value = TaskStatus.success(developer)
            }, onError = {
                updateDeveloperEvent.value = TaskStatus.failure(it)
            }
        )
    }

    fun addDeveloper(fullName: String, email: String, phoneNumber: String, company: String) {
        val newDeveloper = Developer(
            id = 0,
            name = fullName,
            email = email,
            phoneNumber = phoneNumber,
            company = company
        )
        viewModelScope.launch(
            work = {
                addDeveloperEvent.postValue(TaskStatus.loading(true))
                delay(DEFAULT_DIALOG_ANIMATION_DELAY)
                apiService.addDeveloper(newDeveloper)
            }, onSuccess = {
                addDeveloperEvent.value = TaskStatus.success(newDeveloper.copy(id = Random.nextInt()))
            }, onError = {
                addDeveloperEvent.value = TaskStatus.failure(it)
            }
        )
    }
}