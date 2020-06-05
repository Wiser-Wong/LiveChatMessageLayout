package com.wiser.livechatmessagelayout.ui;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;

import java.util.List;

/**
 * @author wangxy
 *
 * floor层消息处理交互接口
 */
public interface ILiveVideoFloorMessageView {

    void setChatTextColors(String mineNickNameColor, String mineChatContentColor, String otherNickNameColor, String otherChatContentColor, String shareLiveColor, String systemMsgColor);

    void initMessages(List<LiveChatBean> chatBeans);

    void initMessage(LiveChatBean chatBean);

    void addMessages(List<LiveChatBean> chatBeans);

    void addMessage(LiveChatBean chatBean);

    void addTipMessage(LiveChatBean chatBean);
}