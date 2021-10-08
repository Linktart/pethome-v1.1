package cn.chengli.service.impl;

import cn.chengli.domain.TDepartment;
import cn.chengli.mapper.DepartmentMapper;
import cn.chengli.query.DeptQuery;
import cn.chengli.service.IDepartmentService;
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
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<TDepartment> findAll() {
        return departmentMapper.findAll();
    }

    /**
     * 新增或修改
     *
     * @param department
     */
    @Override
    public void saveOrUpdate(TDepartment department) {
        if (department.getId() == null && "".equals(department.getId())) {
            departmentMapper.save(department);
        } else {
            departmentMapper.update(department);
        }
    }

    @Override
    public void delete(Long id) {
        departmentMapper.delete(id);
    }

    @Override
    public PageInfo<TDepartment> queryPage(DeptQuery query) {

        //开启pagehelper
        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());

        List<TDepartment> list = departmentMapper.queryPage(query);
        return new PageInfo<TDepartment>(list);
    }

    @Override
    public void batchdelete(List<Long> userIds) {
        departmentMapper.batchdelete(userIds);
    }

    /**
     * 查询所有部门及其下级部门
     *
     * @return
     */
    @Override
    public AjaxResult treeDept() {
        //查询顶级部门
        try {
            List<TDepartment> list = departmentMapper.findByParentId(0L);
            findAllDepts(list);
            return AjaxResult.createSuccess("查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.createError(e.getMessage());
        }
    }

    void findAllDepts(List<TDepartment> list) {
        if (list != null && list.size() > 0) {
            for (TDepartment department : list) {
                List<TDepartment> children = departmentMapper.findByParentId(department.getId());
                department.setChildren(children);
                findAllDepts(children);
            }
        }
    }
}
