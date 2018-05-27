package cn.edu.bjfu.igarden.dao;

import cn.edu.bjfu.igarden.entity.PlantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<PlantTable, Integer> {
    PlantTable findById(int id);
    PlantTable findByPlantName(String name);
}
