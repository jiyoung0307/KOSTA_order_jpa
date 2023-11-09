package com.example.order_jpa.controller;

import com.example.order_jpa.dto.ProductUpdateDTO;
import com.example.order_jpa.entity.Product;
import com.example.order_jpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    /** putMapping이나 deleteMapping 같은 경우 RESTful에서만 지원 / HTML같은 화면은 GET, POST만 존재 */

    /** 상품 조회 */
    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<Product> products =  productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/productList";
    }

    /** 상품등록 */
    @GetMapping("/add")
    public String addProduct() {
        /** 새로운 데이터를 받아옴 */
        return "product/productForm";
    }

    /** view에서 form으로 받아온 내용 추가 */
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/product/list";    /** PRG */
    }

    /** 상품 삭제 */
    @PostMapping("/list")
    public String deleteProduct(@RequestParam long productId) {
        productService.deleteProduct(productId);
        return "redirect:/product/list";
    }

    /** 상품 상세 조회 */
    @GetMapping("/info/{productId}")
    public String getProductInfo(Model model, @PathVariable Long productId) {
        Product product = productService.getProductInfo(productId);
        model.addAttribute("product", product);
        return "product/productInfo";
    }

    @GetMapping("/update/{productId}")
    public String updateProduct(Model model, @PathVariable Long productId) {
        Product product = productService.getProductInfo(productId);
        model.addAttribute("product", product);
        return "product/productUpdate";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductUpdateDTO productUpdateDTO) {
        productService.updateProduct(productUpdateDTO);
        return "redirect:/product/list";
    }
}