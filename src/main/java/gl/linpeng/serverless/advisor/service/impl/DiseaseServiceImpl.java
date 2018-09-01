package gl.linpeng.serverless.advisor.service.impl;

import gl.linpeng.gf.base.PageInfo;
import gl.linpeng.serverless.advisor.model.Disease;
import gl.linpeng.serverless.advisor.service.DiseaseService;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Paginator;

import java.util.List;

/**
 * @author lin.peng
 * @since 1.0
 **/
public class DiseaseServiceImpl implements DiseaseService {

    @Override
    public Disease getDiseaseById(Long id) {
        Base.open();
        Disease disease = Disease.findById(id);
        Base.close();
        return disease;
    }

    @Override
    public PageInfo query(String query, Integer pageSize, Integer page) {
        Base.open();
        Paginator p = new Paginator(Disease.class, pageSize, "name like ?", "%" + query + "%").orderBy("name desc");
        List<Disease> list = p.getPage(page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(p.getCount());
        pageInfo.setPageSize(pageSize);
        pageInfo.setPage(page);
        // lazy list here,so do not close connection right now
        // Base.close();
        return pageInfo;
    }
}
