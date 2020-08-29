package com.shun.timer;

import com.shun.net.PublicNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServerTimerTask {
    private String ip;
    @Autowired
    private PublicNetwork publicNetwork;

    // 每10秒获取公网ip，检查服务
    @Scheduled(fixedRate = 10000)
    public void handlePublicIP() {
        String soip = publicNetwork.getSoip();
        System.out.println(soip);
    }
}
