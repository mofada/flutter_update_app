# update_app
更新app, 传入更新的地址, 进行app更新,
Android部分使用DownloadManager实现.若文件已下载, 则直接安装.  
Ios部分不支持外部更新, 跳转App Store.

## 使用方法
```dart
import 'package:update_app/update_app.dart';

UpdateApp.updateApp(url: "apkPath",appleId:"375380948",title:"通知标题",description:"通知描述");
```

# 特点
## Android部分
1. 使用[DownloadManager](https://developer.android.com/reference/android/app/DownloadManager)进行下载
2. 监听下载完成广播  
3. 适配[Android 6.0运行时权限](https://developer.android.com/training/permissions/requesting?hl=zh-cn) 文件存储在app目录, 不需要申请运行时权限
4. 适配[Android 7.0FileProvider](https://developer.android.com/reference/android/support/v4/content/FileProvider)
5. 适配Android 8.0安装权限
6. 适配[Android 9.0网络安全配置](https://developer.android.com/training/articles/security-config)

## IOS部分
1. 跳转应用商店