package cn.roboteco.chapter2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private Long id;

    private String name;

    private String contact;

    private String telephone;

    private String email;

    private String remark;
}
