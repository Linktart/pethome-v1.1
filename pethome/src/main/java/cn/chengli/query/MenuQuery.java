package cn.chengli.query;

import lombok.Data;

/**
 * @chengli
 */
@Data
public class MenuQuery extends BaseQuery {
    private String name;
    private Long parentId;

}
