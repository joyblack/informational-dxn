# 简介
分页获取用户列表。

# 访问地址
system-role/getAllList

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|search|string|否|通用查询子串，不同的列表接口涵盖不同，例如用户列表接口，可能代表对真实姓名、登陆名的模糊查询。|

## 请求示例
```json
{
	"search": "xiaoyao"

}
```

# 返回结果
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": [
        {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-17 12:17:25",
            "updateTime": "2019-08-17 12:17:25",
            "remarks": null,
            "idNumber": "522401199401025931",
            "loginName": "xiaoyao_change",
            "phone": "13535565497",
            "password": "e10adc3949ba59abbe56e057f20f883e",
            "affirmPassword": null,
            "username": null,
            "userType": "CP_ADMIN",
            "status": null,
            "permissions": null,
            "department": {
                "id": 6,
                "isDelete": false,
                "createTime": "2019-08-17 11:19:01",
                "updateTime": "2019-08-17 11:19:01",
                "remarks": null,
                "departmentName": "测试部门6",
                "code": "01",
                "parentId": 0,
                "responseUser": "赵义",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "6-",
                "children": null
            }
        },
        {
            "id": 6,
            "isDelete": false,
            "createTime": "2019-08-17 11:57:00",
            "updateTime": "2019-08-17 11:57:00",
            "remarks": null,
            "idNumber": "522401199401025931",
            "loginName": "xiaoyao",
            "phone": "13535565497",
            "password": "e10adc3949ba59abbe56e057f20f883e",
            "affirmPassword": null,
            "username": null,
            "userType": "CM_COMMON",
            "status": null,
            "permissions": null,
            "department": {
                "id": 5,
                "isDelete": false,
                "createTime": "2019-08-17 11:19:01",
                "updateTime": "2019-08-17 11:19:01",
                "remarks": null,
                "departmentName": "测试部门5",
                "code": "01",
                "parentId": 0,
                "responseUser": "赵义",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "5-",
                "children": null
            }
        },
        {
            "id": 7,
            "isDelete": false,
            "createTime": "2019-08-17 11:58:45",
            "updateTime": "2019-08-17 11:58:45",
            "remarks": null,
            "idNumber": "522401199401025931",
            "loginName": "xiaoyao1",
            "phone": "13535565497",
            "password": "e10adc3949ba59abbe56e057f20f883e",
            "affirmPassword": null,
            "username": null,
            "userType": "CM_COMMON",
            "status": null,
            "permissions": null,
            "department": {
                "id": 5,
                "isDelete": false,
                "createTime": "2019-08-17 11:19:01",
                "updateTime": "2019-08-17 11:19:01",
                "remarks": null,
                "departmentName": "测试部门5",
                "code": "01",
                "parentId": 0,
                "responseUser": "赵义",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "5-",
                "children": null
            }
        }
    ],
    "code": 200
}
```

**失败**

若传递非法参数，则会出现查询异常。

```json
{
    "code": 406,
    "detailMessage": "\r\n### Error querying...",
    "state": false,
    "data": null,
    "message": "服务器繁忙，请稍后再试"
}
```

# 备注
错误码参见错误码对照表。