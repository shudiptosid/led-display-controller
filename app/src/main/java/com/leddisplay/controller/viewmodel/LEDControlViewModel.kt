package com.leddisplay.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.leddisplay.controller.data.MqttManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LEDControlViewModel(application: Application) : AndroidViewModel(application) {
    private val mqttManager = MqttManager(application)
    
    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus
    
    private val _brightness = MutableStateFlow(1f)
    val brightness: StateFlow<Float> = _brightness
    
    private val _displayText = MutableStateFlow("")
    val displayText: StateFlow<String> = _displayText
    
    init {
        // Observe MQTT connection status
        viewModelScope.launch {
            mqttManager.connectionStatus.collect { isConnected ->
                _connectionStatus.value = isConnected
            }
        }
    }
    
    fun connect() {
        viewModelScope.launch {
            mqttManager.connect()
        }
    }
    
    fun disconnect() {
        viewModelScope.launch {
            mqttManager.disconnect()
        }
    }
    
    fun updateBrightness(value: Float) {
        _brightness.value = value
        viewModelScope.launch {
            if (_connectionStatus.value) {
                val brightnessPercent = (value * 100).toInt()
                mqttManager.publishMessage("led/brightness", brightnessPercent.toString())
            }
        }
    }
    
    fun updateDisplayText(text: String) {
        _displayText.value = text
        viewModelScope.launch {
            if (_connectionStatus.value) {
                mqttManager.publishMessage("led/text", text)
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        mqttManager.disconnect()
    }
}
