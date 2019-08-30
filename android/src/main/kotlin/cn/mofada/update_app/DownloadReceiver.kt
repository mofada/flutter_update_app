package cn.mofada.update_app

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * @author : fada
 * @email : fada@mofada.cn
 * @date : 2019/8/30 19:52
 * @description : input your description
 **/
class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> downloadComplete(context, intent)
            DownloadManager.ACTION_NOTIFICATION_CLICKED -> downloadComplete(context, intent)
        }
    }

    /**
     * 下载完成
     */
    private fun downloadComplete(context: Context?, intent: Intent?) {

    }
}