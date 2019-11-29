# 定时任务

通过把quartz框架进行封装和整合，使得使用起来更加简单，去除原来实现任务的动态的增删查改需要 12 张表，改为修改后的 1 张表实现，通过类路径和方法名，使用反射调用相关的方法执行，service 封装了增删查改的相关方法，以及触发任务、执行任务、移除任务、关闭定时任务容器等方法。

## 使用方法

1、在 WEB-INF 目录下建立 lib 目录，并把jar包复制到 lib 目录；

2、在maven配置文件，即 pom.xml 中，添加以下依赖：
  
  ```
  
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context-support</artifactId>
      <version>4.2.4.RELEASE</version>
    </dependency>
    <dependency>
      <groupId>org.quartz-scheduler</groupId>
      <artifactId>quartz</artifactId>
      <version>2.2.1</version>
    </dependency>
    <dependency>
      <groupId>org.quartz-scheduler</groupId>
      <artifactId>quartz-jobs</artifactId>
      <version>2.2.1</version>
    </dependency>
    
    <dependency>
      <groupId>quartz</groupId>
      <artifactId>quartz</artifactId>
      <version>1.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/quartz.jar</systemPath>
    </dependency>
    
   ```
    
## 配置文件修改

1、在相关配置文件中，增加 entity、dao、controller、service的扫描路径（也可以用继承加注解的方式实现）：
```
com.ycj.fastframe.quartz.entity
com.ycj.fastframe.quartz.dao
com.ycj.fastframe.quartz.controller
com.ycj.fastframe.quartz.service
```
比如配置service扫描，只需要在原有项目基础路径后面用逗号分隔，加上需要扫描的包路径即可，如下所示（也可以在现有项目中新建类继承相关的类来实现）：
  ```
    <!-- 扫描service -->
    <context:component-scan base-package="com.construct.service,com.ycj.fastframe.quartz.service"/>
 ```
 
 2、在spring配置文件中加入quartz的相关初始化bean和实例化方法：
 ```
    <bean id="userUtils" class="com.ycj.fastframe.quartz.util.SpringContextHolder"/>
    <bean id="startQuertz" lazy-init="true" autowire="no" class="org.springframework.scheduling.quartz.SchedulerFactoryBean"></bean>
    <bean id="quartzManager" class="com.ycj.fastframe.quartz.config.QuartzManager" lazy-init="false" init-method="startJobs" >
        <property name="scheduler" ref="startQuertz" />
    </bean>
 ```
 3、继承加注解实现扫描装载
 
 如果用继承加注解实现扫描装载，如上面第二点的 <bean id="userUtils" class="com.ycj.fastframe.quartz.util.SpringContextHolder"/> ，可用下面方式实现：
 
 1）、新建一个类：MySpringHolder，继承SpringContextHolder：
 ```
 public class MySpringHolder extends SpringContextHolder {
}
 ```
 2）添加注解 @Component
 ```
 @Component
public class MySpringHolder extends SpringContextHolder {
}
 ```
 
## 编写任务方法

1、新建一个类 MyTask，并编写自己需要执行的方法，记得需要加上注解，或者在spring配置文件中申明：

```

@Component
public class MyTask {
    public void startTask(){
        System.out.println("绝密任务开始执行！");
    }

    public void synData(){
        System.out.println("数据同步任务开始");
        TestDataSyn.quartzSynData();;
        System.out.println("数据同步任务结束");
    }
}


```

## 生成数据表
简单设置数据库连接信息，自动生成相应的数据表。
```
public class TestQuartz {
    public static void main(String[] args) {
        DatabaseInfo databaseInfo=new DatabaseInfo();
        databaseInfo.setDatabaseURL("jdbc:mysql://127.0.0.1:3306/db_name?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&useSSL=false");
        databaseInfo.setUserName("root");
        databaseInfo.setPassword("123456");
        QuartzJob quartzJob=new QuartzJob();
        boolean sys_schedule_job = quartzJob.createTable("sys_schedule_job", databaseInfo);
        System.out.println("执行结果："+sys_schedule_job);
    }
  }
```

## 实现任务的增删改查

