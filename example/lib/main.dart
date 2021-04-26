import 'dart:async';

import 'package:flutter/material.dart';
import 'package:update_app/bean/download_process.dart';

import 'package:update_app/update_app.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Timer? timer;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext? context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Update app'),
        ),
        body: Center(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text('click right bottom download button download!'),
              Text('Adapt to Android 6.0'),
              Text('Adapt to Android 7.0'),
              Text('Adapt to Android 8.0'),
              Text('Adapt to Android 9.0'),
              Text('Adapt to Android 10.0'),
            ],
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: download,
          child: Icon(Icons.file_download),
        ),
      ),
    );
  }

  @override
  void dispose() {
    timer?.cancel();
    super.dispose();
  }

  void download() async {
    var downloadId = await UpdateApp.updateApp(
        url:
            "https://fga1.market.xiaomi.com/download/AppStore/0940f4c57a12240e1b1e9ce5f80e03313954376d6/com.taobao.taobao.apk",
        appleId: "375380948");

    if (downloadId == 0 || downloadId == -1) {
      return;
    }

    print(downloadId);

    timer = Timer.periodic(Duration(milliseconds: 100), (timer) async {
      var downloadProcess =
          await UpdateApp.downloadProcess(downloadId: downloadId);
      if (downloadProcess.status == ProcessState.STATUS_SUCCESSFUL || downloadProcess.status == ProcessState.STATUS_FAILED) {
        //如果已经下载成功, 那就取消
        timer.cancel();
      } else {
        //更新界面状态
        print(downloadProcess);
        setState(() {});
      }
    });


  }
}
