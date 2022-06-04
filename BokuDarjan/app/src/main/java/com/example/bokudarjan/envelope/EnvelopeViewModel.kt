package com.example.bokudarjan.envelope

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bokudarjan.database.BokudarjanDatabase
import com.example.bokudarjan.envelope.Envelope
import com.example.bokudarjan.envelope.EnvelopeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EnvelopeViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<Envelope>>
    private val repository: EnvelopeRepository


    init {
        val envelopeDao = BokudarjanDatabase.getDatabase(application).envelopeDao()
        repository = EnvelopeRepository(envelopeDao)
        readAllData = repository.readAllData
    }

    fun addEnvelope(envelope: Envelope){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(envelope)
        }
    }

}
