package cn.edu.bjfu.igarden.model;

import cn.edu.bjfu.igarden.controller.SignController;
import cn.edu.bjfu.igarden.dao.PlantRepository;
import cn.edu.bjfu.igarden.entity.BaseEntity;
import cn.edu.bjfu.igarden.entity.Plant;
import cn.edu.bjfu.igarden.entity.PlantList;
import cn.edu.bjfu.igarden.entity.PlantTable;
import cn.edu.bjfu.igarden.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlantIdtImpl {
    @Autowired
    private SignController signController;
    @Autowired
    private PlantRepository plantRepository;

    public BaseEntity<List<PlantList.ResultBean>> uploadImgBase64(String img) {
        PlantList plant = signController.getFlowerList(img);
        BaseEntity<List<PlantList.ResultBean>> entity = new BaseEntity<>();
        entity.setCode(plant.getStatus() == 0 ? 200 : 500);
        entity.setMessage(plant.getStatus() == 0 ? "success" : plant.getMessage());
        entity.setData(plant.getStatus() == 0 ? plant.getResult() : null);
        LogUtil.d("test:" + img);
        LogUtil.d("test:" + plant.getResult().get(0).getInfoUrl());
        return entity;
    }

    public BaseEntity<Plant.ResultBean> getPlant(String infoUrl) {
        Plant plant = signController.getFlower(infoUrl);
        BaseEntity<Plant.ResultBean> entity = new BaseEntity<>();
        entity.setCode(plant.getStatus() == 0 ? 200 : 500);
        entity.setMessage(plant.getStatus() == 0 ? "success" : plant.getMessage());
        entity.setData(plant.getStatus() == 0 ? plant.getResult() : null);

        // 保存植物信息
        PlantTable plantTable = new PlantTable();
        Plant.ResultBean plantResult = plant.getResult();
        Plant.ResultBean.InfoBean plantInfo = plantResult.getInfo();
        plantTable.setPlantName(plantResult.getNameStd());
        plantTable.setPlantLatinName(plantResult.getNameLt());
        plantTable.setPlantFamily(plantResult.getFamilyCn());
        plantTable.setPlantAlias(plantResult.getAlias());
        plantTable.setPlantGenus(plantResult.getGenusCn());
        StringBuilder saveImages = new StringBuilder();
        List<String> images = plantResult.getImages();
        for (int i = 0; i < images.size(); i++) {
            if (i == images.size() - 1) {
                saveImages.append(images.get(i));
            } else {
                saveImages.append(images.get(i)).append("#");
            }
        }
        plantTable.setPlantImage(saveImages.toString());
        plantTable.setPlantDescription(plantResult.getDescription());
        plantTable.setPlantXgsc(plantInfo.getXgsc());
        plantTable.setPlantJzgy(plantInfo.getJzgy());
        plantTable.setPlantFbdq(plantInfo.getFbdq());
        plantTable.setPlantYhjs(plantInfo.getYhjs());
        plantTable.setPlantBxtz(plantInfo.getBxtz());
        plantTable.setPlantHksj(plantInfo.getHksj());
        plantTable.setCreateTime(System.currentTimeMillis() / 1000);
        plantTable.setUpdateTime(System.currentTimeMillis() / 1000);
        savePlant(plantTable);

        LogUtil.d("test:" + plant.getStatus());
        LogUtil.d("test:" + plant.getResult().getDescription());
        return entity;
    }

    private void savePlant(PlantTable plant) {
        PlantTable plantTable = plantRepository.findByPlantName(plant.getPlantName());
        if (plantTable != null) {
            plant.setId(plantTable.getId());
            plant.setCreateTime(plantTable.getCreateTime());
        }
        plantRepository.save(plant);
    }
}