新建一个Controller类QuartzController来继承ScheduleJobController：
```
@Controller
@RequestMapping("/scheduleJob")
public class QuartzController extends ScheduleJobController {

    @Autowired
    ScheduleJobServiceImpl scheduleJobService;

    @RequestMapping(value="/test")
    @ResponseBody
    public String test(HttpServletRequest request) {
        //获取bean对象
        QuartzManager quartzManager =SpringContextHolder.getBean("quartzManager");
        //从数据库查询指定的定时任务
        ScheduleJob scheduleJob = scheduleJobService.selectById(6);
        //立即执行
        //对于刚刚添加到数据库的任务，在添加的时候没有同时 addjog（），则需要先执行addjob（），然后再立即执行
        scheduleJobService.addJob(scheduleJob);
        quartzManager.runAJobNow(scheduleJob);
        //添加定时任务，但不保存到数据库，保存到 RAM
        quartzManager.addJob("test", "test", "test", "test", QuartzTask.class, "0/3 * * * * ?",new TaskDataImpl());

        return "Quartz start success!";
    }

    @RequestMapping(value="/addtest")
    @ResponseBody
    public String addtest(HttpServletRequest request) {
        //创建一个定时任务
        ScheduleJob scheduleJob=new ScheduleJob();
        scheduleJob.setBeanClass("com.construct.service.quartz.MyTask");
        scheduleJob.setCreateTime(new Date());
        //如果不懂cron表达是，可以用相应的工具转换
        scheduleJob.setCronExpression("*/5 * * * * ?");
        //cron 转换工具
        //scheduleJob.setCronExpression(QuartzCronDate.getCron(new Date()));
        scheduleJob.setIsConcurrent(ScheduleJobConts.CONCURRENT_NOT);
        scheduleJob.setJobGroup("mytest");
        scheduleJob.setJobName("test3");
        //设置任务的启动、停止状态
        scheduleJob.setJobStatus(ScheduleJobConts.STATUS_RUNNING);
        scheduleJob.setMethodName("startTask");
        //保存任务到数据库,这样的操作没有创建触发器，不建议这样做，这样做需要手动调用add()一次，定时任务才能实时生效
        //scheduleJobService.insert(scheduleJob);
        //add(scheduleJob);
        //，保存到数据库同时建立触发器，生成定时任务,推荐使用
        ResponseEntity<?> responseEntity = scheduleJobService.addScheduleJob(scheduleJob);
        return JSON.toJSONString(responseEntity);
    }
}

```

## Service 类介绍
service主要包含两个QuartzService 和ScheduleJobServiceImpl，前者没有实现与数据库的交互，后者实现了与数据库的交互，各个service中都包含相应的增删改查方法，使用时可以点进去详细查看。

## 任务方法实现的另一种方式
除了上面介绍的在一个任务类中编写相关的任务方法外，还支持实现接口的方式，比如新建一个类TaskDataImpl。
```
public class TaskDataImpl implements TaskData {
    @Override
    public void execute() {
        System.out.println("定时任务。。。。。。。。。。。。执行中。。。。。");
    }
}
```

使用TaskDataImpl定时任务方法：
```
public class TestQuartz {
    public static void main(String[] args) throws BeansException {
        QuartzManager quartzManager = QuartzConfig.initQuartzTask();
        try {
            System.out.println("【系统启动】开始(每1秒输出一次 job2)...");
            Thread.sleep(5000);
            System.out.println("【增加job1启动】开始(每1秒输出一次)...");
            quartzManager.addJob("test", "test", "test", "test", QuartzTask.class, "0/1 * * * * ?",new TaskDataImpl());

            Thread.sleep(5000);
            System.out.println("【修改job1时间】开始(每2秒输出一次)...");
            quartzManager.modifyJobTime("test", "test", "test", "test", "0/2 * * * * ?");

            Thread.sleep(10000);
            System.out.println("【移除job1定时】开始...");
            quartzManager.removeJob("test", "test", "test", "test");

            // 关掉任务调度容器
            quartzManager.shutdownJobs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

  
## 其它方法
定时任务中还有很多其他方法，根据实际业务需要进行使用！

例如：在定时任务执行前的动作做一些其它的逻辑判断或数据处理。

1、实现DataOperateByJob接口。

```
public class TaskOrder implements DataOperateByJob {
    @Override
    public void excuteDataOperate(ScheduleJob scheduleJob) {
        //将待同步的计划写入静态变量中
        MyTask.projctCode=scheduleJob.getJobGroup();
        System.out.println("待同步数据库的项目编号："+  myTask.projctCode);

    }
}
```

2、在编写同步方法的所在类，添加接口赋值加载。

```
@Component
public class MyTask {
private static TaskOrder taskOrder=new TaskOrder();

    static {
        QuartzManager.dataOperateByJob=taskOrder;
    }
}
```

## 常见问题及解决
https://blog.csdn.net/u014374009/article/details/97638636



