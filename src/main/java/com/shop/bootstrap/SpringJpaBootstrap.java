package com.shop.bootstrap;

import com.shop.repository.entity.Product;
import com.shop.repository.entity.Role;
import com.shop.repository.entity.User;
import com.shop.repository.jpa.ProductRepository;
import com.shop.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
//    @Autowired
//    private RoleService roleService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadProducts();
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
    }

    private void loadProducts() {
        Product shirt = new Product();
        shirt.setDescription("Spring Framework Guru Shirt");
        shirt.setPrice(new BigDecimal("18.95"));
        shirt.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
        shirt.setProductId("235268845711068308");
        productRepository.save(shirt);

        logger.info("Saved Shirt - id: " + shirt.getId());

        Product mug = new Product();
        mug.setDescription("Spring Framework Guru Mug");
        mug.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
        mug.setProductId("168639393495335947");
        mug.setPrice(new BigDecimal("11.95"));
        productRepository.save(mug);

        for(int i = 0; i < 50; i++) {
            Product test = new Product();
            test.setDescription("Test product");
            test.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
            test.setProductId("168639393495335947" + i);
            test.setPrice(new BigDecimal("1" + i + "1.95"));
            productRepository.save(test);
        }

        logger.info("Saved Mug - id:" + mug.getId());
    }

    private void loadUsers() {
//        User user1 = new User();
//        user1.setUsername("user");
//        user1.setPassword("user");
//        userService.save(user1);
//
//        User user2 = new User();
//        user2.setUsername("admin");
//        user2.setPassword("admin");
//        userService.saveOrUpdate(user2);
    }

    private void loadRoles() {
        Role role = new Role();
        role.setName("ROLE_USER");
        //roleService.saveOrUpdate(role);
        logger.info("Saved role" + role.getName());
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        //roleService.saveOrUpdate(adminRole);
        logger.info("Saved role" + adminRole.getName());
    }
    private void assignUsersToUserRole() {
//        List<Role> roles = (List<Role>) roleService.listAll();
//        List<User> users = (List<User>) userService.listAll();
//
//        roles.forEach(role -> {
//            if (role.getRole().equalsIgnoreCase("USER")) {
//                users.forEach(user -> {
//                    if (user.getUsername().equals("user")) {
//                        user.addRole(role);
//                        userService.saveOrUpdate(user);
//                    }
//                });
//            }
//        });
    }
    private void assignUsersToAdminRole() {
//        List<Role> roles = (List<Role>) roleService.listAll();
//        List<User> users = (List<User>) userService.listAll();
//
//        roles.forEach(role -> {
//            if (role.getRole().equalsIgnoreCase("ADMIN")) {
//                users.forEach(user -> {
//                    if (user.getUsername().equals("admin")) {
//                        user.addRole(role);
//                        userService.saveOrUpdate(user);
//                    }
//                });
//            }
//        });
    }
}


