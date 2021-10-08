package cn.chengli.query;

import lombok.Data;

/**
 * @chengli
 */
@Data
public class DeptQuery extends BaseQuery {
    private String name;
    private Integer state;
    private Long parentId;

}
