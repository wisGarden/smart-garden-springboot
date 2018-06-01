package cn.edu.bjfu.igarden.model;

import cn.edu.bjfu.igarden.dao.PlantRepository;
import cn.edu.bjfu.igarden.entity.PlantDescription;
import cn.edu.bjfu.igarden.entity.PlantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlantImpl {
    @Autowired
    PlantRepository plantRepository;
    public PlantDescription getDescription(int id) {
        PlantTable plantTable = plantRepository.findByIdAndDeleteTime(id, 0);
        PlantDescription plantDescription = new PlantDescription();
        plantDescription.setId(plantTable.getId());
        plantDescription.setPlantName(plantTable.getPlantName());
        plantDescription.setPlantDescription(plantTable.getPlantDescription().trim().equals("") ? plantTable.getPlantFbdq() : plantTable.getPlantDescription());
        plantDescription.setImageUrl(plantTable.getPlantImage());
        return plantDescription;
    }
}
