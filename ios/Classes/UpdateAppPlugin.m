#import "UpdateAppPlugin.h"
#import <update_app/update_app-Swift.h>

@implementation UpdateAppPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftUpdateAppPlugin registerWithRegistrar:registrar];
}
@end
