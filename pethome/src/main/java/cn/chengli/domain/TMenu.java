package cn.chengli.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class TMenu {

  private Long id;
  private String name;
  private String url;
  private String icon;
  private Long index;
  private Long parentId;
  private String parentName;
  //如果为空，不转换为json
  @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
  private List<TMenu> children;


}
