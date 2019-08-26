# 简介

# 访问地址
train-training/get

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
        "createTime": "2019-08-22 10:18:08",
        "updateTime": "2019-08-22 10:18:08",
        "remarks": "备注信息..._change",
        "trainingName": "海边培训_change",
        "trainingTime": "2019-08-23 00:00:00",
        "trainingUsername": "赵义_change",
        "department": {
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
        "company": {
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
        "trainingContent": "去海边游玩..._change",
        "trainingPhotos": [
            {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-22 10:21:13",
                "updateTime": "2019-08-22 10:21:13",
                "remarks": null,
                "storePath": "E:\\informational_dxn\\train_photo\\b983b6b7201645edabd457603046f9b6.jpg",
                "fileName": "b983b6b7201645edabd457603046f9b6.jpg",
                "fileSize": 30811
            }
        ],
        "trainingAttachments": [
            {
                "id": 1,
                "isDelete": false,
                "createTime": "2019-08-22 14:10:33",
                "updateTime": "2019-08-22 14:10:33",
                "remarks": null,
                "storePath": "E:\\informational_dxn\\train_attachment\\bac04dea0e4c429890890fab2af04ba4.jpg",
                "fileName": "bac04dea0e4c429890890fab2af04ba4.jpg",
                "fileSize": 56857
            },
            {
                "id": 2,
                "isDelete": false,
                "createTime": "2019-08-22 14:11:39",
                "updateTime": "2019-08-22 14:11:39",
                "remarks": null,
                "storePath": "E:\\informational_dxn\\train_attachment\\850328d2f27245ad92f0ac38cc03e001.pdf",
                "fileName": "850328d2f27245ad92f0ac38cc03e001.pdf",
                "fileSize": 4472872
            }
        ]
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。