package cn.mofada.update_app

import android.app.DownloadManager
import android.app.usage.ExternalStorageStats
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ProviderInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import io.flutter.plugin.common.MethodCall
import java.io.File
import java.lang.Exception
import java.nio.file.spi.FileSystemProvider
import java.security.Provider

/**
 * @author : fada
 * @email : fada@mofada.cn
 * @date : 2019/8/31 13:46
 * @description : input your description
 **/

/**
 * 更新的文件地址
 */
const val argumentsUrl: String = "argumentsUrl"

/**
 * 通知栏标题
 */
const val argumentsTitle: String = "argumentsTitle"

/**
 * 通知栏描述
 */
const val argumentsDescription: String = "argumentsDescription"

/**
 * 文件存放目录
 */
const val apkDirector: String = "apks"

/**
 * apk文件类型
 */
const val apkType = "application/vnd.android.package-archive"

/**
 * 下载apk文件
 */
fun downloadApp(call: MethodCall, context: Context): Boolean {
    return try {
        //下载的文件地址
        val url = call.argument<String>(argumentsUrl)
        //标题
        val title = call.argument<String>(argumentsTitle)
        //描述
        val description = call.argument<String>(argumentsDescription)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
        //读取apk文件名称
        val fileName = url?.substring(url.lastIndexOf("/") + 1)
        //设置存放路径, 放在应用内部目录
        request.setDestinationInExternalFilesDir(context, apkDirector, fileName)

        //判断apk是否存在, 若存在, 直接安装, 否则下载
        val filePath = "${context.getExternalFilesDir(apkDirector)?.absolutePath}/$fileName"
        if (verificationApkInfo(context, filePath)) {
            //安装文件
            installApk(context, File(filePath))
            return true
        }

        //设置下载完成仍然显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        //设置标题
        request.setTitle(title)
        //设置描述
        request.setDescription(description)
        //设置下载类型apk
        request.setMimeType(apkType)
        //开始下载
        downloadManager.enqueue(request)
        return true
    } catch (e: Exception) {
        false
    }
}

/**
 * 验证apk文件是否完整
 */
fun verificationApkInfo(context: Context, filePath: String): Boolean {
    val pm = context.packageManager
    //获取应用信息
    val info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES)
    //如果不为null,则有数据
    return info != null
}

/**
 * 安装apk
 */
fun installApk(context: Context, file: File) {
    val intent = Intent(Intent.ACTION_VIEW)
    //设置flag
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //7.0及以上
        val uri = FileProvider.getUriForFile(context, context.applicationInfo.packageName + "" +
                ".updateprovider", file)
        intent.setDataAndType(uri, apkType)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } else {
        //7.0以下, 设置数据
        intent.setDataAndType(Uri.fromFile(file), apkType)
    }
    //启动activity
    context.startActivity(intent)
}

