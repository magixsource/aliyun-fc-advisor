# aliyun-fc-advisor
aliyun-fc-advisor

# how to run
1. add maven phase `org.javalite:activejdbc-instrumentation:2.0:instrument`
2. run instrument before build
3. run as serverless or springboot as you like

# Advisor API
## 通用搜索
### 搜索联想词

- 接口名：/api/v1/health/search/suggest
- 请求方法： GET
- 入参： 
	- q | String | 查询文本
- 响应： 

		  {
		    "message":"查询成功",// 响应文本消息
		    "payload":{ // 响应消息主体
		       list:[
		        {"title":"苹果", // 名称
				"type":"i",     // 类型：f:food,d:disease,i:ingredient
				"id":1111},     // 主键值
				{"title":"感冒","type":"d","id":2222},
                ...
		       ]
		    }
		  }
	     

### 全域搜索
- 接口名： /api/v1/health/search
- 请求方法：GET
- 入参： 
	- q | String | 查询文本
	- type(Optional) | String{f,d} | 类型
	- id(Optinal) | Number | 主键
	- pageSize(Optional) | Number | 分页数,默认10
	- page(Optional) | Number | 页码,默认1
- 请求示例
	- /api/v1/health/search?q=感冒&type=d&page=2
- 响应：

		{
			"message":"查询成功",
			"payload":{
				"pagination":{
					"page":1,
					"pageSize":10,
					"total":102
				},
				list:[
					{
						"name":"苹果",
						"type":"i",
						"id":1111,
						"imgs":[
							"http://example.com/cdn/f/apple.png?h=60&w=60",
							"http://example.com/cdn/f/apple2.png?h=60&w=60",
							"http://example.com/cdn/f/apple3.png?h=60&w=60"
						],
						"remark":"苹果是一种营养丰富的北方水果,在我国北方广袤的平原上，生长着广阔的苹果园..."
					},
					{
						"name":"感冒"
						"type":"d",
						"id":2222,
						"imgs":[
							"http://example.com/cdn/d/gm.png?h=60&w=60",
							"http://example.com/cdn/d/gm2.png?h=60&w=60",
							"http://example.com/cdn/d/gm3.png?h=60&w=60",
						],
						"department":"内科",
						"remark":"感冒是一种常见病，感冒分风热感冒和风寒感冒..."
					},
					...
				]
			}
		}

### 健康建议
- 接口名：/api/v1/health/advisor
- 请求方法：GET
- 入参：
	- id | Number | 主键
	- type | String | 类型
	- filters | String | 查询过滤(more,less,forbidden)
	- pageSize | Number | 页大小
	- page | Number | 页码
- 请求示例： /api/v1/health/advisor?id=1111&type=f&filters=more&page=1
- 响应

		{
			"message":"查询成功",
			"payload":{
				"advises"{
					"pagination":{
						"page":1,
						"pageSize":10,
						"total":102
					},
					"list":[
						{
							"name":"感冒",
							"id":2222,
							"type":"d"
							"imgs":[
								"http://example.com/cdn/d/img.png"
							]
						},
						{
							"name":"高血压",
							"id":2333,
							"type":"d"
							"imgs":[
								"http://example.com/cdn/d/img.png"
							]
						}
						...
					]
					
				}
			}
		}

### 食品详情
- 接口名：/api/v1/health/ingredient
- 请求方法：GET
- 入参：
	- id | Number | 主键
- 请求示例： /api/v1/health/ingredient?id=1111
- 响应
	
		{
			"message":"查询成功",
			"payload":{
				"ingredient":{
					"name":"苹果",
					"enName":"apple",
					"kind":"水果",
					"origin":"山西"    // 原产地
					"ediblePart":65.4,
					"energy":12.2,
					"water":10.7,
					"protein":4.5,
					"fat":0.3,
					"dietaryFiber":5.5,
					"carbohydrate":3.2,
					"retinolEquivalent":0.1,
					"vb1":0.2,
					"vb2":0.1,
					"vpp":0.3,
					"vitaminA":0.4,
					"vitaminB1":2.25,
					"vitaminB2":0.21,
					"vitaminB6":6.1,
					"vitaminB12":9.2,
					"vitaminC":12.8,
					"vitaminD":4.1,
					"vitaminE":2.5,
					"vitaminK":2.2,
					"vitaminB1":1.2,
					"natrium":0.01,
					"calcium":0.01,
					"ferrum":0.01,
					"vc":0.3,
					"cholestenone":0.9,
					"phosphorus":3.3,
					"potassium":4.2,
					"magnesium":1.8,
					"zinc":7.2
				},
				imgs:[
					"http://example.com/i/apple1.png?h=50&w=50",
					"http://example.com/i/apple2.png?h=50&w=50",
					"http://example.com/i/apple3.png?h=50&w=50"
				]
				
			}
		}

### 疾病详情
- 接口名：/api/v1/health/disease
- 请求方法：GET
- 入参：
	- id | Number | 主键
- 请求示例： /api/v1/health/disease?id=2222
- 响应


		{
			"message":"查询成功",
			"payload":{
				"disease":{
					"name":"感冒",
					"enName":"cough",
					"department":"内科",
					"summary":"感冒简介",
					"treatment":"治疗方案说明"
				},
				imgs:[
					"http://example.com/i/cough1.png?h=50&w=50",
					"http://example.com/i/cough2.png?h=50&w=50",
					"http://example.com/i/cough3.png?h=50&w=50"
				]
			}
		}

### 菜谱详情
- 接口名：/api/v1/health/food
- 请求方法：GET
- 入参：
	- id | Number | 主键
