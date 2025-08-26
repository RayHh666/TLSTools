package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.dto.CertificateInfoDTO;
import com.example.tlstool.entity.po.CertificateDetailPO;
import com.example.tlstool.entity.vo.CertificateInfoVO;
import com.example.tlstool.service.CertificateDetailService;
import com.example.tlstool.mapper.CertificateDetailMapper;
import com.example.tlstool.util.DateTimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author admin
* @description 针对表【certificate_detail】的数据库操作Service实现
* @createDate 2025-08-06 17:08:57
*/
@Service
public class CertificateDetailServiceImpl extends ServiceImpl<CertificateDetailMapper, CertificateDetailPO>
    implements CertificateDetailService{

    @Async
    @Override
    public void saveCertificateDetailFromSslyze(JsonNode certificateDetails, Long certificateDeploymentId, Long pathValidationResultId) {
        if (certificateDetails != null && certificateDetails.isArray()) {
            Integer index = 0;
            for (JsonNode certificateDetail : certificateDetails) {

                String subjectCountry = null;
                String subjectState = null;
                String subjectLocality = null;
                String subjectOrganization = null;
                String subjectOrganizationalUnit = null;
                String subjectCommonName = null;
                String issuerCountry = null;
                String issuerState = null;
                String issuerLocality = null;
                String issuerOrganization = null;
                String issuerOrganizationalUnit = null;
                String issuerCommonName = null;

                JsonNode subjectAttributes = certificateDetail.findValue("subject").findValue("attributes");
                if (subjectAttributes != null && subjectAttributes.isArray()) {
                    for (JsonNode subjectAttribute : subjectAttributes) {
                        if ("countryName".equals(subjectAttribute.findValue("oid").findValue("name").asText())) {
                            subjectCountry = subjectAttribute.findValue("value").asText();
                        } else if ("stateOrProvinceName".equals(subjectAttribute.findValue("oid").findValue("name").asText())) {
                            subjectState = subjectAttribute.findValue("value").asText();
                        } else if ("localityName".equals(subjectAttribute.findValue("oid").findValue("name").asText())) {
                            subjectLocality = subjectAttribute.findValue("value").asText();
                        } else if ("organizationName".equals(subjectAttribute.findValue("oid").findValue("name").asText())) {
                            subjectOrganization = subjectAttribute.findValue("value").asText();
                        } else if ("organizationalUnitName".equals(subjectAttribute.findValue("oid").findValue("name").asText())) {
                            subjectOrganizationalUnit = subjectAttribute.findValue("value").asText();
                        } else if ("commonName".equals(subjectAttribute.findValue("oid").findValue("name").asText())) {
                            subjectCommonName = subjectAttribute.findValue("value").asText();
                        }
                    }
                }

                JsonNode issuerAttributes = certificateDetail.findValue("issuer").findValue("attributes");
                if (issuerAttributes != null && issuerAttributes.isArray()) {
                    for (JsonNode issuerAttribute : issuerAttributes) {
                        if ("countryName".equals(issuerAttribute.findValue("oid").findValue("name").asText())) {
                            issuerCountry = issuerAttribute.findValue("value").asText();
                        } else if ("stateOrProvinceName".equals(issuerAttribute.findValue("oid").findValue("name").asText())) {
                            issuerState = issuerAttribute.findValue("value").asText();
                        } else if ("localityName".equals(issuerAttribute.findValue("oid").findValue("name").asText())) {
                            issuerLocality = issuerAttribute.findValue("value").asText();
                        } else if ("organizationName".equals(issuerAttribute.findValue("oid").findValue("name").asText())) {
                            issuerOrganization = issuerAttribute.findValue("value").asText();
                        } else if ("organizationalUnitName".equals(issuerAttribute.findValue("oid").findValue("name").asText())) {
                            issuerOrganizationalUnit = issuerAttribute.findValue("value").asText();
                        } else if ("commonName".equals(issuerAttribute.findValue("oid").findValue("name").asText())) {
                            issuerCommonName = issuerAttribute.findValue("value").asText();
                        }
                    }
                }

                // 公钥加密算法安全性分析
                String publicKeyAlgorithm = certificateDetail.findValue("public_key").findValue("algorithm").asText();
                String publicKeyRemark = "此公钥加密算法符合当前安全标准";
                switch (publicKeyAlgorithm) {
                    case "RSAPublicKey":
                        if (certificateDetail.findValue("public_key").findValue("key_size").asInt() < 2048) {
                            publicKeyRemark = "RSA密钥长度小于2048位，已淘汰";
                        }
                        break;
                    case "ECPublicKey":
                        if ("secp192k1".equals(certificateDetail.findValue("public_key").findValue("ec_curve_name").asText())) {
                            publicKeyRemark = "ECC密钥使用了弱曲线 secp192k1，应禁用";
                        }
                        break;
                }

                // 证书签名算法安全性分析
                String signatureHashAlgorithmName = certificateDetail.findValue("signature_hash_algorithm").findValue("name").asText();
                String signatureHashAlgorithmRemark = null;
                switch (signatureHashAlgorithmName) {
                    case "sha1":
                        signatureHashAlgorithmRemark = "此证书使用了SHA-1弱哈希算法，建议升级为 SHA-2 系列（如 SHA-256、SHA-384）";
                        break;
                    case "md5":
                        signatureHashAlgorithmRemark = "此证书使用了已淘汰的MD5算法，建议升级为 SHA-2 系列（如 SHA-256、SHA-384）";
                        break;
                    case "sha256":
                        signatureHashAlgorithmRemark = "此证书使用了SHA-256哈希算法，符合当前安全标准";
                        break;
                    case "sha384":
                        signatureHashAlgorithmRemark = "此证书使用了SHA-384哈希算法，符合当前安全标准";
                        break;
                }

                CertificateDetailPO certificateDetailPO = new CertificateDetailPO().builder()
                        .asPem(certificateDetail.findValue("as_pem").asText())
                        .fingerprintSha1(certificateDetail.findValue("fingerprint_sha1").asText())
                        .fingerprintSha256(certificateDetail.findValue("fingerprint_sha256").asText())
                        .serialNumber(String.valueOf(certificateDetail.findValue("serial_number").asText()))
                        .notValidBefore(DateTimeUtils.convertToLocalDateTime(certificateDetail.findValue("not_valid_before").asText()))
                        .notValidAfter(DateTimeUtils.convertToLocalDateTime(certificateDetail.findValue("not_valid_after").asText()))
                        .subjectAlternativeName(certificateDetail.findValue("subject_alternative_name"))
                        .signatureHashAlgorithmName(certificateDetail.findValue("signature_hash_algorithm").findValue("name").asText())
                        .signatureHashAlgorithmDigestSize(certificateDetail.findValue("signature_hash_algorithm").findValue("digest_size").asInt())
                        .signatureAlgorithmOidName(certificateDetail.findValue("signature_algorithm_oid").findValue("name").asText())
                        .signatureAlgorithmOidDottedString(certificateDetail.findValue("signature_algorithm_oid").findValue("dotted_string").asText())
                        .subjectRfc4514String(certificateDetail.findValue("subject").findValue("rfc4514_string").asText())
                        .subjectCountry(subjectCountry)
                        .subjectState(subjectState)
                        .subjectLocality(subjectLocality)
                        .subjectOrganization(subjectOrganization)
                        .subjectOrganizationalUnit(subjectOrganizationalUnit)
                        .subjectCommonName(subjectCommonName)
                        .issuerCountry(issuerCountry)
                        .issuerState(issuerState)
                        .issuerLocality(issuerLocality)
                        .issuerOrganization(issuerOrganization)
                        .issuerOrganizationalUnit(issuerOrganizationalUnit)
                        .issuerCommonName(issuerCommonName)
                        .issuerRfc4514String(certificateDetail.findValue("issuer").findValue("rfc4514_string").asText())
                        .publicKeyAlgorithm(certificateDetail.findValue("public_key").findValue("algorithm").asText())
                        .publicKeyKeySize(certificateDetail.findValue("public_key").findValue("key_size").asInt())
                        .publicKeyRsaE(certificateDetail.findValue("public_key").findValue("rsa_e").asLong())
                        .publicKeyRsaN(certificateDetail.findValue("public_key").findValue("rsa_n").asLong())
                        .publicKeyEcCurveName(certificateDetail.findValue("public_key").findValue("ec_curve_name").asText())
                        .publicKeyEcX(certificateDetail.findValue("public_key").findValue("ec_x").asLong())
                        .publicKeyEcY(certificateDetail.findValue("public_key").findValue("ec_y").asLong())
                        .createdAt(LocalDateTime.now())
                        .certificateDeploymentId(certificateDeploymentId)
                        .pathValidationResultId(pathValidationResultId)
                        .certificateDetailIndex(index)
                        .publicKeyRemark(publicKeyRemark)
                        .signatureHashAlgorithmRemark(signatureHashAlgorithmRemark)
                        .build();

                baseMapper.insert(certificateDetailPO);

                index++;
            }
        }
    }

    @Override
    public CertificateInfoVO getCertificateInfoPage(Long taskId,  Integer count, int page, int size) {
        CertificateInfoVO  certificateInfoVO = new CertificateInfoVO();
        int offset = (page - 1) * size;
        List<CertificateInfoDTO> certificateInfoDTOList = baseMapper.getCertificateInfoPageByTaskId(taskId, count, offset, size);
        Integer total = baseMapper.getCertificateInfoTotalByTaskId(taskId, count);
        certificateInfoVO.setTotal(total);
        certificateInfoVO.setCertificateInfoDTOList(certificateInfoDTOList);
        return certificateInfoVO;
    }

    @Override
    public List<CertificateDetailPO> getCertificateChainDetail(Long certificateDeploymentId) {
        return baseMapper.getCertificateChainDetail(certificateDeploymentId);
    }
}




