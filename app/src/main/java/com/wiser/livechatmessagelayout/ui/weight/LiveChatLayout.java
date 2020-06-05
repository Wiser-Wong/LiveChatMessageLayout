package com.wiser.livechatmessagelayout.ui.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.R;
import com.wiser.livechatmessagelayout.ui.ILiveVideoFloorMessageView;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author wangxy
 *
 * 弹幕布局
 */
public class LiveChatLayout extends FrameLayout implements LiveChatRecycleView.OnChatViewListener, View.OnClickListener, ILiveVideoFloorMessageView {

    private LiveChatRecycleView rlvChat; // 弹幕控件

    private TextView tvChatNewMsgCount;        // 弹幕新消息数

    private LiveChatIntoRoomTipLayout tipLayout; // 提示进入房间布局

    private boolean isRunningAnim;                // 是否正在运行动画

    private static final boolean IS_ANIM = false;    // 是否展示消息使用动画

    private static final int MAX_MSG_COUNT = 99;     // 最大消息之后展示99+

    private long count;                        // 记录消息数

    public LiveChatLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public LiveChatLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.live_video_floor_bottom_chat_layout, this, true);
        rlvChat = findViewById(R.id.rlv_chat);
        tvChatNewMsgCount = findViewById(R.id.tv_chat_new_message_count);
        tipLayout = findViewById(R.id.layout_into_room_tip);

        rlvChat.setOnChatViewListener(this);
        tvChatNewMsgCount.setOnClickListener(this);
    }

    // 设置聊天区域消息颜色配置
    @Override
    public void setChatTextColors(String mineNickNameColor, String mineChatContentColor, String otherNickNameColor, String otherChatContentColor, String shareLiveColor, String systemMsgColor) {
        if (rlvChat != null) rlvChat.setChatTextColors(mineNickNameColor,mineChatContentColor,otherNickNameColor,otherChatContentColor,shareLiveColor,systemMsgColor);
        if (tipLayout != null) tipLayout.setChatTextColors(shareLiveColor,otherNickNameColor,otherChatContentColor);
    }

    // 添加垂直弹幕数据
    @Override
    public void initMessages(List<LiveChatBean> chatBeans) {
        if (chatBeans == null) return;
        rlvChat.initMessages(chatBeans);
    }

    // 添加垂直弹幕数据
    @Override
    public void initMessage(LiveChatBean chatBean) {
        if (chatBean == null) return;
        rlvChat.initMessage(chatBean);
    }

    // 添加聊天多条数据
    @Override
    public void addMessages(List<LiveChatBean> chatBeans) {
        if (chatBeans == null) return;
        rlvChat.addMessages(chatBeans);
    }

    // 添加聊天单条数据
    @Override
    public void addMessage(LiveChatBean chatBean) {
        if (chatBean == null) return;
        rlvChat.addMessage(chatBean);
    }

    // 添加提醒单条数据
    @Override
    public void addTipMessage(LiveChatBean chatBean) {
        if (chatBean == null) return;
        rlvChat.addTipMessage(chatBean);

        // 没有按下并且滚动到底才会显示进入布局
        if (!rlvChat.isTouch() && rlvChat.isScrollBottom() && !rlvChat.isScrolling())
            tipLayout.intoRoomTipData(chatBean);
        else tipLayout.updateRoomTipData(chatBean);//数据非常多处于滑动中但是没有到底还需要更新进入直播间数据UI
    }

    // 展示新消息数量
    public void showNewMsgCountAnim(final boolean isShow) {
        if (IS_ANIM) {
            if (isRunningAnim) return;
            if (isShow && tvChatNewMsgCount.getVisibility() == View.VISIBLE) return;
            if (!isShow && tvChatNewMsgCount.getVisibility() == View.GONE) return;
            ObjectAnimator animator;
            if (isShow) {
                animator = ObjectAnimator.ofFloat(tvChatNewMsgCount, "translationY", tvChatNewMsgCount.getMeasuredHeight(), 0);
            } else {
                animator = ObjectAnimator.ofFloat(tvChatNewMsgCount, "translationY", tvChatNewMsgCount.getMeasuredHeight());
            }
            animator.setDuration(200);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    isRunningAnim = true;
                    tvChatNewMsgCount.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isRunningAnim = false;
                    if (isShow) tvChatNewMsgCount.setVisibility(View.VISIBLE);
                    else {
                        tvChatNewMsgCount.setVisibility(View.GONE);
                        resetMessageCount();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                    isRunningAnim = false;
                    if (isShow) tvChatNewMsgCount.setVisibility(View.VISIBLE);
                    else {
                        tvChatNewMsgCount.setVisibility(View.GONE);
                        resetMessageCount();
                    }
                }
            });

            animator.start();
        } else {
            if (isShow) {
                if (tvChatNewMsgCount.getVisibility() == GONE)
                    tvChatNewMsgCount.setVisibility(View.VISIBLE);
            } else {
                if (tvChatNewMsgCount.getVisibility() == VISIBLE) {
                    tvChatNewMsgCount.setVisibility(View.GONE);
                }
                resetMessageCount();
            }
        }
    }

    // 设置消息数量文本
    private void setMessageCountText() {
        if (this.count > MAX_MSG_COUNT)
            tvChatNewMsgCount.setText(MessageFormat.format("{0}+条新消息", MAX_MSG_COUNT));
        else tvChatNewMsgCount.setText(MessageFormat.format("{0}条新消息", this.count));
    }

    // 重置消息数量
    private void resetMessageCount() {
        LiveChatLayout.this.count = 0;
    }

    public void detach() {
        if (rlvChat != null) rlvChat.detach();
        rlvChat = null;
        tipLayout = null;
        tvChatNewMsgCount = null;
    }

    @Override
    public void showMessageCount(boolean isShow) {
        if (isShow) tipLayout.setHideUi(true);
        showNewMsgCountAnim(isShow);
        if (isShow && this.count > 0) setMessageCountText();
    }

    @Override
    public void calculateMessageCount(int count) {
        this.count = this.count + count;
    }

    @Override
    public void isHideIntoLayout(boolean isHide) {
        // 为了处理数据比列表控件高度小及没有填满的情况，进入布局和列表最后一条布局（两个布局是显示相同）错乱问题
        if (!rlvChat.canScrollVertically(-1)) return;
        // 列表最后一条是和进入布局一样的布局，所以此处控制进入布局隐藏，列表最后一条显示，列表最后一条隐藏，进入布局显示
        rlvChat.updateLastItemStatus(!isHide);
        tipLayout.setHideUi(isHide);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_chat_new_message_count) {// 新消息布局
            //隐藏新消息数量
            showNewMsgCountAnim(false);
            //处理显示进入主播间消息布局
            tipLayout.setHideUi(true);
            //处理点击气泡之后列表数据
            rlvChat.clickMsgBubble();
        }
    }
}
