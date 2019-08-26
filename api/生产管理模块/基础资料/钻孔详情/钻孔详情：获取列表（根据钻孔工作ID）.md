# 简介
通过此接口，可以获取钻孔工作的钻孔详细信息列表。

# 访问地址
produce-drill-hole/getAllByDrillWorkId

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|钻孔工作的ID|

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
    "data": [
        {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-26 11:26:00",
            "updateTime": "2019-08-26 11:26:00",
            "remarks": null,
            "drillWork": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-26 11:25:24",
                "updateTime": "2019-08-26 11:25:24",
                "remarks": null,
                "drillWorkName": "钻孔工作B",
                "drillTime": "2019-09-22 00:00:00",
                "drillCategory": "GEOLOGY",
                "drillType": "GAS",
                "drillRockCharacter": "COAL_LAYER"
            },
            "orderNumber": 2,
            "code": "AES-002",
            "totalLength": 3000.50,
            "doneLength": 0.00,
            "leftLength": 3000.50,
            "dipAngle": 80.00,
            "predicateAppearCoal": 40.00,
            "realAppearCoal": null,
            "predicateDisappearCoal": 20.00,
            "realDisappearCoal": null,
            "predicateCoalThickness": 100.00,
            "realCoalThickness": null,
            "completeTime": null
        }
    ],
    "code": 200
}
```


# 备注
错误码参见错误码对照表。