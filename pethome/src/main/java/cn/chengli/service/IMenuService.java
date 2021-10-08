package cn.chengli.service;

import cn.chengli.domain.TMenu;
import cn.chengli.query.MenuQuery;
import cn.chengli.util.AjaxResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @chengli
 */
public interface IMenuService {
    List<TMenu> findAll();

    void saveOrUpdate(TMenu menu);

    void delete(Long id);

    PageInfo<TMenu> queryPage(MenuQuery query);

    void batchdelete(List<Long> userIds);

    AjaxResult treeMenu();
}
