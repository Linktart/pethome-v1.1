package cn.chengli.service.impl;

import cn.chengli.domain.TEmployee;
import cn.chengli.mapper.EmployeeMapper;
import cn.chengli.query.EmployeeQuery;
import cn.chengli.service.IEmployeeService;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @chengli
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<TEmployee> findAll() {
        return employeeMapper.findAll();
    }

    /**
     * 新增或修改
     *
     * @param employee
     */
    @Override
    public void saveOrUpdate(TEmployee employee) {
        if (employee.getId() == null || "".equals(employee.getId())) {
            employeeMapper.save(employee);
        } else {
            employeeMapper.update(employee);
        }
    }

    @Override
    public void delete(Long id) {
        employeeMapper.delete(id);
    }

    @Override
    public PageInfo<TEmployee> queryPage(EmployeeQuery query) {

        //开启pagehelper
        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());

        List<TEmployee> list = employeeMapper.queryPage(query);
        return new PageInfo<TEmployee>(list);
    }

    @Override
    public void batchdelete(List<Long> userIds) {
        employeeMapper.batchdelete(userIds);
    }

    @Override
    public List<TEmployee> findAllNameAndIdAndPhone() {
        return employeeMapper.findAllNameAndIdAndPhone();
    }


}
