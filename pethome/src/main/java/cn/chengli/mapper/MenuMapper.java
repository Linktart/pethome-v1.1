package cn.chengli.mapper;

import cn.chengli.domain.TMenu;
import cn.chengli.query.DeptQuery;
import cn.chengli.query.MenuQuery;

import javax.management.Query;
import java.util.List;

/**
 * @chengli
 */
public interface MenuMapper {
    List<TMenu> findAll();

    void save(TMenu menu);

    void update(TMenu menu);

    void delete(Long id);

    List<TMenu> queryPage(MenuQuery query);

    void batchdelete(List<Long> userIds);

    List<TMenu> findByParentId(long id);
}
