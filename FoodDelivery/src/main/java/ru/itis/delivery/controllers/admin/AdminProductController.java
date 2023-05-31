package ru.itis.delivery.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.dto.product.RequestProductForm;
import ru.itis.delivery.services.ProductService;

import javax.validation.Valid;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    ProductService productService;

    @GetMapping("/add")
    public String getPageForAddingProduct(Model model){
        model.addAttribute("requestProductForm", new RequestProductForm());
        return "admin/addProduct";
    }

    @PostMapping("/add")
    public String addNewProduct(@ModelAttribute("requestProductForm") @Valid RequestProductForm requestProductForm,
                                BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "admin/addProduct";
        }

        ProductDto savedProduct = productService.save(requestProductForm);

        return "redirect:/admin/product/"+savedProduct.getId();
    }

    @GetMapping("/{product-id}")
    public String getProductPage(@PathVariable("product-id") Long productId, Model model,
                                 @ModelAttribute("errorValidMessage") String message){

        if(message != null){
            model.addAttribute("errorValidMessage", message);
        }

        ProductDto productDto = productService.getProductById(productId);
        model.addAttribute("product", productDto);

        return "admin/productPage";
    }

    @PutMapping("/{product-id}")
    public String updateProduct(@PathVariable("product-id") Long productId,
                                @ModelAttribute("requestProductForm") @Valid RequestProductForm requestProductForm,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if (bindingResult.getAllErrors().size() == 6){
            redirectAttributes.addAttribute("errorValidMessage", "упс, заполни хотя бы одно поле, чтобы изменить эту позицию меню");

            return "redirect:/admin/product/"+productId;
        }

        productService.updateProduct(productId, requestProductForm);

        return "redirect:/admin/product/"+productId;

    }

    @DeleteMapping("/{product-id}")
    public String deleteProduct(@PathVariable("product-id") Long productId){
        productService.delete(productId);

        return "redirect:/admin/menu";
    }

}
