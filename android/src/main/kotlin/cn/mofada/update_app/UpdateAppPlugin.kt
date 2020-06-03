package cn.mofada.update_app

import android.content.Context
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class UpdateAppPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel

    private lateinit var context: Context

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "cn.mofada.cn/update_app")
            val updateAppPlugin = UpdateAppPlugin()
            channel.setMethodCallHandler(updateAppPlugin)
            //初始化上下文
            updateAppPlugin.context = registrar.context()
        }
    }


    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "updateApp" -> result.success(downloadApp(call, context))
            else -> result.notImplemented()
        }
    }

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.flutterEngine.dartExecutor, "cn.mofada.cn/update_app")
        context = flutterPluginBinding.applicationContext
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}



