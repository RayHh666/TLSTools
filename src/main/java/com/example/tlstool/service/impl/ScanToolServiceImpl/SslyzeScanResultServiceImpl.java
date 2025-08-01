package com.example.tlstool.service.impl.ScanToolServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.ScanToolPO.SslyzeScanResultPO;
import com.example.tlstool.entity.po.TlsTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.mapper.TlsTaskMapper;
import com.example.tlstool.service.ScanToolService.SslyzeScanResultService;
import com.example.tlstool.mapper.ScanToolMapper.SslyzeScanResultMapper;
import com.example.tlstool.util.DateTimeUtils;
import com.example.tlstool.util.SslyzeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* @author admin
* @description 针对表【t_sslyze_scan_result】的数据库操作Service实现
* @createDate 2025-07-30 16:32:47
*/
@Service
public class SslyzeScanResultServiceImpl extends ServiceImpl<SslyzeScanResultMapper, SslyzeScanResultPO>
    implements SslyzeScanResultService {

    @Resource
    private TlsTaskMapper tlsTaskMapper;

    @Override
    @Async
    public void createSslyzeTask (TlsCreateTaskRO tlsCreateTaskRO, TlsTaskPO tlsTaskPO) throws Exception {
        String sslyzeCommand = "";
        // TODO 将前端参数转换为sslyze command

        String targetsStr = tlsCreateTaskRO.getTargets().trim().replace(",", " ");

        if (StringUtils.isNotBlank(tlsCreateTaskRO.getTlsProtocols())) {
            Set<String> tlsProtocolSet = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getTlsProtocols().split(",")));
            for (String tlsProtocol: tlsProtocolSet) {
                sslyzeCommand = sslyzeCommand + " --" + tlsProtocol;
            }
        }

        if (StringUtils.isNotBlank(tlsCreateTaskRO.getMailProtocols())) {
            Set<String> mailProtocolSet = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getMailProtocols().split(",")));
            for (String mailProtocol: mailProtocolSet) {
                sslyzeCommand = sslyzeCommand + " --" + mailProtocol;
            }
        }

        JsonNode jsonRoot = SslyzeUtils.getSslyzeJsonOutput(targetsStr, sslyzeCommand);

        // 更新扫描任务数据
        JsonNode invalidServerStrings = jsonRoot.get("invalid_server_strings");
        String dateScansStarted = jsonRoot.get("date_scans_started").asText();
        String dateScansCompleted = jsonRoot.get("date_scans_completed").asText();
        String detectionToolVersion = jsonRoot.get("sslyze_version").asText();
        String remark = jsonRoot.get("sslyze_url").asText();

        if (tlsTaskPO != null) {
            tlsTaskPO.setInvalidServerStrings(invalidServerStrings);
            tlsTaskPO.setDateScansStarted(DateTimeUtils.convertToLocalDateTime(dateScansStarted));
            tlsTaskPO.setDateScansCompleted(DateTimeUtils.convertToLocalDateTime(dateScansCompleted));
            tlsTaskPO.setDetectionToolVersion(detectionToolVersion);
            tlsTaskPO.setRemark(remark);
            // 使用 updateById 确保类型处理器生效
            tlsTaskMapper.updateById(tlsTaskPO);
        }

        // 解析本次扫描结果集
        JsonNode serverScanResults = jsonRoot.get("server_scan_results");
        for (JsonNode serverScanResult : serverScanResults) {
            JsonNode scanResult = serverScanResult.get("scan_result");
            SslyzeScanResultPO sslyzeScanResultPO = new SslyzeScanResultPO().builder()
                    .uuid(serverScanResult.get("uuid").asText())
                    .taskId(tlsTaskPO.getId())
                    .serverLocation(serverScanResult.get("server_location"))
                    .networkConfiguration(serverScanResult.get("network_configuration"))
                    .connectivityStatus(serverScanResult.get("connectivity_status").asText())
                    .connectivityErrorTrace(serverScanResult.get("connectivity_error_trace").asText())
                    .connectivityResult(serverScanResult.get("connectivity_result"))
                    .scanStatus(serverScanResult.get("scan_status").asText())
                    .certificateInfo(scanResult.get("certificate_info"))
                    .ssl20CipherSuites(scanResult.get("ssl_2_0_cipher_suites"))
                    .ssl30CipherSuites(scanResult.get("ssl_3_0_cipher_suites"))
                    .tls10CipherSuites(scanResult.get("tls_1_0_cipher_suites"))
                    .tls11CipherSuites(scanResult.get("tls_1_1_cipher_suites"))
                    .tls12CipherSuites(scanResult.get("tls_1_2_cipher_suites"))
                    .tls13CipherSuites(scanResult.get("tls_1_3_cipher_suites"))
                    .tlsCompression(scanResult.get("tls_compression"))
                    .tls13EarlyData(scanResult.get("tls_1_3_early_data"))
                    .opensslCcsInjection(scanResult.get("openssl_ccs_injection"))
                    .tlsFallbackScsv(scanResult.get("tls_fallback_scsv"))
                    .heartbleed(scanResult.get("heartbleed"))
                    .robot(scanResult.get("robot"))
                    .sessionRenegotiation(scanResult.get("session_renegotiation"))
                    .sessionResumption(scanResult.get("session_resumption"))
                    .ellipticCurves(scanResult.get("elliptic_curves"))
                    .httpHeaders(scanResult.get("http_headers"))
                    .tlsExtendedMasterSecret(scanResult.get("tls_extended_master_secret"))
                    .build();
            save(sslyzeScanResultPO);
        }
    }
}




