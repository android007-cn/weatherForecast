# Introduction
A simple weather forecast app.
# Features
1. show weather in current city
2. show hourly weather of today.
3. show at most seven days.

# Screenshot
![](https://raw.githubusercontent.com/android007-cn/browseBeauty/master/screenshots/screenshot1.gif)
# Key Points
1. Retrofit+okHttp: fetch data from network
2. Coroutines: make asynchronous operations
3. ViewPager2: swipe to change page
4. Glide: show images
5. Room: read and write on sqLite database
6. BottomNavigationView: show bottom navigation
7. Recyclerview: show list
# License
Copyright 2020 android007-cn

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


# 简单天气预报demo

## 功能介绍：

1. 支持定位当前所在城市（集成高德地图）
2. 支持加载多个城市天气
3. 支持显示实时天气，当前天气和之后6天天气。


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




