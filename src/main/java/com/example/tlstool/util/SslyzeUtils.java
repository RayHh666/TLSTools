package com.example.tlstool.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

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

    public static JsonNode getSslyzeJsonOutput(String command, String target) throws Exception {

        if(StringUtils.isBlank(command)){
            command = "-O";
        }
        String sslyzeCommand = "sslyze --slow_connection" + command + " --json_out -  " + target;

        log.info("sslyzeCommand: {}", sslyzeCommand);
        JsonNode jsonRoot = null;
        try {
            StringBuilder result = new StringBuilder();
//            JsonFactory factory = new ObjectMapper().getFactory();
            ObjectMapper mapper = new ObjectMapper();

            ProcessBuilder builder = new ProcessBuilder();

            String osName = System.getProperty("os.name").toLowerCase();

           if (osName.contains("windows")) {
                //windowssystem
                sslyzeCommand = "python -m" + sslyzeCommand;
                builder.command("cmd", "/c", sslyzeCommand);
           } else {
               //Other systems
               builder.command("bash", "-c", sslyzeCommand);
           }

            //Standard errors will be merged with standard outputs
            builder.redirectErrorStream(true);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            int startIndex = result.indexOf("{  \"invalid_server_strings\"");
            if (startIndex != -1) {
                result.delete(0, startIndex);
                String jsonString = result.toString();
                log.info("result: {}", jsonString);
                jsonRoot = mapper.readTree(jsonString);
            }
//            JsonParser parser = factory.createParser(process.getInputStream());
//            jsonRoot = parser.readValueAsTree();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonRoot;
    }

    public static void main(String[] args) throws Exception {
//        String osName = System.getProperty("os.name").toLowerCase();
//        System.out.println(osName);
//        getScanData("10.101.24.31", "ping");
        StringBuilder result = new StringBuilder();
        JsonFactory factory = new ObjectMapper().getFactory();

        JsonNode jsonRoot = null;
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd", "/c", "python -m sslyze --slow_connection --tlsv1_2 --tlsv1_1 --sslv3 --tlsv1_3 --heartbleed --robot --compression --elliptic_curves --openssl_ccs --certinfo --json_out - example.com github.com baidu.com");
        //Standard errors will be merged with standard outputs
        builder.redirectErrorStream(true);
        Process process = builder.start();

//        JsonParser parser = factory.createParser(process.getInputStream());
//        jsonRoot = parser.readValueAsTree();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        int startIndex = result.indexOf("{  \"invalid_server_strings\"");
        if (startIndex != -1) {
            result.delete(0, startIndex);
        }

        process.waitFor();
        log.info("result: {}", result);
    }
}
