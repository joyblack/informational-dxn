# 参考模板
http://192.168.30.170:3030/#/system/department/list  
* 1

http://117.187.16.82:8070/digitizationMine/sumbitLogin 
* 账号:admin 密码：a123456 公司名：gyehj#



# 1、接口调用
## 1.1、调用地址
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

* 接口请求时，数据修改为对象形式；

## 1.2、返回参数
* 如果是获取数据的接口，若提供不存在的查询条件或者ID，返回也是成功提示，但是data属性中的属性值为空。
* 接口返回时，考虑到参数名重复导致混淆的问题，数据改为嵌套对象形式；
* remark字段修改为remarks；
* 枚举类型的数据不再使用数字表示(当然也支持，不过不推荐)，而是具体的带有确切含义的英文字符串，相关的可选列表会在**参数参考表**中一一列出。

# 设计原理
## 1、登陆
### 1.1、如何登录
登录成功后，可获取服务器返回的data字段，将其值作为以后请求的请求头的 **AUTHORIZATION-GM** 即可实现认证。


## 2、人事管理
###  2.1、员工入职
* 在同个部门下、同个职位是不允许再次入职的。
* 集团用户可以管理所有煤矿平台，煤矿平台只能管理自己所属的平台；


### 2.2、黑名单策略
* 员工最后一个职位离职后，才自动加入黑名单；
* 若手动添加黑名单，则必须保证员工当前不处于在职状态；
* 黑名单处显示的煤矿平台，以员工最后一个离职岗位所在的公司/平台为准。
* 若未设定黑名单解禁时间，则默认为半年（后续可考虑设置为配置项）

### 2.3、职位调动策略
* 必选调动时间
* 调动的职位信息从原始职位信息中复制（目前只能填写目标部门、职位等信息，那么类似工资之类的信息只能从原始职位获取了）
* 调动结果是删除选择的入职信息，新增一条结果信息；

### 2.4、员工模块
* 每天0点检测黑名单，解禁黑名单到期的用户，释放；

## 3、生产管理
### 3.1、掘进工作面
* 已添加日报的工作面无法删除（必须将日报删除了，才能删工作面）
* 已掘长度和剩余长度根据关联此工作面的日报数据自动更新

## 4、定时任务










# 时间轴
**2019-8-12 -> 2019-8-16**
* 完成绿映塘项目的架构搭建，确定了数据库架构。
* 开始大西南项目的项目。
* 完善项目的设置模块：煤矿平台创建后台接口。
* 完善项目的员工模块：员工入职相关管理后台接口

**下周计划**
* 完善职工模块。
* 开始安全管理、设备管理制作。

# 待处理
* 部门表路径会出现null。（已解决：不允许修改父节点ID，不会有问题。）
* 离职添加处未处理参保状态信息。
* 离职更新该怎么做，有待商榷。
* 需要考虑到Session的地方，都会通过大写的SESSION标注。
* 加入黑名单时，获取最新一条离职信息的操作是否有效；
* 修改掘进面信息的时候是否允许修改长度？
* 员工模块，定时任务：解除黑名单；
* 时间入参忽略时分秒问题；




