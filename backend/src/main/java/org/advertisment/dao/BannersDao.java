package org.advertisment.dao;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.advertisment.entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class BannersDao {

    @PersistenceContext
    private EntityManager em;


    @Autowired
    public BannersDao(EntityManager em) {
        this.em = em;
    }

     //receive non-deleted banners from the choosen category
    public List<BannerEntity> getBannersByCategoryName(String req_name, String user_agent, String ip_address, LocalDateTime requestTime) {
        List<BannerEntity> outputList = Collections.emptyList();
        //find category_id
        CategoryEntity ce;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> categoryCriteria = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = categoryCriteria.from(CategoryEntity.class);
        categoryCriteria.select(categoryRoot);
        categoryCriteria.where(cb.equal(categoryRoot.get("req_name"), req_name));
        ce = em.createQuery(categoryCriteria).getSingleResult();
        Integer category_id = ce.getId();

        //запросить баннеры из этой категории без удалённых
        CriteriaQuery<BannerEntity> bannerCriteria = cb.createQuery(BannerEntity.class);
        Root<BannerEntity> bannerRoot = bannerCriteria.from(BannerEntity.class);
        bannerCriteria.select(bannerRoot);
        bannerCriteria.where(
                cb.and(
                        cb.equal(bannerRoot.get("category_id"), category_id),
                        cb.equal(bannerRoot.get("deleted"), false)
                )
        );
        List<BannerEntity> selectedBanners = em.createQuery(bannerCriteria).getResultList();

        //запросить баннеры, которые уже показывались этому клиенту менее суток назад
        CriteriaQuery<Integer> bannerIdCriteria = cb.createQuery(Integer.class);
        Root<RequestEntity> requestRoot = bannerIdCriteria.from(RequestEntity.class);
        bannerIdCriteria.select(requestRoot.get("banner_id"));
        bannerIdCriteria.where(
                cb.and(
                        cb.and(
                                cb.equal(requestRoot.get("user_agent"), user_agent),
                                cb.equal(requestRoot.get("ip_address"), ip_address)
                        ),
                        cb.gt(requestRoot.get("data"),
                                requestTime.plusDays(-1).toLocalTime().toNanoOfDay())
                )
        );
        List<Integer> demonstratedBanners = em.createQuery(bannerIdCriteria).getResultList();

        //удалить показанные за последние сутки баннеры из списка предлагаемых к показу
        for (BannerEntity b : selectedBanners) {
            if (!demonstratedBanners.contains(b.getId())) {
                outputList.add(b);
            }
        }
        return outputList;
    }

    //adding information about request
    public void addRequest(BannerEntity banner, String user_agent, String ip_address, LocalDateTime date) {
        RequestEntity re = new RequestEntity(banner, user_agent, ip_address, date);
        em.persist(re);
        em.flush();
    }

}
