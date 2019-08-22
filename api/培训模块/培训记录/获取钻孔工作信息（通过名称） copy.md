# 简介

# 访问地址
produce-drill-work/getByName

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|name|[string]|是|名称|

## 请求示例
```json
{
	"name": "钻孔工作AB"
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
        "createTime": "2019-08-21 18:00:45",
        "updateTime": "2019-08-21 18:00:45",
        "remarks": null,
        "drillWorkName": "钻孔工作AB",
        "drillTime": "2019-09-20 00:00:00",
        "drillCategory": "GEOLOGY",
        "drillType": "GAS",
        "drillRockCharacter": "COAL_LAYER",
        "drillWorkDetail": [
            {
                "id": 2,
                "isDelete": false,
                "createTime": "2019-08-21 18:53:44",
                "updateTime": "2019-08-21 18:53:44",
                "remarks": null,
                "orderNumber": 2,
                "code": "AES-002",
                "totalLength": 3000.60,
                "dipAngle": 80.00,
                "predicateAppearCoal": 40.00,
                "predicateDisappearCoal": 20.10,
                "predicateCoalThickness": 100.00
            },
            {
                "id": 5,
                "isDelete": false,
                "createTime": "2019-08-21 18:53:44",
                "updateTime": "2019-08-21 18:53:44",
                "remarks": null,
                "orderNumber": 2,
                "code": "AES-002",
                "totalLength": 3000.60,
                "dipAngle": 80.00,
                "predicateAppearCoal": 40.00,
                "predicateDisappearCoal": 20.10,
                "predicateCoalThickness": 100.00
            }
        ]
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。