package com.sepia.pets.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.sepia.pets.R
import com.sepia.pets.model.ConfigModel
import com.sepia.pets.model.PetsModel
import com.sepia.pets.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PetsViewModel @Inject constructor() : ViewModel() {
    private val petsListData = MutableLiveData<DataState<PetsModel>>()
    val petsListObservable: LiveData<DataState<PetsModel>> get() = petsListData
    private val gson = Gson()

    fun getPetsList(context : Context) =
        viewModelScope.launch {
            petsListData.value = DataState.Loading
            try {
                val objectArrayString: String = context.resources.openRawResource(R.raw.petslist).bufferedReader().use { it.readText() }
                val jsonObject: JsonObject = JsonParser.parseString(objectArrayString).getAsJsonObject()
                val petsList = gson.fromJson(jsonObject, PetsModel::class.java)
                petsListData.value = DataState.Success(petsList)
            } catch (ex: IOException) {
                ex.printStackTrace()
                petsListData.value = DataState.Error(ex.toString())
            }
        }

   private val appTimeData = MutableLiveData<DataState<ConfigModel>>()
    val appTimeObservable: LiveData<DataState<ConfigModel>> get() = appTimeData

    fun getApptime(context : Context) =
        viewModelScope.launch {
            appTimeData.value = DataState.Loading
            try {
                val objectArrayString: String = context.resources.openRawResource(R.raw.config).bufferedReader().use { it.readText() }
                val jsonObject: JsonObject = JsonParser.parseString(objectArrayString).getAsJsonObject()
                val settings = gson.fromJson(jsonObject, ConfigModel::class.java)
                appTimeData.value = DataState.Success(settings)
            } catch (ex: IOException) {
                ex.printStackTrace()
                appTimeData.value = DataState.Error(ex.toString())
            }
        }

}