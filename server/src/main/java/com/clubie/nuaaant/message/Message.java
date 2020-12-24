package com.clubie.nuaaant.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Message {
    private Integer ID;
    private Integer SenderID;
    private Date Time;
    private String Text;
    private Boolean HasRead;
}
