package org.advertisment.entities;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "req_name")
    private String req_name;

    @Column(name = "deleted")
    private Boolean deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getReq_name() {
        return req_name;
    }

    public void setReq_name(String req_name) {
        this.req_name = req_name;
    }


    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CategoryEntity() {
    }

    public CategoryEntity(String name, String req_name, Boolean deleted) {
        this.name = name;
        this.req_name = req_name;
        this.deleted = deleted;
    }

    public CategoryEntity(Integer id, String name, String req_name) {
        this.id = id;
        this.name = name;
        this.req_name = req_name;
        this.deleted = false;
    }

    public CategoryEntity(Integer id, String name, String req_name, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.req_name = req_name;
        this.deleted = deleted;
    }
}
