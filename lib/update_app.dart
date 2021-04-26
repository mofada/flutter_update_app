import 'dart:async';

import 'package:flutter/services.dart';
import 'package:update_app/constant/argument_name.dart';
import 'package:update_app/constant/channel_name.dart';

class UpdateApp {
  static const MethodChannel _channel =
      const MethodChannel(ChannelName.update_plugin);

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  //更新app
  static Future<bool?> updateApp({
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

  //查询下载进度
  static Future<bool> downloadProcess({
    @required int downloadId,
  }) async {
    var result = await _channel.invokeMethod('updateApp', {
      "argumentsDownloadId": downloadId,
    });
    return result;
  }

  ///根据下载地址获取文件名称
  static String appName(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }
}
