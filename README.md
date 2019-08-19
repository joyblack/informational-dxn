# Introduce
This is the first item on Guizhou, May I try my best to do it!


* http://192.168.30.170:3030/#/system/department/list  1
* http://117.187.16.82:8070/digitizationMine/sumbitLogin admin&a123456 公司名：gyehj#

# Service Call
## service path
各接口的调用模板：**{server:port}/{project}/{version}/{module}/{method}**

* server:port： 根据服务器环境进行设置，例如: 127.0.0.1:8090
* project： 根据具体的项目名进行设置，例如：informational-dxn
* version： 当前接口的版本号，例如：v1
* module： 当前调用接口所属模块，例如：user
* method： 当前调用接口的操作类型，例如：add

综上，可以得出，本项目若想添加一个用户，可使用如下的URL进行调用:
```
127.0.0.1:8090/informational-dxn/v1/user/add
```
前半部分除了端口意外其他不会变动，因此，接口文档中的调用地址，只会写调用接口的 **{module}/{method}** 部分，请留意。

## return parameter
如果是获取数据的接口，若提供不存在的查询条件或者ID，返回也是成功提示，但是data属性中的属性值为空。

# TimeLine
**2019-8-12 -> 2019-8-16**

1. 完成绿映塘项目的架构搭建，确定了数据库架构。

2. 开始大西南项目的项目。

3. 完善项目的设置模块：煤矿平台创建后台接口。

4. 完善项目的员工模块：员工入职相关管理后台接口





# N、修改点
1. 接口请求时，数据修改为对象形式；

2. 接口返回时，考虑到参数名重复导致混淆的问题，数据改为嵌套对象形式；

3. remark字段修改为remarks；

4. 枚举类型的数据不再使用数字表示，而是具体的带有确切含义的英文字符串，相关的可选列表会在**参数参考表**中一一列出。


**下周计划**
1. 完善职工模块。

2. 开始安全管理、设备管理制作。

# 问题
1. 部门表路径会出现null。

2. 离职添加处未处理参保状态信息。

3. 离职更新该怎么做，有待商榷。