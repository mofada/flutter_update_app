import 'dart:async';

import 'package:flutter/services.dart';

class UpdateApp {
  static const MethodChannel _channel =
      const MethodChannel('update_app');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
