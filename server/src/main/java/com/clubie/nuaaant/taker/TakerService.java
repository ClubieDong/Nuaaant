package com.clubie.nuaaant.taker;

public interface TakerService {
    void GiveUp(int userID, int orderID);

    void Submit(int userID, int orderID);

    void CancelSubmit(int userID, int orderID);
}
