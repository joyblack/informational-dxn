# 简介
获取资源信息。

# 访问地址
system-resource/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|记录ID|

## 请求示例
```json
{
	"id": 2
}
```

# 返回结果
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": {
        "id": 2,
        "isDelete": false,
        "createTime": "2019-08-30 11:14:19",
        "updateTime": "2019-08-30 11:14:19",
        "remarks": null,
        "resourceUrl": "",
        "resourceType": "RESOURCE_TYPE_MENU",
        "resourceName": "staff-entry",
        "resourceAliasName": "入职管理",
        "parentId": 1,
        "path": "1-2-",
        "children": null
    },
    "code": 200
}
```


# 备注
错误码参见错误码对照表。