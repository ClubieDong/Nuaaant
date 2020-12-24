package com.clubie.nuaaant.agree;

public interface AgreeService {
    void Agree(int userID, int remarkID);

    void CancelAgree(int userID, int remarkID);
}
