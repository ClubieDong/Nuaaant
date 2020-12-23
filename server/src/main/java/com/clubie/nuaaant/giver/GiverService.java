package com.clubie.nuaaant.giver;

public interface GiverService {
    void Republish(int userID, int orderID);

    void Withdraw(int userID, int orderID);

    void ChooseTaker(int userID, int orderID, int takerID);

    void CancelChooseTaker(int userID, int orderID);

    void Accept(int userID, int orderID);

    void Reject(int userID, int orderID);
}
