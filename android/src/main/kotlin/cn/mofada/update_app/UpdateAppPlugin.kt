package cn.mofada.update_app

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class UpdateAppPlugin(val context: Context) : MethodCallHandler {
    /**
     * 更新的文件地址
     */
    val argumentsUrl: String = "argumentsUrl"

    /**
     * 通知栏标题
     */
    val argumentsTitle: String = "argumentsTitle"

    /**
     * 通知栏描述
     */
    val argumentsDescription: String = "argumentsDescription"

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "cn.mofada.cn/update_app")
            channel.setMethodCallHandler(UpdateAppPlugin(registrar.context()))
        }
    }


    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "updateApp" -> result.success(downloadApp(call))
            else -> result.notImplemented()
        }
    }

    /**
     * 下载应用
     */
    private fun downloadApp(call: MethodCall): Boolean {
        //下载的文件地址
        val url = call.argument<String>(argumentsUrl)
        //标题
        val title = call.argument<String>(argumentsTitle)
        //描述
        val description = call.argument<String>(argumentsDescription)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        //设置下载完成仍然显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //设置存放路径, 放在应用内部目录
        request.setDestinationInExternalFilesDir(context,
                "apks", url?.substring(url.lastIndexOf("/") + 1))
        //设置标题
        request.setTitle(title)
        //设置描述
        request.setDescription(description)
        //设置下载类型apk
        request.setMimeType("application/vnd.android.package-archive")
        //开始下载
        downloadManager.enqueue(request)
        return true
    }


}
