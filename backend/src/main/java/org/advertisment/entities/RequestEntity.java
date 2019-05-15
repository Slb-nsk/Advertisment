package org.advertisment.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request")
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "banner_id", referencedColumnName = "id")
    private BannerEntity banner;

    @Column(name = "user_agent")
    private String user_agent;

    @Column(name = "ip_address")
    private String ip_address;

    @Column(name = "date")
    private LocalDateTime date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BannerEntity getBanner() {
        return banner;
    }

    public void setBanner(BannerEntity banner_id) {
        this.banner = banner_id;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public RequestEntity() {
    }

    public RequestEntity(BannerEntity banner, String user_agent, String ip_address, LocalDateTime date) {
        this.banner = banner;
        this.user_agent = user_agent;
        this.ip_address = ip_address;
        this.date = date;
    }
}
