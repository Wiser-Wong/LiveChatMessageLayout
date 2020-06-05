package com.wiser.livechatmessagelayout.utils;

/**
 * @author wangxy
 *
 * 直播垂直聊天消息类型
 */
public interface LiveChatType {

    int CHAT_SYS_TIP = 1000;//系统第一条消息

    int CHAT_SIMPLE = 1001;//普通消息

    int CHAT_INTO_ROOM = 1002;//进入直播间消息
    
    int CHAT_SHARE_OTHER = 1003;//分享他人直播间

    int CHAT_SHARE_ONESELF = 1004;//分享本人直播间

    int CHAT_MINE_INFO_ROOM = 1005;//自己进入直播间

}
