package com.wiser.livechatmessagelayout.ui.fragment;

import com.wiser.livechatmessagelayout.bean.LiveChatBean;
import com.wiser.livechatmessagelayout.utils.LiveChatType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wangxy
 *
 * 直播浮层聊天评论业务
 */
public class LiveVideoFloorChatBiz {

    private LiveVideoFloorChatFragment ui;

    private String[] nickNames = new String[]{"天才", "傻瓜", "林俊杰", "杨丞琳", "天才", "傻瓜", "林俊杰", "杨丞琳", "天才", "傻瓜", "林俊杰", "杨丞琳", "天才", "傻瓜", "林俊杰", "杨丞琳", "天才", "傻瓜", "林俊杰", "杨丞琳", "天才", "傻瓜", "林俊杰", "杨丞琳"};

    private String[] content = new String[]{"那女孩对我说，说我是个傻瓜", "我命由我不由天", "我太难了啊", "直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报", "那女孩对我说，说我是个傻瓜", "我命由我不由天", "我太难了啊", "直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报", "那女孩对我说，说我是个傻瓜", "我命由我不由天", "我太难了啊", "直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报", "那女孩对我说，说我是个傻瓜", "我命由我不由天", "我太难了啊", "直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报", "那女孩对我说，说我是个傻瓜", "我命由我不由天", "我太难了啊", "直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报", "那女孩对我说，说我是个傻瓜", "我命由我不由天", "我太难了啊", "直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报"};

    private String[] data1 = new String[]{"小倩", "鬼吹灯", "玉皇大帝", "孙悟空"};

    public LiveVideoFloorChatBiz(LiveVideoFloorChatFragment ui) {
        this.ui = ui;
    }

    //第一条系统消息
    public LiveChatBean getSysMessage() {
        LiveChatBean liveChatBean = new LiveChatBean();
        liveChatBean.type = LiveChatType.CHAT_SYS_TIP;
        liveChatBean.content = "欢迎来到直播间！XXX严禁未成年进行直播或打赏，请大家共同准守、监督。直播间内严禁出现违法违规、低俗、色情、吸烟酗酒等内容，若有违规行为请及时举报。如主播在直播过程中以陪玩、送礼等方式进行诱导打赏、私下交易，清谨慎判断，以防人身或财产损失。";
        return liveChatBean;
    }

    public List<LiveChatBean> addData() {
        List<LiveChatBean> models = new ArrayList<>();
        int random = new Random().nextInt(nickNames.length);
        for (int i = 0; i < 10; i++) {
            LiveChatBean model = new LiveChatBean();
            model.nickName = nickNames[random];
            model.content = content[random];
            model.isVip = true;
            model.type = LiveChatType.CHAT_SIMPLE;
            models.add(model);
        }
        return models;
    }

    public LiveChatBean addItem() {
        LiveChatBean model = new LiveChatBean();
        model.nickName = data1[new Random().nextInt(4)];
        model.type = LiveChatType.CHAT_INTO_ROOM;
        model.isVip = true;
        model.isIntoRoomTip = true;
        model.isHideLastItem = true;
        return model;
    }

    public void detach() {
        ui = null;
    }

}
