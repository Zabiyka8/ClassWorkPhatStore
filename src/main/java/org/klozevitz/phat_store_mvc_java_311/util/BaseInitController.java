package org.klozevitz.phat_store_mvc_java_311.util;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.BrandService;
import org.klozevitz.phat_store_mvc_java_311.dao.services.CategoryService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Color;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Sex;
import org.klozevitz.phat_store_mvc_java_311.model.entities.itemAttributes.Size;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Brand;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Category;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.Item;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.entities.StockPosition;
import org.klozevitz.phat_store_mvc_java_311.model.secuirty.ApplicationUser;
import org.klozevitz.phat_store_mvc_java_311.repositories.ApplicationUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@RestController
@RequestMapping("/s")
@RequiredArgsConstructor
public class BaseInitController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ApplicationUserRepository appUserRepo;
    private final PasswordEncoder encoder;
    private Category tempCategory;
    private Brand tempBrand;
    private Item tempItem;

    @GetMapping("/init")
    public void init() throws IOException {
        if (categoryService.findById(1).isEmpty()) {
            List.of("ГОЛОВНЫЕ УБОРЫ", "ОДЕЖДА", "ОБУВЬ", "СУМКИ", "АКСЕССУАРЫ", "ТВОРЧЕСТВО")
                    .forEach(category -> categoryService.save(Category.builder()
                            .name(category)
                            .catalogue(new HashSet<>())
                            .build())
                    );
            List.of("Kangol", "ProClub", "Novesta", "PHAT")
                    .forEach(brand -> brandService.save(Brand.builder()
                            .name(brand)
                            .catalogue(new HashSet<>())
                            .build()));
            itemsInit();
            usersInit();
        }
    }

    private void itemsInit() throws IOException {
        String itemsFile = "items.txt";
        try (Stream<String> stream = Files.lines(Path.of(itemsFile))) {
            List<String> lines = stream.toList();
            for (int i = 0; i < lines.size(); i++) {
                if (i < 15) {
                    if (i == 0) {
                        tempBrand = brandService.findById(1).get();
                        tempCategory = categoryService.findById(1).get();
                    }
                    saveModel(lines.get(i), 0);
                }
                if (14 < i && i < 30) {
                    if (i == 15) {
                        categoryService.save(tempCategory);
                        brandService.save(tempBrand);
                        tempBrand = brandService.findById(2).get();
                        tempCategory = categoryService.findById(2).get();
                    }
                    saveModel(lines.get(i), 1);
                }
                if (29 < i) {
                    if (i == 30) {
                        categoryService.save(tempCategory);
                        brandService.save(tempBrand);
                        tempBrand = brandService.findById(3).get();
                        tempCategory = categoryService.findById(3).get();
                    }
                    saveModel(lines.get(i), 2);
                }
            }
            categoryService.save(tempCategory);
            brandService.save(tempBrand);
        } catch (Exception e) {
            throw new RemoteException();
        }
    }

    private void saveModel(String modelDescription, int type) {
        String[] itemProps = modelDescription
                .substring(1, modelDescription.length() - 2)
                .split(", ");
        saveModelFromStringArray(itemProps, type);
    }

    private void saveModelFromStringArray(String[] itemProps, int type) {
        tempItem = Item.builder()
                .article(String.valueOf(new Random().nextInt(1000_000, 10_000_000)))
                .model(itemProps[2])
                .price(Double.parseDouble(itemProps[3]))
                .brand(tempBrand)
                .category(tempCategory)
                .sex(Sex.UNISEX)
                .positions(new HashSet<>())
                .build();
        tempCategory
                .getCatalogue()
                .add(tempItem);
        tempBrand
                .getCatalogue()
                .add(tempItem);
        saveModelStockPositions(type);
    }

    private void saveModelStockPositions(int type) {
        Arrays
                .stream(Color.values())
                .forEach(color -> saveStockPositionsByColor(color, type));
    }

    private void saveStockPositionsByColor(Color color, int type) {
        switch (type) {
            case 0 -> Size.HATS.getSizes()
                    .forEach(size ->
                            saveStockPositionByColorAndSize(color, size));
            case 1 -> Size.CLOTHES.getSizes()
                    .forEach(size ->
                            saveStockPositionByColorAndSize(color, size));
            case 2 -> Size.SHOES.getSizes()
                    .forEach(size ->
                            saveStockPositionByColorAndSize(color, size));
            default -> System.out.println();
        }
    }

    private void saveStockPositionByColorAndSize(Color color, String size) {
        StockPosition stockPosition = StockPosition.builder()
                .item(tempItem)
                .color(color)
                .size(size)
                .amount(new Random().nextInt(0, 20))
                .build();
        tempItem.getPositions().add(stockPosition);
    }

    private void usersInit() {
        ApplicationUser admin = new ApplicationUser("admin@ya.ru", encoder.encode("admin"));
        ApplicationUser user = new ApplicationUser("user@ya.ru", encoder.encode("user"));

        appUserRepo.save(admin);
        appUserRepo.save(user);
    }
}