- 请求示例： /api/v1/health/food?id=3
- 响应


		{
			"message":"查询成功",
			"payload":{
				{
                    "content":"<p>黄瓜切成片,小葱切段,西红柿切成适量大小。</p><p><img class='conimg' src='https://s1.st.meishij.net/rs/87/39/13384837/n13384837_154529331922255.jpg' alt=''></p> <p>. 将冷面汤充分搅拌后,放入冰箱中冷藏</p><p><img class='conimg' src='https://s1.st.meishij.net/rs/87/39/13384837/n13384837_154529335423696.jpg' alt=''></p> <p>在黄瓜和西红柿里倒入汤汁,搅拌均匀后,在食用前撒上葱末即可。</p><p><img class='conimg' src='https://s1.st.meishij.net/rs/87/39/13384837/n13384837_154529336621539.jpg' alt=''></p>",
                    "create_time":1546741368000,
                    "id":3,
                    "name":"冷面的制作方法",
                    "update_time":1546741368000
                }
			}
		}

### 菜谱构成详情
- 接口名：/api/v1/health/foodMaterial
- 请求方法：GET
- 入参：
	- id | Number | 主键
- 请求示例： /api/v1/health/foodMaterial?id=3
- 响应


		{
			"message":"查询成功",
			"payload":{
				"list":[
                        {
                            "kind":31,
                            "origin":"",
                            "name":"小葱",
                            "id":891
                        },
                        {
                            "kind":32,
                            "origin":"",
                            "name":"黄瓜（胡瓜）",
                            "id":910
                        },
                        {
                            "kind":83,
                            "origin":"",
                            "name":"白糖（绵白糖）",
                            "id":1151
                        },
                        {
                            "kind":82,
                            "origin":"",
                            "name":"醋",
                            "id":1180
                        },
                        {
                            "kind":82,
                            "origin":"",
                            "name":"盐",
                            "id":1208
                        },
                        {
                            "kind":0,
                            "name":"西红柿",
                            "id":1309
                        },
                        {
                            "kind":0,
                            "name":"冷水",
                            "id":1310
                        }
                    ]
			}
		}

### 菜谱查询
- 接口名：/api/v1/health/foodQuery
- 请求方法：GET
- 入参：
	- q | String | 名称
- 请求示例： /api/v1/health/foodQuery?q=猪肉
- 响应


		{
			"message":"查询成功",
			"payload":{
				"list":[
                            {
                                "content":"",
                                "create_time":1546753399000,
                                "id":9162,
                                "name":"猪肉韭菜饺子",
                                "summary":"半肥猪肉150克，韭菜100克，饺子皮20张，盐，胡椒粉，姜末，花生油少许，芝麻油几滴，陈醋，辣椒酱，生抽，酱油",
                                "update_time":1546753399000
                            },
                            {
                                "content":"<span>1</span><p>在一个大碗中混合绞肉/ 蒜头/ 辣椒/ 芫荽/ 面包粉/ 一半姜泥/ 五香粉/ 一半洋葱末/ 蛋/ 少许盐和黑胡椒，混合均匀后放在冰箱里备用。我提前三个小时完成让它入味些。</p>",
                                "create_time":1546753947000,
                                "id":10523,
                                "name":"猪肉酿榅桲&石榴果实和香菜",
                                "summary":"这道菜以水果入菜挺特殊，只是石榴籽有点麻烦，如果可以买到籽较小且果实较艳红的石榴品种会更加分。买不到榅桲，用甜椒或圆胖型茄子(非长条形)代替也都很不错。",
                                "update_time":1546753947000
                            }
                        ],
                        "page":1,
                        "pageSize":10,
                        "total":18
			}
		}


### 菜谱标签查询
- 接口名：/api/v1/health/foodTag
- 请求方法：GET
- 入参：
	- id | Number | id
- 请求示例： /api/v1/health/foodTag?id=3
- 响应

		{
			"message":"查询成功",
			"payload":{
			    "list":[
                        {
                            "id":1,
                            "title":"热菜"
                        },
                        {
                            "id":2,
                            "title":"家常菜"
                        },
                        {
                            "id":3,
                            "title":"凉菜"
                        }
                    ]
			}
		}
		
		
### 原料菜谱查询
- 接口名：/api/v1/health/ingredientFoodQuery
- 请求方法：GET
- 入参：
	- id | Number | id
- 请求示例： /api/v1/health/ingredientFoodQuery?id=3
- 响应

		{
			"message":"查询成功",
			"payload":{
			    "list":[
                        {
                            "content":"xxxxxxxxxxxxxxxxxxxx",
                            "name":"热菜",
                            "id":1,
                            "summary":"xxxxx"
                        },
                        {
                            "id":2,
                            "content":"xxxxxxxxxxxxxxxxxxxx",
                            "name":"热菜",
                            "summary":"xxxxx"
                        }
                    ],
                    "page":1,
                    "pageSize":10,
                    "total":8
			}
		}
		
		
### 标签菜谱查询
- 接口名：/api/v1/health/tagFoodQuery
- 请求方法：GET
- 入参：
	- q | String | 名称
- 请求示例： /api/v1/health/tagFoodQuery?q=家常菜
- 响应

		{
			"message":"查询成功",
			"payload":{
			    "list":[
                        {
                            "content":"xxxxxxxxxxxxxxxxxxxx",
                            "name":"热菜",
                            "id":1,
                            "summary":"xxxxx"
                        },
                        {
                            "id":2,
                            "content":"xxxxxxxxxxxxxxxxxxxx",
                            "name":"热菜",
                            "summary":"xxxxx"
                        }
                    ],
                    "page":1,
                    "pageSize":10,
                    "total":8
			}
		}
