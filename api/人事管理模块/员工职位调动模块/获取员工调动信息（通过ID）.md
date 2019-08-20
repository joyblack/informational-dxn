# 简介

# 访问地址
staff-shift/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|记录ID|

## 请求示例
```json
{
	"id": 3
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
        "id": 3,
        "isDelete": false,
        "createTime": "2019-08-20 15:59:41",
        "updateTime": "2019-08-20 15:59:41",
        "remarks": "调动信息",
        "staffPersonal": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-20 15:46:36",
            "updateTime": "2019-08-20 15:46:36",
            "remarks": null,
            "idNumber": "522401199401025931",
            "username": "赵小艺",
            "sex": "MAN",
            "nationality": "汉族",
            "birthDate": "2015-06-06 16:04:04",
            "education": "DOCTOR",
            "accountCharacter": "COUNTRYSIDE_CHARACTER",
            "phone": "13535565497",
            "homeAddress": "贵州省毕节市",
            "insured": "YES",
            "insuredTime": null,
            "nativePlace": null,
            "graduationCollege": null,
            "graduationTime": null,
            "profession": "电子商务",
            "idNumberPhoto": null,
            "oneInchPhoto": null
        },
        "shiftTime": "2019-08-20 00:00:01",
        "targetCompany": {
            "id": 2,
            "isDelete": false,
            "createTime": "2019-08-20 10:44:25",
            "updateTime": "2019-08-20 10:44:25",
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
        "targetDepartment": {
            "id": 6,
            "isDelete": false,
            "createTime": "2019-08-20 15:52:25",
            "updateTime": "2019-08-20 15:52:25",
            "remarks": "备注信息",
            "departmentName": "信息分院10",
            "code": "00",
            "parentId": 0,
            "responseUser": "jake",
            "phone": "13535565497",
            "departmentType": "CM_PLATFORM",
            "path": "6-",
            "children": null
        },
        "targetPosition": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-19 16:51:40",
            "updateTime": "2019-08-19 16:51:40",
            "remarks": "备注信息...",
            "positionName": "业务员1",
            "describes": "测试职位数据"
        }
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。