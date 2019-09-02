# 简介

# 访问地址
produce-back-mining-face/get

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
        "createTime": "2019-08-21 11:16:06",
        "updateTime": "2019-08-21 11:16:06",
        "remarks": null,
        "backMiningFaceName": "回采工作面A",
        "slopeLength": 2800.50,
        "returnAirChute": 100.50,
        "transportChute": 100.50,
        "doneLength": 200.40,
        "trendLength": 50.40,
        "startTime": "2019-09-10 00:00:00",
        "coalSeamThickness": 4000.00,
        "coalSeamDipAngle": 20.00,
        "miningHigh": 20.00,
        "ventilationMode": "U",
        "backMiningMode": "INTEGRATION_MACHINE",
        "recoverReserves": null
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。