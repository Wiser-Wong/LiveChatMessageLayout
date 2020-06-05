package com.wiser.livechatmessagelayout.ui.adapter.holder.chatholder;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wiser.livechatmessagelayout.ui.adapter.LiveChatAdapter;
import com.wiser.livechatmessagelayout.utils.CenterImageSpan;
import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.utils.LiveChatType;
import com.wiser.livechatmessagelayout.utils.LiveHelper;
import com.wiser.livechatmessagelayout.R;
import com.wiser.livechatmessagelayout.ui.adapter.holder.BaseHolder;

/**
 * @author wangxy
 *
 * 聊天普通消息holder
 */
public class LiveChatSimpleHolder extends BaseHolder<LiveChatBean> {

    private TextView tvLiveChatContent;

    public LiveChatSimpleHolder(@NonNull View itemView) {
        super(itemView);
        tvLiveChatContent = itemView.findViewById(R.id.tv_live_chat_content);
    }

    @Override
    public void bindData(LiveChatBean model, int position) {
        if (model == null) return;
        if (adapter() instanceof LiveChatAdapter){
            String nickName = TextUtils.isEmpty(model.nickName) ? "" : model.nickName;
            String content = TextUtils.isEmpty(model.content) ? "" : model.content;
            SpannableString spannableString;
            if (model.type == LiveChatType.CHAT_SHARE_ONESELF) {//本人分享直播间
                if (model.isVip) {
                    spannableString = new SpannableString("icon " + nickName + "  分享了本次直播");
                } else {
                    spannableString = new SpannableString(nickName + "  分享了本次直播");
                }
                if (model.isVip) {
                    Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                    drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                    spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getShareLiveColor())), 5, nickName.length() + 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getShareLiveColor())), 0, nickName.length() + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tvLiveChatContent.setText(spannableString);
            } else if (model.type == LiveChatType.CHAT_MINE_INFO_ROOM) {//自己来到直播间
                if (model.isVip) {
                    spannableString = new SpannableString("icon " + nickName + "  来了");
                } else {
                    spannableString = new SpannableString(nickName + "  来了");
                }
                if (model.isVip) {
                    Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                    drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                    spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineNickNameColor())), 5, nickName.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineChatContentColor())), nickName.length() + 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineNickNameColor())), 0, nickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineChatContentColor())), nickName.length(),spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tvLiveChatContent.setText(spannableString);
            } else {//普通评论
                if (model.isVip) {
                    spannableString = new SpannableString("icon " + nickName + "：" + content);
                    Drawable drawable = getContext().getResources().getDrawable(R.drawable.live_video_icon_vip);
                    drawable.setBounds(0, 0, LiveHelper.dip2px(getContext(), 13), LiveHelper.dip2px(getContext(), 13));
                    spannableString.setSpan(new CenterImageSpan(drawable), 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    if (model.isMine) {
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineNickNameColor())), 0, nickName.length() + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineChatContentColor())), nickName.length() + 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherNickNameColor())), 0, nickName.length() + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherChatContentColor())), nickName.length() + 6, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    tvLiveChatContent.setText(spannableString);
                } else {
                    if (model.isMine) {
                        spannableString = new SpannableString(nickName + "：" + content);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineNickNameColor())), 0, nickName.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getMineChatContentColor())), nickName.length() + 1,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvLiveChatContent.setText(spannableString);
                    } else {
                        spannableString = new SpannableString(nickName + "：" + content);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherNickNameColor())), 0, nickName.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherChatContentColor())), nickName.length() + 1,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tvLiveChatContent.setText(spannableString);
                    }
                }
            }
        }
    }
}