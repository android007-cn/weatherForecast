# 简单天气预报demo

## 功能介绍：

1. 支持定位当前所在城市（集成高德地图）
2. 支持加载多个城市天气
3. 支持显示实时天气


## 技术实现介绍：

1. coroutines+retrofit请求网络接口
2. 通过RecyclerView以列表形式展示数据
3. 结合SwipeRefreshLayout，支持下拉刷新
4. 加载多个城市天气： SwipeRefreshLayout内嵌ViewPager，SwipeRefreshLayout实现整体刷新，ViewPager用于展示各个城市天气。
通过VpSwipeRefreshLayout解决二者滑动冲突问题。
5. 接口调用时里包含日志拦截器
6. 支持koin
7. 支持ViewModel
8. 支持LiveData




