package cn.edu.bjfu.igarden.controller;

import cn.edu.bjfu.igarden.entity.BaseEntity;
import cn.edu.bjfu.igarden.entity.DiseaseTable;
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
     *
     * @param name 输入参数
     * @param page 页数，从1开始
     * @return 病害列表
     */
    @GetMapping(value = "/getDiseaseList")
    public BaseEntity getDiseaseList(@RequestParam("name") String name, @RequestParam("page") int page) {
        BaseEntity<List> baseEntity = new BaseEntity<>();
        baseEntity.setCode(200);
        baseEntity.setMessage("success");
        List list = diseaseImpl.getDiseaseList(0, name, page);
        if (list.size() != 0) {
            baseEntity.setData(list);
        }
        return baseEntity;
    }

    /**
     * 虫害查询，支持多参数，以空格隔开
     *
     * @param name 输入参数
     * @param page 页数，从1开始
     * @return 虫害列表
     */
    @GetMapping(value = "/getInsectList")
    public BaseEntity getInsectList(@RequestParam("name") String name, @RequestParam("page") int page) {
        BaseEntity<List> baseEntity = new BaseEntity<>();
        baseEntity.setCode(200);
        baseEntity.setMessage("success");
        List list = diseaseImpl.getDiseaseList(1, name, page);
        if (list.size() != 0) {
            baseEntity.setData(list);
        }
        return baseEntity;
    }

    @GetMapping(value = "/getDisease")
    public BaseEntity<DiseaseTable> getDisease(@RequestParam("id") int id) {
        BaseEntity<DiseaseTable> baseEntity = new BaseEntity<>();
        baseEntity.setCode(200);
        baseEntity.setMessage("success");
        baseEntity.setData(diseaseImpl.getDisease(id));
        return baseEntity;
    }
}
