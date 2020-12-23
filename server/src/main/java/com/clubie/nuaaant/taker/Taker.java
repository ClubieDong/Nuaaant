package com.clubie.nuaaant.taker;

import com.clubie.nuaaant.user.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Taker extends User {
    private BigDecimal TakerScoreTotal;
    private Integer TakerScoreCount;
}
