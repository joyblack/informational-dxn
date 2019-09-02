# 简介
返回当前节点的所有祖先节点的信息，同时包含自身的信息。

# 访问地址
device-category/getParentNodesJustIds

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
## 返回数据
**成功**
```json
{
    "state": true,
    "message": "操作成功",
    "detailMessage": "",
    "data": [
        1,
        2,
        3
    ],
    "code": 200
}
```

**失败**
```json
{
    "state": false,
    "message": "父部门信息不存在",
    "detailMessage": "父部门信息不存在",
    "data": null,
    "code": 10201
}
```

# 备注
错误码参见错误码对照表。