import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:update_app/bean/download_process.dart';

import 'package:update_app/update_app.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  //定时更新进度
  Timer? timer;

  //下载进度
  double downloadProcess = 0;

  //下载状态
  String downloadStatus = "";

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
              Container(
                padding: EdgeInsets.all(16),
                width: 200,
                height: 200,
                child: CircularProgressIndicator(
                  value: downloadProcess,
                  strokeWidth: 10,
                ),
              ),
              Text("Download status: $downloadStatus"),
              Text("Please replace the download url with your own address"),
            ],
          ),
        ),
        bottomNavigationBar: Container(
            padding: EdgeInsets.all(20),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                Text(
                  'click right bottom download button download!',
                  style: TextStyle(color: Theme.of(context!).primaryColor),
                ),
                Text('Adapt to Android 6.0'),
                Text('Adapt to Android 7.0'),
                Text('Adapt to Android 8.0'),
                Text('Adapt to Android 9.0'),
                Text('Adapt to Android 10.0'),
                Text('Adapt to Android 10.0'),
                Text('Add download progress monitoring'),
              ],
            )),
        floatingActionButton: FloatingActionButton(
          onPressed: download,
          child: Icon(Icons.file_download),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.endDocked,
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

    //本地已有一样的apk, 下载成功
    if (downloadId == 0) {
      setState(() {
        downloadProcess = 1;
        downloadStatus = ProcessState.STATUS_SUCCESSFUL.toString();
      });
      return;
    }

    //出现了错误, 下载失败
    if (downloadId == -1) {
      setState(() {
        downloadProcess = 1;
        downloadStatus = ProcessState.STATUS_FAILED.toString();
      });
      return;
    }

    //正在下载文件
    timer = Timer.periodic(Duration(milliseconds: 100), (timer) async {
      var process = await UpdateApp.downloadProcess(downloadId: downloadId);
      //更新界面状态
      setState(() {
        downloadProcess = process.current / process.count;
        downloadStatus = process.status.toString();
      });

      if (process.status == ProcessState.STATUS_SUCCESSFUL ||
          process.status == ProcessState.STATUS_FAILED) {
        //如果已经下载成功, 取消计时
        timer.cancel();
      }
    });
  }
}
