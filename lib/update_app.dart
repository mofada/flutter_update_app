import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class UpdateApp {
  static const MethodChannel _channel =
      const MethodChannel('cn.mofada.update_app');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  //更新app
  static Future<bool> updateApp({
    @required String url,
    @required String appleId,
    String title,
    String description = "应用更新",
  }) async {
    var result = await _channel.invokeMethod('updateApp', {
      "argumentsUrl": url,
      "argumentsTitle": title ?? appName(url),
      "argumentsDescription": description,
      "appleId": appleId
    });
    return result;
  }

  ///根据下载地址获取文件名称
  static String appName(String url) {
    return url.substring(url.lastIndexOf("/") + 1);
  }
}
