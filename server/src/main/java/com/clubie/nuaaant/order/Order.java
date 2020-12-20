package com.clubie.nuaaant.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Order {
    private Integer ID;
    private Integer TypeIndex;
    private String Title;
    private BigDecimal Reward;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date Deadline;
    private Integer GiverID;
    private Integer TakerID;
    private Boolean IsTemplate;
    private String FromAddr;
    private String ToAddr;
    private Boolean IsSelf;
    private Integer SizeIndex;
    private Integer WeightIndex;
    private String ExpressCode;
    private Integer QuestionTypeIndex;
    private Integer Duration;
    private Integer UnitIndex;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date ReturnTime;
    private String SimpleDesc;
    private String DetailedDesc;
}
