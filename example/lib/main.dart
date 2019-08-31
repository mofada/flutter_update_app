import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:update_app/update_app.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
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

  void download() async {
    var name =
        await UpdateApp.updateApp(url: "https://mofada.cn/apks/exam.apk");
    print(name);

    setState(() {});
  }
}
