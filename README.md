# OpenApi Gateway SDK
### 特性

- 基于filter模式进行扩展，可以选择性使用加签，验签，加密等功能，也可以添加自己的过滤器实现。
- 内置了okhttp进行http请求，同时也支持替换其他http客户端。
- 内置了本地监控，对流量，耗时，成功率进行统计。


### 用法

maven依赖：

``` xml
<dependency>
	<groupId>com.wacai</groupId>
	<artifactId>wacai-gtateway-sdk</artifactId>
	<version>1.0.0</version>
</dependency>

```

一个简单的例子：

``` java
NeptuneConfig config = Neptune.config()
		.host("http://neptune-server.middleware.k2.test.wacai.info")
		.appKey("abrc8udxqybq")
		.appSecret("t9urvc3ddtcrd9mb")
		.build();

NeptuneClient client = Neptune.client(config);
ApiRequest request = new ApiRequest("message.api.push", "1.0");
String json = "{\"content\":\"mytest\"}";
request.setData(json.getBytes());
ApiResponse respone = client.invoke(request);

System.out.println(respone.getCode());
System.out.println(new String(respone.getData()));
```

"message.api.push" 是我在server端添加好的 api，以上代码输出：

```
code:200
response:{"id":0,"header":{},"content":"mytest","offset":0}
```

### 加签
默认使用 MAC 算法加签，如果使用其他加密算法，可以如下配置进行修改：

```java

NeptuneConfig config = Neptune.config()
		.signature(Signatures.RSA.getName())
		.publicKey("设置公钥"))
		.build();
```


### 验签
可以对API返回的数据进行延签， 默认不开启验签，如要开启通过如下配置：

```java

NeptuneConfig config = Neptune.config()
		.verifier(Signatures.MAC.getName())
		.build();
NeptuneClient client = Neptune.client(config);		
//省略后续调用代码
```


### 加解密
默认不会加密数据，可以通过配置打开数据加密功能：

```java

NeptuneConfig config = Neptune.config()
		.encryptor(Encryptors.DES.getName())
		.build();
NeptuneClient client = Neptune.client(config);		

```





### 异常处理

如调用失败，NeptuneClient 会抛出 NeptuneException，使用方需要捕获该异常。
