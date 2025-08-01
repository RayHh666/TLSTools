-- 扫描任务表（增强工具元数据）
CREATE TABLE scan_task (
                           task_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           task_type ENUM('TLS_SCAN', 'HTTP_SCAN', 'QUIC_SCAN', 'CUSTOM') NOT NULL DEFAULT 'TLS_SCAN',
                           tool_name VARCHAR(50) NOT NULL DEFAULT 'sslyze',
                           tool_version VARCHAR(20) NULL COMMENT '工具版本',
                           status ENUM('PENDING', 'RUNNING', 'COMPLETED', 'FAILED') NOT NULL DEFAULT 'PENDING',
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           started_at TIMESTAMP NULL,
                           completed_at TIMESTAMP NULL,

                           INDEX idx_tool_version (tool_name, tool_version)
);

-- 扫描目标表（通用化字段）
CREATE TABLE scan_target (
                             target_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             task_id BIGINT NOT NULL,
                             host VARCHAR(255) NOT NULL COMMENT '目标主机(域名或IP)',
                             port INT NOT NULL DEFAULT 443,
                             resolved_ip VARCHAR(45) NULL COMMENT '解析后的IP地址',
                             connection_type ENUM('DIRECT', 'HTTP_PROXY', 'SOCKS') NULL,
                             proxy_settings JSON NULL COMMENT '代理配置',

                             INDEX idx_target_host (host),
                             INDEX idx_target_ip (resolved_ip),
                             CONSTRAINT fk_target_task FOREIGN KEY (task_id) REFERENCES scan_task(task_id)
);

-- 连接详情表（通用协议信息）
CREATE TABLE connection_detail (
                                   connection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   target_id BIGINT NOT NULL,
                                   highest_tls_version VARCHAR(10) NULL COMMENT '最高支持的TLS版本',
                                   cipher_suite_supported VARCHAR(100) NULL COMMENT '协商的密码套件',
                                   client_auth_requirement VARCHAR(20) NULL COMMENT 'DISABLED/OPTIONAL/REQUIRED',
                                   supports_ecdh BOOLEAN DEFAULT 0,

                                   CONSTRAINT fk_connection_target FOREIGN KEY (target_id) REFERENCES scan_target(target_id)
);

