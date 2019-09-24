package cn.mofada.update_app

import android.content.Context
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class UpdateAppPlugin(private val context: Context) : MethodCallHandler {
    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "cn.mofada.cn/update_app")
            channel.setMethodCallHandler(UpdateAppPlugin(registrar.context()))
        }
    }


    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "updateApp" -> result.success(downloadApp(call, context))
            else -> result.notImplemented()
        }
    }
}



