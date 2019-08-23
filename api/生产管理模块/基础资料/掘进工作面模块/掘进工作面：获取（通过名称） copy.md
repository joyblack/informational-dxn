# 简介

# 访问地址
produce-driving-face/getByName

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|name|[string]|是|工作面的名称|

## 请求示例
```json
{
	"name": "工作面A"
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
        "createTime": "2019-08-21 10:08:15",
        "updateTime": "2019-08-21 10:08:15",
        "remarks": null,
        "drivingFaceName": "工作面A",
        "totalLength": 2800.50,
        "doneLength": 200.40,
        "leftLength": 2600.10,
        "startTime": "2019-09-10 00:00:00",
        "drivingHigh": 20.00,
        "drivingSlope": 35.00,
        "crossSection": 15.00,
        "crossSectionType": "HALF_ROUND",
        "coalSeamThickness": 2000.00,
        "drivingTechnologyType": "BLASTING",
        "supportMethod": "ANCHOR_SPRAY",
        "rockCharacter": "HALF_COAL"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。