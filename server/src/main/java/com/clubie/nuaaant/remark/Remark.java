package com.clubie.nuaaant.remark;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Remark {
    private Integer ID;
    private Integer UserID;
    private Integer OrderID;
    private Date Time;
    private String Text;
}
