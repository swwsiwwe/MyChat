package com.company.utils;

import com.company.dao.IFriendsDao;
import com.company.dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.io.InputStream;

@Aspect
@Component
public class MySqlUtils {
    private InputStream in;
    private SqlSessionFactory sessionFactory;
    private SqlSession sqlSession;
    public MySqlUtils(){
        try{
            System.out.println("自定义构造函数");
            //1.读取配置文件
            in = Resources.getResourceAsStream("SqlMapConfig");
            //2.创建SQLSessionFactory工厂
            sessionFactory = new SqlSessionFactoryBuilder().build(in);
        }catch (Exception e){
            System.out.println("email重复");
            e.printStackTrace();
        }
    }
    @Pointcut("execution(* com.company.service.daoService.*.*(..))")

    private void aop(){}

    @Around("aop()")
    public Object exec(ProceedingJoinPoint pjp){
        Object []args = pjp.getArgs();
        try{
            open();
            return  pjp.proceed(args);
        }catch (Throwable t){
            System.out.println(t);
            throw new RuntimeException();
        }finally {
            commit();
            close();
        }
    }
    public void open(){
        System.out.println("open!");
        //3.使用工厂创建sqlSession对象
        sqlSession = sessionFactory.openSession();
    }
    public void commit(){
        sqlSession.commit();
    }

    public IUserDao getIUserDao(){
        return sqlSession.getMapper(IUserDao.class);
    }
    public IFriendsDao getIFriendsDao(){
        return sqlSession.getMapper(IFriendsDao.class);
    }

    public void close(){
        try{
            sqlSession.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}