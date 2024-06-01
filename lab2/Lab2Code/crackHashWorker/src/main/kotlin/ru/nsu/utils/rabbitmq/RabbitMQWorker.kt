package ru.nsu.utils.rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.nsu.utils.crackPart


class RabbitMQWorker (private val channel : Channel) {
    @OptIn(DelicateCoroutinesApi::class)
    fun startConsuming(queueName: String){
        channel.basicConsume(queueName, true, object : DefaultConsumer(channel){
            override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
                println("Consumed!")
                val message = Json.decodeFromString<WorkRequest>(String(body!!, Charsets.UTF_8))
                try{
                    GlobalScope.launch {
                        val result = crackPart(message.hash, message.partNumber, message.partCount)
                        println(result)
                        val response = Json.encodeToString(TaskResponce(message.requestId, result, "READY"))
                        channel.basicPublish("update_exchange", "update_routing", null, response.toByteArray(Charsets.UTF_8))
                    }
                } catch (e : Exception){
                    println(e.message)
                    // Переотправит сообщение в очередь
                    channel.basicNack(envelope!!.deliveryTag, false, true)
                }
            }
        })
    }
}