# 简介

# 访问地址
document-licence/get

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
        "createTime": "2019-08-29 21:21:36",
        "updateTime": "2019-08-29 21:21:36",
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
        "licenceType": "LICENCE_SAFE_PRODUCE",
        "licenceNumber": "TOFF-2209-4465-ZYLL",
        "expiryTime": "2020-08-08",
        "issueOffice": "贵州煤矿安全监察局",
        "issueTime": "2019-08-29",
        "tipDays": 360,
        "mineArea": 500.26,
        "mineScale": 5000.00,
        "mineElevation": 300.25
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。