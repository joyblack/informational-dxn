# 简介

# 访问地址
produce-cm-statistic/getStatisticData

# 请求参数

## 请求方式
POST

## 请求格式
JSON

## 请求数据
|参数名|类型|必填|说明|
|-|-|-|-|
|time|[date]|是|需要进行统计的日期，默认情况下请提交当天时间|

## 请求示例
```json
{
	"time": "2019-08-24"
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
        "id": null,
        "isDelete": false,
        "createTime": "2019-08-27 15:13:53",
        "updateTime": "2019-08-27 15:13:53",
        "remarks": null,
        "belongCompany": null,
        "dailyTime": null,
        "drivingStatistic": [
            {
                "workName": "工作面A",
                "morningLength": 1000.00,
                "noonLength": 0,
                "eveningLength": 0,
                "shiftTotalLength": 1000.00,
                "morningPeople": 50,
                "noonPeople": 0,
                "eveningPeople": 0,
                "shiftTotalPeople": 50,
                "monthLength": 1000.00,
                "dayOutput": 500.00,
                "monthOutput": 500.00
            },
            {
                "workName": "合计",
                "morningLength": 1000.00,
                "noonLength": 0,
                "eveningLength": 0,
                "shiftTotalLength": 1000.00,
                "morningPeople": 50,
                "noonPeople": 0,
                "eveningPeople": 0,
                "shiftTotalPeople": 50,
                "monthLength": 1000.00,
                "dayOutput": 0,
                "monthOutput": 500.00
            }
        ],
        "backMiningStatistic": [
            {
                "workName": "合计",
                "morningLength": 0,
                "noonLength": 0,
                "eveningLength": 0,
                "shiftTotalLength": 0,
                "morningPeople": 0,
                "noonPeople": 0,
                "eveningPeople": 0,
                "shiftTotalPeople": 0,
                "monthLength": 0,
                "dayOutput": 0,
                "monthOutput": 0
            }
        ],
        "drillStatistic": [
            {
                "workName": "合计",
                "morningLength": 0,
                "noonLength": 0,
                "eveningLength": 0,
                "shiftTotalLength": 0,
                "morningPeople": 0,
                "noonPeople": 0,
                "eveningPeople": 0,
                "shiftTotalPeople": 0,
                "monthLength": 0,
                "dayOutput": 0,
                "monthOutput": 0
            }
        ]
    },
    "code": 200
}
```

# 备注
错误码参见错误码对照表。