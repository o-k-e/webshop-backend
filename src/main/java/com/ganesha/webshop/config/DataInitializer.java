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


    public DataInitializer(ProductRepository productRepository, CategoryRepository categoryRepository, RoleRepository roleRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
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
        // Scents
        Category scents = new Category();
        scents.setCategoryName("Scents");
        categoryRepository.save(scents);

        // Rose incense
        Product roseIncense = new Product();
        roseIncense.setProductName("Rose Incense");
        String description = """
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
            <br />
            <h4>Nature's Nest – Rose Incense</h4>
            <p>Soft scent, deep emotions – naturally.
            Nature's Nest Rose Incense is a natural, harmonious version of the classic rose scent – not overwhelming, but gently enveloping.
            A perfect choice to calm your mind, open your heart or simply beautify the moment.</p>
            <br />
            <h4>Scent profile:</h4>
            <ul>
              <li>Floral, sweet and pure rose fragrance</li>
              <li>For emotional balance and mood enhancement</li>
              <li>Made from natural ingredients, without artificial fragrances</li>
            </ul>
        """;
        roseIncense.setProductDescription(description);
        roseIncense.setPrice(780);
        roseIncense.setCategories(new ArrayList<>(List.of(scents)));

        ProductImage img1 = new ProductImage();
        img1.setUrl("rozsa-fustolo-1.1.jpeg");
        img1.setProduct(roseIncense);

        ProductImage img2 = new ProductImage();
        img2.setUrl("rozsa-fustolo-1.2.jpeg");
        img2.setProduct(roseIncense);

        roseIncense.setImages(new ArrayList<>(List.of(img1, img2)));

        productRepository.save(roseIncense);

        // Sandalwood incense
        Product sandalwood = new Product();
        sandalwood.setProductName("Sandalwood Incense");
        String description2 = """
            <p>The scent of calm and spirituality. The warm, woody, slightly sweet scent of sandalwood has been a favorite companion of meditation and spiritual practice for centuries.
            It helps to quiet the mind, creates an atmosphere of inner peace, and harmonizes the energy of the space.</p>
            <br />
            <h4>What to expect:</h4>
            <ul>
              <li>Deeply calming, slightly balsamic scent</li>
              <li>Long-lasting, balanced aroma</li>
              <li>Ideal for meditation, relaxation, or a quiet evening</li>
            </ul>
            <br />
            <h4>Nature's Nest – Chandan Incense</h4>
            <p>Spiritual depth and grounding calm in one scent.
            Chandan, or sandalwood incense, is one of Nature's Nest's most recognized and noble fragrances. Its warm, balsamic, slightly spicy aroma helps to quiet the mind, supports deeper meditation, and harmonizes the space.
            This scent not only purifies but also elevates – physically, emotionally, spiritually.</p>
            <br />
            <h4>Scent profile:</h4>
            <ul>
              <li>Warm, woody, slightly sweet-balsamic character</li>
              <li>Handmade from natural ingredients</li>
              <li>Long-lasting, gently enveloping scent cloud</li>
            </ul>
            <br />
            <h4>Recommended for:</h4>
            <ul>
              <li>Meditation, yoga, spiritual deepening</li>
              <li>Space cleansing and creating a calm atmosphere</li>
              <li>Finding inner focus on a busy day</li>
            </ul>
        """;
        sandalwood.setProductDescription(description2);
        sandalwood.setPrice(780);
        sandalwood.setCategories(new ArrayList<>(List.of(scents)));

        ProductImage img3 = new ProductImage();
        img3.setUrl("incense-sandalwood-1.1.jpeg");
        img3.setProduct(sandalwood);

        sandalwood.setImages(new ArrayList<>(List.of(img3)));
        productRepository.save(sandalwood);

        // Ayurveda
        Category ayurveda = new Category();
        ayurveda.setCategoryName("Ayurveda");
        categoryRepository.save(ayurveda);

        Product tongueCleaner = new Product();
        tongueCleaner.setProductName("Copper Tongue Cleaner");
        tongueCleaner.setProductDescription("An Ayurvedic hygiene tool for removing residue from the tongue. Morning use freshens breath and supports digestion.");
        tongueCleaner.setPrice(1900);
        tongueCleaner.setCategories(new ArrayList<>(List.of(ayurveda)));

        ProductImage img4 = new ProductImage();
        img4.setUrl("rez-nyelvkaparo.jpeg");
        img4.setProduct(tongueCleaner);

        tongueCleaner.setImages(new ArrayList<>(List.of(img4)));
        productRepository.save(tongueCleaner);

        // Vastu
        Category vastu = new Category();
        vastu.setCategoryName("Vastu");
        categoryRepository.save(vastu);

        Product sriYantra = new Product();
        sriYantra.setProductName("Sri Yantra");
        sriYantra.setProductDescription("Sri Yantra is one of the strongest symbols of Vastu, bringing abundance, spiritual growth, and harmonious energy to the space.");
        sriYantra.setPrice(4500);
        sriYantra.setCategories(new ArrayList<>(List.of(vastu)));

        ProductImage img5 = new ProductImage();
        img5.setUrl("sri-yantra.jpeg");
        img5.setProduct(sriYantra);

        sriYantra.setImages(new ArrayList<>(List.of(img5)));
        productRepository.save(sriYantra);

        // Yoga
        Category yoga = new Category();
        yoga.setCategoryName("Yoga");
        categoryRepository.save(yoga);

        Product cushion = new Product();
        cushion.setProductName("Meditation Cushion");
        cushion.setProductDescription("A cushion made with natural bio filling that provides stable and comfortable support for longer seated practices.");
        cushion.setPrice(14500);
        cushion.setCategories(new ArrayList<>(List.of(yoga)));

        ProductImage img6 = new ProductImage();
        img6.setUrl("meditacios-parna.jpeg");
        img6.setProduct(cushion);

        cushion.setImages(new ArrayList<>(List.of(img6)));
        productRepository.save(cushion);

        // Home Decor
        Category home = new Category();
        home.setCategoryName("Home Decor");
        categoryRepository.save(home);

        Product bedcover = new Product();
        bedcover.setProductName("Mandala Patterned Bedspread");
        bedcover.setProductDescription("Indian cotton bedspread with mandala pattern. Can be used for both decorative and functional purposes, size 240x210 cm.");
        bedcover.setPrice(9500);
        bedcover.setCategories(new ArrayList<>(List.of(home)));

        ProductImage img7 = new ProductImage();
        img7.setUrl("mandala-mintas-agyterito.jpeg");
        img7.setProduct(bedcover);

        bedcover.setImages(new ArrayList<>(List.of(img7)));
        productRepository.save(bedcover);

        // Clothing
        Category clothes = new Category();
        clothes.setCategoryName("Clothing");
        categoryRepository.save(clothes);

        Product tunic = new Product();
        tunic.setProductName("Women's Indian Tunic");
        tunic.setProductDescription("Light, airy tunic made from 100% cotton, ideal for summer wear or yoga.");
        tunic.setPrice(9500);
        tunic.setCategories(new ArrayList<>(List.of(clothes)));

        ProductImage img8 = new ProductImage();
        img8.setUrl("noi-indiai-tunika.jpeg");
        img8.setProduct(tunic);

        tunic.setImages(new ArrayList<>(List.of(img8)));
        productRepository.save(tunic);

        // Minerals
        Category minerals = new Category();
        minerals.setCategoryName("Minerals and Jewelry");
        categoryRepository.save(minerals);

        Product bracelet = new Product();
        bracelet.setProductName("Clear Quartz Bracelet");
        bracelet.setProductDescription("Clear quartz is known for its cleansing and energizing effects. Worn on the wrist, this bracelet supports focus and inner balance.");
        bracelet.setPrice(4500);
        bracelet.setCategories(new ArrayList<>(List.of(minerals)));

        ProductImage img9 = new ProductImage();
        img9.setUrl("hegyikristaly-karkoto.jpeg");
        img9.setProduct(bracelet);

        bracelet.setImages(new ArrayList<>(List.of(img9)));
        productRepository.save(bracelet);

        // Scents - Dummy product
        Product roseDeluxe = new Product();
        roseDeluxe.setProductName("Rose Incense Deluxe");
        roseDeluxe.setProductDescription(description); // reuse original description
        roseDeluxe.setPrice(880); // slight variation
        roseDeluxe.setCategories(new ArrayList<>(List.of(scents)));

        ProductImage img1Dup = new ProductImage();
        img1Dup.setUrl("rozsa-fustolo-1.1.jpeg");
        img1Dup.setProduct(roseDeluxe);

        ProductImage img2Dup = new ProductImage();
        img2Dup.setUrl("rozsa-fustolo-1.2.jpeg");
        img2Dup.setProduct(roseDeluxe);

        roseDeluxe.setImages(new ArrayList<>(List.of(img1Dup, img2Dup)));
        productRepository.save(roseDeluxe);

// Scents - Dummy product 2
        Product sandalwoodClassic = new Product();
        sandalwoodClassic.setProductName("Sandalwood Incense Classic");
        sandalwoodClassic.setProductDescription(description2);
        sandalwoodClassic.setPrice(820);
        sandalwoodClassic.setCategories(new ArrayList<>(List.of(scents)));

        ProductImage img3Dup = new ProductImage();
        img3Dup.setUrl("incense-sandalwood-1.1.jpeg");
        img3Dup.setProduct(sandalwoodClassic);

        sandalwoodClassic.setImages(new ArrayList<>(List.of(img3Dup)));
        productRepository.save(sandalwoodClassic);

// Ayurveda - Dummy product
        Product tongueCleanerPlus = new Product();
        tongueCleanerPlus.setProductName("Copper Tongue Cleaner Plus");
        tongueCleanerPlus.setProductDescription("A premium version of the classic Ayurvedic tongue scraper, slightly wider and thicker for extra durability.");
        tongueCleanerPlus.setPrice(2100);
        tongueCleanerPlus.setCategories(new ArrayList<>(List.of(ayurveda)));

        ProductImage img4Dup = new ProductImage();
        img4Dup.setUrl("rez-nyelvkaparo.jpeg");
        img4Dup.setProduct(tongueCleanerPlus);

        tongueCleanerPlus.setImages(new ArrayList<>(List.of(img4Dup)));
        productRepository.save(tongueCleanerPlus);

// Vastu - Dummy product
        Product sriYantraMini = new Product();
        sriYantraMini.setProductName("Sri Yantra Mini");
        sriYantraMini.setProductDescription("A smaller version of the Sri Yantra, ideal for desks or meditation corners. Brings harmony and focus.");
        sriYantraMini.setPrice(3500);
        sriYantraMini.setCategories(new ArrayList<>(List.of(vastu)));

        ProductImage img5Dup = new ProductImage();
        img5Dup.setUrl("sri-yantra.jpeg");
        img5Dup.setProduct(sriYantraMini);

        sriYantraMini.setImages(new ArrayList<>(List.of(img5Dup)));
        productRepository.save(sriYantraMini);

// Yoga - Dummy product
        Product cushionEco = new Product();
        cushionEco.setProductName("Eco Meditation Cushion");
        cushionEco.setProductDescription("An eco-conscious version made with recycled materials and organic cotton cover.");
        cushionEco.setPrice(13900);
        cushionEco.setCategories(new ArrayList<>(List.of(yoga)));

        ProductImage img6Dup = new ProductImage();
        img6Dup.setUrl("meditacios-parna.jpeg");
        img6Dup.setProduct(cushionEco);

        cushionEco.setImages(new ArrayList<>(List.of(img6Dup)));
        productRepository.save(cushionEco);

// Home Decor - Dummy product
        Product bedcoverMandalaGold = new Product();
        bedcoverMandalaGold.setProductName("Mandala Gold Bedspread");
        bedcoverMandalaGold.setProductDescription("Limited edition golden-tone mandala patterned cotton bedspread. Size: 240x210 cm.");
        bedcoverMandalaGold.setPrice(9900);
        bedcoverMandalaGold.setCategories(new ArrayList<>(List.of(home)));

        ProductImage img7Dup = new ProductImage();
        img7Dup.setUrl("mandala-mintas-agyterito.jpeg");
        img7Dup.setProduct(bedcoverMandalaGold);

        bedcoverMandalaGold.setImages(new ArrayList<>(List.of(img7Dup)));
        productRepository.save(bedcoverMandalaGold);

// Clothing - Dummy product
        Product tunicFloral = new Product();
        tunicFloral.setProductName("Floral Cotton Tunic");
        tunicFloral.setProductDescription("Soft cotton tunic with subtle floral prints. Ideal for casual wear and summer yoga.");
        tunicFloral.setPrice(9200);
        tunicFloral.setCategories(new ArrayList<>(List.of(clothes)));

        ProductImage img8Dup = new ProductImage();
        img8Dup.setUrl("noi-indiai-tunika.jpeg");
        img8Dup.setProduct(tunicFloral);

        tunicFloral.setImages(new ArrayList<>(List.of(img8Dup)));
        productRepository.save(tunicFloral);

// Minerals - Dummy product
        Product braceletDeluxe = new Product();
        braceletDeluxe.setProductName("Clear Quartz Bracelet Deluxe");
        braceletDeluxe.setProductDescription("Enhanced design with polished clear quartz beads. Supports focus and adds elegant energy to any outfit.");
        braceletDeluxe.setPrice(4900);
        braceletDeluxe.setCategories(new ArrayList<>(List.of(minerals)));

        ProductImage img9Dup = new ProductImage();
        img9Dup.setUrl("hegyikristaly-karkoto.jpeg");
        img9Dup.setProduct(braceletDeluxe);

        braceletDeluxe.setImages(new ArrayList<>(List.of(img9Dup)));
        productRepository.save(braceletDeluxe);

        // Scents - Dummy product
        Product roseMini = new Product();
        roseMini.setProductName("Rose Incense Mini");
        roseMini.setProductDescription("A smaller pack of the beloved rose incense – same calming floral scent, now in a travel-friendly size.");
        roseMini.setPrice(590);
        roseMini.setCategories(new ArrayList<>(List.of(scents)));

        ProductImage img1Mini = new ProductImage();
        img1Mini.setUrl("rozsa-fustolo-1.1.jpeg");
        img1Mini.setProduct(roseMini);

        roseMini.setImages(new ArrayList<>(List.of(img1Mini)));
        productRepository.save(roseMini);

// Scents - Dummy product
        Product sandalwoodSoft = new Product();
        sandalwoodSoft.setProductName("Sandalwood Soft Aroma");
        sandalwoodSoft.setProductDescription("A milder version of the sandalwood scent, ideal for those who prefer subtle spiritual fragrances.");
        sandalwoodSoft.setPrice(750);
        sandalwoodSoft.setCategories(new ArrayList<>(List.of(scents)));

        ProductImage img3Soft = new ProductImage();
        img3Soft.setUrl("incense-sandalwood-1.1.jpeg");
        img3Soft.setProduct(sandalwoodSoft);

        sandalwoodSoft.setImages(new ArrayList<>(List.of(img3Soft)));
        productRepository.save(sandalwoodSoft);

// Ayurveda - Dummy product
        Product tongueCleanerPro = new Product();
        tongueCleanerPro.setProductName("Copper Tongue Cleaner Pro");
        tongueCleanerPro.setProductDescription("Upgraded Ayurvedic tongue cleaner with ergonomic grip and premium polish finish.");
        tongueCleanerPro.setPrice(2300);
        tongueCleanerPro.setCategories(new ArrayList<>(List.of(ayurveda)));

        ProductImage img4Pro = new ProductImage();
        img4Pro.setUrl("rez-nyelvkaparo.jpeg");
        img4Pro.setProduct(tongueCleanerPro);

        tongueCleanerPro.setImages(new ArrayList<>(List.of(img4Pro)));
        productRepository.save(tongueCleanerPro);

// Vastu - Dummy product
        Product sriYantraWall = new Product();
        sriYantraWall.setProductName("Sri Yantra Wall Decor");
        sriYantraWall.setProductDescription("Wall-hanging version of the sacred Sri Yantra, handmade with golden accents for spiritual ambiance.");
        sriYantraWall.setPrice(4800);
        sriYantraWall.setCategories(new ArrayList<>(List.of(vastu)));

        ProductImage img5Wall = new ProductImage();
        img5Wall.setUrl("sri-yantra.jpeg");
        img5Wall.setProduct(sriYantraWall);

        sriYantraWall.setImages(new ArrayList<>(List.of(img5Wall)));
        productRepository.save(sriYantraWall);

// Yoga - Dummy product
        Product cushionBasic = new Product();
        cushionBasic.setProductName("Basic Meditation Cushion");
        cushionBasic.setProductDescription("Simple and supportive meditation cushion for beginners. Budget-friendly, filled with natural husk.");
        cushionBasic.setPrice(9900);
        cushionBasic.setCategories(new ArrayList<>(List.of(yoga)));

        ProductImage img6Basic = new ProductImage();
        img6Basic.setUrl("meditacios-parna.jpeg");
        img6Basic.setProduct(cushionBasic);

        cushionBasic.setImages(new ArrayList<>(List.of(img6Basic)));
        productRepository.save(cushionBasic);

// Home Decor - Dummy product
        Product bedcoverClassic = new Product();
        bedcoverClassic.setProductName("Classic Mandala Bedspread");
        bedcoverClassic.setProductDescription("Traditional Indian bedcover with vibrant mandala design. Ideal for bedrooms, sofas, or wall decor.");
        bedcoverClassic.setPrice(9400);
        bedcoverClassic.setCategories(new ArrayList<>(List.of(home)));

        ProductImage img7Classic = new ProductImage();
        img7Classic.setUrl("mandala-mintas-agyterito.jpeg");
        img7Classic.setProduct(bedcoverClassic);

        bedcoverClassic.setImages(new ArrayList<>(List.of(img7Classic)));
        productRepository.save(bedcoverClassic);

// Clothing - Dummy product
        Product tunicSummer = new Product();
        tunicSummer.setProductName("Summer Breeze Tunic");
        tunicSummer.setProductDescription("Lightweight Indian tunic with summer pattern – perfect for hot days and spiritual practices.");
        tunicSummer.setPrice(9100);
        tunicSummer.setCategories(new ArrayList<>(List.of(clothes)));

        ProductImage img8Summer = new ProductImage();
        img8Summer.setUrl("noi-indiai-tunika.jpeg");
        img8Summer.setProduct(tunicSummer);

        tunicSummer.setImages(new ArrayList<>(List.of(img8Summer)));
        productRepository.save(tunicSummer);

// Minerals - Dummy product
        Product braceletEnergy = new Product();
        braceletEnergy.setProductName("Energy Quartz Bracelet");
        braceletEnergy.setProductDescription("Clear quartz bracelet infused with vibrant energy – handmade with love to support clarity and strength.");
        braceletEnergy.setPrice(4700);
        braceletEnergy.setCategories(new ArrayList<>(List.of(minerals)));

        ProductImage img9Energy = new ProductImage();
        img9Energy.setUrl("hegyikristaly-karkoto.jpeg");
        img9Energy.setProduct(braceletEnergy);

        braceletEnergy.setImages(new ArrayList<>(List.of(img9Energy)));
        productRepository.save(braceletEnergy);
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