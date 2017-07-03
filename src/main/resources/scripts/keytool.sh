#!/bin/bash
#使用keytool工具生成单向认证
echo =====生成密钥=====
keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA1withRSA -validity 36000 -alias www.91160.com -keystore server.keystore
echo =====导出服务端证书=====
keytool -exportcert -alias www.91160.com -keystore server.keystore -file server.cer -rfc


