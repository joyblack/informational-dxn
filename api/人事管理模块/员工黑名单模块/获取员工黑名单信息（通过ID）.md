# 简介

# 访问地址
staff-blacklist/get

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
    "data": {
        "id": 1,
        "isDelete": false,
        "createTime": "2019-08-20 11:08:31",
        "updateTime": "2019-08-20 11:08:31",
        "remarks": "半年时间反省一下。",
        "staffPersonal": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-20 10:50:45",
            "updateTime": "2019-08-20 10:50:45",
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
        "company": {
            "id": 1,
            "isDelete": false,
            "createTime": "2019-08-20 10:44:08",
            "updateTime": "2019-08-20 10:44:08",
            "remarks": "备注信息",
            "departmentName": "信息分院1_change",
            "code": "00",
            "parentId": 0,
            "responseUser": "jake",
            "phone": "13535565497",
            "departmentType": "CM_PLATFORM",
            "path": "1-",
            "children": null
        },
        "blacklistReasons": "欺负弱小",
        "overTime": "2020-01-01 00:00:00"
    },
    "code": 200
}
```

**失败**
```json
{
    "state": false,
    "message": "请求参数缺失或未空值",
    "detailMessage": "ID不能为空",
    "data": null,
    "code": 100000
}
```

# 备注
错误码参见错误码对照表。