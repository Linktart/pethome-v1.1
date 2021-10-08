package cn.chengli.service;

import cn.chengli.domain.TEmployee;
import cn.chengli.query.EmployeeQuery;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @chengli
 */
public interface IEmployeeService {
    List<TEmployee> findAll();

    void saveOrUpdate(TEmployee department);

    void delete(Long id);

    PageInfo<TEmployee> queryPage(EmployeeQuery query);

    void batchdelete(List<Long> userIds);


    List<TEmployee> findAllNameAndIdAndPhone();
}
