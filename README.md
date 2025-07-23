更多更新细节，请自行体验

# 更新九
修复newActivity传递的Intent参数不可获取问题，现在你将可以用如下的方式在不同Activity之间传递参数

       --import "java.lang.Object" 你也可以是任何类
       activity.newActivity( activity_path, Object{参数1, 参数2, 参数N} ) --newActivity还有其他参数传递方式，具体的看LuaActivity类
       在新启动的Activity里
       activity.getArg(0) --下标从0开始，如果你传的参数长度大于等于1的话

# 更新八

自动的实时的语法查错功能，报错内容包含行号，报错在当前行的位置，报错附近的字符等，报错行将设置为高亮状态

更新了一个比原来好一点的UI，即使它还十分粗糙。

       LuaUtil类中增加了访问网络时设置参数更为方便的静态方法makeRequest
       makeRequest(String url)
       makeRequest(String url,Map<Object,Object> headers)
       makeRequest(String url,String data)
       makeRequest(String url,Map<Object,Object> headers,String data)


你可以如下使用

       require "import"
       import "com.androlua.LuaUtil"
       local content = LuaUtil.makeRequest("https://github.com/JealousCat/AndroLua_Pro_Plus")
       print(content)

--返回类型为Map，可以使用luajava.astable转为lua table，对于key或value是Map的，则多次使用该函数即可

# 更新七
       //整除，返回值为整数

       != 不等于，与~=兼容

       !逻辑否，与not兼容

       &&逻辑与，与and兼容

       &按位与

       ?逻辑与，与and兼容

       @逻辑或，与or兼容

       ||逻辑或，与or兼容

       |按位或

       exp ? exp1 @ exp2  三目运算，与exp and exp1 or exp2 兼容



# 更新六
使用print将可以直接打印table的具体内容

优化完全的支持中文变量名

更新了string.split函数，在导入import方法时可用

       local 啊 = "sac,cve,avs,av,s,av,s,av,s,avs,"
       list = 啊:split(",")
       print(list)

# 更新五
支持 loadlayout加载布局时，如果background属性是一个编译后的xml矢量图，它将会尝试加载

layouthelper 布局助手内也可以预览它

 
# 更新四
更新并修复支持Java数组的创建

       local a = int{1,2,3}
       a = Object{2,1,"few",true}

在使用这些时请确保你的 import是导入的



# 更新三
采用了新的代码高亮方式，包括关键字、短字符串、长字符串、数字、导入的类（包）名、表的KEY、函数名等


# 更新二
恢复支持数组创建，它的执行将比使用表进行创建快一些

数组创建：

       local a = [1,2,445,"32",true]


恢复对switch case 语句的支持，并优化支持连续的case句子

       switch 43
         case 3,7,85,3
         case 4  
         case 5
         print("2222")
        default
        print(1234)
       end


