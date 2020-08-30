package com.shun.net;

import com.dtflys.forest.annotation.Get;

public interface PublicNetwork {

    @Get(
            timeout = 10000,
            url = "http://www.net.cn/static/customercare/yourip.asp",
            headers = "{'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36 Edg/85.0.564.41'}"
    )
    String getPublicIp03();

    @Get(
            timeout = 10000,
            url = "http://ipinfo.io/ip",
            headers = "{'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36 Edg/85.0.564.41'}"
    )
    String getPublicIp02();

    @Get(
            timeout = 10000,
            url = "http://ifconfig.me/ip",
            headers = "{'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36 Edg/85.0.564.41'}"
    )
    String getPublicIp01();
}
