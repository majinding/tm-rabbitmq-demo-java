package cn.majingjing.mq.extensions.tx

import cn.majingjing.mq.util.ConnectionUtils
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection

object TxSend {

    private val QUEUE_NAME = "queue_extensions_tx"

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val connection = ConnectionUtils.getConnection()
        val channel = connection.createChannel()

        channel.queueDeclare(QUEUE_NAME, false, false, false, null)
        val message = "Hello World!"

        try {
            channel.txSelect()
            channel.basicPublish("", QUEUE_NAME, null, message.toByteArray(charset("UTF-8")))
            val a = 1 / 0
            println(" [x] Sent '$message'")
            channel.txCommit()
        } catch (e: Exception) {
            channel.txRollback()
            println(" send message txRollback")
        }

        channel.close()
        connection.close()

    }

}
