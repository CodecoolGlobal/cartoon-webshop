package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDaoMem productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier disney = new Supplier("Disney", "We entertain, inform and inspire people around the globe through the power of unparalleled storytelling, reflecting the iconic brands, creative minds and innovative technologies that make ours the world’s premier entertainment company.");
        supplierDataStore.add(disney);
        Supplier pixar = new Supplier("Pixar", "Pixar Animation Studios is an American computer animation film studio based in Emeryville, California.");
        supplierDataStore.add(pixar);
        Supplier cartoonNetwork = new Supplier("Cartoon Network", "Cartoon Network is an American pay television channel owned by AT&T's WarnerMedia.");
        supplierDataStore.add(cartoonNetwork);

        //setting up a new product category
        ProductCategory hero = new ProductCategory("Hero", "Fictional characters", "A hero is a real person or a main fictional character of a literary work who, in the face of danger, combats adversity through feats of ingenuity, bravery, or strength.");
        productCategoryDataStore.add(hero);
        ProductCategory villain = new ProductCategory("Villain", "Fictional characters", "A villain is an evil fictional character, whether based on a historical narrative or one of literary fiction.");
        productCategoryDataStore.add(villain);
        ProductCategory princess = new ProductCategory("Princess", "Fictional characters", "Princess is a regal rank and the feminine equivalent of prince. Most often, the term has been used for the consort of a prince, or for the daughter of a king or prince.");
        productCategoryDataStore.add(princess);
        ProductCategory group = new ProductCategory("Group", "Fictional characters", "A group of fictional characters linked by a familial association or a shared cause.");
        productCategoryDataStore.add(group);
        ProductCategory sidekick = new ProductCategory("Sidekick", "Fictional characters", "A sidekick is a slang expression for a close companion or colleague who is, or generally regarded as, subordinate to the one he or she accompanies.");
        productCategoryDataStore.add(sidekick);

        //setting up products and printing it
        productDataStore.add(new Product("Simba", 200, "USD", "Simba is the son of Mufasa, destined to rule the Pride Lands, as king.", hero, disney));
        productDataStore.add(new Product("Scar", 350, "USD", "As the younger brother of Mufasa and second-born prince of the Pride Lands, Scar is next in line to assume the throne as king.", villain, disney));
        productDataStore.add(new Product("Pumba", 250, "USD", "Pumba is a gluttonous warthog and the best friend of Timon, a meerkat.", sidekick, disney));
        productDataStore.add(new Product("Timon", 75, "USD", "Timon is a wisecracking meerkat and the best friend of Pumbaa, a warthog.", sidekick, disney));
        productDataStore.add(new Product("Powerpuff Girls", 500, "USD", "The Powerpuff Girls were created by Professor Utonium in an attempt \"to create the perfect little girls\" using a mixture of \"sugar, spice, and everything nice\".", group, cartoonNetwork));
        productDataStore.add(new Product("Mojo Jojo", 300, "USD", "A mad scientist anthropomorphic chimpanzee with great intelligence, who speaks with a Japanese accent.", villain, cartoonNetwork));
        productDataStore.add(new Product("Hercules", 125, "USD", "The son of Zeus and Hera, Hercules was stripped away from his home on Mount Olympus and turned mortal by his evil uncle, Hades, though he maintained his godlike strength. He works out in his free time.", hero, disney));
        productDataStore.add(new Product("Hades", 500, "USD", "The fast-talking god of the Underworld with a fiery temper and a vendetta against his eldest brother, Zeus.", villain, disney));
        productDataStore.add(new Product("Pegasus", 200, "USD", "Described by Zeus himself as \"a magnificent horse with the brain of a bird\".", sidekick, disney));
        productDataStore.add(new Product("Jasmine", 275, "USD", "An independent and rebellious young princess from Agrabah, a Middle Eastern kingdom ruled by her father, the Sultan.", princess, disney));
        productDataStore.add(new Product("Aladdin", 175, "USD", "Spent much of his youth scraping for food and ducking guards with his monkey sidekick, Abu. He sustained a heroically selfless deposition, which in turn made him entitled to a magic lamp concealing a wish-granting genie.", hero, disney));
        productDataStore.add(new Product("Mulan", 456, "USD", "The strong-willed and tenacious daughter of  a war veteran. When her father is called back into battle to defend China, she opts to protect him by taking his place.", princess, disney));
        productDataStore.add(new Product("Mushu", 55, "USD", "Once a guardian spirit of the Fa family, Mushu had been demoted to the humiliating position of an incense burner and gong-ringer for the deceased Fa ancestors.", sidekick, disney));
        productDataStore.add(new Product("Mike Wazowski", 125, "USD", "Mike Wazowski is a proud and confident monster, partly defined by his friendship with James P. Sullivan, whom he works with as his Scaring Assistant.", hero, disney));
        productDataStore.add(new Product("Sulley", 680, "USD", "A top scarer under his boss Henry J. Waternoose, who is the CEO of Monsters, Inc. Sulley's best friend is Mike Wazowski.", sidekick, disney));
        productDataStore.add(new Product("Mr. Big", 700, "USD", "An arctic shrew with an ironic reputation of being the most feared crime boss in Tundratown.", villain, disney));
        productDataStore.add(new Product("Judy Hopps", 215, "USD", "The first bunny ever to join Zootopia's police department. Determined to prove herself, Judy jumps at the chance to crack a case, even if it means teaming up with a con artist fox.", hero, disney));
        productDataStore.add(new Product("Nick Wilde", 200, "USD", "A con-artist fox in the city of Zootopia who finds himself compelled to aid Officer Judy Hopps, a rabbit, in her investigation.", hero, disney));
        productDataStore.add(new Product("The Incredibles", 999, "USD", "The Incredibles are a family of retired superheroes: the super-strong Mr. Incredible, the dexterous Elastigirl and their 3 children - Violet, Dashiell and Jack-Jack.", group, pixar));
        productDataStore.add(new Product("Buddy Pine/Syndrome", 170, "USD", "Buddy Pine, commonly known as Syndrome (and formerly Incrediboy), is the main antagonist of The Incredibles. He is Mr. Incredible's former fan.", villain, pixar));
        productDataStore.add(new Product("Toy Story", 750, "USD", "Toy Story is set in a world where toys come alive. Andy Davis 's favorite toy is a pull-string cowboy doll named Woody, who worries about being replaced by Andy's newest toy, a space ranger named Buzz Lightyear.", group, pixar));
        productDataStore.add(new Product("Merida", 325, "USD", "Passionate and fiery, Merida is a headstrong teenager of royal upbringing who is struggling to take control of her own destiny. She feels most at home in the outdoors honing her impressive athletic skills as an archer and swordfighter.", princess, pixar));
        productDataStore.add(new Product("Inside Out", 1000, "USD", "A group of five emotions: Joy, Sadness, Fear, Disgust and Anger trying to help her cope with her new life. They live in headquarters and control how a 11-year-old girl, Riley feels.", group, pixar));
        productDataStore.add(new Product("Johnny Bravo", 100, "USD", "Johnny Bravo has the looks of James Dean and sounds like Elvis Presley. He is incredibly super muscular, narcissistic and dim-witted, traits, that lead to a severe incorrigible inability to attract women.", hero, cartoonNetwork));
        productDataStore.add(new Product("Mandark", 400, "USD", "Dexter's rival and later mortal nemesis who often seeks to destroy both Dexter and his lab to prove once and for all that he is the superior genius.", villain, cartoonNetwork));
        productDataStore.add(new Product("Dee Dee", 320, "USD", "Dexter's older sister, who is mostly seen destroying or ruining Dexter's lab in various ways.", sidekick, cartoonNetwork));

        productDataStore.doSort();

        ProductCategory test = new ProductCategory("TEST", "Fictional characters", "dsn vndsv jkdsjkvndjkvn");
        ProductCategoryDaoDB catDB = ProductCategoryDaoDB.getInstance();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(catDB.getAll());
    }
}
