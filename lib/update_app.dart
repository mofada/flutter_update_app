import 'dart:async';

import 'package:flutter/services.dart';
import 'package:update_app/bean/download_process.dart';
import 'package:update_app/constant/argument_name.dart';
import 'package:update_app/constant/channel_name.dart';

class UpdateApp {
  static const MethodChannel _channel =
      const MethodChannel(ChannelName.update_plugin);

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// Download the app and update
  ///
  /// appleId: This is the app id of the app store, used to jump to the app store
  /// url: This is the URL to download the apk file
  /// title: Android download the title displayed in the notification bar, generally use the file name, or like this `update version 1.6`
  /// description: Android download the description displayed in the notification bar, similar to the subtitle
  ///
  /// @return `-1` -> `download failed`, `0` -> `There is a current apk locally, and the download is successful`, other (int) -> `downloadId`
  static Future<int> updateApp({
    required String url,
    required String appleId,
    String? title,
    String description = "应用更新",
  }) async {
    var result = await _channel.invokeMethod('updateApp', {
      ArgumentName.url: url,
      ArgumentName.title: title ?? appName(url),
      ArgumentName.description: description,
      ArgumentName.appleId: appleId
    });
    return result;
  }

  /// Query apk download progress
  ///
  /// downloadId: [updateApp] 返回的downloadId
  ///
  /// @return [DownloadProcess]
  /// current: Currently downloaded length
  /// count: Total length
  /// status: [ProcessState]
  ///         STATUS_PENDING: [ProcessState.STATUS_PENDING](https://developer.android.com/reference/android/app/DownloadManager#STATUS_PENDING)
  ///         STATUS_RUNNING: [ProcessState.STATUS_RUNNING](https://developer.android.com/reference/android/app/DownloadManager#STATUS_RUNNING)
  ///         STATUS_PAUSED: [ProcessState.STATUS_PAUSED](https://developer.android.com/reference/android/app/DownloadManager#STATUS_PAUSED)
  ///         STATUS_SUCCESSFUL: [ProcessState.STATUS_SUCCESSFUL](https://developer.android.com/reference/android/app/DownloadManager#STATUS_SUCCESSFUL)
  ///         STATUS_FAILED: [ProcessState.STATUS_FAILED](https://developer.android.com/reference/android/app/DownloadManager#STATUS_FAILED)
  static Future<DownloadProcess> downloadProcess({
    required int downloadId,
  }) async {
    var result = await _channel.invokeMethod('downloadProcess', {
      ArgumentName.downloadId: downloadId,
    });
    return DownloadProcess.fromMap(result);
  }

  ///Obtain the file name according to the download address
  static String appName(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }
}
