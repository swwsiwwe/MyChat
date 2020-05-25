package com.company.service.daoService;

import com.company.dao.IUserDao;
import com.company.domain.User;
import com.company.utils.MySqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDaoService {
    @Autowired
    private MySqlUtils mySqlUtils = null;

    public User selectUser(String username){
        if(mySqlUtils==null){
            System.out.println("dao is null");
        }
        IUserDao dao = mySqlUtils.getIUserDao();

        User u = dao.select(username);
        return u;
    }

    public void insertUser(User u){
        IUserDao dao = mySqlUtils.getIUserDao();
        dao.insert(u);
    }
}
