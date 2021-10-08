package cn.chengli.domain;

import lombok.Data;

@Data
public class TEmployee {

  private Long id;
  private String username;
  private String email;
  private String phone;
  private String password;
  private Long age;
  private Long state;

}