# 简介

# 访问地址
produce-back-mining-daily/get

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
        "createTime": "2019-08-25 09:34:53",
        "updateTime": "2019-08-25 09:34:53",
        "remarks": null,
        "backMiningFace": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-25 09:32:43",
            "updateTime": "2019-08-25 09:32:43",
            "remarks": null,
            "backMiningFaceName": "回采工作面B_change",
            "slopeLength": 2800.51,
            "returnAirChute": 100.51,
            "transportChute": 100.51,
            "doneLength": 700.41,
            "trendLength": 50.41,
            "startTime": "2019-09-20 00:00:00",
            "coalSeamThickness": 4000.10,
            "coalSeamDipAngle": 21.00,
            "miningHigh": 20.10,
            "ventilationMode": "Z",
            "backMiningMode": "INTEGRATION_MACHINE",
            "recoverReserves": 42.00
        },
        "dailyTime": "2019-08-24",
        "totalPeopleNumber": 50,
        "totalDoneLength": 500.00,
        "totalOutput": 500.00,
        "backMiningDailyDetail": [
            {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-25 09:37:48",
                "updateTime": "2019-08-25 09:37:48",
                "remarks": null,
                "backMiningDaily": {
                    "id": 1,
                    "isDelete": false,
                    "createTime": "2019-08-25 09:34:53",
                    "updateTime": "2019-08-25 09:34:53",
                    "remarks": null,
                    "backMiningFace": {
                        "id": 1,
                        "isDelete": false,
                        "createTime": "2019-08-25 09:32:43",
                        "updateTime": "2019-08-25 09:32:43",
                        "remarks": null,
                        "backMiningFaceName": "回采工作面B_change",
                        "slopeLength": 2800.51,
                        "returnAirChute": 100.51,
                        "transportChute": 100.51,
                        "doneLength": 700.41,
                        "trendLength": 50.41,
                        "startTime": "2019-09-20 00:00:00",
                        "coalSeamThickness": 4000.10,
                        "coalSeamDipAngle": 21.00,
                        "miningHigh": 20.10,
                        "ventilationMode": "Z",
                        "backMiningMode": "INTEGRATION_MACHINE",
                        "recoverReserves": 42.00
                    },
                    "dailyTime": "2019-08-24",
                    "totalPeopleNumber": 50,
                    "totalDoneLength": 500.00,
                    "totalOutput": 500.00
                },
                "shifts": "NOON",
                "team": {
                    "id": 2,
                    "isDelete": false,
                    "createTime": "2019-08-21 16:47:15",
                    "updateTime": "2019-08-21 16:47:15",
                    "remarks": "备注信息",
                    "departmentName": "信息分院2",
                    "code": "00",
                    "parentId": 0,
                    "responseUser": "jake",
                    "phone": "13535565497",
                    "departmentType": "CM_PLATFORM",
                    "path": "2-",
                    "children": null
                },
                "peopleNumber": 50,
                "doneLength": 500.00,
                "output": 500.00,
                "workContent": "我们做了件大事。"
            }
        ]
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。