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
        // illatok
        Category scents = new Category();
        scents.setCategoryName("Illatok");
        categoryRepository.save(scents);

        // Rozsa fustolo
        Product roseIncense = new Product();
        roseIncense.setProductName("Rózsa füstölő");
        String description = """
            <p>A szív nyugalmának és a szeretet illata. A rózsa nemcsak a szerelem szimbóluma, hanem az egyik legmagasabb rezgésű illat a természetben.
            Lágy, virágos aromája megnyugtatja az elmét, nyitja a szívet, és finoman emeli a hangulatot.
            Kiváló választás érzelmi tisztuláshoz, bensőséges pillanatokhoz vagy egyszerűen a mindennapi kényeztetéshez.</p>
            <br />
            <h4>Miért szeretik oly sokan?</h4>
            <ul>
              <li>Édesen virágos, mégsem tolakodó illat</li>
              <li>Segít oldani a feszültséget, támogatja az önszeretetet</li>
              <li>Harmonizálja a légkört, meghittebbé teszi az otthonodat</li>
            </ul>
            <br />
            <h4>Natur's Nest – Rózsa füstölő</h4>
            <p>Lágy illat, mély érzelmek – természetesen.
            A Natur's Nest Rózsa füstölő a klasszikus rózsa illat természetes, harmonikus változata – nem tolakodó, hanem finoman körbeölel.
            Tökéletes választás, ha szeretnéd megnyugtatni az elmédet, megnyitni a szívedet vagy egyszerűen csak szebbé tenni a pillanatot.</p>
            <br />
            <h4>Illatprofil:</h4>
            <ul>
              <li>Virágos, édes és tiszta rózsaillat</li>
              <li>Érzelmi kiegyensúlyozásra és hangulatjavításra</li>
              <li>Természetes alapanyagokból, mesterséges illatanyagok nélkül</li>
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


        // szantalfa fustolo
        Product sandalwood = new Product();
        sandalwood.setProductName("Szantálfa füstölő");
        String description2 = """
            <p>A nyugalom és spiritualitás illata. A szantálfa meleg, fás, enyhén édeskés illata évszázadok óta a meditáció és spirituális elmélyülés egyik legkedveltebb kísérője. 
            Segít elcsendesíteni az elmét, megteremti a belső béke légkörét, és harmonizálja a tér energiáit.</p>
            <br />
            <h4>Mire számíthatsz:</h4>
            <ul>
              <li>Mélyen megnyugtató, enyhén balzsamos illat</li>
              <li>Hosszan tartó, kiegyensúlyozott aroma</li>
              <li>Ideális meditációhoz, relaxációhoz vagy akár egy csendes estéhez</li>
            </ul>
            <br />
            <h4>Natur's Nest – Chandan füstölő</h4>
            <p>Spirituális mélység és földelő nyugalom egy illatban.
            A Chandan, vagyis szantálfa füstölő a Natur's Nest egyik legismertebb és legnemesebb illata. Meleg, balzsamos, 
            enyhén fűszeres aromája segít elcsendesíteni az elmét, támogatja a mélyebb meditációt és harmonizálja a teret. 
            Ez az illat nemcsak tisztít, hanem emel is – testileg, lelkileg, szellemileg.</p>
            <br />
            <h4>Illatprofil:</h4>
            <ul>
              <li>Meleg, fás, enyhén édes-balzsamos karakte</li>
              <li>Kézzel készített természetes alapanyagokból</li>
              <li>Hosszan tartó, finoman burkoló illatfelhő</li>
            </ul>
            <br />
            <h4>Mire ajánljuk:</h4>
            <ul>
              <li>Meditációhoz, jógához, spirituális elmélyüléshez</li>
              <li>Tértisztításhoz és nyugalmas légkör kialakításához</li>
              <li>Belső fókusz megtalálásához egy rohanó napon</li>
                
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
        tongueCleaner.setProductName("Réz nyelvkaparó");
        tongueCleaner.setProductDescription("Az ayurvédikus higiénia eszköze a szájban lerakódott lepedék eltávolítására. Reggeli használata frissíti a leheletet és támogatja az emésztést.");
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
        sriYantra.setProductDescription("A Sri Yantra a Vastu egyik legerősebb szimbóluma, amely a bőséget, spirituális fejlődést és harmonikus energiát hoz a térbe.");
        sriYantra.setPrice(4500);
        sriYantra.setCategories(new ArrayList<>(List.of(vastu)));

        ProductImage img5 = new ProductImage();
        img5.setUrl("sri-yantra.jpeg");
        img5.setProduct(sriYantra);

        sriYantra.setImages(new ArrayList<>(List.of(img5)));

        productRepository.save(sriYantra);

// Jóga
        Category yoga = new Category();
        yoga.setCategoryName("Jóga");
        categoryRepository.save(yoga);

        Product cushion = new Product();
        cushion.setProductName("Meditációs párna");
        cushion.setProductDescription("Természetes bio töltettel készült párna, amely stabil és kényelmes alátámasztást biztosít hosszabb ülő gyakorlatokhoz.");
        cushion.setPrice(14500);
        cushion.setCategories(new ArrayList<>(List.of(yoga)));

        ProductImage img6 = new ProductImage();
        img6.setUrl("meditacios-parna.jpeg");
        img6.setProduct(cushion);

        cushion.setImages(new ArrayList<>(List.of(img6)));

        productRepository.save(cushion);

// Lakberendezés
        Category home = new Category();
        home.setCategoryName("Lakberendezés");
        categoryRepository.save(home);

        Product bedcover = new Product();
        bedcover.setProductName("Mandala mintás ágyterítő");
        bedcover.setProductDescription("Indiai pamut ágyterítő mandala mintával. Díszítő és funkcionális célokra is használható, 240x210 cm-es méret.");
        bedcover.setPrice(9500);
        bedcover.setCategories(new ArrayList<>(List.of(home)));

        ProductImage img7 = new ProductImage();
        img7.setUrl("mandala-mintas-agyterito.jpeg");
        img7.setProduct(bedcover);

        bedcover.setImages(new ArrayList<>(List.of(img7)));

        productRepository.save(bedcover);

// Ruhák
        Category clothes = new Category();
        clothes.setCategoryName("Ruhák");
        categoryRepository.save(clothes);

        Product tunic = new Product();
        tunic.setProductName("Női indiai tunika");
        tunic.setProductDescription("Könnyű, légies és 100% pamut anyagból készült tunika, amely ideális nyári viseletként vagy jógázáshoz.");
        tunic.setPrice(9500);
        tunic.setCategories(new ArrayList<>(List.of(clothes)));

        ProductImage img8 = new ProductImage();
        img8.setUrl("noi-indiai-tunika.jpeg");
        img8.setProduct(tunic);

        tunic.setImages(new ArrayList<>(List.of(img8)));

        productRepository.save(tunic);

// Ásványok
        Category minerals = new Category();
        minerals.setCategoryName("Ásványok és ékszerek");
        categoryRepository.save(minerals);

        Product bracelet = new Product();
        bracelet.setProductName("Hegyi kristály karkötő");
        bracelet.setProductDescription("A hegyi kristály tisztító és energetizáló hatásáról ismert ásvány. A karkötő a csuklón viselve támogatja a fókuszt és belső egyensúlyt.");
        bracelet.setPrice(4500);
        bracelet.setCategories(new ArrayList<>(List.of(minerals)));

        ProductImage img9 = new ProductImage();
        img9.setUrl("hegyikristaly-karkoto.jpeg");
        img9.setProduct(bracelet);

        bracelet.setImages(new ArrayList<>(List.of(img9)));

        productRepository.save(bracelet);

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