注：switch case的判断值，你可以是任意有效的值；

       switch 与 end配对，在switch或者case的表达式后如果你加了do或then或{或:并不会影响你的句子执行
       case的表达式后面可以连续的用  【,】  分割多个值，它和你写多个case分支不写block时执行效果一致
       default语句不是必要的语句
       

# 更新一
Lua 5.4.8 for Android


# 本项目更改内容
升级Lua为官方5.4.8版，未适配原版的switch case语句和module函数等，故对相应的.lua做了对应修改，以保证打包后项目应用能正常运行

原仓库链接https://github.com/nirenr/AndroLua_pro


关于
AndroLua是基于LuaJava开发的安卓平台轻量级脚本编程语言工具，既具有Lua简洁优雅的特质，又支持绝大部分安卓API，可以使你在手机上快速编写小型应用。

本程序使用了以下开源项目部分代码

bson,crypt,md5
https://github.com/cloudwu/skynet

cjson
https://sourceforge.net/projects/cjson/

zlib
https://github.com/brimworks/lua-zlib

xml
https://github.com/chukong/quick-cocos2d-x

luv
https://github.com/luvit/luv
https://github.com/clibs/uv

zip
https://github.com/brimworks/lua-zip
https://github.com/julienr/libzip-android

luagl
http://luagl.sourceforge.net/

luasocket
https://github.com/diegonehab/luasocket

sensor
https://github.com/ddlee/AndroidLuaActivity

canvas
由落叶似秋开发

jni
由nirenr开发


与标准Lua5.3.1的不同
打开了部分兼容选项，module，unpack，bit32
添加string.gfind函数，用于递归返回匹配位置
增加tointeger函数，强制将数值转为整数
修改tonumber支持转换Java对象

1，参考链接
关于lua的语法和Android API请参考以下网页。
Lua官网：
http://www.lua.org
Android 中文API：
http://android.toolib.net/reference/packages.html
[绘制.lua](../%E7%BB%98%E5%88%B6.lua)
2，导入模块
在每个脚本程序的开头应该写上 require "import" 以导入import模块，简化写代码的难度。目前程序内置bson,canvas,cjson,crypt,ftp,gl,http,import,md5,smtp,socket,sensor,xml,zip,zlib。

3，导入包或类
可以导入包或者类

       import "android.widget.*"
       import "android.widget.Button"
       
导入内部类

       import "android.view.View_*"
       
或

       import "android.view.View_OnClickListener"
       
或

       View.OnClickListene
       
包名和类名必须用引号包围。

4，创建布局与组件

       layout=LinearLayout(activity)
       activity.setContentView(layout)
       button=Button(activity)
       layout.addView(button)
       注.activity是当前窗口的Context对象，如果习惯写this只需要
       this=activity
       button=Button(this)

5，使用方法

       button.setText("按钮")

       getter/setter
       Java的getxxx方法没有参数与setxxx方法只有一个参数时可以简写，
       button.Text="按钮"
       x=button.X


6，使用事件
创建事件处理函数

       function click(s)
         print("点击")
       end
       
把函数添加到事件接口

       listener=View.OnClickListener{onClick = click}
       
把接口注册到组件

       button.setOnClickListener(listener)

也可以使用匿名函数

       button.setOnClickListener(View.OnClickListener {onClick = function(s)
         print("点击")
       end
       })
       

onxxx事件可以简写

       button.onClick=function(v)
         print(v)
       end


7，回调方法
function onResume()
print("返回程序")
end
function onDestroy()
print("程序已退出")
end
function onCreateOptionsMenu(menu)
menu.add("菜单")
end
支持onCreate,onStart,onResume,onPause,onStop,onDestroy,onActivityResult,onCreateOptionsMenu,onCreateContextMenu,onMenuItemSelected

8，按键与触控
function onKeyDown(code,event)
print(code event)
end
function onTouchEvent(event)
print(event)
end
支持onKeyDown,onKeyUp,onKeyLongPress,onTouchEvent
函数必须返布尔值

9，使用数组
array=float{1,2,3}
或者
array=int[10]
a=array[0]
array[0]=4

10，使用线程
需导入import模块，参看thread,timer与task函数说明
任务

task(str,args,callback)

str 为任务执行代码，args 为参数，callback 为回调函数，任务返回值将传递到回调方法
线程

t=thread(str,args)

str 为线程中执行的代码，args 为初始传入参数
调用线程中方法
call(t,fn,args)
t 为线程，fn 为方法名称，args 为参数
设置线程变量
set(t,fn,arg)
t 为线程，fn 为变量名称，arg 为变量值
线程调用主线程中方法
call(fn,args)
fn 为方法名称，args 为参数
线程设置主线程变量
set(fn,arg)
fn 为变量名称，arg 为变量值

注. 参数类型为 字符串，数值，Java对象，布尔值与nil
线程要使用quit结束线程。

t=timer(func,delay,period,args)

func 为定时器执行的函数，delay 为定时器延时，period 为定时器间隔，args 为初始化参数
t.Enable=false 暂停定时器
t.Enable=true 启动定时器
t.stop() 停止定时器

注意：定时器函数定义run函数时定时器重复执行run函数，否则重复执行构建时的func函数

11，使用布局表
使用布局表须导入android.view与android.widget包。
require "import"
import "android.widget.*"
import "android.view.*"
布局表格式
layout={
控件名称,
id=控件名称,
属性=值,
{
子控件名称,
id=控件名称,
属性=值,
}
}

布局表支持大全部安卓控件属性，
与安卓XML布局文件的不同点：
id表示在Lua中变量的名称，而不是安卓的可以findbyid的数字id。
ImageView的src属性是当前目录图片名称或绝对文件路径图片或网络上的图片，
layout_width与layout_height的值支持fill与wrap简写，
onClick值为lua函数或java onClick接口或他们的全局变量名称，
背景background支持背景图片，背景色与LuaDrawable自绘制背景，背景图片参数为是当前目录图片名称或绝对文件路径图片或网络上的图片，颜色同backgroundColor，自绘制背景参数为绘制函数或绘制函数的全局变量名称，
绘制函数形式
function draw(canvas,paint)
canvas.drawRect(1,1,100,100,paint)
end
控件背景色使用backgroundColor设置，值为"十六进制颜色值"。
其他参考loadlayout与loadbitmap

12，2D绘图
require "import"
import "android.app.*"
import "android.os.*"
import "android.widget.*"
import "android.view.*"
import "android.graphics.*"
activity.setTitle('AndroLua')

paint=Paint()
paint.setARGB(100,0,250,0)
paint.setStrokeWidth(20)
paint.setTextSize(28)

sureface = SurfaceView(activity);
callback=SurfaceHolder_Callback{
surfaceChanged=function(holder,format,width,height)
end,
surfaceCreated=function(holder)
ca=holder.lockCanvas()
if (ca~=nil) then
ca.drawRGB(0,79,90);
ca.drawRect(0,0,200,300,paint)
end
holder.unlockCanvasAndPost(ca)
end,
surfaceDestroyed=function(holder)
end
}
holder=sureface.getHolder()
holder.addCallback(callback)
activity.setContentView(sureface)

13，Lua类型与Java类型
在大多数情况下androlua可以很好的处理Lua与Java类型之间的自动转换，但是Java的数值类型有多种(double,float,long,int,short,byte)，而Lua只有number，在必要的情况下可以使用类型的强制转换。
i=int(10)
i就是一个Java的int类型数据
d=double(10)
d是一个Java的double类型
在调用Java方法时androlua可以自动将Lua的table转换成Java的array，Map或interface
Map类型可以像使用Lua表一样简便。
map=HashMap{a=1,b=2}
print(map.a)
map.a=3
取长度运算符#可以获取Java中array，List,Map,Set，String的长度。


14，部分模块
(1) canvas模块
require "import"
import "canvas"
import "android.app.*"
import "android.os.*"
import "android.widget.*"
import "android.view.*"
import "android.graphics.*"
activity.setTitle('AndroLua')

paint=Paint()
paint.setARGB(100,0,250,0)
paint.setStrokeWidth(20)
paint.setTextSize(28)

sureface = SurfaceView(activity);
callback=SurfaceHolder_Callback{
surfaceChanged=function(holder,format,width,height)
end,
surfaceCreated=function(holder)
ca=canvas.lockCanvas(holder)
if (ca~=nil) then
ca:drawRGB(0,79,90)
ca:drawRect(0,0,200,300,paint)
end
canvas.unlockCanvasAndPost(holder,ca)
end,
surfaceDestroyed=function(holder)
end
}
holder=sureface.getHolder()
holder.addCallback(callback)
activity.setContentView(sureface)

(2) OpenGL模块
require "import"
import "gl"
import "android.app.*"
import "android.os.*"
import "android.widget.*"
import "android.view.*"
import "android.opengl.*"
activity.setTitle('AndroLua')
--activity.setTheme( android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen)

mTriangleData ={
0.0, 0.6, 0.0,
-0.6, 0.0, 0.0,
0.6, 0.0, 0.0,
};
mTriangleColor = {
1, 0, 0, 0,
0, 1, 0, 0,
0, 0, 1, 0,
};

sr=GLSurfaceView.Renderer{
onSurfaceCreated=function(gl2, config)
gl.glDisable(gl.GL_DITHER);
gl.glHint(gl.GL_PERSPECTIVE_CORRECTION_HINT, gl.GL_FASTEST);
gl.glClearColor(0, 0, 0, 0);
gl.glShadeModel(gl.GL_SMOOTH);
gl.glClearDepth(1.0)
gl.glEnable(gl.GL_DEPTH_TEST);
gl.glDepthFunc(gl.GL_LEQUAL);
end,
onDrawFrame=function(gl2, config)
gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
gl.glMatrixMode(gl.GL_MODELVIEW);
gl.glLoadIdentity();
gl.glRotate(0,1,1,1)
gl.glTranslate(0, 0,0);
gl.glEnableClientState(gl.GL_VERTEX_ARRAY);
gl.glEnableClientState(gl.GL_COLOR_ARRAY);
gl.glVertexPointer( mTriangleData,3);
gl.glColorPointer(mTriangleColor,4);
gl.glDrawArrays( gl.GL_TRIANGLE_STRIP , 0, 3);
gl.glFinish();
gl.glDisableClientState(gl.GL_VERTEX_ARRAY);
gl.glDisableClientState(gl.GL_COLOR_ARRAY);
end,
onSurfaceChanged= function (gl2, w, h)
gl.glViewport(0, 0, w, h);
gl.glLoadIdentity();
ratio =  w / h;
gl.glFrustum(-rautio, ratio, -1, 1, 1, 10);
end
}

glSurefaceView = GLSurfaceView(activity);
glSurefaceView.setRenderer(sr);
activity.setContentView(glSurefaceView);


(3) http模块
body,cookie,code,headers=http.get(url [,cookie])
body,cookie,code,headers=http.post(url ,postdata [,cookie])
code,headers=http.download(url [,cookie])
body,cookie,code,headers=http.upload(url ,datas ,files [,cookie])

require "import"
import "http"

--get函数以get请求获取网页，参数为请求的网址与cookie
body,cookie,code,headers=http.get("http://www.androlua.com")

--post函数以post请求获取网页，通常用于提交表单，参数为请求的网址，要发送的内容与cookie
body,cookie,code,headers=http.post("http://androlua.com/Login.Asp?Login=Login&Url=http://androlua.com/bbs/index.asp","name=用户名&pass=密码&ki=1")

--download函数和get函数类似，用于下载文件，参数为请求的网址，保存文件的路径与cookie
http.download("http://androlua.com","/sdcard/a.txt")

--upload用于上传文件，参数是请求的网址，请求内容字符串部分，格式为以key=value形式的表，请求文件部分，格式为key=文件路径的表，最后一个参数为cookie
http.upload("http://androlua.com",{title="标题",msg="内容"},{file1="/sdcard/1.txt",file2="/sdcard/2.txt"})

(4) import模块

require "import"
import "android.widget.*"
import "android.view.*"
layout={
LinearLayout,
orientation="vertical",
{
EditText,
id="edit",
layout_width="fill"
},
{
Button,
text="按钮",
layout_width="fill",
onClick="click"
}
}

function click()
Toast.makeText(activity, edit.getText().toString(), Toast.LENGTH_SHORT ).show()
end
activity.setContentView(loadlayout(layout))


关于打包
新建工程或在脚本目录新建init.lua文件。
写入以下内容，即可将文件夹下所有lua文件打包，main.lua为程序人口。
appname="demo"
appver="1.0"
packagename="com.androlua.demo"
目录下icon.png替换图标，welcome.png替换启动图。
没有int.lua文件打包当前文件。
打包使用debug签名。

部分函数参考

[a]表示参数a可选，(...)表示不定参数。函数调用在只有一个参数且参数为字符串或表时可以省略括号。
AndroLua库函数在import模块，为便于使用都是全局变量。
s 表示string类型，i 表示整数类型，n 表示浮点数或整数类型，t 表示表类型，b 表示布尔类型，o 表示Java对象类型，f为Lua函数。
--表示注释。

each(o)
参数：o 实现Iterable接口的Java对象
返回：用于Lua迭代的闭包
作用：Java集合迭代器


enum(o)
参数：o 实现Enumeration接口的Java对象
返回：用于Lua迭代的闭包
作用：Java集合迭代器

import(s)
参数：s 要载入的包或类的名称
返回：载入的类或模块
作用：载入包或类或Lua模块
import "http" --载入http模块
import "android.widget.*" --载入android.widget包
import "android.widget.Button" --载入android.widget.Button类
import "android.view.View$OnClickListener" --载入android.view.View.OnClickListener内部类

loadlayout(t [,t2])
参数：t 要载入的布局表，t2 保存view的表
返回：布局最外层view
作用：载入布局表，生成view
layout={
LinearLayout,
layout_width="fill",
{
TextView,
text="Androlua",
id="tv"
}
}
main={}
activity.setContentView(loadlayout(layout,main))
print(main.tv.getText())

loadbitmap(s)
参数：s 要载入图片的地址，支持相对地址，绝对地址与网址
返回：bitmap对象
作用：载入图片
注意：载入网络图片需要在线程中进行

task(s [,...], f)
参数：s 任务中运行的代码或函数，... 任务传入参数，f 回调函数
返回：无返回值
作用：在异步线程运行Lua代码，执行完毕在主线程调用回调函数
注意：参数类型包括 布尔，数值，字符串，Java对象，不允许Lua对象
function func(a,b)
require "import"
print(a,b)
return a+b
end
task(func,1,2,print)

thread(s[,...])
参数：s 线程中运行的lua代码或脚本的相对路径(不加扩展名)或函数，... 线程初始化参数
返回：返回线程对象
作用：开启一个线程运行Lua代码
注意：线程需要调用quit方法结束线程
func=[[
a,b=...
function add()
call("print",a+b)
end
]]
t=thread(func,1,2)
t.add()

timer(s,i1,i2[,...])
参数：s 定时器运行的代码或函数，i1 前延时，i2 定时器间隔，... 定时器初始化参数
返回：定时器对象
作用：创建定时器重复执行函数
function f(a)
function run()
print(a)
a=a+1
end
end

t=timer(f,0,1000,1)
t.Enabled=false--暂停定时器
t.Enabled=true--重新定时器
t.stop()--停止定时器

new_env()
参数：无
返回：一个继承了import模块函数的环境表
作用：产生一个继承import模块函数的环境表
function foo()
local _ENV=new_env()
import "android.widget.*"
b=Button(activity)
end

luajava.bindClass(s)
参数：s class的完整名称，支持基本类型
返回：Java class对象
作用：载入Java class
Button=luajava.bindClass("android.widget.Button")
int=luajava.bindClass("int")

luajava.createProxy(s,t)
参数：s 接口的完整名称，t 接口函数表
返回：Java接口对象
作用：创建Java接口
onclick=luajava.createProxy("android.view.View$OnClickListener",{onClick=function(v)print(v)end})

luajava.createArray(s,t)
参数：s 类的完整名称，支持基本类型，t 要转化为Java数组的表
返回：创建的Java数组对象
作用：创建Java数组
arr=luajava.createArray("int",{1,2,3,4})

luajava.newInstance(s [,...])
参数：s 类的完整名称，... 构建方法的参数
作用：创建Java类的实例
b=luajava.newInstance("android.widget.Button",activity)

luajava.new(o[,...])
参数：o Java类对象，... 参数
返回：类的实例或数组对象或接口对象
作用：创建一个类实例或数组对象或接口对象
注意：当只有一个参数且为表类型时，如果类对象为interface创建接口，为class创建数组，参数为其他情况创建实例
b=luajava.new(Button,activity)
onclick=luajava.new(OnClickListener,{onClick=function(v)print(v)end})
arr=luajava.new(int,{1,2,3})
(示例中假设已载入相关类)

luajava.coding(s [,s2 [, s3]])
参数：s 要转换编码的Lua字符串，s2 字符串的原始编码，s3 字符串的目标编码
返回：转码后的Lua字符串
作用：转换字符串编码
注意：默认进行GBK转UTF8

luajava.clear(o)
参数：o Java对象
返回：无
作用：销毁Java对象
注意：尽量避免使用此函数，除非确认不在使用此对象，且该对象比较大

luajava.astable(o)
参数：o Java对象
返回：Lua表
作用：转换Java的Array List或Map为Lua表

luajava.tostring(o)
参数：o Java对象
返回：Lua字符串
作用：相当于 o.toString()

activity部分API参考
setContentView(layout, env)
设置布局表layout为当前activity的主视图，env是保存视图ID的表，默认是_G
getLuaDir()
返回脚本当前目录
getLuaDir(name)
返回脚本当前目录的子目录
getLuaExtDir()
返回Androlua在SD的工作目录
getLuaExtDir(name)
返回Androlua在SD的工作目录的子目录
getWidth()
返回屏幕宽度
getHeight()
返回屏幕高度，不包括状态栏与导航栏
loadDex(path)
加载当前目录dex或jar，返回DexClassLoader
loadLib(path)
加载当前目录c模块，返回载入后模块的返回值(通常是包含模块函数的包)
registerReceiver(filter)
注册一个广播接收者，当再次调用该方法时将移除上次注册的过滤器
newActivity(req, path, arg)
打开一个新activity，运行路径为path的Lua文件，其他两个参数为可选，arg为表，接受脚本为变长参数
newTask(func, update, callback)
新建一个Task异步任务，在线程中执行func函数，其他两个参数可选，执行结束回调callback，在任务调用update函数时在UI线程回调该函数
新建的Task在调用execute{}时通过表传入参数，在func以unpack形式接收，执行func可以返回多个值，
newThread(func, arg)
新建一个线程，在线程中运行func函数，可以以表的形式传入arg，在func以unpack形式接收
新建的线程调用start()方法运行，线程为含有loop线程，在当前activity结束后自动结束loop
newTimer(func, arg)
新建一个定时器，在线程中运行func函数，可以以表的形式传入arg，在func以unpack形式接收
调用定时器的start(delay, period)开始定时器，stop()停止定时器，Enabled暂停恢复定时器，Period属性改变定时器间隔

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
