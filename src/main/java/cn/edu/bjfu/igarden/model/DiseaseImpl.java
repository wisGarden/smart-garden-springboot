package cn.edu.bjfu.igarden.model;

import cn.edu.bjfu.igarden.dao.DiseaseRepository;
import cn.edu.bjfu.igarden.dao.PlantRepository;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class DiseaseImpl {
    @Autowired
    DiseaseRepository diseaseRepository;
    @Autowired
    PlantRepository plantRepository;
    @Autowired
    EntityManager entityManager;

    public List getDisease(String name) {
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
                "from disease d inner join plant p on d.plant_id = p.id where d.delete_time = 0 and p.delete_time = 0 and d.disease_type = 0 and %s";
        String mQuery = String.format(baseQuery, whereBuilder.toString());

        // 自定义query，提高查询效率
        Query query = entityManager.createNativeQuery(mQuery);
        // 转换查询方式，返回key-value形式
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        return query.getResultList();
    }
}
