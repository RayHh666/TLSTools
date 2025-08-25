package com.example.tlstool.util.XxlJob;


import com.alibaba.fastjson.JSONObject;
import com.example.tlstool.util.HttpClientUtil;
import com.example.tlstool.util.Resp;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;




import javax.servlet.http.HttpServletRequest;
import java.net.HttpCookie;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class XxlJobServiceApi {

    @Value("${xxl.job.userName}")
    private String userName;
    @Value("${xxl.job.password}")
    private String password;
    @Value("${xxl.job.admin.addresses}")
    private String basicUrl;
    @Value("${xxl.job.executor.appname}")
    private String appname;
    @Value("${xxl.job.tlsconfig.jobgroup}")
    private int jobGroup;

    private final Map<String, String> loginCookie = new HashMap<>();

    /**
     * 登录接口
     */
//    public String loginXxlJob() {
//        Map<String, String > params = new HashMap<>();
//        params.put("username", userName);
//        params.put("password", password);
//        HttpResponse response = HttpClientUtil.post(basicUrl + XxlJobServiceUrl.loginXxlJob, params);;
//        List<HttpCookie> cookies = response.getCookies();
//        Optional<HttpCookie> cookieOpt = cookies.stream().filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
//        if (!cookieOpt.isPresent()) {
//            throw new RuntimeException("xxlJob登录失败!");
//        }
//        String xxlJobCookie = cookieOpt.get().getValue();
//        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", xxlJobCookie);
//        return xxlJobCookie;
//    }



    //        // 1. 创建CookieStore存储Cookie
//        CookieStore cookieStore = new BasicCookieStore();
//
//        // 2. 创建带CookieStore的HttpClient
//        try (CloseableHttpClient httpClient = HttpClients.custom()
//                .setDefaultCookieStore(cookieStore)
//                .build()) {
//
//            // 4. 创建POST请求
//            HttpPost httpPost = new HttpPost(basicUrl + XxlJobServiceUrl.loginXxlJob);
//            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//
//            // 5. 执行请求
//            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//                // 6. 检查响应状态码
//                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode != 200) {
//                    throw new RuntimeException("XXL-JOB登录失败! 状态码: " + statusCode);
//                }
//
//                // 7. 从CookieStore获取Cookie列表
//                List<Cookie> cookies = cookieStore.getCookies();
//                Optional<Cookie> cookieOpt = cookies.stream()
//                        .filter(cookie -> "XXL_JOB_LOGIN_IDENTITY".equals(cookie.getName()))
//                        .findFirst();
//
//                // 8. 验证Cookie
//                if (!cookieOpt.isPresent()) {
//                    throw new RuntimeException("XXL_JOB_LOGIN_IDENTITY Cookie未找到!");
//                }
//
//                // 9. 存储并返回Cookie值
//                String xxlJobCookie = cookieOpt.get().getValue();
//                loginCookie.put("XXL_JOB_LOGIN_IDENTITY", xxlJobCookie);
//                return xxlJobCookie;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("XXL-JOB登录异常: " + e.getMessage(), e);
//        }



//    public String loginXxlJob() {
//
//        // 准备登录参数
//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("username", userName);
//        params.put("password", password);
//
//        try {
//            List<Cookie> cookies = HttpClientUtil.doPostForCookie(basicUrl + XxlJobServiceUrl.loginXxlJob, params, null);
//            log.info("cookies:{}", cookies);
//            Optional<Cookie> cookieOpt = cookies.stream()
//                    .filter(cookie -> "XXL_JOB_LOGIN_IDENTITY".equals(cookie.getName()))
//                    .findFirst();
//
//            // 验证Cookie
//            if (!cookieOpt.isPresent()) {
//                throw new RuntimeException("XXL_JOB_LOGIN_IDENTITY Cookie未找到!");
//            }
//
//            // 存储并返回Cookie值
//            String xxlJobCookie = cookieOpt.get().getValue();
//            loginCookie.put("XXL_JOB_LOGIN_IDENTITY", xxlJobCookie);
//            return xxlJobCookie;
//        } catch (Exception e) {
//            throw new RuntimeException("XXL-JOB登录异常: " + e.getMessage(), e);
//        }
//    }


    public String loginXxlJob() {
        String url=basicUrl + "/login";
        HttpResponse response = HttpRequest.post(url)
                .form("userName",userName)
                .form("password",password)
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent())
            throw new RuntimeException("get xxl-job cookie error!");

        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY",value);
        return value;
    }


    /**
     * 查询定时任务执行器
     */
    public String findXxlJobGroup() {
        Map<String, String> paramMap = new LinkedHashMap<>();
//        paramMap.add("appname", appname);
//        paramMap.add("start", "0");
        paramMap.put("id", "2");
        HashMap<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !cookieStr.isEmpty() ? cookieStr : loginXxlJob());
        Map map = JSONObject.parseObject(HttpClientUtil.doGet(basicUrl + XxlJobServiceUrl.loadById, paramMap, headers), HashMap.class);
        if (!ObjectUtils.isEmpty(map) && map.containsKey("data") && !ObjectUtils.isEmpty(map.get("data"))) {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get("data");
            return mapList.get(0).get("id").toString();
        }
        throw new RuntimeException("xxlJob执行器获取失败!");
    }


    /**
     * 新增定时任务
     * 返回值的content代表主键ID
     */
    public JSONObject addXxlJob(String jobDesc, String cron, String handler, String param) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("jobDesc", jobDesc);
        paramMap.put("scheduleConf", cron);
        paramMap.put("cronGen_display", cron);
        paramMap.put("schedule_conf_CRON", cron);
        paramMap.put("executorHandler", handler);
        paramMap.put("executorParam", param);
        paramMap.put("jobGroup", String.valueOf(jobGroup));
        paramMap.put("author", "admin");
        paramMap.put("scheduleType", "CRON");
        paramMap.put("glueType", "BEAN");
        paramMap.put("executorRouteStrategy", "FIRST");
        paramMap.put("misfireStrategy", "DO_NOTHING");
        paramMap.put("executorBlockStrategy", "SERIAL_EXECUTION");
        paramMap.put("executorTimeout", "0");
        paramMap.put("executorFailRetryCount", "0");
        paramMap.put("glueRemark", "GLUE代码初始化");
        HashMap<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPostWithHeader(basicUrl + XxlJobServiceUrl.addXxlJob, paramMap, headers), JSONObject.class);
    }


