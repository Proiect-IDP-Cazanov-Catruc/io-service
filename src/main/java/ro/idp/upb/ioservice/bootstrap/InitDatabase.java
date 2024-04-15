/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.bootstrap;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.idp.upb.ioservice.data.entity.Category;
import ro.idp.upb.ioservice.data.entity.Product;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.data.enums.Role;
import ro.idp.upb.ioservice.repository.CategoryRepository;
import ro.idp.upb.ioservice.repository.ProductRepository;
import ro.idp.upb.ioservice.repository.UserRepository;

@RequiredArgsConstructor
@Component
@Slf4j
public class InitDatabase implements CommandLineRunner {

	private final ResourceLoader resourceLoader;
	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;

	private final String categoryFilePath = "static/csv/category.csv";
	private final String clientFilePath = "static/csv/clients.csv";
	private final String productFilePath = "static/csv/products.csv";

	private final Map<Integer, Category> categoryIdToCategoryEntity = new HashMap<>();

	@Override
	public void run(String... args) throws Exception {
		if (categoryRepository.count() == 0) {
			log.info("Bootstrapping categories, users, products!");
			parseCategoriesCSV(getResourceFilePath(categoryFilePath));
			parseUsersCSV(getResourceFilePath(clientFilePath));
			parseProductsCSV(getResourceFilePath(productFilePath));
			addManagerAndAdmin();
		}
	}

	private void addManagerAndAdmin() {
		User adminUser =
				User.builder()
						.firstname("Admin")
						.lastname("User")
						.role(Role.ADMIN)
						.password(passwordEncoder.encode("admin"))
						.email("admin@admin.com")
						.build();
		userRepository.save(adminUser);
		User managerUser =
				User.builder()
						.firstname("Manager")
						.lastname("Manager")
						.role(Role.MANAGER)
						.password(passwordEncoder.encode("manager"))
						.email("manager@manager.com")
						.build();
		userRepository.save(managerUser);
	}

	public void parseCategoriesCSV(String filePath) throws IOException {
		FileReader reader = new FileReader(filePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

		for (CSVRecord record : csvParser) {
			Category category = new Category();
			Integer categoryId = Integer.parseInt(record.get("id"));
			category.setName(record.get("name"));

			Category savedCategory = categoryRepository.save(category);
			categoryIdToCategoryEntity.put(categoryId, savedCategory);
		}
	}

	public void parseUsersCSV(String filePath) throws IOException {
		FileReader reader = new FileReader(filePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

		for (CSVRecord record : csvParser) {
			User user = new User();
			user.setFirstname(record.get("first name"));
			user.setLastname(record.get("last name"));
			user.setEmail(record.get("email"));
			// Set default password
			user.setPassword(passwordEncoder.encode("123"));
			user.setRole(Role.USER);

			userRepository.save(user);
		}
	}

	public void parseProductsCSV(String filePath) throws IOException {
		FileReader reader = new FileReader(filePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());

		for (CSVRecord record : csvParser) {
			Product product = new Product();
			product.setName(record.get("name"));
			product.setDescription(record.get("description"));
			product.setPrice(Double.parseDouble(record.get("price")));
			product.setQuantity(Integer.parseInt(record.get("quantity")));
			Integer categoryId = Integer.parseInt(record.get("category_id"));
			product.setCategory(categoryIdToCategoryEntity.get(categoryId));

			productRepository.save(product);
		}
	}

	private String getResourceFilePath(String resourcePath) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + resourcePath);
		return Objects.requireNonNull(resource.getFile().getAbsolutePath());
	}
}
