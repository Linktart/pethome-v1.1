package cn.chengli.query;

import lombok.Data;

/**
 * @chengli
 */
@Data
public class BaseQuery {
    /**
     * 当前页
     */
    protected Integer currentPage = 1;

    /**
     * 每页显示的条数
     */
    protected Integer pageSize = 5;
}
