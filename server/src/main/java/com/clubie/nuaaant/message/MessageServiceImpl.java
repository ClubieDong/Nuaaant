package com.clubie.nuaaant.message;

import com.clubie.nuaaant.utils.NuaaAntException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void AddMessage(int senderID, int receiverID, String text) {
        if (senderID == receiverID)
            throw new NuaaAntException("不能给自己发送信息");
        if (text == null || text.isBlank())
            throw new NuaaAntException("发送内容不能为空");
        messageMapper.AddMessage(senderID, receiverID, new Date(), text);
    }

    @Override
    public List<Map<String, Object>> GetMessage(int senderID, int receiverID) {
        var res = messageMapper.GetMessage(senderID, receiverID);
        if (res == null)
            throw new NuaaAntException("无法获取聊天记录");
        ReadAll(senderID, receiverID);
        return res;
    }

    @Override
    public void ReadAll(int senderID, int receiverID) {
        messageMapper.ReadAll(senderID, receiverID);
    }

    @Override
    public List<Map<String, Object>> GetMessageList(int userID) {
        var res = messageMapper.GetMessageList(userID);
        if (res == null)
            throw new NuaaAntException("无法获取聊天记录");
        return res;
    }
}
