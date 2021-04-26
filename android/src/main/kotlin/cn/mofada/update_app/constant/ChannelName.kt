package cn.mofada.update_app.constant

/**
 * @author fada
 * @email fada@mofada.cn
 * @date 2020/8/10
 * @description 通道名称, 定义常量
 */
enum class ChannelName(val id: String) {
    /**
     * 推送插件
     */
    UPDATE_PLUGIN("mofada.cn/update_app"),

    /**
     * 广播接收插件
     */
    RECEIVER_PLUGIN("mofada.cn/update_app_receiver")
}