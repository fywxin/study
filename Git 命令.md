Git 命令

- A fork B 源码，A有更新，B需要拉取A的更新

  - git remote add {A别名}  {A的git地址}     ->将远程源添加到源列表

    ```
    git remote add abtest0 http://git.onemt.com.cn/abtest/abtest.git
    ```

  - git pull {A别名} master          -> 拉取master分支

    ```
    git pull abtest0 master
    ```

  - git checkout  {branch}

  - git pull abtest0   {branch}     -> 切换到分支更新

    ```
    git checkout 1.3.0
    git pull abtest0 1.3.0
    
    :wq
    ```

    

1. 