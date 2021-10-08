package cn.chengli.mapper;

import cn.chengli.domain.TEmployee;
import cn.chengli.query.EmployeeQuery;

import java.util.List;

/**
 * @chengli
 */
public interface EmployeeMapper {
    List<TEmployee> findAll();

    void save(TEmployee employee);

    void update(TEmployee employee);

    void delete(Long id);

    List<TEmployee> queryPage(EmployeeQuery query);

    void batchdelete(List<Long> userIds);

    List<TEmployee> findAllNameAndIdAndPhone();
}
