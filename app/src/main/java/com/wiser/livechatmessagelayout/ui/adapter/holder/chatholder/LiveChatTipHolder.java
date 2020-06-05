package com.wiser.livechatmessagelayout.ui.adapter.holder.chatholder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.R;
import com.wiser.livechatmessagelayout.ui.adapter.LiveChatAdapter;
import com.wiser.livechatmessagelayout.ui.adapter.holder.BaseHolder;

/**
 * @author wangxy
 * 
 *         聊天系统消息holder
 */
public class LiveChatTipHolder extends BaseHolder<LiveChatBean> {

	private TextView tvLiveChatContent;

	public LiveChatTipHolder(@NonNull View itemView) {
		super(itemView);
		tvLiveChatContent = itemView.findViewById(R.id.tv_live_chat_content);
	}

	@Override
    public void bindData(LiveChatBean model, int position) {
		if (model == null) return;
		//配置系统消息颜色
		if (adapter() instanceof LiveChatAdapter)
			tvLiveChatContent.setTextColor(Color.parseColor(((LiveChatAdapter) adapter()).getSystemMsgColor()));
		tvLiveChatContent.setText(model.content);
	}
}