package ru.pozitron.pbe.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.pozitron.pbe.domain.Product;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public String uploadFile(MultipartFile file, String path){
        File uploadDir = new File(path);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file.getOriginalFilename();


        try {
            file.transferTo(new File(path + "/" + resultFileName));
        }
        catch (IOException e){
            System.out.println("upload error:" + e.getMessage());
        }
        return resultFileName;
    }

    public BigDecimal calculatePriceWithDiscount(BigDecimal price,Integer discountPercent){
        BigDecimal discount = price.
                divide(new BigDecimal("100"),7,BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(discountPercent));
        return price.subtract(discount).setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    public ArrayList<Product> productsByDiscountWithLimit(ArrayList<Product> products, long limitMaxSize){
        return products
                .stream()
                .limit(limitMaxSize)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
