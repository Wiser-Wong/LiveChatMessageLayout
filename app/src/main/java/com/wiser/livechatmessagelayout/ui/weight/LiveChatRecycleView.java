package com.wiser.livechatmessagelayout.ui.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.ui.ILiveVideoFloorMessageView;
import com.wiser.livechatmessagelayout.ui.adapter.LiveChatAdapter;
import com.wiser.livechatmessagelayout.utils.ChatSmoothLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxy
 *
 * 直播弹幕列表视图
 */
public class LiveChatRecycleView extends RecyclerView implements ILiveVideoFloorMessageView {

    private LiveChatAdapter chatAdapter;

    private boolean isTouch; // 是否触摸聊天列表

    private final int MAX_COUNT = 100; // 最大数量

    private final int NEAR_BOTTOM_COUNT = 1; // 滚动到接近底部然后顺滑滚动

    private List<LiveChatBean> cacheList; // 缓存滑动或者触摸聊天列表时并且没有处于最底部的时候记录存储，等没有触摸并且处于最底部的时候讲缓存数据添加进聊天列表

    private List<LiveChatBean> models = new ArrayList<>(); //记录新增消息

    private OnChatViewListener onChatViewListener;

    private LiveChatBean intoRoomTipModel;   // 记录进入直播间数据

    private boolean isScrolling;                // 是否滑动中

    private boolean isScrollTop;                // 是否滚动到顶部

    private boolean isClickBubble;//是否点击气泡

    public LiveChatRecycleView(@NonNull Context context) {
        super(context);
        init();
    }

    public LiveChatRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 初始化
    private void init() {
        setLayoutManager(new ChatSmoothLayoutManager(getContext()));
//        doTopGradualEffect();
        setItemAnimator(null);// 去掉动画要不删除最后一条的时候滚动到底会影响滚动
        setAdapter(chatAdapter = new LiveChatAdapter(getContext()));
    }

    // 设置聊天区域消息颜色配置
    @Override
    public void setChatTextColors(String mineNickNameColor, String mineChatContentColor, String otherNickNameColor, String otherChatContentColor, String shareLiveColor, String systemMsgColor) {
        if (chatAdapter != null) chatAdapter.setChatTextColors(mineNickNameColor,mineChatContentColor,otherNickNameColor,otherChatContentColor,shareLiveColor,systemMsgColor);
    }

    // 初始消息数据
    @Override
    public void initMessages(List<LiveChatBean> chatBeans) {
        if (chatBeans == null) return;
        chatAdapter.setItems(chatBeans);
        scrollBottom();
    }

    // 初始消息数据
    @Override
    public void initMessage(LiveChatBean chatBean) {
        if (chatBean == null) return;
        List<LiveChatBean> chatBeans = new ArrayList<>();
        chatBeans.add(chatBean);
        initMessages(chatBeans);
    }

    // 添加聊天多条数据
    @Override
    public synchronized void addMessages(List<LiveChatBean> chatBeans) {
        if (chatBeans == null) return;
        //检查是否超过阈值 进行删除
        checkRemoveList(chatAdapter.getItemCount() - MAX_COUNT);
        // 不点击气泡的时候才会处理
        if (!isClickBubble){
            //按下的时候 存储缓存 抬起的时候处理了将缓存再次添加到消息中
            if (isTouch || (!isScrollBottom() && !isScrolling)) {
                addCacheList(chatBeans);
                if (isClickBubble){
                    isClickBubble = false;
                }else if (onChatViewListener != null) {
                    if (isScrollBottom()) {
                        onChatViewListener.showMessageCount(false);
                        return;
                    }
                    onChatViewListener.calculateMessageCount(chatBeans.size());
                    if (!isTouch){
                        onChatViewListener.showMessageCount(true);
                    }
                }
                return;
            }
        }

        // 点击状态重置
        isClickBubble = false;

        // 只要是进入到这个地方，就不显示消息气泡以及
        if (onChatViewListener != null){
            onChatViewListener.showMessageCount(false);
            onChatViewListener.isHideIntoLayout(false);
        }

        // 如果最后一条是进入直播间提示
        if (isIntoRoomTip()) {
            if (chatAdapter.getItemCount() > 0) {
                // 删除最后一条
                chatAdapter.delete(chatAdapter.getItemCount() - 1);
            }
            // 如果进入直播间的数据不为空，再将其添加进聊天列表
            if (LiveChatRecycleView.this.intoRoomTipModel != null) chatBeans.add(intoRoomTipModel);
        }
        // 将新增数据添加到列表
        chatAdapter.addList(chatBeans);
        scrollBottom();

        // 清除缓存
        clearCache();
    }

    // 添加聊天单条数据
    @Override
    public synchronized void addMessage(LiveChatBean chatBean) {
        if (chatBean == null) return;

        resetModels();

        models.add(chatBean);

        addMessages(models);
    }

    // 添加提醒单条数据
    @Override
    public synchronized void addTipMessage(LiveChatBean chatBean) {
        if (chatBean == null) return;

        this.intoRoomTipModel = chatBean;

        if (isTouch || (!isScrollBottom() )) return;

        // 如果最后一条是进入直播间提示
        if (isIntoRoomTip()) {
            // 更新聊天列表最后一条数据
            chatAdapter.getItems().set(chatAdapter.getItemCount() - 1, chatBean);
            chatAdapter.notifyItemChanged(chatAdapter.getItemCount() - 1);
        } else {
            // 否则增加最后一条数据
            chatAdapter.addItem(chatAdapter.getItemCount(), chatBean);
        }

        scrollBottom();
    }

