package com.nin.xloyalty.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nin.xloyalty.model.Product;
import com.nin.xloyalty.service.ProductService;

@RestController
@RequestMapping("/api/v1")
public class ApiProductController {
    private ProductService productService;
    public ApiProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<Map<String, Object>> findProductByName(@RequestParam(value = "name") String name ) {
        try {
            List<Product> currentProduct = productService.findProductByName(name);
            List<Map<String, Object>> result = new ArrayList<>();
            for(Product product : currentProduct) {
                Map<String, Object> obj = new HashMap<>();
                obj.put("productId", product.getProductId());
                obj.put("code", product.getCode());
                obj.put("title", product.getName());
                obj.put("image", product.getImage());
                obj.put("webLink", product.getWebLink());
                obj.put("description", product.getDescription());
                obj.put("hasNew", product.getHasNew());
                result.add(obj);
            }
            
            Map<String, Object> out = new HashMap<String, Object>() {{
                put("data", result);
                put("error", 0);

            }};
            return new ResponseEntity<>(out, HttpStatus.OK);

        }catch (Exception e) {
            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("Message", e.getMessage());
            responseMap.put("data", responseMap);
            responseMap.put("error", -1);
            return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
        }

    }
}
