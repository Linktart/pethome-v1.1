package cn.chengli.controller;

import cn.chengli.domain.BatchDel;
import cn.chengli.domain.TDepartment;
import cn.chengli.query.DeptQuery;
import cn.chengli.service.IDepartmentService;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @chengli
 */
@RestController
@RequestMapping("/dept")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    PageInfo<TDepartment> list(@RequestBody DeptQuery query) {

        return departmentService.queryPage(query);
    }

    /**
     * 查询所有部门及其子部门
     *
     * @return
     */
    @GetMapping("/treedept")
    AjaxResult treeDept() {

        return departmentService.treeDept();
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
    AjaxResult saveOrUpdate(@RequestBody TDepartment department) {
        try {
            departmentService.saveOrUpdate(department);
            return AjaxResult.createSuccess("操作成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.createError(e.getMessage());
        }
    }

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping
    List<TDepartment> findAll(){
        return departmentService.findAll();
    }
}

