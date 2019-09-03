# 简介

# 访问地址
document-borrow/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|记录的ID|

## 请求示例
```json
{
    "id": 3
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
        "id": 3,
        "isDelete": false,
        "createTime": "2019-08-29 18:12:02",
        "updateTime": "2019-08-29 18:12:02",
        "remarks": null,
        "belongCompany": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-26 20:07:29",
            "updateTime": "2019-08-26 20:07:29",
            "remarks": null,
            "departmentName": "大西南矿业集团",
            "code": "SUPER-COMPANY",
            "parentId": 0,
            "responseUser": null,
            "phone": null,
            "departmentType": "CP_GROUP",
            "path": "1-",
            "children": []
        },
        "documentName": "灰与幻想的格林姆迦尔",
        "borrowPeople": "赵义",
        "deadTime": "2018-09-10",
        "returnStatus": "RETURN_STATUS_NO",
        "isOverTime": "NO"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。