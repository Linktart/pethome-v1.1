package cn.chengli.service.impl;

import cn.chengli.domain.TMenu;
import cn.chengli.mapper.MenuMapper;
import cn.chengli.query.MenuQuery;
import cn.chengli.service.IMenuService;
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
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<TMenu> findAll() {
        return menuMapper.findAll();
    }

    /**
     * 新增或修改
     *
     * @param menu
     */
    @Override
    public void saveOrUpdate(TMenu menu) {
        if (menu.getId() == null) {
            menuMapper.save(menu);
        } else {
            menuMapper.update(menu);
        }
    }

    @Override
    public void delete(Long id) {
        menuMapper.delete(id);
    }

    @Override
    public PageInfo<TMenu> queryPage(MenuQuery query) {

        //开启pagehelper
        PageHelper.startPage(query.getCurrentPage(), query.getPageSize());

        List<TMenu> list = menuMapper.queryPage(query);
        return new PageInfo<TMenu>(list);
    }

    @Override
    public void batchdelete(List<Long> userIds) {
        menuMapper.batchdelete(userIds);
    }

    /**
     * 查询所有部门及其下级部门
     *
     * @return
     */
    @Override
    public AjaxResult treeMenu() {
        //查询顶级部门
        try {
            List<TMenu> list = menuMapper.findByParentId(0L);
            findAllMenus(list);
            return AjaxResult.createSuccess("查询成功", list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.createError(e.getMessage());
        }
    }

    void findAllMenus(List<TMenu> list) {
        if (list != null && list.size() > 0) {
            for (TMenu menu : list) {
                List<TMenu> children = menuMapper.findByParentId(menu.getId());
                menu.setChildren(children);
                findAllMenus(children);
            }
        }
    }
}
