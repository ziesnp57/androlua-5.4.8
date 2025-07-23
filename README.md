更多更新细节，请自行体验
Lua 5.4.8 for Android


# 本项目更改内容
升级Lua为官方5.4.8版，未适配原版的switch case语句和module函数等，故对相应的.lua做了对应修改，以保证打包后项目应用能正常运行

原仓库链接https://github.com/nirenr/AndroLua_pro


关于
AndroLua是基于LuaJava开发的安卓平台轻量级脚本编程语言工具，既具有Lua简洁优雅的特质，又支持绝大部分安卓API，可以使你在手机上快速编写小型应用。

与标准Lua5.3.1的不同
打开了部分兼容选项，module，unpack，bit32
添加string.gfind函数，用于递归返回匹配位置
增加tointeger函数，强制将数值转为整数
修改tonumber支持转换Java对象

1，参考链接
关于lua的语法请参考以下网页。
Lua官网：
http://www.lua.org

LICENSE:
Androlua+ Copyright (C) 2015-2016 by Nirenr

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
