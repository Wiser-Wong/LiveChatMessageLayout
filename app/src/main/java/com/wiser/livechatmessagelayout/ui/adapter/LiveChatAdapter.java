package com.wiser.livechatmessagelayout.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.R;
import com.wiser.livechatmessagelayout.ui.adapter.holder.BaseHolder;
import com.wiser.livechatmessagelayout.ui.adapter.holder.chatholder.LiveChatIntoRoomHolder;
import com.wiser.livechatmessagelayout.ui.adapter.holder.chatholder.LiveChatSimpleHolder;
import com.wiser.livechatmessagelayout.ui.adapter.holder.chatholder.LiveChatTipHolder;
import com.wiser.livechatmessagelayout.utils.LiveChatType;

/**
 * @author LiveChatAdapter 聊天适配器
 */
public class LiveChatAdapter extends BaseAdapter<LiveChatBean, BaseHolder> {

    private String mineNickNameColor = "#ADF448";//我的昵称颜色

    private String mineChatContentColor = "#FFFFFF";//我发送文本的颜色

    private String otherNickNameColor = "#FFDB3E";//其他昵称颜色

    private String otherChatContentColor = "#FFFFFF";//其他用户发送文本的颜色

    private String shareLiveColor = "#FFDB3E";//分享直播间颜色

    private String systemMsgColor = "#FFDB3E";//系统消息颜色

    public void setChatTextColors(String mineNickNameColor, String mineChatContentColor, String otherNickNameColor, String otherChatContentColor, String shareLiveColor, String systemMsgColor){
        this.mineNickNameColor = !TextUtils.isEmpty(mineNickNameColor) ? mineNickNameColor : "#ADF448";
        this.mineChatContentColor = !TextUtils.isEmpty(mineChatContentColor) ? mineChatContentColor : "#FFFFFF";
        this.otherNickNameColor = !TextUtils.isEmpty(otherNickNameColor) ? otherNickNameColor : "#FFDB3E";
        this.otherChatContentColor = !TextUtils.isEmpty(otherChatContentColor) ? otherChatContentColor : "#FFFFFF";
        this.shareLiveColor = !TextUtils.isEmpty(shareLiveColor) ? shareLiveColor : "#FFDB3E";;
        this.systemMsgColor = !TextUtils.isEmpty(systemMsgColor) ? systemMsgColor : "#FFDB3E";;
    }

    public String getOtherNickNameColor() {
        return otherNickNameColor;
    }

    public void setOtherNickNameColor(String otherNickNameColor) {
        this.otherNickNameColor = !TextUtils.isEmpty(otherNickNameColor) ? otherNickNameColor : "#FFDB3E";
    }

    public String getOtherChatContentColor() {
        return otherChatContentColor;
    }

    public void setOtherChatContentColor(String otherChatContentColor) {
        this.otherChatContentColor = !TextUtils.isEmpty(otherChatContentColor) ? otherChatContentColor : "#FFFFFF";
    }

    public String getMineNickNameColor() {
        return mineNickNameColor;
    }

    public void setMineNickNameColor(String mineNickNameColor) {
        this.mineNickNameColor = !TextUtils.isEmpty(mineNickNameColor) ? mineNickNameColor : "#ADF448";
    }

    public String getMineChatContentColor() {
        return mineChatContentColor;
    }

    public void setMineChatContentColor(String mineChatContentColor) {
        this.mineChatContentColor = !TextUtils.isEmpty(mineChatContentColor) ? mineChatContentColor : "#FFFFFF";
    }

    public String getShareLiveColor() {
        return shareLiveColor;
    }

    public void setShareLiveColor(String shareLiveColor) {
        this.shareLiveColor = !TextUtils.isEmpty(shareLiveColor) ? shareLiveColor : "#FFDB3E";;
    }

    public String getSystemMsgColor() {
        return systemMsgColor;
    }

    public void setSystemMsgColor(String systemMsgColor) {
        this.systemMsgColor = !TextUtils.isEmpty(systemMsgColor) ? systemMsgColor : "#FFDB3E";;
    }

    public LiveChatAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) != null) return getItem(position).type;
        return super.getItemViewType(position);
    }

    @Override
    public BaseHolder newViewHolder(ViewGroup viewGroup, int type) {
        switch (type) {
            case LiveChatType.CHAT_SYS_TIP://系统第一条消息
                return new LiveChatTipHolder(inflate(R.layout.live_video_floor_chat_tip_item, viewGroup));
            case LiveChatType.CHAT_SIMPLE://普通消息
            case LiveChatType.CHAT_SHARE_ONESELF://本人分享直播间
                return new LiveChatSimpleHolder(inflate(R.layout.live_video_floor_chat_item, viewGroup));
            case LiveChatType.CHAT_INTO_ROOM://进入直播间
            case LiveChatType.CHAT_SHARE_OTHER://他人分享直播间
                return new LiveChatIntoRoomHolder(inflate(R.layout.live_video_floor_chat_item, viewGroup));
            default:
                return new LiveChatSimpleHolder(inflate(R.layout.live_video_floor_chat_item, viewGroup));

        }
    }

}
