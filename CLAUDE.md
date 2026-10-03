# webshop-backend

Spring Boot 3.4.5, Java 21, package root `com.ganesha.webshop`.

## Layering & request flow

Consistent 4-layer pattern: `@RestController` → `@Service` → `JpaRepository` → `@Entity`, with dedicated `@Component` mapper classes converting DTOs ↔ entities.

- **Product flow**: `controller/ProductController.java` (`POST /api/products`) → `service/ProductService.create()` resolves categories via `CategoryRepository`, builds the entity via `service/mapper/NewProductMapper.mapToProduct()` (including child `ProductImage`s), saves via `repository/ProductRepository`. Reads mirror this: `ProductResponseMapper.mapToProductResponse()` composes `CategoryWithIdAndNameResponseMapper` + `ProductImageResponseMapper`. `/search` and `/paginated` build a `Pageable`/`Specification` and return `PaginatedResponse<ProductResponse>`.
- **Category flow**: `controller/CategoryController.java` → `service/CategoryService.java`; `create()` checks `CategoryRepository.findByCategoryName()` for dupes before mapping/saving. `getProductsByCategoryId()` reuses `ProductResponseMapper` — mappers are shared across services. Its `GET /{id}/products` is marked (`//Todo`) as legacy vs. `ProductController`'s `/search`.
- **Auth flow**: `controller/AuthController.java` delegates to `security/service/AuthenticationService` and `RegisterService` rather than the generic `service` package — same shape, different home.

## Domain model

- `model/entity/product/Product.java`: `@ManyToMany(EAGER)` → `Category` (join table `product_category`); `@OneToMany(cascade=ALL, orphanRemoval=true, EAGER)` → `ProductImage`, ordered by id.
- `ProductImage`: `@ManyToOne` back to `Product`; just `url` + `id`.
- `Category`: inverse `@ManyToMany(mappedBy="categories")` → `Product`; unique `categoryName`.
- `model/entity/user/Member.java`: `@ManyToOne(EAGER)` → `Role`; `@OneToOne(mappedBy="member")` → `Address`. Unique constraints on username/email/phoneNumber — and also on `password`, which looks like a copy-paste artifact rather than intentional.
- `Role`: `@OneToMany` list of `Member` with no `mappedBy`, meaning it expects a separate join table instead of reusing `Member.role`'s FK — a modeling inconsistency worth knowing about before touching user/role code.
- `Address`: owning `@OneToOne` with `@JoinColumn(member_id)`.

## Security

JWT-based, stateless, under `security/configuration`, `security/jwt`, `security/service`:

- `WebSecurityConfig`: BCrypt `PasswordEncoder`, `AuthTokenFilter` registered before `UsernamePasswordAuthenticationFilter`, `STATELESS` sessions, CSRF disabled. Authorization: `DELETE/POST/PUT` on `/api/products/**`, `/api/categories/**`, and `POST /images/upload-image` require `ROLE_ADMIN`; `GET /api/products/**`, `GET /api/categories/{id}/products`, and `/api/auth/**` are `permitAll()`. The final fallback rule is `anyRequest().permitAll()`, so anything not explicitly locked down above (e.g. `DELETE /images/{id}`, `GET /api/categories`) is effectively public right now. Several matchers are commented out — this policy still looks mid-tuning.
- `AuthTokenFilter`: parses `Authorization: Bearer <token>`, validates via `JwtUtils`, loads `UserDetailsServiceImpl`, populates `SecurityContextHolder`; 401 on invalid/expired tokens.
- `JwtUtils`: `io.jsonwebtoken` (jjwt 0.12.6) HMAC; secret/expiry from `WEBSHOP_JWT_SECRET` / `JWT_EXPIRATION_MS` env vars.
- `AuthEntryPointJwt` exists as a `@Component` but isn't actually wired into `WebSecurityConfig`'s `.exceptionHandling()` — currently dead code.
- `security/service`: `AuthenticationService` (login, issues JWT + role), `RegisterService` (dup checks, BCrypt-encodes, assigns default `USER` role), `UserDetailsServiceImpl` (`ROLE_<role.name>` authority).

