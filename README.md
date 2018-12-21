# SubCNString
中文分词工具

# 概述：

  使用springboot搭建项目，后台单独生成词库文件，简单快捷，配置好初始化词库后即可使用。
  个人闲的无聊自己写的，欢迎有不同思路的同仁指教。
  # 工作忙,更新较慢!!!

# 使用步骤：

maven导出SubCNS模块，并配置application.properties文件，将项目根目录chars.dbs文件剪切到db.path指定的路径完成词库初始化。

#properties配置说明：

	db.path	数据库备份路径，初始化时必须将chars.dbs文件剪切到此路径下。
	manager.id	管理员id，增加或删除词组时必须在请求头中加入managerId。
	backups.time 词库备份时间，单位未分钟。
	其他参数请参考springboot配置。


