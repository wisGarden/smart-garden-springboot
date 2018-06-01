package cn.edu.bjfu.igarden.controller;

import cn.edu.bjfu.igarden.entity.BaseEntity;
import cn.edu.bjfu.igarden.entity.PlantDescription;
import cn.edu.bjfu.igarden.model.PlantImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlantController {
    @Autowired
    PlantImpl plantImpl;

    @GetMapping(value = "getPlantDescription")
    public BaseEntity<PlantDescription> getDescription(@RequestParam(value = "id")int id) {
        BaseEntity<PlantDescription> baseEntity = new BaseEntity<>();
        baseEntity.setCode(200);
        baseEntity.setMessage("success");
        PlantDescription description = plantImpl.getDescription(id);
        if (description.getPlantName() != null && description.getPlantDescription() != null) {
            baseEntity.setData(plantImpl.getDescription(id));
        }
        return baseEntity;
    }
}
