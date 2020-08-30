# auto-aliyundns
### 简介
本项目为spirngboot项目，自动获取本机公网ip，并添加或修改阿里云dns解析记录，实现动态解析。
### 使用方法
1、clone本项目到本地  
2、删除application.yml，将application.yml.simple修改为application.ylm。  
3、分别修改AccessKeyID、AccessKeySecret、mainDemain、subDemain信息。  
配置信息解释
```
  AccessKeyID: 阿里云AccessKey ID，登陆阿里云在"AccessKey 管理"查看和申请
  AccessKeySecret: 阿里云AccessKey Secret
  mainDemain: 域名，如果解析test.github.com就填：github.com
  subDemain: 主机记录，如果解析test.github.com就填：test
```
4、启动springboot项目。  

