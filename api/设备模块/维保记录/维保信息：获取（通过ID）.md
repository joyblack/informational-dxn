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
    "id": 6
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
        "id": 6,
        "isDelete": false,
        "createTime": "2019-08-28 18:21:52",
        "updateTime": "2019-08-28 20:01:38",
        "remarks": null,
        "deviceName": "挖掘机A-2-2",
        "deviceCategory": {
            "id": 2,
            "isDelete": false,
            "createTime": "2019-08-28 14:27:18",
            "updateTime": "2019-08-28 14:29:26",
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
            "categoryName": "皮带运输机类_CHANGE",
            "parentId": 1,
            "path": "1-2-",
            "children": null,
            "parent": true
        },
        "deviceModel": "DTD-626_CHANGE",
        "manufacture": "贵阳机器工厂_CHANGE",
        "deviceCode": "DTD-626-001_CHANGE",
        "manufactureTime": "2019-08-10",
        "usePlace": "遵义煤矿_CHANGE",
        "cmCode": "ZYMK-01_CHANGE",
        "productionCode": "GYMC-01_CHANGE",
        "beforeMaintainTime": "2019-08-29",
        "maintainIntervalTime": 31,
        "tipDays": 4,
        "deviceStatus": "DEVICE_STATUS_USING",
        "technicalData": "技术文档_CHANGE",
        "maintainNumber": 0,
        "nextMaintainTime": "2019-09-29",
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
    "code": 200
}
```

# 备注
错误码参见错误码对照表。