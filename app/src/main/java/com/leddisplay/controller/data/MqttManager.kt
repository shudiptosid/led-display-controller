package com.leddisplay.controller.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttManager(private val context: Context) {
    
    companion object {
        private const val TAG = "MqttManager"
        private const val BROKER_URL = "tcp://broker.hivemq.com:1883"
        private const val CLIENT_ID = "LEDControllerApp"
        private const val QOS = 1
    }
    
    private var mqttClient: MqttAndroidClient? = null
    
    private val _connectionStatus = MutableStateFlow(false)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus
    
    fun connect() {
        try {
            mqttClient = MqttAndroidClient(context, BROKER_URL, CLIENT_ID)
            
            mqttClient?.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    Log.e(TAG, "Connection lost", cause)
                    _connectionStatus.value = false
                }
                
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    Log.d(TAG, "Message arrived on topic $topic: ${message?.toString()}")
                }
                
                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d(TAG, "Delivery complete")
                }
            })
            
            val options = MqttConnectOptions().apply {
                isCleanSession = true
                connectionTimeout = 10
                keepAliveInterval = 60
            }
            
            mqttClient?.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connected to MQTT broker")
                    _connectionStatus.value = true
                }
                
                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e(TAG, "Failed to connect to MQTT broker", exception)
                    _connectionStatus.value = false
                }
            })
            
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to MQTT broker", e)
            _connectionStatus.value = false
        }
    }
    
    fun publishMessage(topic: String, message: String) {
        try {
            if (mqttClient?.isConnected == true) {
                val mqttMessage = MqttMessage(message.toByteArray()).apply {
                    qos = QOS
                    isRetained = false
                }
                
                mqttClient?.publish(topic, mqttMessage, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d(TAG, "Message published to $topic: $message")
                    }
                    
                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e(TAG, "Failed to publish message to $topic", exception)
                    }
                })
            } else {
                Log.e(TAG, "MQTT client is not connected")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error publishing message", e)
        }
    }
    
    fun disconnect() {
        try {
            if (mqttClient?.isConnected == true) {
                mqttClient?.disconnect(null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        Log.d(TAG, "Disconnected from MQTT broker")
                        _connectionStatus.value = false
                    }
                    
                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        Log.e(TAG, "Failed to disconnect from MQTT broker", exception)
                    }
                })
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting from MQTT broker", e)
        }
    }
}
