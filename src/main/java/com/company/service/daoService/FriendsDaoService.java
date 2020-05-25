package com.company.service.daoService;

import com.company.dao.IFriendsDao;
import com.company.domain.Friends;
import com.company.utils.MySqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("friendsDaoService")
public class FriendsDaoService {
    @Autowired
    MySqlUtils mySqlUtils=null;

    public void insert(Friends f){
        IFriendsDao dao = mySqlUtils.getIFriendsDao();
        dao.insert(f);
    }

    public void delete(String username,String friend){
        IFriendsDao dao = mySqlUtils.getIFriendsDao();
        dao.delete(username,friend);
    }

    public List<Friends> select(String username){
        IFriendsDao dao = mySqlUtils.getIFriendsDao();
        return dao.select(username);
    }

    public Friends selectF(String username,String friend){
        IFriendsDao dao= mySqlUtils.getIFriendsDao();
        return dao.selectF(username, friend);
    }

}
