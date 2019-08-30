package cn.mofada.update_app

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.lang.Exception

/**
 * @author : fada
 * @email : fada@mofada.cn
 * @date : 2019/8/30 19:52
 * @description : input your description
 **/
class DownloadReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            DownloadManager.ACTION_DOWNLOAD_COMPLETE -> downloadComplete(context, intent)
        }
    }

    /**
     * 下载完成
     */
    private fun downloadComplete(context: Context, intent: Intent) {
        if (!intent.hasExtra(DownloadManager.EXTRA_DOWNLOAD_ID)) {
            //如果没有包含下载的id, 那么返回
            return
        }

        //获取下载的id
        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

        //如果下载id为-1, 说明没有值
        if (downloadId == -1L) return

        //获取查询
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        //获取下载服务
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val cursor = downloadManager.query(query)
        try {
            if (cursor.moveToFirst()) {
                val status = cursor.getString(cursor.getColumnIndex(DownloadManager
                        .COLUMN_STATUS))
                val localUri = cursor.getString(cursor.getColumnIndex(DownloadManager
                        .COLUMN_LOCAL_URI))
                Log.d("TAG", status)
            }
        }catch (e:Exception){

        }finally {
            cursor.close()
        }
    }
}