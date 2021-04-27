# [update_app](https://pub.dev/packages/update_app)

Update the app, pass in the updated address, and update the app. 更新app, 传入更新的地址, 进行app更新

The Android part is implemented using DownloadManager. If the file has been downloaded, install it directly.
Android部分使用DownloadManager实现.若文件已下载, 则直接安装.

The Ios section does not support external updates, skip the App Store. Ios部分不支持外部更新, 跳转App Store.

## Experience demo(体验Demo)

[download apk(下载APK)](apks/app-debug.apk)

 <img src="images/screen.png" width = "320" height = "580" alt="screen" align=center />

## How to use(如何使用)

```dart
import 'package:update_app/update_app.dart';

/// Download the app and update
///
/// appleId: This is the app id of the app store, used to jump to the app store
/// url: This is the URL to download the apk file
/// title: Android download the title displayed in the notification bar, generally use the file name, or like this `update version 1.6`
/// description: Android download the description displayed in the notification bar, similar to the subtitle
///
/// @return `-1` -> `download failed`, `0` -> `There is a current apk locally, and the download is successful`, other (int) -> `downloadId`
var downloadId = await
UpdateApp.updateApp(url: "
apkPath
"
,
appleId:"
375380948
"
,
title:"
通知标题
"
,
description:"
通知描述
"
);

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
var process = await
UpdateApp.downloadProcess(downloadId: downloadId);
```

## Document

### UpdateApp.updateApp

#### Params

appleId - This is the app id of the app store, used to jump to the app store  
url - This is the URL to download the apk file  
title - Android download the title displayed in the notification bar, generally use the file name description - Android
download the description displayed in the notification bar, similar to the subtitle

#### Return

`-1` -> `download failed`   
`0` -> `There is a current apk locally, and the download is successful`   
other (int) -> `downloadId`

### UpdateApp.downloadProcess

#### Params

downloadId - [updateApp] 返回的downloadId

#### Return
[DownloadProcess]   
current: Currently downloaded length  
count: Total length  
status: [ProcessState]  
STATUS_PENDING: [ProcessState.STATUS_PENDING](https://developer.android.com/reference/android/app/DownloadManager#STATUS_PENDING)  
STATUS_RUNNING: [ProcessState.STATUS_RUNNING](https://developer.android.com/reference/android/app/DownloadManager#STATUS_RUNNING)  
STATUS_PAUSED: [ProcessState.STATUS_PAUSED](https://developer.android.com/reference/android/app/DownloadManager#STATUS_PAUSED)  
STATUS_SUCCESSFUL: [ProcessState.STATUS_SUCCESSFUL](https://developer.android.com/reference/android/app/DownloadManager#STATUS_SUCCESSFUL)  
STATUS_FAILED: [ProcessState.STATUS_FAILED](https://developer.android.com/reference/android/app/DownloadManager#STATUS_FAILED)  

# Features(特点)

## Android part(Android部分)

1. Download using [DownloadManager](https://developer.android.com/reference/android/app/DownloadManager)  
   使用[DownloadManager](https://developer.android.com/reference/android/app/DownloadManager)进行下载
2. Monitor download completion broadcast  
   监听下载完成广播
3. Adapt [Android 6.0 Runtime Permissions](https://developer.android.com/training/permissions/requesting?hl=en) Files
   are stored in the app directory, no need to apply for runtime permissions  
   适配[Android 6.0运行时权限](https://developer.android.com/training/permissions/requesting?hl=zh-cn) 文件存储在app目录, 不需要申请运行时权限
4. Adapt [Android 7.0FileProvider](https://developer.android.com/reference/android/support/v4/content/FileProvider)  
   适配[Android 7.0FileProvider](https://developer.android.com/reference/android/support/v4/content/FileProvider)
5. Adapt Android 8.0 installation permissions  
   适配Android 8.0安装权限
6.
Adapt [Android 9.0 Network Security Configuration] (https://developer.android.com/training/articles/security-config)  
适配[Android 9.0网络安全配置](https://developer.android.com/training/articles/security-config)

## IOS section(IOS部分)

1. Jump to the app store 跳转应用商店