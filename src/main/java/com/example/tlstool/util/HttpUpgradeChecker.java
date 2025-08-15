package com.example.tlstool.util;

import com.example.tlstool.entity.dto.HttpUpgradeResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HttpUpgradeChecker {

    /**
     * 检测目标URL是否已升级到HTTPS
     * @param url 待检测的HTTP地址（需以http://开头）
     * @return true表示已升级到HTTPS，false表示未升级
     */
    public static HttpUpgradeResultDTO isUpgradedToHttps(String url) throws Exception {
        // 1. 构建curl命令
        List<String> command = new ArrayList<>();
        command.add("curl");
        command.add("-IL");  // 获取响应头+跟随重定向
        command.add("-s");   // 静默模式（不输出进度）
        command.add("-k");   // 忽略SSL证书错误（避免测试环境证书无效导致失败）
        command.add("-m");   // 设置超时时间（秒）
        command.add("10");
        command.add(url);
        Boolean httpsFlag = false;
        if (url.trim().startsWith("https://")) {
            httpsFlag = true;
        }

        try {
            // 2. 执行命令并捕获输出
            String output = executeCommand(command);

            // 3. 解析输出判断协议状态
            return analyzeOutput(output, httpsFlag);
        } catch (Exception e) {
            throw new Exception("检测失败: " + e.getMessage());
            // System.err.println("检测失败: " + e.getMessage());
            // return false;
        }
    }

    private static String executeCommand(List<String> command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        // 读取命令输出
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        // 等待命令完成并检查退出码
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("curl命令执行失败，退出码: " + exitCode);
        }
        return output.toString();
    }

    private static HttpUpgradeResultDTO analyzeOutput(String output, Boolean httpsFlag) {
        String[] lines = output.split("\n");
        boolean foundHttps = false;
        String location = null;
        // 遍历所有响应头行
        for (String line : lines) {

//            log.info(line);

            // 关键检查1: 请求是否OK
            if (line.contains("HTTP/") && line.contains("200")) {
                if (httpsFlag) {
                    foundHttps = true;
                }
            }

            // 关键检查2: 是否直接通过HTTPS访问
            if (line.trim().toLowerCase().startsWith("location: https://")) {
                httpsFlag = true;
                location = line.trim();
            }
        }

        if (location.startsWith("Location: ")) {
            location = StringUtils.substringAfter(location, "Location: ");
        }

        HttpUpgradeResultDTO result = new HttpUpgradeResultDTO().builder()
                .status(foundHttps)
                .location(location)
                .build();
        return result; // 返回最终请求的协议状态
    }

    public static void main(String[] args) {
        try {
            String testUrl = "example.com"; // 替换为待检测URL
            HttpUpgradeResultDTO isUpgraded = isUpgradedToHttps(testUrl);
            System.out.println("服务" + (isUpgraded.getStatus() ? "已" : "未") + "升级到HTTPS");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}