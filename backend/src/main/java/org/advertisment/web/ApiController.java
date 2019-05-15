package org.advertisment.web;

import org.advertisment.dao.APIDao;
import org.advertisment.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ApiController {
    private final APIDao dao;

    @Autowired
    public ApiController(APIDao dao) {
        this.dao = dao;
    }

    //get-запрос списка всех категорий
    @GetMapping("/categories")
    public CategoryAPI[] askForCategoriesList() {
        return dao.askForCategoriesList().toArray(new CategoryAPI[0]);
    }

    //get-запрос списка всех баннеров
    @GetMapping("/banners")
    public BannerAPI[] askForBannersList() {
        return dao.askForBannersList().toArray(new BannerAPI[0]);
    }

    //создание баннера через post-запрос
    @PostMapping("/banner")
    public ResponseEntity<String> createNewBanner(@RequestBody BannerAPI newBanner) {
        if (newBanner.getName() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot banner's name");
        } else if (newBanner.getCategory() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot to add the name of category");
        } else if (newBanner.getContent() == "") {
            return ResponseEntity.badRequest()
                    .body("Your banner hasn't any content");
        } else if (dao.isSuchBannerExists(newBanner.getName()) != 0) {
            return ResponseEntity.badRequest()
                    .body("Banner with name '" + newBanner.getName() + "' already exists");
        } else {
            List<CategoryEntity> categoriesWithThisName = dao.isSuchCategoryExists(newBanner.getCategory());
            if (categoriesWithThisName.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Create the category " + newBanner.getCategory() + " first");
            } else {
                CategoryEntity category = categoriesWithThisName.get(0);
                dao.createNewBanner(newBanner.getName(), newBanner.getPrice(), newBanner.getContent(), category);
                return ResponseEntity.ok().build();
            }
        }
    }

    //создание категории через post-запрос
    @PostMapping("/category")
    public ResponseEntity<String> createNewCategory(@RequestBody CategoryAPI newCategory) {
        if (newCategory.getName() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot category's name");
        } else if (newCategory.getReq_name() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot category's request ID");
        } else if (!dao.isSuchCategoryExists(newCategory.getName()).isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Category with name '" + newCategory.getName() + "'already exists");
        } else if (!dao.isCategoryWithThisReqnameExists(newCategory.getReq_name()).isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Category with request ID '" + newCategory.getReq_name() + "'already exists");
        } else {
            dao.createNewCategory(newCategory.getName(), newCategory.getReq_name());
            return ResponseEntity.ok().build();
        }
    }


    //обновление баннера через put-запрос
    @PutMapping("/banner")
    public ResponseEntity<String> updateBanner(@RequestBody BannerAPI banner) {
        if (banner.getName() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot banner's name");
        } else if (banner.getCategory() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot to add the name of category");
        } else if (banner.getContent() == "") {
            return ResponseEntity.badRequest()
                    .body("Your banner hasn't any content");
        } else if (dao.isSuchBannerExists(banner.getName()) != banner.getId()) {
            return ResponseEntity.badRequest()
                    .body("Banner with name '" + banner.getName() + "' already exists");
        } else {
            List<CategoryEntity> categoriesWithThisName = dao.isSuchCategoryExists(banner.getCategory());
            if (categoriesWithThisName.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Create the category " + banner.getCategory() + " first");
            } else {
                CategoryEntity category = categoriesWithThisName.get(0);
                dao.updateBanner(banner.getId(), banner.getName(), banner.getPrice(), banner.getContent(), category);
                return ResponseEntity.ok().build();
            }
        }
    }

    //обновление категории через put-запрос
    @PutMapping("/category")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryAPI category) {
        if (category.getName() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot category's name");
        } else if (category.getReq_name() == "") {
            return ResponseEntity.badRequest()
                    .body("You forgot category's request ID");
        } else {
            List<CategoryEntity> checkName = dao.isSuchCategoryExists(category.getName());
            if (!checkName.isEmpty() && (checkName.get(0).getId() != category.getId())) {
                return ResponseEntity.badRequest()
                        .body("Category with name '" + category.getName() + "'already exists");
            } else {
                List<CategoryEntity> checkCatName = dao.isCategoryWithThisReqnameExists(category.getReq_name());
                if ((!checkCatName.isEmpty() && (checkCatName.get(0).getId() != category.getId()))) {
                    return ResponseEntity.badRequest()
                            .body("Category with request ID '" + category.getReq_name() + "'already exists");
                } else {
                    CategoryEntity categoryForUpdate = new CategoryEntity(category.getId(), category.getName(), category.getReq_name());
                    dao.updateCategory(categoryForUpdate);
                    return ResponseEntity.ok().build();
                }
            }
        }
    }

    //удаление баннера через delete-запрос
    @DeleteMapping("/banner/{id}")
    public ResponseEntity<String> deleteBanner(@PathVariable Integer id) {
        dao.deleteBanner(id);
        return ResponseEntity.ok().build();
    }

    //удаление категории через delete-запрос
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        List<String> undeletedBanners = dao.undeletedBannersInGategory(id);
        if (undeletedBanners.size() > 0) {
            String output = "Cannot delete category, it contains undeleted banners: ";
            for (String banner : undeletedBanners) {
                output += banner + ", ";
            }
            return ResponseEntity.badRequest()
                    .body(output);
        } else {
            dao.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
    }


}
