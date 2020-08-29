package com.shun.net;

import com.dtflys.forest.annotation.Get;

public interface PublicNetwork {
    @Get(
            url = "http://www.baidu.com/s?wd=%E5%85%AC%E7%BD%91ip",
            headers = "{'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36'}"
    )
    String getSoip();
}
