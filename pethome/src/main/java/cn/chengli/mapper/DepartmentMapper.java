package cn.chengli.mapper;

import cn.chengli.domain.TDepartment;
import cn.chengli.query.DeptQuery;

import java.util.List;

/**
 * @chengli
 */
public interface DepartmentMapper {
    List<TDepartment> findAll();

    void save(TDepartment department);

    void update(TDepartment department);

    void delete(Long id);

    List<TDepartment> queryPage(DeptQuery query);

    void batchdelete(List<Long> userIds);

    List<TDepartment> findByParentId(long id);
}
