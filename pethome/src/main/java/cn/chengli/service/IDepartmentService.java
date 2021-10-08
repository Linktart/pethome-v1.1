package cn.chengli.service;

import cn.chengli.domain.TDepartment;
import cn.chengli.query.DeptQuery;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @chengli
 */
public interface IDepartmentService {
    List<TDepartment> findAll();

    void saveOrUpdate(TDepartment department);

    void delete(Long id);

    PageInfo<TDepartment> queryPage(DeptQuery query);

    void batchdelete(List<Long> userIds);

    AjaxResult treeDept();
}
