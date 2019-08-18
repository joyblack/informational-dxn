# 简介

# 访问地址
staff-personal/get

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|id|[number]|是|个人信息的ID|

## 请求示例
```json
{
	"id": "1"
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
            "createTime": "2019-08-15 18:03:30",
            "updateTime": "2019-08-15 18:03:30",
            "status": 1,
            "remark": null,
            "staffName": "赵小艺",
            "idNumber": "522401199401025931",
            "sex": 0,
            "nationality": "汉族",
            "companyId": 1,
            "departmentId": 2,
            "positionId": 11,
            "entryTime": "2019-08-15 16:56:54",
            "phone": "13535565497",
            "archive": "000",
            "birthDate": "2019-08-15 16:56:54",
            "education": 0,
            "accountCharacter": 0,
            "homeAddress": "贵州省毕节市",
            "insured": 0,
            "insuredTime": "2019-08-15 16:56:54",
            "physicalExaminationHospital": "贵阳医科院",
            "physicalExaminationTime": "2019-08-15 16:56:54",
            "nativePlace": "贵州省毕节市",
            "graduationCollege": "贵州大学",
            "graduationTime": "2019-08-15 16:56:54",
            "profession": "电子商务",
            "remuneration": 10000,
            "introducer": "赵三",
            "reviewStatus": null
        },
        {
            "id": 3,
            "isDelete": false,
            "createTime": "2019-08-16 14:07:40",
            "updateTime": "2019-08-16 14:07:40",
            "status": 1,
            "remark": null,
            "staffName": "赵小艺",
            "idNumber": "522401199401025931",
            "sex": 0,
            "nationality": "汉族",
            "companyId": 1,
            "departmentId": 2,
            "positionId": 11,
            "entryTime": "2019-08-15 16:56:54",
            "phone": "13535565497",
            "archive": "000",
            "birthDate": "2019-08-15 16:56:54",
            "education": 0,
            "accountCharacter": 0,
            "homeAddress": "贵州省毕节市",
            "insured": 0,
            "insuredTime": "2019-08-15 16:56:54",
            "physicalExaminationHospital": "贵阳医科院",
            "physicalExaminationTime": "2019-08-15 16:56:54",
            "nativePlace": "贵州省毕节市",
            "graduationCollege": "贵州大学",
            "graduationTime": "2019-08-15 16:56:54",
            "profession": "电子商务",
            "remuneration": 10000,
            "introducer": "赵三",
            "reviewStatus": null
        }
    ],
    "code": 200
}
```

**失败**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": [],
    "code": 200
}
```

# 备注
错误码参见错误码对照表。