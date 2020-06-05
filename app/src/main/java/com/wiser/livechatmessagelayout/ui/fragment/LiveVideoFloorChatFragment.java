package com.wiser.livechatmessagelayout.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wiser.livechatmessagelayout.utils.HandlerHelper;
import com.wiser.livechatmessagelayout.ui.ILiveVideoFloorMessageView;
import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.ui.weight.LiveChatLayout;
import com.wiser.livechatmessagelayout.utils.LiveChatType;
import com.wiser.livechatmessagelayout.R;

import java.util.List;

/**
 * @author wangxy
 *
 *         聊天室评论Fragment
 */
public class LiveVideoFloorChatFragment extends Fragment implements ILiveVideoFloorMessageView {

	private LiveChatLayout chatLayout;

	private LiveVideoFloorChatBiz biz;

	private long delayMillis = 2000;//延迟时间 进入直播间添加消息和普通消息内容延迟时间要保持一致

	@Nullable
	@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.live_video_floor_chat_fragment, container, false);
		chatLayout = view.findViewById(R.id.live_chat);
		initData();
		return view;
	}

	private void initData() {
		biz = new LiveVideoFloorChatBiz(this);

		chatLayout.initMessage(biz.getSysMessage());

		mineIntoRoom();

		HandlerHelper.mainLooper().execute(runnable,delayMillis);

		HandlerHelper.mainLooper().execute(runnable1,delayMillis);

	}

	private Runnable	runnable	= new Runnable() {

		@Override public void run() {
			addMessages(biz.addData());
			//一定要现在添加速度
			HandlerHelper.mainLooper().execute(runnable, delayMillis);
		}
	};

	private Runnable	runnable1	= new Runnable() {

		@Override public void run() {
			addTipMessage(biz.addItem());
			HandlerHelper.mainLooper().execute(runnable1, delayMillis);
		}
	};

	//本人进入直播间
	private void mineIntoRoom(){
		//本人进入直播间
		HandlerHelper.mainLooper().execute(() -> {
				addMessage(new LiveChatBean(true, true, "自己的昵称","", LiveChatType.CHAT_MINE_INFO_ROOM, false, false));
		},1500);
	}

	// 设置聊天区域消息颜色配置
	@Override
    public void setChatTextColors(String mineNickNameColor, String mineChatContentColor, String otherNickNameColor, String otherChatContentColor, String shareLiveColor, String systemMsgColor) {
		if (chatLayout != null) chatLayout.setChatTextColors(mineNickNameColor,mineChatContentColor,otherNickNameColor,otherChatContentColor,shareLiveColor,systemMsgColor);
	}

	@Override
    public void initMessages(List<LiveChatBean> chatBeans) {
		if (chatLayout != null) chatLayout.initMessages(chatBeans);
	}

	@Override
    public void initMessage(LiveChatBean chatBean) {
		if (chatLayout != null) chatLayout.initMessage(chatBean);
	}

	@Override
    public void addMessages(List<LiveChatBean> chatBeans) {
		if (chatLayout != null) chatLayout.addMessages(chatBeans);
	}

	// 添加评论消息
	@Override
    public void addMessage(LiveChatBean chatBean) {
		if (chatLayout != null) chatLayout.addMessage(chatBean);
	}

	// 添加提示消息
	@Override
    public void addTipMessage(LiveChatBean chatBean) {
		if (chatLayout != null) chatLayout.addTipMessage(chatBean);
	}

	@Override
    public void onDetach() {
		HandlerHelper.mainLooper().getHandler().removeCallbacks(runnable);
		HandlerHelper.mainLooper().getHandler().removeCallbacks(runnable1);
		super.onDetach();
        if (biz != null) biz.detach();
		biz = null;
		if (chatLayout != null) chatLayout.detach();
		chatLayout = null;
	}
}
