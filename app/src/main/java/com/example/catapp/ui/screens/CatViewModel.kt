package com.example.catapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.catapp.DogApplication
import com.example.catapp.data.Cat
import com.example.catapp.data.CatRepository
import com.example.catapp.data.CatRepository.cats
import com.example.catapp.data.DefaultDogRepository
import com.example.catapp.data.DogRepository
import com.example.catapp.model.Dog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DogsUiState {
    data class Success(val dogData: List<String>) : DogsUiState
    object Error : DogsUiState
    object Loading : DogsUiState
}

class CatViewModel (private val dogRepository: DogRepository) : ViewModel() {
    private val _cat: MutableStateFlow<Cat?> = MutableStateFlow(null)
    val cat = _cat.asStateFlow()

    var dogsUiState: DogsUiState by mutableStateOf(DogsUiState.Loading)
        private set

    init {
        getDogsData()
    }

    private val catRepository = CatRepository



    fun catSelected(index: Int) {
        _cat.value = cats[index]
    }

    private fun getDogsData() {
        viewModelScope.launch {
            dogsUiState = try {
                val listResult = dogRepository.getDogs()
                DogsUiState.Success(listResult.imgSrc)
            } catch (e: Exception) {
                println(e)
                DogsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DogApplication)
                val dogRepository = application.container.dogRepository
                CatViewModel(dogRepository = dogRepository)
            }
        }
    }

}