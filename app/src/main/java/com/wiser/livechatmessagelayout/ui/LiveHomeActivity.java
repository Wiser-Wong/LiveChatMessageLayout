package com.wiser.livechatmessagelayout.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wiser.livechatmessagelayout.R;
import com.wiser.livechatmessagelayout.ui.fragment.LiveVideoFloorChatFragment;
import com.wiser.livechatmessagelayout.ui.fragment.LiveVideoFragment;
import com.wiser.livechatmessagelayout.utils.LiveHelper;

/**
 * @author Wiser
 *
 *         直播间首页
 */
public class LiveHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LiveHelper.setStatusBarFullTransparent(this);
        setContentView(R.layout.live_video_home_act);

        LiveHelper.commitReplace(this,R.id.fl_video,new LiveVideoFragment(),LiveVideoFragment.class.getName());
        LiveHelper.commitReplace(this,R.id.fl_chat,new LiveVideoFloorChatFragment(),LiveVideoFloorChatFragment.class.getName());
    }
}
