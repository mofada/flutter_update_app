package cn.mofada.update_app

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import cn.mofada.update_app.constant.ArgumentName
import io.flutter.plugin.common.MethodCall
import java.io.File

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
 * @return -1: 表示下载失败, 出现异常. 0: 表示本地apk已存在, 正在安装. other: 下载的id, 用于查询下载进度
 */
fun downloadApp(call: MethodCall, context: Context): Long {
    return try {
        //下载的文件地址
        val url = call.argument<String>(ArgumentName.URL)
        //标题
        val title = call.argument<String>(ArgumentName.TITLE)
        //描述
        val description = call.argument<String>(ArgumentName.DESCRIPTION)

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
            return 0
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
        return downloadManager.enqueue(request)
    } catch (e: Exception) {
        return -1L
    }
}

/**
 * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
 *
 * @param
 * @return
 */
fun downloadProcess(call: MethodCall, context: Context): IntArray {
    val bytesAndStatus = intArrayOf(-1, -1, 0)

    //获取下载id
    val downloadId = call.argument<Long>(ArgumentName.DOWNLOADID) ?: return bytesAndStatus

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    //通过下载id查询
    val query = DownloadManager.Query().setFilterById(downloadId)
    var cursor: Cursor? = null
    try {
        cursor = downloadManager.query(query)
        if (cursor != null && cursor.moveToFirst()) {
            //已经下载文件大小
            bytesAndStatus[0] =
                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            //下载文件的总大小
            bytesAndStatus[1] =
                cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            //下载状态
            bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
        }
    } finally {
        cursor?.close()
    }
    return bytesAndStatus
}


/**
 * 验证apk文件是否完整
 */
fun verificationApkInfo(context: Context, filePath: String): Boolean {
    //如果文件不存在, 直接跳过
    if (!File(filePath).exists()) return false
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
        val uri = FileProvider.getUriForFile(
            context, context.applicationInfo.packageName + "" +
                    ".updateprovider", file
        )
        intent.setDataAndType(uri, apkType)

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } else {
        //7.0以下, 设置数据
        intent.setDataAndType(Uri.fromFile(file), apkType)
    }
    //启动activity
    context.startActivity(intent)
}

