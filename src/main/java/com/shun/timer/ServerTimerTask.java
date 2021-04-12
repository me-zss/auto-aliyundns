package com.shun.timer;

import com.shun.dns.AliyunDns;
import com.shun.net.PublicNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component

public class ServerTimerTask {
    private static int index;
    private Logger logger = LoggerFactory.getLogger(ServerTimerTask.class);
    private static String ip;
    @Autowired
    private PublicNetwork publicNetwork;
    @Autowired
    private AliyunDns aliyunDns;

    // 每5分钟获取公网ip，检查服务
    @Scheduled(fixedRate = 300000)
    public void handlePublicIP() {
        String newIp = getIp();
        if (newIp ==null ) {
            logger.error("获取公网IP失败！");
        } else if (ip == null || !newIp.trim().equals(ip)) {
            // 进行dns解析
            logger.info("IP改变：" + ip + " ---> " + newIp);
            ip = newIp.trim();
            String recordId = aliyunDns.getrRecordId();
            if (recordId == null) {
                logger.info("添加dns解析记录");
                boolean b = aliyunDns.addRecord(ip);
                if (b) {
                    logger.info("成功添加dns记录为："+ip);
                } else {
                    logger.error("添加dns记录失败");
                }
            } else {
                String orignIp = aliyunDns.getOrignIp();
                if (!ip.equals(orignIp)) {
                    logger.info("公网IP"+ip+"与远端IP"+orignIp+"不一致，进行修改");
                    boolean b = aliyunDns.updateRocord(ip);
                    if (b) {
                        logger.info("dns记录成功修改为："+ip);
                    } else {
                        logger.error("dns记录修改失败");
                    }
                } else {
                    logger.info("公网IP"+ip+"已在远端解析，跳过");
                }
            }
        } else {
            newIp = newIp.trim();
            String orignIp = aliyunDns.getOrignIp();
            if (!newIp.equals(orignIp)) {
                logger.info("公网IP"+newIp+"与远端IP"+orignIp+"不一致，进行修改");
                boolean b = aliyunDns.updateRocord(ip);
                if (b) {
                    logger.info("dns记录成功修改为："+ip);
                } else {
                    logger.error("dns记录修改失败");
                }
            } else {
                logger.info("IP未改变：" + ip);
            }
        }

    }

    private String getIp() {
        try {
            String newIp = null;
//            if (index == 0) {
//                logger.info("访问http://ifconfig.me/ip获取IP中");
//                newIp = publicNetwork.getPublicIp01();
//            } else
            if (index == 0) {
                logger.info("访问http://ipinfo.io/ip获取IP中");
                newIp = publicNetwork.getPublicIp02();
            } else if (index == 1) {
                logger.info("访问http://www.net.cn/static/customercare/yourip.asp获取IP中");
                newIp = publicNetwork.getPublicIp03();
                Pattern pattern = Pattern.compile("((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)");
                Matcher m = pattern.matcher(newIp);
                if (m.find()) {
                    newIp = m.group(0);
                }
            }
            index = index == 1 ? 0 : ++index;
            return newIp;
        } catch (Exception e) {
            logger.error("获取公网IP出现异常！\n"+e.getMessage());
            return null;
        }
    }
}