## Error handling

Single `@ControllerAdvice`: `controller/advice/ProductControllerAdvice.java` (name is a misnomer — it also covers category/auth exceptions):

- `ProductNotFoundException`, `CategoryNotFoundException`, `ImageFileNotFoundException` → `404`
- `HandleUserNameExistException`, `HandleEmailExistException`, `HandleCategoryExistException` → `409`
- Gaps: `ProductImageNotFoundException` (`model/exception/`, thrown from `ImageService.deleteImageById`) has no registered handler — surfaces as an unhandled `500`. No handler for `MethodArgumentNotValidException` either, so `@Valid` failures fall back to Spring Boot's default error shape instead of the custom `ErrorResponse` record.

## Cross-cutting conventions

- **Pagination**: generic `PaginatedResponse<T>` record (`content, pageNumber, pageSize, totalElements, totalPages, last`), built from Spring's `Page<T>` — used by `/api/products/paginated` and `/search`.
- **Validation**: Jakarta Bean Validation (`@NotBlank`, `@NotEmpty`, `@Positive`, `@PositiveOrZero`, `@Size`, `@Email`, `@NotNull`) on request DTO records, triggered via `@Valid`. `LoginRequest` and `Base64ImageUploadRequest` have no validation annotations.
- **Config** (`config/`): `CorsConfig.java` is currently wide open (`allowedOrigins("*")`, all methods/headers). `DataInitializer.java` wipes and reseeds product/category tables and seeds `USER`/`ADMIN` roles + one demo member each on every startup (consistent with `ddl-auto=create`).
- **Images/files**: local filesystem only (README notes Cloudinary is planned, not yet done). `FileUploaderService` decodes base64 uploads to `${fileuploader.directory}` with UUID-prefixed names; `ImageService` serves/deletes images; `FileDeletionService` is the shared delete helper used by both `ImageService` and `ProductService.delete()`.

## Build/runtime

- `pom.xml`: Spring Boot 3.4.5 parent, Java 21; `spring-boot-starter-{data-jpa,validation,web,security}`, `devtools` (optional/runtime), `postgresql` (runtime), `h2database` (not test-scoped despite a commented-out `<scope>test</scope>` — H2 is effectively a main dependency too), Lombok, jjwt 0.12.6, test-scoped `spring-boot-starter-test` + `spring-security-test`.
- `application.properties`: `ddl-auto=create` (drops/recreates schema every boot); datasource creds from env vars, but driver-class-name and dialect are hardcoded to H2 even though the Postgres dependency exists — the app currently runs on H2 regardless of environment.
- `application-test.properties`: in-memory H2, `ddl-auto=update`.
- `Dockerfile`: multi-stage (Maven/Corretto 23 build → Temurin 23 JRE Alpine runtime), copies `static/`, entrypoint runs the jar.
- README describes a separate `frontend/` (React/Vite/Tailwind) deployed on Render.com, documents demo admin/user creds, and lists a To-Do (cart/checkout, OAuth2, Cloudinary, CI/CD) — this is still actively evolving.
- Also present: top-level `.env`, `data/demo.mv.db` (H2 file), `not-used-docker-compose/docker-compose.yaml` (unused per its own directory name).

## Testing

Essentially none: the only test is `src/test/java/.../WebshopApplicationTests.java`, a single `@SpringBootTest` smoke test (`contextLoads()`) with no assertions beyond context startup. No `MockMvc`, no controller/service/repository tests, no `Testcontainers`. No coverage of business logic, security, or the API surface today.

## Known gotchas

- `ProductImageNotFoundException` has no advice handler → unhandled 500s.
- `AuthEntryPointJwt` is defined but never wired in.
- `anyRequest().permitAll()` fallback in `WebSecurityConfig` is broader than it may look at a glance.
- `Role.members` has no `mappedBy`, implying an unintended extra join table vs. `Member.role`.
- Zero test coverage beyond the context-load smoke test.
