import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:update_app/update_app.dart';

void main() {
  const MethodChannel channel = MethodChannel('update_app');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    await UpdateApp.updateApp(
        url:
            "https://cdn.51bolema.com/2019/08/24/ffbf264bc36d404e81bb113fed72bacf.apk");
  });
}
