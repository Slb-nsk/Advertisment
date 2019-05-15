package org.advertisment.entities;

public class CategoryAPI {
    private Integer id;
    private String name;
    private String req_name;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CategoryAPI(Integer id, String name, String req_name) {
        this.id = id;
        this.name = name;
        this.req_name = req_name;
    }
}
