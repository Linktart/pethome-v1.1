package cn.chengli.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class TDepartment {

  private Long id;
  private String sn;
  private String name;
  private Integer state;
  private Long managerId;
  private Long parentId;
  private String parentName;
  private String managerName;
  //如果为空，不转换为json
  @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
  private List<TDepartment> children;
}