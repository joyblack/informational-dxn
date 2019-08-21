# 简介

# 访问地址
produce-driving-face/get

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
        "createTime": "2019-08-21 10:14:28",
        "updateTime": "2019-08-21 10:14:28",
        "remarks": null,
        "drivingFaceName": "工作面B_change",
        "totalLength": 2800.51,
        "doneLength": 200.41,
        "leftLength": 2600.10,
        "startTime": "2019-09-11 00:00:00",
        "drivingHigh": 20.10,
        "drivingSlope": 35.10,
        "crossSection": 15.10,
        "crossSectionType": "TRAPEZIUM",
        "coalSeamThickness": 2000.10,
        "drivingTechnologyType": "FULLY",
        "supportMethod": "COMBINED_SUPPORT",
        "rockCharacter": "ALL_COAL"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。