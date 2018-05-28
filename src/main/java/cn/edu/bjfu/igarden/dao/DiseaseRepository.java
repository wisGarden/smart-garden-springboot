package cn.edu.bjfu.igarden.dao;

import cn.edu.bjfu.igarden.entity.DiseaseTable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DiseaseRepository extends JpaRepository<DiseaseTable, Integer> {

    DiseaseTable findByIdAndDeleteTime(int id, long deleteTime);
}
