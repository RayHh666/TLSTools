package com.example.tlstool.util;

import com.example.tlstool.util.XxlJob.XxlJobServiceUrl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @Author: zhusw
 * @Description:
 * @Date: 2024/3/19 18:47
 */
@Slf4j
public class HttpClientUtil {
    /***
     *  编码集
     */
    private final static String CHAR_SET = "UTF-8";
    /***
     *  Post表单请求形式请求头
     */
    private final static String CONTENT_TYPE_POST_FORM = "application/x-www-form-urlencoded";
    /***
     *  Post Json请求头
     */
    private final static String CONTENT_TYPE_JSON = "application/json";
    /***
     *  连接管理器
     */
    private static PoolingHttpClientConnectionManager poolManager;
    /***
     *  请求配置
     */
    private static RequestConfig requestConfig;

    static {
        // 静态代码块,初始化HtppClinet连接池配置信息,同时支持http和https
        try {
            System.out.println("初始化连接池-------->>>>开始");
            // 使用SSL连接Https
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLContext sslContext = builder.build();
            // 创建SSL连接工厂
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslConnectionSocketFactory).build();
            // 初始化连接管理器
            poolManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 设置最大连接数
            poolManager.setMaxTotal(1000);
            // 设置最大路由
            poolManager.setDefaultMaxPerRoute(300);
            // 从连接池获取连接超时时间
            int coonectionRequestTimeOut = 5000;
            // 客户端和服务器建立连接超时时间
            int connectTimeout = 5000;
            // 客户端从服务器建立连接超时时间
            int socketTimeout = 5000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(coonectionRequestTimeOut)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(socketTimeout).build();
            System.out.println("初始化连接池-------->>>>结束");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化连接池-------->>>>失败");
        }
    }


    public static String doGet(String url, Map<String, String> params) {
        String result = "";
        // 获取http客户端
        // CloseableHttpClient httpClient = getCloseableHttpClient();
        // 获取http客户端从连接池中
        CloseableHttpClient httpClient = getCloseableHttpClientFromPool();
        // 响应模型
        CloseableHttpResponse httpResponse = null;
        log.info("创建result：" + result);
        try {
            log.info("准备创建uriBuilder，url：" + url);
            // 创建URI 拼接请求参数
            URIBuilder uriBuilder = new URIBuilder(url);
            log.info("已创建uriBuilder" + uriBuilder);
            // uri拼接参数
            if (null != params) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    uriBuilder.addParameter(next.getKey(), next.getValue());
                }
            }
            log.info("uriBuilder赋值后：" + uriBuilder);
            URI uri = uriBuilder.build();
            // 创建Get请求
            HttpGet httpGet = new HttpGet(uri);
            log.info("httpGet:   " + httpGet);

            httpResponse = httpClient.execute(httpGet);
            log.info("httpResponse:   " + httpResponse);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                log.info("获取响应实体");
                HttpEntity httpEntity = httpResponse.getEntity();
                log.info("httpEntity:  " + httpEntity);
                if (null != httpEntity) {
                    result = EntityUtils.toString(httpEntity, CHAR_SET);
                    log.info("result:   " + result);
                    return result;
                }
            }
            StatusLine statusLine = httpResponse.getStatusLine();
            log.info("statusLine:   " + statusLine);
            int statusCode = statusLine.getStatusCode();
            log.info("响应码:" + statusCode);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String doGet(String url, Map<String, String> params, HashMap<String, String> headerMap) {
        String result = "";
        // 获取http客户端
        // CloseableHttpClient httpClient = getCloseableHttpClient();
        // 获取http客户端从连接池中
        CloseableHttpClient httpClient = getCloseableHttpClientFromPool();
        // 响应模型
        CloseableHttpResponse httpResponse = null;
        log.info("创建result：" + result);
        try {
            log.info("准备创建uriBuilder，url：" + url);
            // 创建URI 拼接请求参数
            URIBuilder uriBuilder = new URIBuilder(url);
            log.info("已创建uriBuilder" + uriBuilder);
            // uri拼接参数
            if (params != null) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    uriBuilder.addParameter(next.getKey(), next.getValue());
                }
            }
            log.info("uriBuilder赋值后：" + uriBuilder);
            URI uri = uriBuilder.build();
            // 创建Get请求
            HttpGet httpGet = new HttpGet(uri);
            log.info("httpGet:   " + httpGet);

            // 设置Get请求头
            if (headerMap != null) {
                headerMap.forEach(httpGet::setHeader);
            }
            httpResponse = httpClient.execute(httpGet);
            log.info("httpResponse:   " + httpResponse);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                log.info("获取响应实体");
                HttpEntity httpEntity = httpResponse.getEntity();
                log.info("httpEntity:  " + httpEntity);
                if (null != httpEntity) {
                    result = EntityUtils.toString(httpEntity, CHAR_SET);
                    log.info("result:   " + result);
                    return result;
                }
            }
            StatusLine statusLine = httpResponse.getStatusLine();
            log.info("statusLine:   " + statusLine);
            int statusCode = statusLine.getStatusCode();
            log.info("响应码:" + statusCode);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (null != httpResponse) {
                    httpResponse.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    private static CloseableHttpClient getCloseableHttpClient() {
        return HttpClientBuilder.create().build();
    }

    /**
     * 从http连接池中获取连接
     */
    private static CloseableHttpClient getCloseableHttpClientFromPool() {
        //
        ServiceUnavailableRetryStrategy serviceUnavailableRetryStrategy = new ServiceUnavailableRetryStrategy() {
            @Override
            public boolean retryRequest(HttpResponse httpResponse, int executionCount, HttpContext httpContext) {
                if (executionCount < 3) {
                    System.out.println("ServiceUnavailableRetryStrategy");
                    return true;
                } else {
                    return false;
                }
            }
            // 重试时间间隔
            @Override
            public long getRetryInterval() {
                return 3000L;
            }
        };
        // 设置连接池管理
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolManager).setConnectionManagerShared(true)
                // 设置请求配置策略
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler()).build();
        return httpClient;

    }

    /**
     * Post请求,表单形式
     */
    public static String doPost(String url, Map<String, String> params) {
        String result = "";
        // 获取http客户端
        CloseableHttpClient httpClient = getCloseableHttpClient();
        // 响应模型
        CloseableHttpResponse httpResponse = null;
        try {
            // Post提交封装参数列表
            ArrayList<NameValuePair> postParamsList = new ArrayList<>();
            if (null != params) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    postParamsList.add(new BasicNameValuePair(next.getKey(), next.getValue()));
                }
            }
            // 创建Uri
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postParamsList, CHAR_SET);
            // 设置表达请求类型
            urlEncodedFormEntity.setContentType(CONTENT_TYPE_POST_FORM);
            HttpPost httpPost = new HttpPost(url);
            // 设置请求体
            httpPost.setEntity(urlEncodedFormEntity);
            // 执行post请求
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), CHAR_SET);
                //System.out.println("Post form reponse {}" + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CloseResource(httpClient, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String doPostWithHeader(String url, Map<String, String> params, HashMap<String, String> headerMap) {
        String result = "";
        // 获取http客户端
        CloseableHttpClient httpClient = getCloseableHttpClient();
        // 响应模型
        CloseableHttpResponse httpResponse = null;
        try {
            // Post提交封装参数列表
            ArrayList<NameValuePair> postParamsList = new ArrayList<>();
            if (null != params) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    postParamsList.add(new BasicNameValuePair(next.getKey(), next.getValue()));
                }
            }
            // 创建Uri
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postParamsList, CHAR_SET);
            // 设置表达请求类型
            urlEncodedFormEntity.setContentType(CONTENT_TYPE_POST_FORM);
            HttpPost httpPost = new HttpPost(url);
            // 设置请求体
            httpPost.setEntity(urlEncodedFormEntity);
            // 设置请求头
            if (headerMap != null) {
                headerMap.forEach(httpPost::setHeader);
            }
            // 执行post请求
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), CHAR_SET);
                //System.out.println("Post form reponse {}" + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CloseResource(httpClient, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