--- 证书详情表 (存储单个证书的完整信息)
CREATE TABLE certificate_detail (
                                    cert_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    fingerprint_sha256 VARCHAR(64) NOT NULL COMMENT 'SHA256指纹 (唯一标识)',

    -- 基础信息
                                    as_pem TEXT NOT NULL COMMENT '证书PEM格式',
                                    fingerprint_sha1 VARCHAR(40) NULL COMMENT 'SHA1指纹',
                                    serial_number VARCHAR(100) NOT NULL COMMENT '证书序列号',
                                    not_valid_before DATETIME NOT NULL COMMENT '有效期开始',
                                    not_valid_after DATETIME NOT NULL COMMENT '有效期结束',

    -- 主题信息
                                    subject_rfc4514_string VARCHAR(500) NOT NULL COMMENT '主题完整DN',
                                    subject_common_name VARCHAR(255) NOT NULL COMMENT '主题通用名',
                                    subject_organization VARCHAR(255) NULL COMMENT '主题组织',
                                    subject_organizational_unit VARCHAR(255) NULL COMMENT '主题组织单元',
                                    subject_country VARCHAR(2) NULL COMMENT '主题国家',
                                    subject_state VARCHAR(100) NULL COMMENT '主题州/省',
                                    subject_locality VARCHAR(100) NULL COMMENT '主题地区',

    -- 颁发者信息
                                    issuer_rfc4514_string VARCHAR(500) NOT NULL COMMENT '颁发者完整DN',
                                    issuer_common_name VARCHAR(255) NOT NULL COMMENT '颁发者通用名',
                                    issuer_organization VARCHAR(255) NULL COMMENT '颁发者组织',
                                    issuer_organizational_unit VARCHAR(255) NULL COMMENT '颁发者组织单元',
                                    issuer_country VARCHAR(2) NULL COMMENT '颁发者国家',
                                    issuer_state VARCHAR(100) NULL COMMENT '颁发者州/省',
                                    issuer_locality VARCHAR(100) NULL COMMENT '颁发者地区',

    -- 公钥信息
                                    public_key_algorithm VARCHAR(50) NOT NULL COMMENT '公钥算法',
                                    public_key_key_size SMALLINT UNSIGNED NULL COMMENT '公钥大小 (位)',
                                    public_key_curve_name VARCHAR(50) NULL COMMENT '椭圆曲线名称 (ECC)',
                                    public_key_exponent BIGINT NULL COMMENT 'RSA指数',
                                    public_key_modulus TEXT NULL COMMENT 'RSA模数',

    -- 签名信息
                                    signature_algorithm VARCHAR(50) NOT NULL COMMENT '签名算法',
                                    signature_algorithm_oid VARCHAR(100) NOT NULL COMMENT '签名算法OID',

    -- 扩展信息
                                    subject_alternative_name JSON NOT NULL COMMENT '主题备用名称',
                                    key_usage JSON NULL COMMENT '密钥用途',
                                    extended_key_usage JSON NULL COMMENT '扩展密钥用途',
                                    certificate_policies JSON NULL COMMENT '证书策略',
                                    crl_distribution_points JSON NULL COMMENT 'CRL分发点',
                                    authority_information_access JSON NULL COMMENT '授权信息访问',
                                    basic_constraints JSON NULL COMMENT '基本约束',
                                    name_constraints JSON NULL COMMENT '名称约束',
                                    policy_constraints JSON NULL COMMENT '策略约束',

    -- 标志信息
                                    is_ca BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否是CA证书',
                                    is_self_signed BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否自签名',
                                    is_leaf_certificate BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否是叶子证书',
                                    has_ocsp_must_staple BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否有OCSP装订扩展',
                                    is_ev_certificate BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否是扩展验证(EV)证书',

    -- 时间戳
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 索引
                                    UNIQUE INDEX idx_cert_fingerprint (fingerprint_sha256),
                                    INDEX idx_cert_subject_cn (subject_common_name),
                                    INDEX idx_cert_issuer_cn (issuer_common_name),
                                    INDEX idx_cert_expiry (not_valid_after),
                                    INDEX idx_cert_public_key_algo (public_key_algorithm),
                                    INDEX idx_cert_key_size (public_key_key_size)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPRESSED KEY_BLOCK_SIZE=8;

-- 证书链表 (存储证书链关系)
CREATE TABLE certificate_chain (
                                   chain_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   target_id BIGINT NOT NULL COMMENT '关联扫描目标',

    -- 链信息
                                   leaf_certificate_id BIGINT NOT NULL COMMENT '叶子证书ID',
                                   intermediate_ca_ids JSON NOT NULL COMMENT '中间证书ID列表',
                                   root_ca_id BIGINT NULL COMMENT '根证书ID',

    -- 验证信息
                                   is_trusted BOOLEAN NOT NULL DEFAULT 0 COMMENT '证书链是否受信任',
                                   received_chain_contains_anchor_certificate BOOLEAN NOT NULL DEFAULT 0 COMMENT '链中是否包含锚证书',
                                   received_chain_has_valid_order BOOLEAN NOT NULL DEFAULT 1 COMMENT '链顺序是否正确',

    -- 时间戳
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 外键约束
                                   CONSTRAINT fk_chain_target FOREIGN KEY (target_id) REFERENCES scan_target(target_id),
                                   CONSTRAINT fk_chain_leaf_cert FOREIGN KEY (leaf_certificate_id) REFERENCES certificate_detail(cert_id),
                                   CONSTRAINT fk_chain_root_cert FOREIGN KEY (root_ca_id) REFERENCES certificate_detail(cert_id),

    -- 索引
                                   INDEX idx_chain_trust_status (is_trusted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 路径验证结果表 (存储证书链的验证结果)
CREATE TABLE path_validation_result (
                                        validation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        chain_id BIGINT NOT NULL COMMENT '关联证书链',

    -- 信任库信息
                                        trust_store_name VARCHAR(100) NOT NULL COMMENT '信任库名称',
                                        trust_store_version VARCHAR(50) NULL COMMENT '信任库版本',

    -- 验证结果
                                        was_validation_successful BOOLEAN NOT NULL COMMENT '验证是否成功',
                                        validation_error TEXT NULL COMMENT '验证错误信息',
                                        verified_certificate_chain JSON NOT NULL COMMENT '验证后的证书链',

    -- 时间戳
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 外键约束
                                        CONSTRAINT fk_validation_chain FOREIGN KEY (chain_id) REFERENCES certificate_chain(chain_id),

    -- 索引
                                        INDEX idx_validation_success (was_validation_successful),
                                        INDEX idx_trust_store (trust_store_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 证书链关系视图 (方便查询)
CREATE VIEW certificate_chain_view AS
SELECT
    c.chain_id,
    t.host AS target_host,
    leaf.subject_common_name AS leaf_certificate,
    root.subject_common_name AS root_certificate,
    JSON_LENGTH(c.intermediate_ca_ids) AS intermediate_count,
    c.is_trusted,
    c.received_chain_has_valid_order,
    p.was_validation_successful
FROM certificate_chain c
         JOIN scan_target t ON c.target_id = t.target_id
         JOIN certificate_detail leaf ON c.leaf_certificate_id = leaf.cert_id
         LEFT JOIN certificate_detail root ON c.root_ca_id = root.cert_id
         LEFT JOIN path_validation_result p ON c.chain_id = p.chain_id;

-- 密码套件支持表（标准化存储）
CREATE TABLE cipher_suite_support (
                                      cipher_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      target_id BIGINT NOT NULL,
                                      tls_version VARCHAR(10) NOT NULL COMMENT 'TLS版本',

    -- 套件详情
                                      cipher_name VARCHAR(100) NOT NULL,
                                      is_accepted BOOLEAN NOT NULL DEFAULT 1,
                                      key_size SMALLINT NULL,
                                      forward_secrecy BOOLEAN DEFAULT 0,
                                      rating ENUM('EXCELLENT', 'GOOD', 'WEAK', 'INSECURE') DEFAULT 'GOOD',
                                      openssl_name VARCHAR(100) NOT NULL,

                                      INDEX idx_cipher_name (cipher_name),
                                      INDEX idx_cipher_rating (rating),
                                      CONSTRAINT fk_cipher_target FOREIGN KEY (target_id) REFERENCES scan_target(target_id)
);

-- 漏洞检测框架（支持多工具）
CREATE TABLE vulnerability_detection (
                                         detection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         target_id BIGINT NOT NULL,
                                         tool_name VARCHAR(50) NOT NULL DEFAULT 'sslyze' COMMENT '检测工具',

    -- 检测项信息
                                         detection_name VARCHAR(50) NOT NULL COMMENT '检测项名称',
                                         status ENUM('DETECTED', 'NOT_DETECTED', 'ERROR', 'NOT_TESTED') NOT NULL,
                                         error_reason TEXT NULL COMMENT '错误原因',

    -- 检测结果
                                         is_vulnerable BOOLEAN NULL COMMENT '是否存在漏洞',
                                         severity ENUM('CRITICAL', 'HIGH', 'MEDIUM', 'LOW', 'INFO') NULL COMMENT '漏洞严重等级',

    -- 详细结果
                                         evidence TEXT NULL COMMENT '漏洞证据',
                                         remediation TEXT NULL COMMENT '修复建议',
                                         raw_details JSON NOT NULL COMMENT '原始检测结果',

    -- 时间戳
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                         INDEX idx_detection_name (detection_name),
                                         CONSTRAINT fk_detection_target FOREIGN KEY (target_id) REFERENCES scan_target(target_id)
);

-- 漏洞类型表（标准化标识）
CREATE TABLE vulnerability_type (
                                    vuln_id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL UNIQUE COMMENT '漏洞名称',
                                    standard_key VARCHAR(100) NOT NULL UNIQUE COMMENT '标准化标识',
                                    cve_id VARCHAR(20) NULL COMMENT 'CVE编号',
                                    description TEXT NOT NULL COMMENT '漏洞描述',
                                    severity ENUM('CRITICAL', 'HIGH', 'MEDIUM', 'LOW', 'INFO') NOT NULL,
                                    remediation TEXT NOT NULL COMMENT '修复建议',
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 漏洞关联表（重新引入）
CREATE TABLE vulnerability_association (
                                           association_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           detection_id BIGINT NOT NULL COMMENT '关联vulnerability_detection',
                                           vuln_id INT NOT NULL COMMENT '关联vulnerability_type',

                                           CONSTRAINT fk_assoc_detection FOREIGN KEY (detection_id) REFERENCES vulnerability_detection(detection_id),
                                           CONSTRAINT fk_assoc_vuln FOREIGN KEY (vuln_id) REFERENCES vulnerability_type(vuln_id)
);

-- 工具漏洞映射表（新增）
CREATE TABLE tool_vulnerability_mapping (
                                            mapping_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            tool_name VARCHAR(50) NOT NULL,
                                            tool_vuln_id VARCHAR(100) NOT NULL COMMENT '工具特定漏洞ID',
                                            standard_key VARCHAR(100) NOT NULL COMMENT '标准化漏洞标识',

                                            UNIQUE INDEX idx_tool_mapping (tool_name, tool_vuln_id),
                                            CONSTRAINT fk_mapping_standard FOREIGN KEY (standard_key) REFERENCES vulnerability_type(standard_key)
);

-- 原始结果表（增强元数据）
CREATE TABLE tool_raw_result (
                                 result_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 task_id BIGINT NOT NULL,
                                 tool_name VARCHAR(50) NOT NULL,
                                 tool_version VARCHAR(20) NULL,
                                 output_format ENUM('JSON', 'XML', 'TEXT') NOT NULL DEFAULT 'JSON',
                                 raw_data JSON NOT NULL,
                                 parsed BOOLEAN DEFAULT 0 COMMENT '是否已解析',

                                 CONSTRAINT fk_raw_result_task FOREIGN KEY (task_id) REFERENCES scan_task(task_id)
);

-- 预填充已知漏洞类型
INSERT INTO vulnerability_type (name, cve_id, description, severity) VALUES
                                                                         ('OpenSSL CCS Injection', 'CVE-2014-0224', 'OpenSSL CCS注入漏洞，允许中间人攻击', 'HIGH'),
                                                                         ('Heartbleed', 'CVE-2014-0160', 'OpenSSL心脏滴血漏洞，可泄露服务器内存内容', 'CRITICAL'),
                                                                         ('ROBOT', 'CVE-2017-13098', 'RSA Oracle威胁漏洞，可解密TLS会话', 'HIGH'),
                                                                         ('Session Renegotiation', 'CVE-2009-3555', 'TLS会话重协商漏洞，可导致拒绝服务攻击', 'MEDIUM'),
                                                                         ('CRIME Attack', 'CVE-2012-4929', 'TLS压缩漏洞，可导致会话劫持', 'MEDIUM'),
                                                                         ('TLS 1.3 Early Data', NULL, 'TLS 1.3 0-RTT数据重放攻击风险', 'LOW'),
                                                                         ('Weak Cipher Suite', NULL, '使用弱密码套件，存在安全风险', 'MEDIUM');

-- SSLyze映射
INSERT INTO tool_vulnerability_mapping (tool_name, tool_vuln_id, standard_key) VALUES
                                                                                   ('sslyze', 'openssl_ccs_injection', 'ccs_injection'),
                                                                                   ('sslyze', 'heartbleed', 'heartbleed'),
                                                                                   ('sslyze', 'robot', 'robot'),
                                                                                   ('sslyze', 'weak_cipher_suites', 'weak_cipher');

-- testssl映射
INSERT INTO tool_vulnerability_mapping (tool_name, tool_vuln_id, standard_key) VALUES
                                                                                   ('testssl', 'CCS', 'ccs_injection'),
                                                                                   ('testssl', 'heartbleed', 'heartbleed'),
                                                                                   ('testssl', 'ROBOT', 'robot'),
                                                                                   ('testssl', 'cipherlist_weak', 'weak_cipher'),
                                                                                   ('testssl', 'CRIME_TLS', 'crime'),
                                                                                   ('testssl', 'POODLE_SSL', 'poodle');

-- sslscan映射
INSERT INTO tool_vulnerability_mapping (tool_name, tool_vuln_id, standard_key) VALUES
                                                                                   ('sslscan', 'CVE-2014-0224', 'ccs_injection'),
                                                                                   ('sslscan', 'CVE-2014-0160', 'heartbleed'),
                                                                                   ('sslscan', 'weak-ciphers', 'weak_cipher');

-- 密码套件映射表
CREATE TABLE cipher_suite_mapping (
                                      mapping_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      tool_name VARCHAR(50) NOT NULL,
                                      tool_cipher_name VARCHAR(100) NOT NULL,
                                      standard_cipher_name VARCHAR(100) NOT NULL,

                                      UNIQUE INDEX idx_cipher_mapping (tool_name, tool_cipher_name)
);

-- 示例映射
INSERT INTO cipher_suite_mapping (tool_name, tool_cipher_name, standard_cipher_name) VALUES
                                                                                         ('sslyze', 'TLS_RSA_WITH_RC4_128_SHA', 'RC4-SHA'),
                                                                                         ('testssl', 'RC4-SHA', 'RC4-SHA'),
                                                                                         ('sslscan', 'SSL_RSA_WITH_RC4_128_SHA', 'RC4-SHA'),
                                                                                         ('sslyze', 'TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384', 'ECDHE-RSA-AES256-GCM-SHA384');