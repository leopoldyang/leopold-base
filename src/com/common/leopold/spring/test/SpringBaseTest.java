package com.common.leopold.spring.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基于spring、junit4的测试类的基类
 * 加载spring配置文件,文件名为application.xml
 * 测试方法默认加上事务,声明式事务名字为txManager,事务在方法执行之后自动回滚
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/11/27
 * Time:2:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:application.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class SpringBaseTest {
}
