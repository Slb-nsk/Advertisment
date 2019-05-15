package org.advertisment.dao;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.advertisment.entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Repository
@Transactional
public class APIDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public APIDao(EntityManager em) {
        this.em = em;
    }

    //receive the list of all undeleted categories
    public LinkedList<CategoryAPI> askForCategoriesList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> categoryCriteria = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = categoryCriteria.from(CategoryEntity.class);
        categoryCriteria.select(categoryRoot);
        categoryCriteria.where(cb.equal(categoryRoot.get("deleted"), false));
        List<CategoryEntity> selectedCategories = em.createQuery(categoryCriteria).getResultList();
        LinkedList<CategoryAPI> outputList = new LinkedList<>();
        if (!selectedCategories.isEmpty()) {
            for (CategoryEntity c : selectedCategories) {
                CategoryAPI ca = new CategoryAPI(c.getId(), c.getName(), c.getReq_name());
                outputList.add(ca);
            }
        }
        return outputList;
    }

    //find category's name by id
    public String findCategoryNameById(Integer id) {
        CategoryEntity ce;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> categoryCriteria = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = categoryCriteria.from(CategoryEntity.class);
        categoryCriteria.select(categoryRoot);
        categoryCriteria.where(cb.equal(categoryRoot.get("id"), id));
        ce = em.createQuery(categoryCriteria).getSingleResult();
        return ce.getName();
    }

    //receive the list of all undeleted banners
    public LinkedList<BannerAPI> askForBannersList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BannerEntity> bannerCriteria = cb.createQuery(BannerEntity.class);
        Root<BannerEntity> bannerRoot = bannerCriteria.from(BannerEntity.class);
        bannerCriteria.select(bannerRoot);
        bannerCriteria.where(cb.equal(bannerRoot.get("deleted"), false));
        List<BannerEntity> selectedBanners = em.createQuery(bannerCriteria).getResultList();
        LinkedList<BannerAPI> outputList = new LinkedList<>();
        if (!selectedBanners.isEmpty()) {
            for (BannerEntity b : selectedBanners) {
                String categoryName = findCategoryNameById(b.getCategory().getId());
                BannerAPI ba = new BannerAPI(b.getId(), b.getName(), b.getContent(), b.getPrice(), categoryName);
                outputList.add(ba);
            }
        }
        return outputList;
    }

    //check the existence of banner with this name
    public Integer isSuchBannerExists(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BannerEntity> bannerCriteria = cb.createQuery(BannerEntity.class);
        Root<BannerEntity> bannerRoot = bannerCriteria.from(BannerEntity.class);
        bannerCriteria.select(bannerRoot);
        bannerCriteria.where(cb.equal(bannerRoot.get("name"), name));
        List<BannerEntity> selectedBanners = em.createQuery(bannerCriteria).getResultList();
        if (selectedBanners.isEmpty()) {
            return 0;
        } else {
            return selectedBanners.get(0).getId();
        }
    }

    //return list of categories with this name (i.e. emptylist or list with only one element)
    public List<CategoryEntity> isSuchCategoryExists(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> categoryCriteria = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = categoryCriteria.from(CategoryEntity.class);
        categoryCriteria.select(categoryRoot);
        categoryCriteria.where(cb.equal(categoryRoot.get("name"), name));
        return em.createQuery(categoryCriteria).getResultList();
    }

    //return list of categories with this reqname (i.e. emptylist or list with only one element)
    public List<CategoryEntity> isCategoryWithThisReqnameExists(String categoryReqname) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CategoryEntity> categoryCriteria = cb.createQuery(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = categoryCriteria.from(CategoryEntity.class);
        categoryCriteria.select(categoryRoot);
        categoryCriteria.where(cb.equal(categoryRoot.get("req_name"), categoryReqname));
        return em.createQuery(categoryCriteria).getResultList();
    }

    public void makeCategoryUndeleted(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<CategoryEntity> update = cb.createCriteriaUpdate(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = update.from(CategoryEntity.class);
        update.set("deleted", false);
        update.where(cb.equal(categoryRoot.get("id"), id));
        em.createQuery(update).executeUpdate();
    }

    public void createNewBanner(String name, BigDecimal price, String content, CategoryEntity category) {
        makeCategoryUndeleted(category.getId());
        BannerEntity newBanner = new BannerEntity(name, price, category, content, false);
        em.persist(newBanner);
        em.flush();
    }

    public void createNewCategory(String name, String req_name) {
        CategoryEntity newCategory = new CategoryEntity(name, req_name, false);
        em.persist(newCategory);
        em.flush();
    }

    public void updateBanner(Integer id, String name, BigDecimal price, String content, CategoryEntity category) {
        BannerEntity banner = new BannerEntity(id, name, price, category, content);
        em.merge(banner);
    }

    public void updateCategory(CategoryEntity category) {
        em.merge(category);
    }

    public void deleteBanner(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<BannerEntity> update = cb.createCriteriaUpdate(BannerEntity.class);
        Root<BannerEntity> bannerRoot = update.from(BannerEntity.class);
        update.set("deleted", true);
        update.where(cb.equal(bannerRoot.get("id"), id));
        em.createQuery(update).executeUpdate();
    }

    public List<String> undeletedBannersInGategory(Integer category_id) {
        Query q = em.createNativeQuery("SELECT b.name FROM advertisment.banner b JOIN advertisment.category c " +
                "ON b.category_id = c.id WHERE b.deleted = ? AND c.id=?")
                .setParameter(1, false)
                .setParameter(2, category_id);
        return q.getResultList();
    }

    public void deleteCategory(Integer id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<CategoryEntity> update = cb.createCriteriaUpdate(CategoryEntity.class);
        Root<CategoryEntity> categoryRoot = update.from(CategoryEntity.class);
        update.set("deleted", true);
        update.where(cb.equal(categoryRoot.get("id"), id));
        em.createQuery(update).executeUpdate();
    }

}
