import Flutter
import UIKit

public class SwiftUpdateAppPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "cn.mofada.update_app", binaryMessenger: registrar.messenger())
    let instance = SwiftUpdateAppPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
          switch call.method {
          case "updateApp":
              goAppStore(call: call, result: result)
              break;
          case "getPlatformVersion"
            result("iOS " + UIDevice.current.systemVersion)
           break;
          default:
              result(FlutterMethodNotImplemented)
      }
  }


  // 跳转Apple Store
  func goAppStore(call: FlutterMethodCall, result: @escaping FlutterResult) {
      guard let args = call.arguments else {
          return
      }

      if let arguments = args as? [String: Any],
         //获取Apple ID
         let appleId = arguments["appleId"] as? String {
          //拼接地址
          let appStoreUrl = URL(string: String(format: appStoreLink, appleId))
          if let url = appStoreUrl, UIApplication.shared.canOpenURL(url) {
              if #available(iOS 10.0, *) {
                  UIApplication.shared.open(url, options: [:])
              } else {
                  UIApplication.shared.openURL(url)
              }
          }
          result(true)
      } else {
          result(false)
      }
  }
}
