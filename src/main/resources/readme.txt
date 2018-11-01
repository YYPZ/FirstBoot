访问druid sql 监控 路径在 DruidStatViewServlet 里配置
http://localhost:8082/FirstBoot/druid

分页插件 PageHelper
   用法：
    PageHelper.startPage(1, 5);
	List<com.ye.FirstBoot.dataAccess.mybatis.model.User> userList=userDao.selectAllUsers();
	PageInfo result = new PageInfo(userList);


#actuator 监控配置  访问例子http://localhost:8082/FirstBoot/actuator/health
文档地址https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html