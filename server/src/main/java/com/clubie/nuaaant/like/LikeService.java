package com.clubie.nuaaant.like;

public interface LikeService {
    void Like(int userID, int orderID);

    void CancelLike(int userID, int orderID);

    boolean CheckLike(int userID, int orderID);

    int GetLikeCount(int orderID);
}
