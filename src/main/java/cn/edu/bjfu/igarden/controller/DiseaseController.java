package cn.edu.bjfu.igarden.controller;

import cn.edu.bjfu.igarden.entity.BaseEntity;
import cn.edu.bjfu.igarden.model.DiseaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiseaseController {

    @Autowired
    DiseaseImpl diseaseImpl;

    /**
     * 病害查询，支持多参数，以空格隔开
     * @param name 输入参数
     * @return 病害列表
     */
    @GetMapping(value = "/getDiseaseList")
    public BaseEntity getDisease(@RequestParam("name") String name) {
        BaseEntity<List> baseEntity = new BaseEntity<>();
        baseEntity.setCode(200);
        baseEntity.setMessage("success");
        baseEntity.setData(diseaseImpl.getDisease(name));
        return baseEntity;
    }
}
