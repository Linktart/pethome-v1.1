package cn.chengli.query;

import lombok.Data;

/**
 * @chengli
 */
@Data
public class EmployeeQuery extends BaseQuery {
    private String username;
    private Integer state;
    private String phone;
}
