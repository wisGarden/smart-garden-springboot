package cn.edu.bjfu.igarden.controller;

import cn.edu.bjfu.igarden.entity.BaseEntity;
import cn.edu.bjfu.igarden.entity.Plant;
import cn.edu.bjfu.igarden.entity.PlantDescription;
import cn.edu.bjfu.igarden.entity.PlantTable;
import cn.edu.bjfu.igarden.model.PlantImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlantController {
    @Autowired
    PlantImpl plantImpl;

    /**
     * 病虫害搜索获取某一植物信息
     * @param id 植物id
     */
    @GetMapping(value = "/getPlantDescription")
    public BaseEntity<PlantDescription> getDescription(@RequestParam(value = "id") int id) {
        BaseEntity<PlantDescription> baseEntity = new BaseEntity<>();
        baseEntity.setCode(200);
        baseEntity.setMessage("success");
        PlantDescription description = plantImpl.getDescription(id);
        if (description.getPlantName() != null && description.getPlantDescription() != null) {
            baseEntity.setData(plantImpl.getDescription(id));
        }
        return baseEntity;
    }

    /**
     * 获取植物科与数量
     * @param page 分页
     */
    @GetMapping(value = "/getFamily")
    public BaseEntity<List> getFamily(@RequestParam(value = "page") int page) {
        BaseEntity<List> entity = new BaseEntity<>();
        entity.setCode(200);
        entity.setMessage("success");
        List list = plantImpl.getPlantFamily(page);
        if (list.size() != 0) {
            entity.setData(list);
        }
        return entity;
    }

    /**
     * 根据科获取植物属与数量
     * @param family 科
     * @param page 分页
     */
    @GetMapping(value = "/getGenus")
    public BaseEntity<List> getGenus(@RequestParam(value = "family") String family, @RequestParam(value = "page") int page) {
        BaseEntity<List> entity = new BaseEntity<>();
        entity.setCode(200);
        entity.setMessage("success");
        List list = plantImpl.getPlantGenus(family, page);
        if (list.size() != 0) {
            entity.setData(list);
        }
        return entity;
    }

    /**
     * 根据属获取植物列表
     * @param genus 属
     * @param page 分页
     */
    @GetMapping(value = "/getPlantsByGenus")
    public BaseEntity<List<PlantDescription>> getPlantsByGenus(@RequestParam(value = "genus") String genus, @RequestParam(value = "page") int page) {
        BaseEntity<List<PlantDescription>> entity = new BaseEntity<>();
        entity.setCode(200);
        entity.setMessage("success");
        List<PlantDescription> list = plantImpl.getPlantsByGenus(genus, page);
        if (list.size() != 0) {
            entity.setData(list);
        }
        return entity;
    }

    /**
     * 根据id获取植物详情
     * @param id 植物id
     */
        @GetMapping(value = "/getPlantById")
    public BaseEntity<Plant.ResultBean> getPlantById(@RequestParam(value = "id") int id) {
        BaseEntity<Plant.ResultBean> entity = new BaseEntity<>();
        entity.setCode(200);
        entity.setMessage("success");
        entity.setData(plantImpl.getPlantById(id));
        return entity;
    }


}
