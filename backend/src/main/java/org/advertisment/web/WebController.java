package org.advertisment.web;

import org.advertisment.dao.BannersDao;
import org.advertisment.entities.BannerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;


@RestController
public class WebController {
    private final BannersDao dao;

    @Autowired
    public WebController(BannersDao dao) {
        this.dao = dao;
    }

    //по get-запросу выдать рекламный баннер
    @GetMapping("/bid/{req_name}")
    public ResponseEntity<String> sendBanner(@PathVariable String req_name,
                                             @RequestHeader("User-Agent") String userAgent,
                                             HttpServletRequest httpServletRequest) {
        String clientIp = httpServletRequest.getRemoteAddr();
        LocalDateTime requestTime = LocalDateTime.now();
        List<BannerEntity> listBanners = dao.getBannersByCategoryName(req_name, userAgent, clientIp, requestTime);
        if (listBanners.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            BannerEntity outputBanner = listBanners.get(0);
            BigDecimal price = outputBanner.getPrice();
            for (BannerEntity b : listBanners) {
                if (b.getPrice().compareTo(price) >= 0) {
                    price = b.getPrice();
                    outputBanner = b;
                }
            }
            dao.addRequest(outputBanner, userAgent, clientIp, requestTime);
            return ResponseEntity.ok().body(outputBanner.getContent());
        }
    }

}