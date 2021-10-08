package cn.chengli.controller;

import cn.chengli.domain.BatchDel;
import cn.chengli.domain.TEmployee;
import cn.chengli.query.EmployeeQuery;
import cn.chengli.service.IEmployeeService;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @chengli
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService departmentService;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    PageInfo<TEmployee> list(@RequestBody EmployeeQuery query) {

        return departmentService.queryPage(query);
    }

    @DeleteMapping
    AjaxResult delete(@RequestBody BatchDel batchDel) {
        try {
            if (batchDel.getUserIds() != null && batchDel.getUserIds().size() > 0) {
                departmentService.batchdelete(batchDel.getUserIds());
            }
            if (batchDel.getId() != null) {
                departmentService.delete(batchDel.getId());
            }
            return AjaxResult.createSuccess("删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.createError(e.getMessage());
        }
    }

    /**
     * 新增或修改
     *
     * @return
     */
    @PutMapping
    AjaxResult saveOrUpdate(@RequestBody TEmployee department) {
        try {
            departmentService.saveOrUpdate(department);
            return AjaxResult.createSuccess("操作成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.createError(e.getMessage());
        }
    }

    /**
     * 查询所有员工姓名
     * @return
     */
    @GetMapping
    List<TEmployee> findAllNameAndIdAndPhone(){
        return departmentService.findAllNameAndIdAndPhone();
    }
}

