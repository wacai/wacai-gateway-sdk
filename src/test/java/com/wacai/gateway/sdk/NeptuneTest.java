package com.wacai.gateway.sdk;

import org.junit.Test;

/**
 * @author bairen
 */
public class NeptuneTest {

    @Test
    public void test() {
        NeptuneConfig config = Neptune.config()
                .host("http://neptune.test.wacai.info/api-entry")
                .appKey("vdcxe3qwdd5u")
                .appSecret("fh4ap4wy7nkxywu6")
                .timeoutMs(4000)
                .build();
        NeptuneClient client = Neptune.client(config);
        ApiRequest apiRequest = new ApiRequest("flow.test", "1.0");
        String param = "{\"val\":\"501\"}";
        apiRequest.setData(param);
        try {
            ApiResponse apiResponse = client.invoke(apiRequest);
            System.out.println(new String(apiResponse.getData()));
        } catch (NeptuneException e) {
            e.printStackTrace();
//处理异常逻辑
        }

    }
}
