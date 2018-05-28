package cn.edu.bjfu.igarden.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "disease")
public class DiseaseTable {
    @Id
    @NotNull
    @GeneratedValue
    private int id;
    private int plantId;
    private String diseaseName;
    private String diseasePart;
    private String diseasePeriod;
    private int diseaseType;
    private String diseaseOptionRel;
    private String diseaseDescription;
    private String diseaseSymptom;
    private String diseaseCharacter;
    private String diseaseCustom;
    private String diseaseLaw;
    private String diseasePrevention;
    private String diseaseImage;
    private int hits;
    private long createTime;
    private long updateTime;
    private long deleteTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseasePart() {
        return diseasePart;
    }

    public void setDiseasePart(String diseasePart) {
        this.diseasePart = diseasePart;
    }

    public String getDiseasePeriod() {
        return diseasePeriod;
    }

    public void setDiseasePeriod(String diseasePeriod) {
        this.diseasePeriod = diseasePeriod;
    }

    public int getDiseaseType() {
        return diseaseType;
    }

    public void setDiseaseType(int diseaseType) {
        this.diseaseType = diseaseType;
    }

    public String getDiseaseOptionRel() {
        return diseaseOptionRel;
    }

    public void setDiseaseOptionRel(String diseaseOptionRel) {
        this.diseaseOptionRel = diseaseOptionRel;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public String getDiseaseSymptom() {
        return diseaseSymptom;
    }

    public void setDiseaseSymptom(String diseaseSymptom) {
        this.diseaseSymptom = diseaseSymptom;
    }

    public String getDiseaseCharacter() {
        return diseaseCharacter;
    }

    public void setDiseaseCharacter(String diseaseCharacter) {
        this.diseaseCharacter = diseaseCharacter;
    }

    public String getDiseaseCustom() {
        return diseaseCustom;
    }

    public void setDiseaseCustom(String diseaseCustom) {
        this.diseaseCustom = diseaseCustom;
    }

    public String getDiseaseLaw() {
        return diseaseLaw;
    }

    public void setDiseaseLaw(String diseaseLaw) {
        this.diseaseLaw = diseaseLaw;
    }

    public String getDiseasePrevention() {
        return diseasePrevention;
    }

    public void setDiseasePrevention(String diseasePrevention) {
        this.diseasePrevention = diseasePrevention;
    }

    public String getDiseaseImage() {
        return diseaseImage;
    }

    public void setDiseaseImage(String diseaseImage) {
        this.diseaseImage = diseaseImage;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void hitsPlus() {
        this.hits++;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(long deleteTime) {
        this.deleteTime = deleteTime;
    }
}
