import 'dart:async';

import 'package:flutter/services.dart';

class UpdateApp {
  static const MethodChannel _channel =
      const MethodChannel('cn.mofada.cn/update_app');

  //更新app
  static Future<bool> updateApp({
    String url,
    String title = "应用更新...",
    String description = "正在下载中...",
    String appleId,
  }) async {
    var result = await _channel.invokeMethod('updateApp', {
      "argumentsUrl": url,
      "argumentsTitle": title,
      "argumentsDescription": description,
      "appleId": appleId
    });
    return result;
  }
}
