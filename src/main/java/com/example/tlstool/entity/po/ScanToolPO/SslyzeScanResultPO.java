package com.example.tlstool.entity.po.ScanToolPO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.example.tlstool.handler.JsonNodeTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_sslyze_scan_result
 */
@TableName(value ="t_sslyze_scan_result", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SslyzeScanResultPO {

    /**
     * uuid
     */
    @TableId(value = "uuid")
    private String uuid;

    /**
     * 任务id
     */
    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 
     */
    @TableField(value = "server_location" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode serverLocation;

    /**
     * 
     */
    @TableField(value = "network_configuration" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode networkConfiguration;

    /**
     * 
     */
    @TableField(value = "connectivity_status")
    private String connectivityStatus;

    /**
     * 
     */
    @TableField(value = "connectivity_error_trace")
    private String connectivityErrorTrace;

    /**
     * 
     */
    @TableField(value = "connectivity_result" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode connectivityResult;

    /**
     * 
     */
    @TableField(value = "scan_status")
    private String scanStatus;

    /**
     * 
     */
    @TableField(value = "certificate_info" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode certificateInfo;

    /**
     * 
     */
    @TableField(value = "ssl_2_0_cipher_suites" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode ssl20CipherSuites;

    /**
     * 
     */
    @TableField(value = "ssl_3_0_cipher_suites" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode ssl30CipherSuites;

    /**
     * 
     */
    @TableField(value = "tls_1_0_cipher_suites" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tls10CipherSuites;

    /**
     * 
     */
    @TableField(value = "tls_1_1_cipher_suites" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tls11CipherSuites;

    /**
     * 
     */
    @TableField(value = "tls_1_2_cipher_suites" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tls12CipherSuites;

    /**
     * 
     */
    @TableField(value = "tls_1_3_cipher_suites" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tls13CipherSuites;

    /**
     * 
     */
    @TableField(value = "tls_compression" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tlsCompression;

    /**
     * 
     */
    @TableField(value = "tls_1_3_early_data" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tls13EarlyData;

    /**
     * 
     */
    @TableField(value = "openssl_ccs_injection" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode opensslCcsInjection;

    /**
     * 
     */
    @TableField(value = "tls_fallback_scsv" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tlsFallbackScsv;

    /**
     * 
     */
    @TableField(value = "heartbleed" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode heartbleed;

    /**
     * 
     */
    @TableField(value = "robot" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode robot;

    /**
     * 
     */
    @TableField(value = "session_renegotiation" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode sessionRenegotiation;

    /**
     * 
     */
    @TableField(value = "session_resumption" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode sessionResumption;

    /**
     * 
     */
    @TableField(value = "elliptic_curves" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode ellipticCurves;

    /**
     * 
     */
    @TableField(value = "http_headers" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode httpHeaders;

    /**
     * 
     */
    @TableField(value = "tls_extended_master_secret" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode tlsExtendedMasterSecret;


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SslyzeScanResultPO other = (SslyzeScanResultPO) that;
        return (this.getUuid() == null ? other.getUuid() == null : this.getUuid().equals(other.getUuid()))
            && (this.getServerLocation() == null ? other.getServerLocation() == null : this.getServerLocation().equals(other.getServerLocation()))
            && (this.getNetworkConfiguration() == null ? other.getNetworkConfiguration() == null : this.getNetworkConfiguration().equals(other.getNetworkConfiguration()))
            && (this.getConnectivityStatus() == null ? other.getConnectivityStatus() == null : this.getConnectivityStatus().equals(other.getConnectivityStatus()))
            && (this.getConnectivityErrorTrace() == null ? other.getConnectivityErrorTrace() == null : this.getConnectivityErrorTrace().equals(other.getConnectivityErrorTrace()))
            && (this.getConnectivityResult() == null ? other.getConnectivityResult() == null : this.getConnectivityResult().equals(other.getConnectivityResult()))
            && (this.getScanStatus() == null ? other.getScanStatus() == null : this.getScanStatus().equals(other.getScanStatus()))
            && (this.getCertificateInfo() == null ? other.getCertificateInfo() == null : this.getCertificateInfo().equals(other.getCertificateInfo()))
            && (this.getSsl20CipherSuites() == null ? other.getSsl20CipherSuites() == null : this.getSsl20CipherSuites().equals(other.getSsl20CipherSuites()))
            && (this.getSsl30CipherSuites() == null ? other.getSsl30CipherSuites() == null : this.getSsl30CipherSuites().equals(other.getSsl30CipherSuites()))
            && (this.getTls10CipherSuites() == null ? other.getTls10CipherSuites() == null : this.getTls10CipherSuites().equals(other.getTls10CipherSuites()))
            && (this.getTls11CipherSuites() == null ? other.getTls11CipherSuites() == null : this.getTls11CipherSuites().equals(other.getTls11CipherSuites()))
            && (this.getTls12CipherSuites() == null ? other.getTls12CipherSuites() == null : this.getTls12CipherSuites().equals(other.getTls12CipherSuites()))
            && (this.getTls13CipherSuites() == null ? other.getTls13CipherSuites() == null : this.getTls13CipherSuites().equals(other.getTls13CipherSuites()))
            && (this.getTlsCompression() == null ? other.getTlsCompression() == null : this.getTlsCompression().equals(other.getTlsCompression()))
            && (this.getTls13EarlyData() == null ? other.getTls13EarlyData() == null : this.getTls13EarlyData().equals(other.getTls13EarlyData()))
            && (this.getOpensslCcsInjection() == null ? other.getOpensslCcsInjection() == null : this.getOpensslCcsInjection().equals(other.getOpensslCcsInjection()))
            && (this.getTlsFallbackScsv() == null ? other.getTlsFallbackScsv() == null : this.getTlsFallbackScsv().equals(other.getTlsFallbackScsv()))
            && (this.getHeartbleed() == null ? other.getHeartbleed() == null : this.getHeartbleed().equals(other.getHeartbleed()))
            && (this.getRobot() == null ? other.getRobot() == null : this.getRobot().equals(other.getRobot()))
            && (this.getSessionRenegotiation() == null ? other.getSessionRenegotiation() == null : this.getSessionRenegotiation().equals(other.getSessionRenegotiation()))
            && (this.getSessionResumption() == null ? other.getSessionResumption() == null : this.getSessionResumption().equals(other.getSessionResumption()))
            && (this.getEllipticCurves() == null ? other.getEllipticCurves() == null : this.getEllipticCurves().equals(other.getEllipticCurves()))
            && (this.getHttpHeaders() == null ? other.getHttpHeaders() == null : this.getHttpHeaders().equals(other.getHttpHeaders()))
            && (this.getTlsExtendedMasterSecret() == null ? other.getTlsExtendedMasterSecret() == null : this.getTlsExtendedMasterSecret().equals(other.getTlsExtendedMasterSecret()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUuid() == null) ? 0 : getUuid().hashCode());
        result = prime * result + ((getServerLocation() == null) ? 0 : getServerLocation().hashCode());
        result = prime * result + ((getNetworkConfiguration() == null) ? 0 : getNetworkConfiguration().hashCode());
        result = prime * result + ((getConnectivityStatus() == null) ? 0 : getConnectivityStatus().hashCode());
        result = prime * result + ((getConnectivityErrorTrace() == null) ? 0 : getConnectivityErrorTrace().hashCode());
        result = prime * result + ((getConnectivityResult() == null) ? 0 : getConnectivityResult().hashCode());
        result = prime * result + ((getScanStatus() == null) ? 0 : getScanStatus().hashCode());
        result = prime * result + ((getCertificateInfo() == null) ? 0 : getCertificateInfo().hashCode());
        result = prime * result + ((getSsl20CipherSuites() == null) ? 0 : getSsl20CipherSuites().hashCode());
        result = prime * result + ((getSsl30CipherSuites() == null) ? 0 : getSsl30CipherSuites().hashCode());
        result = prime * result + ((getTls10CipherSuites() == null) ? 0 : getTls10CipherSuites().hashCode());
        result = prime * result + ((getTls11CipherSuites() == null) ? 0 : getTls11CipherSuites().hashCode());
        result = prime * result + ((getTls12CipherSuites() == null) ? 0 : getTls12CipherSuites().hashCode());
        result = prime * result + ((getTls13CipherSuites() == null) ? 0 : getTls13CipherSuites().hashCode());
        result = prime * result + ((getTlsCompression() == null) ? 0 : getTlsCompression().hashCode());
        result = prime * result + ((getTls13EarlyData() == null) ? 0 : getTls13EarlyData().hashCode());
        result = prime * result + ((getOpensslCcsInjection() == null) ? 0 : getOpensslCcsInjection().hashCode());
        result = prime * result + ((getTlsFallbackScsv() == null) ? 0 : getTlsFallbackScsv().hashCode());
        result = prime * result + ((getHeartbleed() == null) ? 0 : getHeartbleed().hashCode());
        result = prime * result + ((getRobot() == null) ? 0 : getRobot().hashCode());
        result = prime * result + ((getSessionRenegotiation() == null) ? 0 : getSessionRenegotiation().hashCode());
        result = prime * result + ((getSessionResumption() == null) ? 0 : getSessionResumption().hashCode());
        result = prime * result + ((getEllipticCurves() == null) ? 0 : getEllipticCurves().hashCode());
        result = prime * result + ((getHttpHeaders() == null) ? 0 : getHttpHeaders().hashCode());
        result = prime * result + ((getTlsExtendedMasterSecret() == null) ? 0 : getTlsExtendedMasterSecret().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uuid=").append(uuid);
        sb.append(", serverLocation=").append(serverLocation);
        sb.append(", networkConfiguration=").append(networkConfiguration);
        sb.append(", connectivityStatus=").append(connectivityStatus);
        sb.append(", connectivityErrorTrace=").append(connectivityErrorTrace);
        sb.append(", connectivityResult=").append(connectivityResult);
        sb.append(", scanStatus=").append(scanStatus);
        sb.append(", certificateInfo=").append(certificateInfo);
        sb.append(", ssl20CipherSuites=").append(ssl20CipherSuites);
        sb.append(", ssl30CipherSuites=").append(ssl30CipherSuites);
        sb.append(", tls10CipherSuites=").append(tls10CipherSuites);
        sb.append(", tls11CipherSuites=").append(tls11CipherSuites);
        sb.append(", tls12CipherSuites=").append(tls12CipherSuites);
        sb.append(", tls13CipherSuites=").append(tls13CipherSuites);
        sb.append(", tlsCompression=").append(tlsCompression);
        sb.append(", tls13EarlyData=").append(tls13EarlyData);
        sb.append(", opensslCcsInjection=").append(opensslCcsInjection);
        sb.append(", tlsFallbackScsv=").append(tlsFallbackScsv);
        sb.append(", heartbleed=").append(heartbleed);
        sb.append(", robot=").append(robot);
        sb.append(", sessionRenegotiation=").append(sessionRenegotiation);
        sb.append(", sessionResumption=").append(sessionResumption);
        sb.append(", ellipticCurves=").append(ellipticCurves);
        sb.append(", httpHeaders=").append(httpHeaders);
        sb.append(", tlsExtendedMasterSecret=").append(tlsExtendedMasterSecret);
        sb.append("]");
        return sb.toString();
    }
}