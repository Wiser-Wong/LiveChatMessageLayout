package com.wiser.livechatmessagelayout.ui.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.utils.LiveHelper;
import com.wiser.livechatmessagelayout.R;
import com.wiser.livechatmessagelayout.utils.CenterImageSpan;
import com.wiser.livechatmessagelayout.utils.LiveChatType;

/**
 * @author wangxy
 *
 * 直播间来人提示布局
 */
public class LiveChatIntoRoomTipLayout extends FrameLayout {

    private TextView tvLiveChatContent;

    private LiveChatBean model;

    private String shareLiveColor = "#FFDB3E";//分享直播间颜色

    private String otherNickNameColor = "#FFDB3E";//其他昵称颜色

    private String otherIntoLiveColor = "#FFFFFF";//来了颜色

    public void setChatTextColors(String shareLiveColor, String otherNickNameColor, String otherIntoLiveColor){
        if (!TextUtils.isEmpty(shareLiveColor)) this.shareLiveColor = shareLiveColor;
        if (!TextUtils.isEmpty(otherNickNameColor)) this.otherNickNameColor = otherNickNameColor;
        if (!TextUtils.isEmpty(otherIntoLiveColor)) this.otherIntoLiveColor = otherIntoLiveColor;
    }

    public LiveChatIntoRoomTipLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public LiveChatIntoRoomTipLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.live_video_floor_chat_item, this, true);
        tvLiveChatContent = findViewById(R.id.tv_live_chat_content);
        setVisibility(GONE);
    }

    public void intoRoomTipData(LiveChatBean model) {
        if (model == null) {
            setVisibility(GONE);
            return;
        }
        this.model = model;

        if (!TextUtils.isEmpty(model.nickName))
            setVisibility(VISIBLE);
        else setVisibility(GONE);
        String nickName = TextUtils.isEmpty(model.nickName) ? "" : model.nickName;
        SpannableString spannableString;
        if (model.type == LiveChatType.CHAT_SHARE_OTHER) {//他人分享直播间
            if (model.isVip) {
                spannableString = new SpannableString("icon " + nickName + "  分享了本次直播");
            } else {
                spannableString = new SpannableString(nickName + "  分享了本次直播");
            }
            if (model.isVip) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(shareLiveColor)), 5, nickName.length() + 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(shareLiveColor)), 0, nickName.length() + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvLiveChatContent.setText(spannableString);
        } else if (model.type == LiveChatType.CHAT_INTO_ROOM) {//进入直播间
            if (model.isVip) {
                spannableString = new SpannableString("icon " + nickName + "  来了");
            } else {
                spannableString = new SpannableString(nickName + "  来了");
            }
            if (model.isVip) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherNickNameColor)), 5, nickName.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherIntoLiveColor)), nickName.length() + 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherNickNameColor)), 0, nickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherIntoLiveColor)), nickName.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvLiveChatContent.setText(spannableString);
        }
    }

    // 更新进入直播间数据
    public void updateRoomTipData(LiveChatBean model){
        if (model == null) {
            setVisibility(GONE);
            return;
        }
        this.model = model;
        if (TextUtils.isEmpty(model.nickName))
            setVisibility(GONE);

        String nickName = TextUtils.isEmpty(model.nickName) ? "" : model.nickName;
        SpannableString spannableString;
        if (model.type == LiveChatType.CHAT_SHARE_OTHER) {//他人分享直播间
            if (model.isVip) {
                spannableString = new SpannableString("icon " + nickName + "  分享了本次直播");
            } else {
                spannableString = new SpannableString(nickName + "  分享了本次直播");
            }
            if (model.isVip) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(shareLiveColor)), 5, nickName.length() + 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(shareLiveColor)), 0, nickName.length() + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvLiveChatContent.setText(spannableString);
        } else if (model.type == LiveChatType.CHAT_INTO_ROOM) {//进入直播间
            if (model.isVip) {
                spannableString = new SpannableString("icon " + nickName + "  来了");
            } else {
                spannableString = new SpannableString(nickName + "  来了");
            }
            if (model.isVip) {
                Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherNickNameColor)), 5, nickName.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherIntoLiveColor)), nickName.length() + 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherNickNameColor)), 0, nickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(otherIntoLiveColor)), nickName.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvLiveChatContent.setText(spannableString);
        }
    }

    // 设置隐藏状态
    public void setHideUi(boolean isHide) {
        if (model == null) {
            if (getVisibility() == VISIBLE) setVisibility(GONE);
            return;
        }
        if (isHide) {
            if (getVisibility() == VISIBLE) setVisibility(GONE);
        } else {
            if (getVisibility() == GONE) setVisibility(VISIBLE);
        }
    }

    public boolean isWandHide() {
        return model == null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        tvLiveChatContent = null;
        model = null;
    }
}
