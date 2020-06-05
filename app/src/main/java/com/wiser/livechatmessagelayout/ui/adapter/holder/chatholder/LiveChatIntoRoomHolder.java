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
 * 聊天进入直播间holder
 */
public class LiveChatIntoRoomHolder extends BaseHolder<LiveChatBean> {

    private TextView tvLiveChatContent;

    public LiveChatIntoRoomHolder(@NonNull View itemView) {
        super(itemView);
        tvLiveChatContent = itemView.findViewById(R.id.tv_live_chat_content);
    }

    @Override
    public void bindData(LiveChatBean model, int position) {
        if (model == null) return;
        if (adapter() instanceof LiveChatAdapter){
            if (position == adapter().getItemCount() - 1 && model.isHideLastItem) {
                itemView.setVisibility(View.INVISIBLE);
                tvLiveChatContent.setVisibility(View.INVISIBLE);
            } else {
                itemView.setVisibility(View.VISIBLE);
                tvLiveChatContent.setVisibility(View.VISIBLE);
            }
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
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getShareLiveColor())), 5, nickName.length() + 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getShareLiveColor())), 0, nickName.length() + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherNickNameColor())), 5, nickName.length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherChatContentColor())), nickName.length() + 5,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else {
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherNickNameColor())), 0, nickName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(((LiveChatAdapter)adapter()).getOtherChatContentColor())), nickName.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tvLiveChatContent.setText(spannableString);
            }
        }
    }
}