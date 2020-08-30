package com.shun.dns;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class AliyunDns {
    @Value("${aliyun.mainDemain}")
    private String mainDemain;
    @Value("${aliyun.subDemain}")
    private String subDemain;
    private String recordId;
    @Resource
    private DefaultProfile profile;
    private Logger logger = LoggerFactory.getLogger(AliyunDns.class);

    public boolean updateRocord(String ip) {
        IAcsClient client = new DefaultAcsClient(profile);

        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setRecordId(getrRecordId());
        request.setRR(subDemain);
        request.setType("A");
        request.setValue(ip);
        try {
            UpdateDomainRecordResponse response = client.getAcsResponse(request);
            logger.info(new Gson().toJson(response));
            return true;
        } catch (ServerException e) {
            logger.error(e.getMessage());
            return false;
        } catch (ClientException e) {
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("RequestId:" + e.getRequestId());
            return false;
        }
    }

    public boolean addRecord(String ip) {
        IAcsClient client = new DefaultAcsClient(profile);
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        request.setValue(ip);
        request.setType("A");
        request.setRR(subDemain);
        request.setDomainName(mainDemain);
        try {
            AddDomainRecordResponse response = client.getAcsResponse(request);
            logger.info(new Gson().toJson(response));
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("RequestId:" + e.getRequestId());
            return false;
        }
    }

    private String getDescribeDomainRecords() {
        IAcsClient client = new DefaultAcsClient(profile);
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setDomainName(mainDemain);
        try {
            DescribeDomainRecordsResponse response = client.getAcsResponse(request);
            logger.info(new Gson().toJson(response));
            String result = new Gson().toJson(response);
            return result;
        } catch (ServerException e) {
            logger.error(e.getMessage());
        } catch (ClientException e) {
            logger.error("ErrCode:" + e.getErrCode());
            logger.error("ErrMsg:" + e.getErrMsg());
            logger.error("RequestId:" + e.getRequestId());
        }
        return null;
    }

    private String initRecordIdAndGetIP() {
        String ip = null;
        try {
            String describeDomainRecords = getDescribeDomainRecords();
            if (describeDomainRecords == null) {
                return null;
            }
            Map resultMap = (Map) JSON.parse(describeDomainRecords);
            List domainRecords = (List) resultMap.get("domainRecords");
            if (domainRecords == null) {
                return null;
            }
            for (Object domainRecord : domainRecords) {
                Map recordMap = (Map) domainRecord;
                String rR = (String) recordMap.get("rR");
                if (subDemain.equals(rR)) {
                    String recordIdItem = (String) recordMap.get("recordId");
                    ip = (String) recordMap.get("value");
                    recordId = recordIdItem;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return ip;
    }

    public String getrRecordId() {
        if (recordId != null) {
            return recordId;
        } else {
            initRecordIdAndGetIP();
        }
        return recordId;
    }
    public String getOrignIp() {
        return initRecordIdAndGetIP();
    }
}
