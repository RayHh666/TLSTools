package com.example.tlstool.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class SslyzeUtils {

    private static final int MAX_OUTPUT_SIZE = 10 * 1024 * 1024; // 10MB

//    public static List<String> getSslyzeScanData(String target, String command){
//        if(StringUtils.isBlank(command)){
//            command = "-O";
//        }
//        String sslyzeCommand = "sslyze " + command+" "+ target;//
//        StringBuilder result = new StringBuilder();
//        try {
//            ProcessBuilder builder = new ProcessBuilder();
//
//            String osName = System.getProperty("os.name").toLowerCase();
//            if (osName.contains("windows")) {
//                //windowssystem
//                builder.command("cmd", "/c", sslyzeCommand);
//            }else {
//                //Other systems
//                builder.command("bash", "-c", sslyzeCommand);
//            }
//
//
//            //Standard errors will be merged with standard outputs
//            builder.redirectErrorStream(true);
//            Process process = builder.start();
//
//            // read The output
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                result.append(line);
////                log.info("output:" + line);
//            }
//
//            // wait for sslyze Process ends
//            process.waitFor();
//            log.info("sslyze scan completed.");
//
//            // Close the flow
//            reader.close();
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    private static String sslyzeScanToString(String target, String sslyzeCommand) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "sslyze",
                    "--json_out=-",
                    target
            );

           String osName = System.getProperty("os.name").toLowerCase();
           if (osName.contains("windows")) {
                //windowssystem
               pb.command("cmd", "/c", sslyzeCommand);
            }else {
                //Other systems
               pb.command("bash", "-c", sslyzeCommand);
            }

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            int totalLength = 0;
            while ((line = reader.readLine()) != null) {
                totalLength += line.length();
                if (totalLength > MAX_OUTPUT_SIZE) {
                    throw new RuntimeException("SSLyze输出超过10MB，可能过大");
                }
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // 读取错误流
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream())
                );
                StringBuilder error = new StringBuilder();
                while ((line = errorReader.readLine()) != null) {
                    error.append(line).append("\n");
                }
                throw new RuntimeException("SSLyze 执行错误:\n" + error.toString());
            }

            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JsonNode parseJsonOutput(String target, String command) throws Exception {

        if(StringUtils.isBlank(command)){
            command = "-O";
        }
        String sslyzeCommand = "sslyze" + command + " --json_out -  " + target;

        JsonNode jsonRoot = null;
        try {
            JsonFactory factory = new ObjectMapper().getFactory();

            ProcessBuilder builder = new ProcessBuilder();

            String osName = System.getProperty("os.name").toLowerCase();

           if (osName.contains("windows")) {
                //windowssystem
                builder.command("cmd", "/c", sslyzeCommand);
            }else {
                //Other systems
                builder.command("bash", "-c", sslyzeCommand);
            }

            //Standard errors will be merged with standard outputs
            builder.redirectErrorStream(true);
            Process process = builder.start();

            JsonParser parser = factory.createParser(process.getInputStream());
            jsonRoot = parser.readValueAsTree();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRoot;
    }

    public static void main(String[] args) {
//        String osName = System.getProperty("os.name").toLowerCase();
//        System.out.println(osName);
//        getScanData("10.101.24.31", "ping");
    }
}
