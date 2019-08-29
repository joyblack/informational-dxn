# 简介

# 访问地址
device-maintain/get

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
    "id": 1
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
        "id": 1,
        "isDelete": false,
        "createTime": "2019-08-29 08:43:48",
        "updateTime": "2019-08-29 08:43:48",
        "remarks": null,
        "belongCompany": null,
        "deviceInfo": {
            "id": 7,
            "isDelete": false,
            "createTime": "2019-08-28 18:21:52",
            "updateTime": "2019-08-29 08:57:22",
            "remarks": null,
            "deviceName": "挖掘机A-2",
            "deviceCategory": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-28 14:26:58",
                "updateTime": "2019-08-28 14:27:18",
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
                "categoryName": "综采类",
                "parentId": 0,
                "path": "1-",
                "children": null,
                "parent": true
            },
            "deviceModel": "DTD-626",
            "manufacture": "贵阳机器工厂",
            "deviceCode": "DTD-626-001",
            "manufactureTime": "2019-08-09",
            "usePlace": "遵义煤矿",
            "cmCode": "ZYMK-01",
            "productionCode": "GYMC-01",
            "beforeMaintainTime": "2019-03-05",
            "maintainIntervalTime": 30,
            "tipDays": 3,
            "deviceStatus": "DEVICE_STATUS_USING",
            "technicalData": null,
            "maintainNumber": 0,
            "nextMaintainTime": "2019-04-04",
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
            }
        },
        "maintainTime": "2019-01-01",
        "maintainStatus": "COMPLETE",
        "maintainType": "MAINTAIN_DAILY",
        "maintainPeople": "赵义_CHANGE",
        "maintainDetail": "重新擦拭了一番，加了点机油_CHANGE"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。