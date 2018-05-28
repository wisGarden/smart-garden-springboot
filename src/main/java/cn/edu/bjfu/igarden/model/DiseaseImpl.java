package cn.edu.bjfu.igarden.model;

import cn.edu.bjfu.igarden.dao.DiseaseRepository;
import cn.edu.bjfu.igarden.dao.PlantRepository;
import cn.edu.bjfu.igarden.entity.DiseaseTable;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiseaseImpl {
    private static final int ITEM_NUM = 10;
    @Autowired
    DiseaseRepository diseaseRepository;
    @Autowired
    PlantRepository plantRepository;
    @Autowired
    EntityManager entityManager;

    public DiseaseTable getDisease(int id) {
        return diseaseRepository.findByIdAndDeleteTime(id, 0);
    }

    public List getDiseaseList(int type, String name, int page) {
        // 多参数where子句组装
        String[] names = name.trim().split(" ");
        String baseWhere = "d.disease_name like '%%%s%%'";
        String and = " and ";
        StringBuilder whereBuilder = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            if (i == names.length - 1) {
                whereBuilder.append(String.format(baseWhere, names[i]));
            } else {
                whereBuilder.append(String.format(baseWhere, names[i])).append(and);
            }
        }

        String baseQuery = "select d.id, d.disease_name name, d.disease_description description, p.plant_name plantName, d.disease_image imageUrl " +
                "from disease d inner join plant p on d.plant_id = p.id where d.delete_time = 0 and p.delete_time = 0 and " +
                "(select (@rowNum\\:=@rowNum+1) from (Select (@rowNum \\:=0) ) b) > (%1$d * %2$d) and d.disease_type = %3$d and %4$s order by d.id asc limit %2$d";
        String mQuery = String.format(baseQuery, page - 1, ITEM_NUM, type, whereBuilder.toString());

        // 自定义query，提高查询效率
        Query query = entityManager.createNativeQuery(mQuery);
        // 转换查询方式，返回key-value形式
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();
    }

    public Page<DiseaseTable> findAll(int page) {
        List<Sort.Order> list = new ArrayList<>();
        list.add(new Sort.Order(Sort.Direction.DESC, "hits"));
        list.add(new Sort.Order(Sort.Direction.DESC, "updateTime"));
        Pageable pageable = new PageRequest(page - 1, ITEM_NUM, new Sort(list));
        return diseaseRepository.findByDeleteTime(0, pageable);
    }

    public void save(DiseaseTable diseaseTable) {
        diseaseRepository.save(diseaseTable);
    }
}
