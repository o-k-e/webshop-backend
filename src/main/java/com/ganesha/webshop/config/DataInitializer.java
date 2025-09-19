package com.ganesha.webshop.config;

import com.ganesha.webshop.model.entity.product.Category;
import com.ganesha.webshop.model.entity.product.Product;
import com.ganesha.webshop.model.entity.product.ProductImage;
import com.ganesha.webshop.model.entity.user.Member;
import com.ganesha.webshop.model.entity.user.Role;
import com.ganesha.webshop.repository.CategoryRepository;
import com.ganesha.webshop.repository.MemberRepository;
import com.ganesha.webshop.repository.ProductRepository;
import com.ganesha.webshop.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private int imageCounter = 1;

    public DataInitializer(ProductRepository productRepository, CategoryRepository categoryRepository,
                           RoleRepository roleRepository, MemberRepository memberRepository,
                           PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        createProductsWithRelatedEntities();
        createMembersWithRoles();
    }

    private void createProductsWithRelatedEntities() {
        Category scents = saveCategory("Scents");
        Category ayurveda = saveCategory("Ayurveda");
        Category vastu = saveCategory("Vastu");
        Category yoga = saveCategory("Yoga");
        Category home = saveCategory("Home Decor");
        Category clothes = saveCategory("Clothing");
        Category minerals = saveCategory("Minerals and Jewelry");

        // 1. Rose Incense
        saveProduct("Rose Incense", getRoseDescription(), 780, List.of(scents),
                List.of("rozsa-fustolo-1.1.jpeg", "rozsa-fustolo-1.2.jpeg"));

        // 2. Sandalwood Incense
        saveProduct("Sandalwood Incense", getSandalwoodDescription(), 780, List.of(scents),
                List.of("incense-sandalwood-1.1.jpeg"));

        // 3. Copper Tongue Cleaner
        saveProduct("Copper Tongue Cleaner", "An Ayurvedic hygiene tool for removing residue from the tongue. Morning use freshens breath and supports digestion.",
                1900, List.of(ayurveda), List.of("rez-nyelvkaparo.jpeg"));

        // 4. Sri Yantra
        saveProduct("Sri Yantra", "Sri Yantra is one of the strongest symbols of Vastu, bringing abundance, spiritual growth, and harmonious energy to the space.",
                4500, List.of(vastu), List.of("sri-yantra.jpeg"));

        // 5. Meditation Cushion
        saveProduct("Meditation Cushion", "A cushion made with natural bio filling that provides stable and comfortable support for longer seated practices.",
                14500, List.of(yoga), List.of("meditacios-parna.jpeg"));

        // 6. Mandala Patterned Bedspread
        saveProduct("Mandala Patterned Bedspread", "Indian cotton bedspread with mandala pattern. Can be used for both decorative and functional purposes, size 240x210 cm.",
                9500, List.of(home), List.of("mandala-mintas-agyterito.jpeg"));

        // 7. Women's Indian Tunic
        saveProduct("Women's Indian Tunic", "Light, airy tunic made from 100% cotton, ideal for summer wear or yoga.",
                9500, List.of(clothes), List.of("noi-indiai-tunika.jpeg"));

        // 8. Clear Quartz Bracelet
        saveProduct("Clear Quartz Bracelet", "Clear quartz is known for its cleansing and energizing effects. Worn on the wrist, this bracelet supports focus and inner balance.",
                4500, List.of(minerals), List.of("hegyikristaly-karkoto.jpeg"));

        // 9–24. Dummy variants
        saveProduct("Rose Incense Deluxe", getRoseDescription(), 880, List.of(scents),
                List.of("rozsa-fustolo-1.1.jpeg", "rozsa-fustolo-1.2.jpeg"));

        saveProduct("Sandalwood Incense Classic", getSandalwoodDescription(), 820, List.of(scents),
                List.of("incense-sandalwood-1.1.jpeg"));

        saveProduct("Copper Tongue Cleaner Plus", "A premium version of the classic Ayurvedic tongue scraper, slightly wider and thicker for extra durability.",
                2100, List.of(ayurveda), List.of("rez-nyelvkaparo.jpeg"));

        saveProduct("Sri Yantra Mini", "A smaller version of the Sri Yantra, ideal for desks or meditation corners. Brings harmony and focus.",
                3500, List.of(vastu), List.of("sri-yantra.jpeg"));

        saveProduct("Eco Meditation Cushion", "An eco-conscious version made with recycled materials and organic cotton cover.",
                13900, List.of(yoga), List.of("meditacios-parna.jpeg"));

        saveProduct("Mandala Gold Bedspread", "Limited edition golden-tone mandala patterned cotton bedspread. Size: 240x210 cm.",
                9900, List.of(home), List.of("mandala-mintas-agyterito.jpeg"));

        saveProduct("Floral Cotton Tunic", "Soft cotton tunic with subtle floral prints. Ideal for casual wear and summer yoga.",
                9200, List.of(clothes), List.of("noi-indiai-tunika.jpeg"));

        saveProduct("Clear Quartz Bracelet Deluxe", "Enhanced design with polished clear quartz beads. Supports focus and adds elegant energy to any outfit.",
                4900, List.of(minerals), List.of("hegyikristaly-karkoto.jpeg"));

        saveProduct("Rose Incense Mini", "A smaller pack of the beloved rose incense – same calming floral scent, now in a travel-friendly size.",
                590, List.of(scents), List.of("rozsa-fustolo-1.1.jpeg"));

        saveProduct("Sandalwood Soft Aroma", "A milder version of the sandalwood scent, ideal for those who prefer subtle spiritual fragrances.",
                750, List.of(scents), List.of("incense-sandalwood-1.1.jpeg"));

        saveProduct("Copper Tongue Cleaner Pro", "Upgraded Ayurvedic tongue cleaner with ergonomic grip and premium polish finish.",
                2300, List.of(ayurveda), List.of("rez-nyelvkaparo.jpeg"));

        saveProduct("Sri Yantra Wall Decor", "Wall-hanging version of the sacred Sri Yantra, handmade with golden accents for spiritual ambiance.",
                4800, List.of(vastu), List.of("sri-yantra.jpeg"));

        saveProduct("Basic Meditation Cushion", "Simple and supportive meditation cushion for beginners. Budget-friendly, filled with natural husk.",
                9900, List.of(yoga), List.of("meditacios-parna.jpeg"));

        saveProduct("Classic Mandala Bedspread", "Traditional Indian bedcover with vibrant mandala design. Ideal for bedrooms, sofas, or wall decor.",
                9400, List.of(home), List.of("mandala-mintas-agyterito.jpeg"));

        saveProduct("Summer Breeze Tunic", "Lightweight Indian tunic with summer pattern – perfect for hot days and spiritual practices.",
                9100, List.of(clothes), List.of("noi-indiai-tunika.jpeg"));

        saveProduct("Energy Quartz Bracelet", "Clear quartz bracelet infused with vibrant energy – handmade with love to support clarity and strength.",
                4700, List.of(minerals), List.of("hegyikristaly-karkoto.jpeg"));
    }

    private Category saveCategory(String name) {
        Category category = new Category();
        category.setCategoryName(name);
        return categoryRepository.save(category);
    }

    private void saveProduct(String name, String description, int price, List<Category> categories, List<String> imageUrls) {
        Product product = new Product();
        product.setProductName(name);
        product.setProductDescription(description);
        product.setPrice(price);
        product.setCategories(new ArrayList<>(categories));

        List<ProductImage> images = new ArrayList<>();
        for (String url : imageUrls) {
            ProductImage img = new ProductImage();
            img.setUrl(url);
            img.setProduct(product);
            images.add(img);
        }

        // Add dummy image
        ProductImage dummyImage = new ProductImage();
        dummyImage.setUrl("no-image-" + imageCounter + ".jpeg");
        dummyImage.setProduct(product);
        images.add(dummyImage);

        imageCounter++;

        product.setImages(images);
        productRepository.save(product);
    }

    private String getRoseDescription() {
        return """
            <p>The scent of calmness and love. Rose is not only the symbol of love, but also one of the highest vibrational scents in nature.
            Its soft, floral aroma soothes the mind, opens the heart, and gently uplifts the mood.
            A perfect choice for emotional cleansing, intimate moments or simple everyday indulgence.</p>
            <br />
            <h4>Why do so many people love it?</h4>
            <ul>
              <li>Sweetly floral, yet not overpowering</li>
              <li>Helps relieve tension, supports self-love</li>
              <li>Harmonizes the atmosphere, makes your home more intimate</li>
            </ul>
        """;
    }

    private String getSandalwoodDescription() {
        return """
            <p>The scent of calm and spirituality. The warm, woody, slightly sweet scent of sandalwood has been a favorite companion of meditation and spiritual practice for centuries.
            It helps to quiet the mind, creates an atmosphere of inner peace, and harmonizes the energy of the space.</p>
            <br />
            <h4>What to expect:</h4>
            <ul>
              <li>Deeply calming, slightly balsamic scent</li>
              <li>Long-lasting, balanced aroma</li>
              <li>Ideal for meditation, relaxation, or a quiet evening</li>
            </ul>
        """;
    }

    private void createMembersWithRoles() {
        if (roleRepository.findByName("USER").isEmpty()) {
            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);

            Member member = new Member();
            member.setUsername("user");
            member.setPassword(passwordEncoder.encode("User1@34"));
            member.setFirstName("User1");
            member.setLastName("User1");
            member.setEmail("user@gmail.com");
            member.setPhoneNumber("06301234567");
            member.setRole(role);
            memberRepository.save(member);
        }

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role role = new Role();
            role.setName("ADMIN");
            roleRepository.save(role);

            Member member = new Member();
            member.setUsername("admin");
            member.setPassword(passwordEncoder.encode("Admin1@34"));
            member.setFirstName("Admin1");
            member.setLastName("Admin1");
            member.setEmail("admin@gmail.com");
            member.setPhoneNumber("06201234567");
            member.setRole(role);
            memberRepository.save(member);
        }
    }
}