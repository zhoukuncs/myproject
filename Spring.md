# Spring

## 两个核心部分：

### IOC：控制翻转，把创建对象和对象之间的调用过程(A 调用 B对象)交给Spring进行管理

​				目的：为了耦合度降低。

​				原理：xml解析，工厂模式，反射机制

​				工厂模式：还是存在耦合度


```java
class UserService {
    execute() {
        UserDao dao = UserFactory.getDao();
        dao.add();
    }
}

class UserDao {
    add(){
        
    }
}

class UserFactory {
    public static UserDao getDao() {
        return new UserDao();
    }
}
```



### AOP：面向切面，不修改源代码进行功能增强。