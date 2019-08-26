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
        "createTime": "2019-08-26 09:53:41",
        "updateTime": "2019-08-26 09:53:41",
        "remarks": null,
        "violationCompany": {
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
        "violationTime": "2019-08-27",
        "threeViolationType": "COMMAND",
        "violationDepartment": {
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
        "violationPeople": "阿尔托利亚_CHANGE",
        "violationPlace": "吉尔吉斯斯坦_CHANGE",
        "dailyShift": "EVENING",
        "checkPeople": "赵义_CHANGE",
        "violationContent": "这样做是不对的_CHANGE。",
        "handlerSuggestion": "审核意见认为你们这样做好一些_CHANGE。"
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。