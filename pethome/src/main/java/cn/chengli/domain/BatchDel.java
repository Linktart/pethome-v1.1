package cn.chengli.domain;

import lombok.Data;

import java.util.List;

/**
 * @chengli
 */
@Data
public class BatchDel {
    private Long id;
    private List<Long> userIds;
}
