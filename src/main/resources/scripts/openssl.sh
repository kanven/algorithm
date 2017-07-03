#!/bin/bash

#使用openssl生成证书和自签名，使用keytool转换成jks,满足单向和双向认证

BASE_DIR=/Applications/ssl

echo 构建已发行证书存放目录 certs
mkdir $BASE_DIR/certs
echo 构建新证书存放目录 newcerts
mkdir $BASE_DIR/newcerts
echo 构建私钥存放目录 private
mkdir $BASE_DIR/private
echo 构建证书吊销列表存放目录 crl
mkdir $BASE_DIR/crl

echo 构建索引文件 index.txt
echo 0>$BASE_DIR/index.txt
echo 构建序列号文件 serial
echo "00">$BASE_DIR/serial

echo =====构建随机数 private/.rand=====
openssl rand -out $BASE_DRI/.rand 1000

echo =====构建根证书私钥 private/ca.key.pem=====
openssl genrsa -aes256 -out $BASE_DIR/private/ca.key.pem 2048

echo =====生成根证书签发申请 private/ca.csr=====
openssl req -new -key $BASE_DIR/private/ca.key.pem -out $BASE_DIR/private/ca.csr -config $BASE_DIR/openssl.cnf

echo =====签发根证书private/ca.cer=====
openssl x509 -req -days 10000 -sha1 -extensions v3_ca -signkey $BASE_DIR/private/ca.key.pem -in $BASE_DIR/private/ca.csr -out $BASE_DIR/certs/ca.cer

echo 根证书转换 =====private/ca.p12=====
openssl pkcs12 -export -cacerts -inkey $BASE_DIR/private/ca.key.pem -in $BASE_DIR/certs/ca.cer -out $BASE_DIR/certs/ca.p12

echo =====查看密钥库信息=====
keytool -list -keystore $BASE_DIR/certs/ca.p12 -storetype pkcs12 -v

echo =====构建服务器私钥=====
openssl genrsa -aes256 -out $BASE_DIR/private/server.key.pem 2048

echo =====构建服务器证书申请 private/server.csr=====
openssl req -new -key $BASE_DIR/private/server.key.pem -out $BASE_DIR/private/server.csr -config $BASE_DIR/openssl.cnf

echo =====签发服务器证书private/server.cer=====
openssl x509 -req -days 3650 -sha1 -extensions v3_req -CA $BASE_DIR/certs/ca.cer -CAkey $BASE_DIR/private/ca.key.pem -CAserial $BASE_DIR/ca.srl -CAcreateserial -in $BASE_DIR/private/server.csr -out $BASE_DIR/certs/server.cer

echo =====服务器证书转换 private/server.p12=====
openssl pkcs12 -export -cacerts -inkey $BASE_DIR/private/server.key.pem -in $BASE_DIR/certs/server.cer -out $BASE_DIR/certs/server.p12

echo ===== 服务端p12转换成jks =====
keytool -importkeystore -srckeystore $BASE_DIR/certs/server.p12 -srcstoretype PKCS12 -destkeystore $BASE_DIR/certs/server.jks

echo =====构建客户端证书私钥 private/client.key.pem=====
openssl genrsa -aes256 -out $BASE_DIR/private/client.key.pem 2048

echo =====构建客户端证书申请 private/client.csr=====
openssl req -new -key $BASE_DIR/private/client.key.pem -out $BASE_DIR/private/client.csr -config $BASE_DIR/openssl.cnf

echo =====签发客户端证书private/client.cer=====
openssl ca -days 3650 -in $BASE_DIR/private/client.csr -out $BASE_DIR/certs/client.cer -cert $BASE_DIR/certs/ca.cer -keyfile $BASE_DIR/private/ca.key.pem -config $BASE_DIR/openssl.cnf

echo =====客户端证书转换 private/client.p12=====
openssl pkcs12 -export -inkey $BASE_DIR/private/client.key.pem -in $BASE_DIR/certs/client.cer -out $BASE_DIR/certs/client.p12

echo =====客户端p12转换成jks ======
keytool -importkeystore -srckeystore $BASE_DIR/certs/client.p12 -srcstoretype PKCS12 -destkeystore $BASE_DIR/certs/client.jks


