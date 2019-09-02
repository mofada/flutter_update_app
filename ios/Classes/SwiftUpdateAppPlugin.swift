import Flutter
import UIKit

public class SwiftUpdateAppPlugin: NSObject, FlutterPlugin {
    //Apple Store Link
    let appStoreLink = "https://itunes.apple.com/us/app/apple-store/id%@?mt=8"

    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "cn.mofada.cn/update_app", binaryMessenger: registrar.messenger())
        let instance = SwiftUpdateAppPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        switch call.method {
        case "updateApp":
            goAppStore(call: call)
            break;
        default:
            result(FlutterMethodNotImplemented)
        }
    }


    // 跳转Apple Store
    func goAppStore(call: FlutterMethodCall) {
        //获取Apple ID
        let appleId: String = call.value(forKey: "appleId") as! String
        //拼接地址
        let appStoreUrl = URL(string: String(format: appStoreLink, appleId))
        if let url = appStoreUrl, UIApplication.shared.canOpenURL(url) {
            if #available(iOS 10.0, *) {
                UIApplication.shared.open(url, options: [:])
            } else {
                UIApplication.shared.openURL(url)
            }
        }
    }
}
