package com.wacai.gateway.sdk.contract;

/**
 *
 * @author bairen
 *
 */
public class Headers {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String USER_AGENT = "User-Agent";
    public static final String TRUE_IP = "X-True-IP";
    public static final String REAL_IP = "X-Real-IP";
    public static final String REMOTE_IP = "RemoteIp";

    public static final String SIGNATURE = "X-Wac-Signature";
    public static final String TIMESTAMP = "X-Wac-Timestamp";
    public static final String APP_KEY = "X-Wac-App-Key";
    public static final String REQUEST_ID = "X-Wac-Request-Id";

    //以下为响应部分

    public static final String RESULT_CODE = "X-Result-Code";
    /**
     * <p>
     * 不推荐把 Error 信息放在 HTTP header 中，原因：
     * 1. header 无法正确识别中文，需要做额外的 URLDecoder 处理；
     * 2. 一般服务器都会对 header 做大小限制，错误信息包含异常堆栈容易触发；
     * 后续版本中可以删除。
     * </p>
     */
    @Deprecated
    public static final String ERROR_REASON = "X-Error-Reason";
    public static final String CLIENT_LATENCY = "X-Client-Latency";
    public static final String GATEWAY_LATENCY = "X-Gateway-Latency";
    public static final String TRACE_ID = "X-Trace-Id";

    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_BINARY = "application/octet-stream";

}