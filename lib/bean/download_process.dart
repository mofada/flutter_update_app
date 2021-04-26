/// @author : fada
/// @email : fada@mofada.cn
/// @date : 2021/4/26 15:31
/// @description : input your description

class DownloadProcess {
  int current;

  int count;

  ProcessState status;

  DownloadProcess(
      {required this.current, required this.count, required this.status});

  static DownloadProcess fromMap(Map<dynamic, dynamic> json) {
    //获取第一个值
    var status = json["status"] as String;

    return DownloadProcess(
        current: json["current"] as int,
        count: json["count"] as int,
        status: ProcessState.values.firstWhere(
            (element) => element.toString() == "ProcessState.$status"));
  }

  @override
  String toString() {
    return 'DownloadProcess{current: $current, count: $count, status: $status}';
  }
}

enum ProcessState {
  ///下载开始等待时
  STATUS_PENDING,

  ///前正在运行下载时
  STATUS_RUNNING,

  ///当下载等待重试或继续时
  STATUS_PAUSED,

  ///成功完成下载后的值
  STATUS_SUCCESSFUL,

  ///下载失败（并且不会重试）时
  STATUS_FAILED,
}
