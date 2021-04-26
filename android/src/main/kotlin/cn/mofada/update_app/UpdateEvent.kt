package cn.mofada.update_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.NonNull
import cn.mofada.update_app.constant.ChannelName
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.PluginRegistry

/**
 * @author fada
 * @email fada@mofada.cn
 * @date 2021/4/26
 * @description 监听下载进度
 */
class UpdateEvent(val context: Context) : EventChannel.StreamHandler {
    /**
     * 创建广播
     */
    private lateinit var broadcast: BroadcastReceiver

    companion object {
        private lateinit var channel: EventChannel

        /**
         * Plugin registration.
         */
        fun registerWith(registrar: PluginRegistry.Registrar) {
            channel = EventChannel(registrar.messenger(), ChannelName.RECEIVER_PLUGIN.id)
            channel.setStreamHandler(UpdateEvent(registrar.context()))
        }

        /**
         * 初始化
         */
        fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
            channel = EventChannel(
                flutterPluginBinding.flutterEngine.dartExecutor,
                ChannelName.RECEIVER_PLUGIN.id
            )
            channel.setStreamHandler(UpdateEvent(flutterPluginBinding.applicationContext))
        }

        /**
         * 取消注册
         */
        fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
            channel.setStreamHandler(null)
        }
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        broadcast = object:BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {

            }
        }

        //创建过滤器
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE")

        //创建广播
        context.registerReceiver(
            broadcast,
            intentFilter
        )
    }

    override fun onCancel(arguments: Any?) {
    }
}