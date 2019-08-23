# 简介

# 访问地址
produce-drill-daily-detail/get

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
        "createTime": "2019-08-23 16:05:35",
        "updateTime": "2019-08-23 16:05:35",
        "remarks": null,
        "orderNumber": 1,
        "doneLength": 300.00,
        "drillHole": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-23 14:31:48",
            "updateTime": "2019-08-23 14:31:48",
            "remarks": null,
            "orderNumber": 2,
            "code": "AES-002",
            "totalLength": 3000.50,
            "doneLength": 300.00,
            "leftLength": 2700.50,
            "dipAngle": 80.00,
            "predicateAppearCoal": 40.00,
            "predicateDisappearCoal": 20.00,
            "predicateCoalThickness": 100.00,
            "drillWork": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-23 14:31:08",
                "updateTime": "2019-08-23 14:31:08",
                "remarks": null,
                "drillWorkName": "钻孔工作A",
                "drillTime": "2019-09-22 00:00:00",
                "drillCategory": "GEOLOGY",
                "drillType": "GAS",
                "drillRockCharacter": "COAL_LAYER"
            }
        },
        "drillDaily": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-23 14:49:23",
            "updateTime": "2019-08-23 14:49:23",
            "remarks": null,
            "drillWork": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-23 14:31:08",
                "updateTime": "2019-08-23 14:31:08",
                "remarks": null,
                "drillWorkName": "钻孔工作A",
                "drillTime": "2019-09-22 00:00:00",
                "drillCategory": "GEOLOGY",
                "drillType": "GAS",
                "drillRockCharacter": "COAL_LAYER"
            },
            "dailyTime": "2019-08-22",
            "shifts": "MORNING",
            "drillTeam": {
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
            "peopleNumber": 20,
            "doneTotalLength": 1300.00
        }
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。