//    public Resp saveXxl() {
//        try {
//            JSONObject requestInfo = new JSONObject();
//            // 执行器主键ID
//            requestInfo.put("jobGroup", 2);
//            // 任务执行CRON表达式
//            long etime1 = System.currentTimeMillis() + 1 * 60 * 1000;//延时函数，单位毫秒，这里是延时了1分钟
//            String date = TimeUtil.getCron(new Date(etime1));
//            System.out.println(date);
////        requestInfo.put("jobCron","0 0/1 * * * ?");
//            requestInfo.put("jobCron", date);
//            // 任务描述
//            requestInfo.put("jobDesc", "xxxJob");
//
//            // 负责人
//            requestInfo.put("author", "admin");
//            // 报警邮件
//            requestInfo.put("alarmEmail", "xxx@satcloud.com.cn");
//
//            // 执行器路由策略
//            requestInfo.put("executorRouteStrategy", "FIRST");
//            // 执行器，任务Handler名称
//            requestInfo.put("executorHandler", "xxxJobHandler");
//            requestInfo.put("executorParam", "测试202006300943");
//            // 阻塞处理策略
//            requestInfo.put("executorBlockStrategy", "SERIAL_EXECUTION");
//            // 任务执行超时时间，单位秒
//            requestInfo.put("executorTimeout", 0);
//            // 失败重试次数
//            requestInfo.put("executorFailRetryCount", 1);
//            // GLUE类型    #com.xxl.job.core.glue.GlueTypeEnum
//            requestInfo.put("glueType", "BEAN");
//            // GLUE备注
//            requestInfo.put("glueRemark", "GLUE代码初始化");
//
//            // 调度状态：0-停止，1-运行
//            requestInfo.put("triggerStatus", 0);
//            // 上次调度时间
//            requestInfo.put("triggerLastTime", 0);
//            // 下次调度时间
//            requestInfo.put("triggerNextTime", 0);
////        requestInfo.put("cronGen_display","0 0/1 * * * ?");
//            JSONObject response = XxlJobUtils.addJob(adminAddresses, requestInfo);
//            if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
//                //修改任务参数 把id放入
//                // 执行器主键ID
//                requestInfo.put("executorParam", "JobId=" + response.get("content") + ";测试202006300943");
//                requestInfo.put("id", Integer.valueOf(response.get("content").toString()));
//                JSONObject responseUpdate = XxlJobUtils.updateJob(adminAddresses, requestInfo);
//                if (responseUpdate.containsKey("code") && 200 == (Integer) responseUpdate.get("code")) {
//                    //加入任务成功之后直接启动
//                    JSONObject responseStart = XxlJobUtils.startJob(adminAddresses, Integer.valueOf(response.get("content").toString()));
//                    if (responseStart.containsKey("code") && 200 == (Integer) responseStart.get("code")) {
//                        return Resp.getInstantiationSuccess("成功", null, null);
//                    } else {
//                        throw new Exception("调用xxl-job-admin-start接口失败！");
//                    }
//                } else {
//                    throw new Exception("调用xxl-job-admin-update接口失败！");
//                }
//            } else {
//                throw new Exception("调用xxl-job-admin-add接口失败！");
//            }
//        } catch (Exception e) {
//            return Resp.getInstantiationError("失败" + e.getMessage(), null, null);
//        }
//    }


    /**
     * 编辑定时任务
     */
    public Map updateXxlJob(String jobId, String jobDesc, String cron, String jobName, String param) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("id", jobId);
        paramMap.put("jobDesc", jobDesc);
        paramMap.put("scheduleConf", cron);
        paramMap.put("cronGen_display", cron);
        paramMap.put("schedule_conf_CRON", cron);
        paramMap.put("executorHandler", jobName);
        paramMap.put("executorParam", param);
        paramMap.put("jobGroup", String.valueOf(jobGroup));
        paramMap.put("author", "admin");
        paramMap.put("scheduleType", "CRON");
        paramMap.put("glueType", "BEAN");
        paramMap.put("executorRouteStrategy", "FIRST");
        paramMap.put("misfireStrategy", "DO_NOTHING");
        paramMap.put("executorBlockStrategy", "SERIAL_EXECUTION");
        paramMap.put("executorTimeout", "0");
        paramMap.put("executorFailRetryCount", "0");
        paramMap.put("glueRemark", "GLUE代码初始化");
        Map<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPost(basicUrl + XxlJobServiceUrl.updateXxlJob, paramMap, headers), HashMap.class);
    }

    /**
     * 启动定时任务
     */
    public JSONObject startXxlJob(String jobId) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("id", jobId);
        HashMap<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPostWithHeader(basicUrl + XxlJobServiceUrl.startXxlJob, paramMap, headers), JSONObject.class);
    }

    public JSONObject triggerXxlJob(String jobId, String executorParam) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("id", jobId);
        paramMap.put("executorParam", executorParam);
        HashMap<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPostWithHeader(basicUrl + XxlJobServiceUrl.triggerXxlJob, paramMap, headers), JSONObject.class);
    }

    /**
     * 暂停定时任务
     */
    public JSONObject stopXxlJob(String jobId) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("id", jobId);
        HashMap<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPostWithHeader(basicUrl + XxlJobServiceUrl.stopXxlJob, paramMap, headers), JSONObject.class);
    }

    /**
     * 删除定时任务
     */
    public JSONObject removeXxlJob(String jobId) {
        Map<String, String> paramMap = new LinkedHashMap<>();
        paramMap.put("id", jobId);
        Map<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPost(basicUrl + XxlJobServiceUrl.removeXxlJob, paramMap, headers), JSONObject.class);
    }

    /**
     * 查询任务列表
     */
    public Map findXxlJobPage() {
        Map<String, String> headers = new LinkedHashMap<>();
        String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
        headers.put("Cookie", !ObjectUtils.isEmpty(cookieStr) ? "XXL_JOB_LOGIN_IDENTITY=" + cookieStr : "XXL_JOB_LOGIN_IDENTITY=" + loginXxlJob());
        return JSONObject.parseObject(HttpClientUtil.doPost(basicUrl + XxlJobServiceUrl.removeXxlJob, headers), HashMap.class);
    }
}