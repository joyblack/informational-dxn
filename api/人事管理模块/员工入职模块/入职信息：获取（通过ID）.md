# 简介

# 访问地址
staff-entry/get

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
            "createTime": "2019-08-21 10:05:26",
            "updateTime": "2019-08-21 10:05:26",
            "remarks": null,
            "company": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-21 10:04:37",
                "updateTime": "2019-08-21 10:04:37",
                "remarks": "备注信息",
                "departmentName": "信息分院10",
                "code": "00",
                "parentId": 0,
                "responseUser": "jake",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "1-",
                "children": null
            },
            "department": {
                "id": 5,
                "isDelete": false,
                "createTime": "2019-08-21 10:04:53",
                "updateTime": "2019-08-21 10:04:53",
                "remarks": "备注信息",
                "departmentName": "信息分院4",
                "code": "00",
                "parentId": 0,
                "responseUser": "jake",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "5-",
                "children": null
            },
            "position": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-21 10:05:10",
                "updateTime": "2019-08-21 10:05:10",
                "remarks": "有前途吗？",
                "positionName": "研发工程师",
                "describes": "是一个吃青春饭的工作。"
            },
            "entryTime": "2015-06-06 00:00:00",
            "physicalExaminationHospital": "贵阳医科院",
            "physicalExaminationTime": "2015-06-06 00:00:00",
            "remuneration": 10000,
            "remunerationL": null,
            "staffStatus": "INCUMBENCY",
            "bankNumber": "6212263602040833126",
            "openBank": "广州银行支行",
            "staffPersonal": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-21 10:05:37",
                "updateTime": "2019-08-21 10:05:37",
                "remarks": null,
                "idNumber": "522401199401025931",
                "username": "赵小艺",
                "sex": "MAN",
                "nationality": "汉族",
                "birthDate": "2015-06-06 00:00:00",
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
            "reviewStatus": "PASS",
            "reviewReasons": null,
            "reviewRemarks": null,
            "reviewTime": null,
            "reviewUser": null
        },
        {
            "id": 2,
            "isDelete": false,
            "createTime": "2019-08-21 10:05:37",
            "updateTime": "2019-08-21 10:05:37",
            "remarks": null,
            "company": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-21 10:04:37",
                "updateTime": "2019-08-21 10:04:37",
                "remarks": "备注信息",
                "departmentName": "信息分院10",
                "code": "00",
                "parentId": 0,
                "responseUser": "jake",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "1-",
                "children": null
            },
            "department": {
                "id": 2,
                "isDelete": false,
                "createTime": "2019-08-21 10:04:44",
                "updateTime": "2019-08-21 10:04:44",
                "remarks": "备注信息",
                "departmentName": "信息分院1",
                "code": "00",
                "parentId": 0,
                "responseUser": "jake",
                "phone": "13535565497",
                "departmentType": "CM_PLATFORM",
                "path": "2-",
                "children": null
            },
            "position": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-21 10:05:10",
                "updateTime": "2019-08-21 10:05:10",
                "remarks": "有前途吗？",
                "positionName": "研发工程师",
                "describes": "是一个吃青春饭的工作。"
            },
            "entryTime": "2015-06-06 00:00:00",
            "physicalExaminationHospital": "贵阳医科院",
            "physicalExaminationTime": "2015-06-06 00:00:00",
            "remuneration": 10000,
            "remunerationL": null,
            "staffStatus": "INCUMBENCY",
            "bankNumber": "6212263602040833126",
            "openBank": "广州银行支行",
            "staffPersonal": {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-21 10:05:37",
                "updateTime": "2019-08-21 10:05:37",
                "remarks": null,
                "idNumber": "522401199401025931",
                "username": "赵小艺",
                "sex": "MAN",
                "nationality": "汉族",
                "birthDate": "2015-06-06 00:00:00",
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
            "reviewStatus": "WAIT",
            "reviewReasons": "已在信息分院4入职，职务为研发工程师\r\n",
            "reviewRemarks": null,
            "reviewTime": null,
            "reviewUser": null
        }
    ],
    "code": 200
}
```

**失败**
```json
{
    "state": false,
    "message": "员工信息不存在",
    "detailMessage": "员工信息不存在",
    "data": null,
    "code": 103000
}
```

# 备注
错误码参见错误码对照表。