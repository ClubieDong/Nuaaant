package com.clubie.nuaaant.agree;

import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreeServiceImpl implements AgreeService {

    @Autowired
    private AgreeMapper agreeMapper;

    @Override
    public void Agree(int userID, int remarkID) {
        try {
            agreeMapper.Agree(userID, remarkID);
        } catch (Exception e) {
            throw new NuaaAntException("无法点赞");
        }
    }

    @Override
    public void CancelAgree(int userID, int remarkID) {
        agreeMapper.CancelAgree(userID, remarkID);
    }

}
