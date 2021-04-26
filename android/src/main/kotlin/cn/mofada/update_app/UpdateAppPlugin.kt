package cn.mofada.update_app

import android.content.Context
import androidx.annotation.NonNull;
import cn.mofada.update_app.constant.ChannelName

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** UpdateAppPlugin */
public class UpdateAppPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel

    private lateinit var context: Context

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(
            flutterPluginBinding.flutterEngine.dartExecutor,
            ChannelName.UPDATE_PLUGIN.id
        )
        context = flutterPluginBinding.applicationContext
        channel.setMethodCallHandler(this)
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), ChannelName.UPDATE_PLUGIN.id)
            val updateAppPlugin = UpdateAppPlugin()
            //初始化上下文
            updateAppPlugin.context = registrar.context()
            channel.setMethodCallHandler(updateAppPlugin)
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "updateApp" -> result.success(downloadApp(call, context))
            "getPlatformVersion" -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
            "downloadProcess" -> result.success(downloadProcess(call, context))
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
