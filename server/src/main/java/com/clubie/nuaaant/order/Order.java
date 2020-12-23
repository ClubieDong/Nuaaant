package com.clubie.nuaaant.order;

import com.clubie.nuaaant.template.Template;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Order extends Template {
    private Integer TakerID;
    private List<Integer> Appliers;
    private Integer State;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date PublishTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date AcceptTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date SubmitTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date CompleteTime;
}
