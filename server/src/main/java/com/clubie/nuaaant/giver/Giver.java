package com.clubie.nuaaant.giver;

import com.clubie.nuaaant.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Giver extends User {
    private BigDecimal GiverScoreTotal;
    private Integer GiverScoreCount;
}