    // 添加滑动缓存 当滑动的时候发来新的聊天消息 记录缓存起来
    private void addCacheList(List<LiveChatBean> models) {
        getCacheList().addAll(models);
        checkRemoveCacheList(getCacheList().size() - MAX_COUNT);
    }

    // 获取缓存的集合
    public List<LiveChatBean> getCacheList() {
        if (cacheList == null) cacheList = new ArrayList<>();
        return cacheList;
    }

    // 是否有缓存
    private boolean isHaveCache(){
        return getCacheList().size() != 0;
    }

    // 清除缓存
    private void clearCache(){
        if (cacheList != null && cacheList.size() > 0) cacheList.clear();
    }

    // 重置记录数据
    private void resetModels() {
        if (models != null) models.clear();
        else models = new ArrayList<>();
    }

    // 移除大于最大条时最开始的数据
    public void checkRemoveList(int size) {
        if (chatAdapter.getItemCount() > MAX_COUNT) {
            for (int i = 0; i < size; i++) {
                if (chatAdapter.getItems() != null) {
                    chatAdapter.delete(i);
                    i--;
                    size--;
                }
            }
        }
    }

    // 移除大于最大条时缓存多的数据
    public void checkRemoveCacheList(int size) {
        if (getCacheList().size() > MAX_COUNT) {
            for (int i = 0; i < size; i++) {
                getCacheList().remove(i);
                i--;
                size--;
            }
        }
    }

    // 滑动到底部
    public void scrollBottom() {
        smoothScrollToPosition(chatAdapter.getItemCount() - 1);
    }

    // 滚动接近底部 然后动效滚动到底部
    public void scrollNearBottom() {
        // 如果当前处于距底部很多条数据的时候会滑动好长时间，所以先滑动到距底部倒数个数位置，然后在平滑滚动到底部
        if (chatAdapter.getItemCount() - 1 - NEAR_BOTTOM_COUNT >= NEAR_BOTTOM_COUNT)
            scrollToPosition(chatAdapter.getItemCount() - 1 - NEAR_BOTTOM_COUNT);
        scrollBottom();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isScrolling = state != 0;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //有可能不为0情况，所以在onScrollStateChanged中最终判断是否滑动
        isScrolling = dy != 0;
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (layoutManager != null) {
            isScrollTop = layoutManager.findFirstVisibleItemPosition() == 0;
        }
        if (isScrollBottom()) {
           if (onChatViewListener != null) onChatViewListener.showMessageCount(false);
        }
        //快速滑动到底没有触摸事件了并且有缓存
        if (!isTouch && isScrollBottom() && isHaveCache()){
            //添加缓存消息
            addMessages(getCacheList());
            //显示进入消息
            if (onChatViewListener != null) onChatViewListener.isHideIntoLayout(false);
        }
    }

    // 是否聊天列表滚动到底部
    public boolean isScrollBottom() {
        return !canScrollVertically(1);
    }

    //是否正在滑动中
    public boolean isScrolling() {
        return isScrolling;
    }

    //是否滚动到顶部
    public boolean isScrollTop() {
        return isScrollTop;
    }

    // 是否最后一条是新进入的提示
    private boolean isIntoRoomTip() {
        return (chatAdapter != null && chatAdapter.getItemCount() > 0 && chatAdapter.getItem(chatAdapter.getItemCount() - 1) != null)
                && chatAdapter.getItem(chatAdapter.getItemCount() - 1).isIntoRoomTip;
    }

    // 更改最后一条状态
    public void updateLastItemStatus(boolean isHide) {
        LiveChatBean model = chatAdapter.getItem(chatAdapter.getItemCount() - 1);
        if (model != null) {
            model.isHideLastItem = isHide;
            // 更新聊天列表最后一条数据
            chatAdapter.getItems().set(chatAdapter.getItemCount() - 1, model);
            chatAdapter.notifyItemChanged(chatAdapter.getItemCount() - 1);
        }
    }

    // 是否触摸聊天列表
    public boolean isTouch() {
        return isTouch;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                performClick();
                // 按下的时候去掉底部进入布局，显示列表最后一条和进入布局一样的item
                if (onChatViewListener != null) onChatViewListener.isHideIntoLayout(true);
                break;
            case MotionEvent.ACTION_MOVE://处理ViewPager滑动影响聊天列表触摸判断
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                performClick();
                //滑动到底刚结束触摸事件并且有缓存
                if (isScrollBottom() && isHaveCache()){
                    //添加缓存消息
                    addMessages(getCacheList());
                    scrollNearBottom();
                    // 抬起的时候显示底部进入布局，去掉列表最后一条和进入布局一样的item
                    if (onChatViewListener != null) onChatViewListener.isHideIntoLayout(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    // 添加缓存的数据
    public synchronized void addStorageData() {
        addMessages(getCacheList());
        // 清空缓存否则再次添加会添加许多条
        if (cacheList != null) cacheList.clear();
    }

    //处理点击气泡
    public void clickMsgBubble(){
        isClickBubble = true;
        //更新聊天列表最后一条消息状态让最后一条消息隐藏
        updateLastItemStatus(false);
        //添加缓存
        addStorageData();
        //滚动到底部
        scrollNearBottom();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        detach();
    }

    public void detach() {
        if (cacheList != null) cacheList.clear();
        cacheList = null;
        if (models != null) models.clear();
        models = null;
        onChatViewListener = null;
        chatAdapter = null;
        intoRoomTipModel = null;
    }

    public void setOnChatViewListener(OnChatViewListener onChatViewListener) {
        this.onChatViewListener = onChatViewListener;
    }

    public interface OnChatViewListener {

        void showMessageCount(boolean isShow);

        void calculateMessageCount(int count);

        //是否隐藏进入直播间布局
        void isHideIntoLayout(boolean isHide);
    }
}
