package com.ganesha.webshop.config;

import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public DataInitializer(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void init() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();

        Category scents = new Category();
        scents.setCategoryName("Illatok");
        categoryRepository.save(scents);

        Product roseIncense = new Product();
        roseIncense.setProductName("Rózsa füstölő – A szív nyugalmának és a szeretet illata");
        String description = """
            <p>A rózsa nemcsak a szerelem szimbóluma, hanem az egyik legmagasabb rezgésű illat a természetben.
            Lágy, virágos aromája megnyugtatja az elmét, nyitja a szívet, és finoman emeli a hangulatot.
            Kiváló választás érzelmi tisztuláshoz, bensőséges pillanatokhoz vagy egyszerűen a mindennapi kényeztetéshez.</p>

            <h4>Miért szeretik oly sokan?</h4>
            <ul>
              <li>Édesen virágos, mégsem tolakodó illat</li>
              <li>Segít oldani a feszültséget, támogatja az önszeretetet</li>
              <li>Harmonizálja a légkört, meghittebbé teszi az otthonodat</li>
            </ul>

            <h4>Natur's Nest – Rózsa füstölő</h4>
            <p>Lágy illat, mély érzelmek – természetesen.
            A Natur's Nest Rózsa füstölő a klasszikus rózsa illat természetes, harmonikus változata – nem tolakodó, hanem finoman körbeölel.
            Tökéletes választás, ha szeretnéd megnyugtatni az elmédet, megnyitni a szívedet vagy egyszerűen csak szebbé tenni a pillanatot.</p>

            <h4>Illatprofil:</h4>
            <ul>
              <li>Virágos, édes és tiszta rózsaillat</li>
              <li>Érzelmi kiegyensúlyozásra és hangulatjavításra</li>
              <li>Természetes alapanyagokból, mesterséges illatanyagok nélkül</li>
            </ul>
        """;
        roseIncense.setProductDescription(description);
        roseIncense.setPrice(780);
        roseIncense.setCategories(List.of(scents));

        ProductImage img1 = new ProductImage();
        img1.setUrl("/images/products/incense-nagchampa-1.1.jpeg");
        img1.setProduct(roseIncense);

        ProductImage img2 = new ProductImage();
        img2.setUrl("/images/products/incense-nagchampa-1.2.jpeg");
        img2.setProduct(roseIncense);

        roseIncense.setImages(List.of(img1, img2));

        productRepository.save(roseIncense);


    }



}
