package com.fc.service;

import com.fc.vo.ResultVO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    ResultVO adminLogin(String name, String password);

    ResultVO customAccountLogin(String username, String password);
}
