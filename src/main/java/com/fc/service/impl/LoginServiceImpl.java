package com.fc.service.impl;

import com.fc.dao.AdminMapper;
import com.fc.dao.CustomAccountMapper;
import com.fc.entity.Admin;
import com.fc.entity.AdminExample;
import com.fc.entity.CustomAccount;
import com.fc.entity.CustomAccountExample;
import com.fc.service.LoginService;
import com.fc.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoginServiceImpl implements LoginService {
    @Autowired
    public AdminMapper adminMapper;
    public CustomAccountMapper customAccountMapper;
    ResultVO resultVO;
    @Override
    public ResultVO adminLogin(String name, String password) {


        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();

        criteria.andNameEqualTo(name);
        criteria.andPasswordEqualTo(password);

        List<Admin> admins = adminMapper.selectByExample(adminExample);

        if (admins.size()>0){
           resultVO = new ResultVO(1,"管理员登录成功！",true,admins.get(0));
        }else {
            resultVO = new ResultVO(-1,"管理员登录失败！",false,null);
        }


   return resultVO;
    }

    @Override
    public ResultVO customAccountLogin(String name, String password) {

        CustomAccountExample customAccountExample = new CustomAccountExample();
        CustomAccountExample.Criteria criteria = customAccountExample.createCriteria();
        criteria.andUsernameEqualTo(name);
        criteria.andPasswordEqualTo(password);

        List<CustomAccount> customAccounts = customAccountMapper.selectByExample(customAccountExample);

        if (customAccounts.size()>0){
            resultVO = new ResultVO(1,"用户登录成功！",true,customAccounts.get(0));
        }else {
            resultVO = new ResultVO(-1,"用户登录失败！",false,null);
        }

return  resultVO;
    }
}
