package cn.chengli.controller;

import cn.chengli.domain.BatchDel;
import cn.chengli.domain.TMenu;
import cn.chengli.query.MenuQuery;
import cn.chengli.service.IMenuService;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @chengli
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    @PostMapping("/list")
    PageInfo<TMenu> list(@RequestBody MenuQuery query) {

        return menuService.queryPage(query);
    }

    /**
     * 查询所有部门及其子部门
     *
     * @return
     */
    @GetMapping("/treemenu")
    AjaxResult treeMenu() {

        return menuService.treeMenu();
    }

    @DeleteMapping
    AjaxResult delete(@RequestBody BatchDel batchDel) {
        try {
            if (batchDel.getUserIds() != null && batchDel.getUserIds().size() > 0) {
                menuService.batchdelete(batchDel.getUserIds());
            }
            if (batchDel.getId() != null) {
                menuService.delete(batchDel.getId());
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
    AjaxResult saveOrUpdate(@RequestBody TMenu menu) {
        try {
            menuService.saveOrUpdate(menu);
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
    List<TMenu> findAll(){
        return menuService.findAll();
    }
}

