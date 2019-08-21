# 简单天气预报demo
1. coroutines+retrofit请求网络接口
2. 通过RecyclerView以列表形式展示数据
3. 结合SwipeRefreshLayout，支持下拉刷新
4. SwipeRefreshLayout内嵌ViewPager，SwipeRefreshLayout实现整体刷新，ViewPager用于展示各个城市天气。
通过VpSwipeRefreshLayout解决二者滑动冲突问题。

支持koin
支持ViewModel
支持LiveData
支持定位当前所在城市（集成高德地图）
支持加载多个城市天气
接口调用时里包含日志拦截器