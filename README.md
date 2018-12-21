# SubCNString
中文分词工具

# 概述：

  使用springboot搭建项目，后台单独生成词库文件，简单快捷，配置好初始化词库后即可使用。
  个人闲的无聊自己写的，欢迎有不同思路的同仁指教。
  # 工作忙,更新较慢!!!

# 使用步骤：

maven导出SubCNS模块，并配置application.properties文件

//项目访问路径
server.context-path=/
//项目访问端口
server.port=9527
//session失效时间
server.session.timeout=30
//编码格式
server.tomcat.uri-encoding=utf-8
//词库生成路径，初始化请将项目根目录chars.dbs文件方式到配置好的路径下，初始化基本词库。
db.path=D:\\temp\\db
//后台管理id，暂未使用
manager.id=Wkdo,lg.wkaugltjwnmtne
