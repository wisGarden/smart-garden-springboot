package cn.edu.bjfu.igarden.controller;

import cn.edu.bjfu.igarden.dao.PlantRepository;
import cn.edu.bjfu.igarden.entity.BaseEntity;
import cn.edu.bjfu.igarden.entity.Plant;
import cn.edu.bjfu.igarden.entity.PlantList;
import cn.edu.bjfu.igarden.entity.PlantTable;
import cn.edu.bjfu.igarden.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RestController
public class PlantIdtController {
    @Autowired
    private SignController signController;
    @Autowired
    private PlantRepository plantRepository;
    private static Logger logger = LoggerFactory.getLogger(PlantIdtController.class);

    // Duplicate 处理文件上传
    @RequestMapping(value = "/testuploadimg", method = RequestMethod.POST)
    public PlantList uploadImg(@RequestParam("file") MultipartFile file,
                               HttpServletRequest request) {
        logger.debug("test: 调用成功！！！");
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        logger.debug("test: " + fileName);
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/
        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            return null;
        }
        FileUtil.reduceImg(filePath, fileName, 200, 200);
        File file1 = new File(filePath + "thumb" + fileName);
        String base64;
        if (file1.exists()) {
            base64 = FileUtil.getImageStr(filePath + "thumb" + fileName);
        } else {
            base64 = FileUtil.getImageStr(filePath + fileName);
        }

        //返回json
        return signController.getFlowerList(base64);
    }

    @PostMapping(value = "/getPlantList")
    public BaseEntity<List<PlantList.ResultBean>> uploadImgBase64(@RequestParam("img") String img) {
        PlantList plant = signController.getFlowerList(img);
        BaseEntity<List<PlantList.ResultBean>> entity = new BaseEntity<>();
        if (plant.getStatus() == 0) {
            entity.setCode(200);
            entity.setMessage("success");
            entity.setData(plant.getResult());
        } else {
            entity.setCode(500);
            entity.setMessage(plant.getMessage());
        }
        logger.debug("test:" + img);
        logger.debug("test:" + plant.getResult().get(0).getInfoUrl());
        return entity;
    }

    @GetMapping(value = "/getPlant")
    public BaseEntity<Plant.ResultBean> getPlant(@RequestParam("code") String infoUrl) {
        Plant plant = signController.getFlower(infoUrl);
        BaseEntity<Plant.ResultBean> entity = new BaseEntity<>();
        if (plant.getStatus() == 0) {
            entity.setCode(200);
            entity.setMessage("success");
            entity.setData(plant.getResult());
        } else {
            entity.setCode(500);
            entity.setMessage(plant.getMessage());
        }

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

        logger.debug("test:" + plant.getStatus());
        logger.debug("test:" + plant.getResult().getDescription());
        return entity;
    }

    // Duplicate
    @PostMapping(value = "/postImg")
    public String postImg(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        logger.debug("test: " + fileName);
        return fileName;
    }

    // Duplicate
    @GetMapping(value = "/testtest")
    public String test() {
        return "Hello world";
    }

    // Duplicate
    @GetMapping(value = "/planttest")
    public BaseEntity<PlantTable> plantTest(@RequestParam("name") String name) {
        BaseEntity<PlantTable> baseEntity = new BaseEntity<>();
//        PlantTable plantTable = plantRepository.findByPlantName(name);
        PlantTable plantTable = new PlantTable();
        plantTable.setPlantName(name);
        plantTable = savePlant(plantTable);
        if (plantTable == null) {
            baseEntity.setCode(404);
            baseEntity.setMessage("not found");
            return baseEntity;
        } else {
            baseEntity.setCode(200);
            baseEntity.setMessage("success");
            baseEntity.setData(plantTable);
        }
        return baseEntity;
    }

    private PlantTable savePlant(PlantTable plant) {
        PlantTable plantTable = plantRepository.findByPlantName(plant.getPlantName());
        if (plantTable != null) {
            plant.setId(plantTable.getId());
            plant.setCreateTime(plantTable.getCreateTime());
        }
        return plantRepository.save(plant);
    }
}