//    public static List<Cookie> doPostForCookie(String url, Map<String, String> params, HashMap<String, String> headerMap) {
//
//        // 1. 创建CookieStore存储Cookie
//        CookieStore cookieStore = new BasicCookieStore();
//
//        // 2. 创建带CookieStore的HttpClient
//        try (CloseableHttpClient httpClient = HttpClients.custom()
//                .setDefaultCookieStore(cookieStore)
//                .build()) {
//
//            HttpPost httpPost = new HttpPost(url);
//
//            if (params != null && !params.isEmpty()) {
//                List<NameValuePair> formParams = new ArrayList<>();
//                for (Map.Entry<String, String> entry : params.entrySet()) {
//                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//                }
//                httpPost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));
//            }
//
//            if (headerMap != null) {
//                headerMap.forEach(httpPost::setHeader);
//            }
//            // 5. 执行请求
//            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//                log.info("response:  {}", response);
//                // 6. 检查响应状态码
//                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode != 200) {
//                    throw new RuntimeException("请求失败! 状态码: " + statusCode);
//                }
//
//                // 7. 从CookieStore获取Cookie列表
//                return cookieStore.getCookies();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("获取cookie异常: " + e.getMessage(), e);
//        }
//    }

    public static List<Cookie> doPostForCookie(String url, Map<String, String> params, HashMap<String, String> headerMap) {
        CookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                // 关键修复：设置宽松的Cookie策略，接受IP地址domain
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD) // 或 CookieSpecs.BEST_MATCH
                        .build())
                .build()) {

            HttpPost httpPost = new HttpPost(url);
            // 设置请求参数
            if (params != null && !params.isEmpty()) {
                List<NameValuePair> formParams = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formParams, StandardCharsets.UTF_8));
            }

            httpPost.setHeader("User-Agent", "Apifox/1.0.0");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.setHeader("Connection", "keep-alive");
            httpPost.setHeader("Cache-Control", "no-cache");

            // 设置请求头
            if (headerMap != null) {
                headerMap.forEach(httpPost::setHeader);
            }
            // 执行请求
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("请求失败! 状态码: " + statusCode);
                }

                // 增强调试：打印所有响应头，确认Set-Cookie是否存在
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    log.info("响应头: {}: {}", header.getName(), header.getValue());
                }

                // 打印CookieStore内容（调试用）
                List<Cookie> cookies = cookieStore.getCookies();
                log.info("CookieStore中的Cookie数量: {}", cookies.size());
                for (Cookie cookie : cookies) {
                    log.info("Cookie详情: name={}, value={}, domain={}, path={}",
                            cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath());
                }

                return cookies;
            }
        } catch (Exception e) {
            throw new RuntimeException("获取cookie异常: " + e.getMessage(), e);
        }
    }

    /**
     * Post请求,表单形式
     */
    public static HttpResponse post(String url, Map<String, String> params) {
        String result = "";
        // 获取http客户端
        CloseableHttpClient httpClient = getCloseableHttpClient();
        // 响应模型
        CloseableHttpResponse httpResponse = null;
        try {
            // Post提交封装参数列表
            ArrayList<NameValuePair> postParamsList = new ArrayList<>();
            if (null != params) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    postParamsList.add(new BasicNameValuePair(next.getKey(), next.getValue()));
                }
            }
            // 创建Uri
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postParamsList, CHAR_SET);
            // 设置表达请求类型
            urlEncodedFormEntity.setContentType(CONTENT_TYPE_POST_FORM);
            HttpPost httpPost = new HttpPost(url);
            // 设置请求体
            httpPost.setEntity(urlEncodedFormEntity);
            // 执行post请求
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //System.out.println("Post form reponse {}" + result);
                return httpResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CloseResource(httpClient, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }

    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        String result = "";
        // 获取http客户端
        CloseableHttpClient httpClient = getCloseableHttpClient();
        // 响应模型
        CloseableHttpResponse httpResponse = null;
        try {
            // Post提交封装参数列表
            ArrayList<NameValuePair> postParamsList = new ArrayList<>();
            if (null != params) {
                Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    postParamsList.add(new BasicNameValuePair(next.getKey(), next.getValue()));
                }
            }
            // 创建Uri
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(postParamsList, CHAR_SET);
            // 设置表达请求类型
            urlEncodedFormEntity.setContentType(CONTENT_TYPE_POST_FORM);
            HttpPost httpPost = new HttpPost(url);
            if (null != headers){
                Iterator<Map.Entry<String, String>> it = headers.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> next = it.next();
                    httpPost.setHeader(next.getKey(), next.getValue());
                }
            }
            // 设置请求体
            httpPost.setEntity(urlEncodedFormEntity);
            // 执行post请求
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), CHAR_SET);
                //System.out.println("Post form reponse {}" + result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CloseResource(httpClient, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static void CloseResource(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) throws IOException {
        if (null != httpResponse) {
            httpResponse.close();
        }
        if (null != httpClient) {
            httpClient.close();
        }
    }

    /***
     *  Post请求,Json形式
     */
    public static String doPostJson(String url, String jsonStr) {
        String result = "";
        CloseableHttpClient httpClient = getCloseableHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建Post
            HttpPost httpPost = new HttpPost(url);
            // 封装请求参数
            StringEntity stringEntity = new StringEntity(jsonStr, CHAR_SET);
            // 设置请求参数封装形式
            stringEntity.setContentType(CONTENT_TYPE_JSON);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity(), CHAR_SET);
                System.out.println(result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CloseResource(httpClient, httpResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // get 请求
        //String getUrl = "";
        //String getUrl = "";
        //String getUrl = "";
        //Map m = new HashMap();
        //  m.put("year","2023");
        // m.put("age","123");
        //String result = doGet(getUrl,m );
        //  System.out.println("result = " + result);

        //Post 请求  baocuo
        // String postUrl = "";
        // Map m = new HashMap();
        //m.put("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VyX2tleSI6IjNkNmI3ZTFjLTU0ZjgtNGE2NS1iMTY1LTE2NjczYzM1MWIxZiIsInVzZXJuYW1lIjoiYWRtaW4ifQ.AyLfDdY9PjpWInEnaiDUBdJVpStEsP1oheWrxCdRcflzJSWPL2VMFFL2SngTO5S0jrI3uwQBWAsccCOG4hRygg");
        //m.put("facregCode","999999");
        // String s = doPost(postUrl, m);
        //System.out.println("s = " + s);


        //String postJsonUrl = "";
     /*   User user = new User();
        user.setUid("123");
        user.setUserName("小明");
        user.setAge("18");
        user.setSex("男");
         String jsonStr = JSON.toJSONString(user);
        doPostJson(postJsonUrl,jsonStr); */

        // System.out.println(s);
        // System.out.println(result);
//        String result = doGet("",null);
        //String result = doGet("",null);
        //JSONObject jsonObject = JSONObject.parseObject(result);
        //System.out.println("result="+result);

        HashMap<String, String> keywordMap = new HashMap();
        keywordMap.put("keyword", "www.baidu.com");
        String result = HttpClientUtil.doGet("http://10.101.6.241", keywordMap);
    }
}