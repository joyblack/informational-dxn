# 简介

# 访问地址
safe-three-violation/get

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
        "createTime": "2019-08-25 12:57:39",
        "updateTime": "2019-08-25 12:57:39",
        "remarks": null,
        "inspectTime": "2019-08-23",
        "inspectCompany": {
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
        "inspectDepartment": {
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
        "inspectType": "ONE_THROUGH_AND_THREE_PREVENTION",
        "inspectPlace": "杨家湾_change",
        "problemDescribes": "水土流失有点严重_change",
        "deadTime": "2019-09-23",
        "tipDays": 100,
        "rectificationPeople": null,
        "rectificationStatus": "NO",
        "isOverTime": null
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。