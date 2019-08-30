# 简介

# 访问地址
produce-driving-daily/get

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
        "createTime": "2019-08-24 12:46:53",
        "updateTime": "2019-08-24 12:46:53",
        "remarks": null,
        "drivingFace": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-24 12:25:52",
            "updateTime": "2019-08-24 12:25:52",
            "remarks": null,
            "drivingFaceName": "工作面A",
            "totalLength": 2800.50,
            "doneLength": 1800.40,
            "leftLength": 1000.10,
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
        "dailyTime": "2019-08-24",
        "totalPeopleNumber": 100,
        "totalDoneLength": 1600.00,
        "totalOutput": 1000.00,
        "drivingDailyDetail": [
            {
                "id": 3,
                "isDelete": false,
                "createTime": "2019-08-24 13:05:51",
                "updateTime": "2019-08-24 13:05:51",
                "remarks": null,
                "drivingDaily": {
                    "id": 1,
                    "isDelete": false,
                    "createTime": "2019-08-24 12:46:53",
                    "updateTime": "2019-08-24 12:46:53",
                    "remarks": null,
                    "drivingFace": {
                        "id": 1,
                        "isDelete": false,
                        "createTime": "2019-08-24 12:25:52",
                        "updateTime": "2019-08-24 12:25:52",
                        "remarks": null,
                        "drivingFaceName": "工作面A",
                        "totalLength": 2800.50,
                        "doneLength": 1800.40,
                        "leftLength": 1000.10,
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
                    "dailyTime": "2019-08-24",
                    "totalPeopleNumber": 100,
                    "totalDoneLength": 1600.00,
                    "totalOutput": 1000.00
                },
                "shifts": "NOON",
                "drivingTeam": {
                    "id": 1,
                    "isDelete": false,
                    "createTime": "2019-08-21 16:47:12",
                    "updateTime": "2019-08-21 16:47:12",
                    "remarks": "备注信息",
                    "departmentName": "信息分院1",
                    "code": "00",
                    "parentId": 0,
                    "responseUser": "jake",
                    "phone": "13535565497",
                    "departmentType": "CM_PLATFORM",
                    "path": "1-",
                    "children": null
                },
                "peopleNumber": 50,
                "doneLength": 1000.00,
                "output": 500.00,
                "workContent": "我们做了件大事。"
            },
            {
                "id": 4,
                "isDelete": false,
                "createTime": "2019-08-24 13:06:42",
                "updateTime": "2019-08-24 13:06:42",
                "remarks": null,
                "drivingDaily": {
                    "id": 1,
                    "isDelete": false,
                    "createTime": "2019-08-24 12:46:53",
                    "updateTime": "2019-08-24 12:46:53",
                    "remarks": null,
                    "drivingFace": {
                        "id": 1,
                        "isDelete": false,
                        "createTime": "2019-08-24 12:25:52",
                        "updateTime": "2019-08-24 12:25:52",
                        "remarks": null,
                        "drivingFaceName": "工作面A",
                        "totalLength": 2800.50,
                        "doneLength": 1800.40,
                        "leftLength": 1000.10,
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
                    "dailyTime": "2019-08-24",
                    "totalPeopleNumber": 100,
                    "totalDoneLength": 1600.00,
                    "totalOutput": 1000.00
                },
                "shifts": "NOON",
                "drivingTeam": {
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
                "doneLength": 600.00,
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