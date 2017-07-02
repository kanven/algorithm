# algorithm
算法实现

SSL单向认证
	单向认证客服端、服务端以及受信任keystore均是同一个。
	生成是要的命令为：
	keytool -genkeypair -keyalg RSA -keysize 2048 -sigalg SHA1withRSA -validity 36000 -alias www.91160.com -keystore ks.keystore

SSL双向认证
	双向认证是指服务端和客户端都需要认证对方的合法性。
    操作步骤：
    	I）服务端
    	  1、生成服务端私钥；
    	  2、生成服务端证书；
    	  3、将服务端证书导入客户端受信任的keystore。
       II）客服端
       	  1、生成客户端私钥；
       	  2、生成客户端证书；
       	  3、将客服端证书导入服务端受信任的keystore。
       	  
 参考连接：
 	http://www.cnblogs.com/yqskj/p/3142861.html
