package cn.mofada.update_app

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.lang.Exception

class UpdateAppPlugin(val context: Context) : MethodCallHandler {
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



