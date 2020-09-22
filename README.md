# webflash-generator
- 本项目基于Intellij IDEA的代码生成插件
- 改插件需要配合[web-flash]()项目中的flash-generator使用。
- 该插件本质是只提供了代码生成的界面配置功能，真正的代码生成逻辑还在flash-generator中。

## 插件可使用范围
- web-flash项目
- guns-lite项目
- 以及其他基于上述两个项目做二次开发的项目，例如：
    - [linjiashop]():一个基于web-flash开发的商城系统
    - [material-admin]():web-flash的单体版(springboot+jsp)


## 使用方法
 以在web-flash项目中使用为例：
- 在idea插件仓库中搜索webflash-generator，或者直接从本地安装插件：flash-generator/idea-plugin.jar
- 写好实体类，打开实体类，右键选择Generator(或者Alt+Insert) 
![webflash-mvc](https://gitee.com/enilu/web-flash/raw/master/docs/img/plugin/generator.jpg)
- 选择web-flash mvc，在弹框中勾选生成选项
![config](https://gitee.com/enilu/web-flash/raw/master/docs/img/plugin/generator-config.jpg)

- 更详细的用法参考文档[http://enilu.gitee.io/web-flash/ecosystem/code-generator.html](http://enilu.gitee.io/web-flash/ecosystem/code-generator.html)
