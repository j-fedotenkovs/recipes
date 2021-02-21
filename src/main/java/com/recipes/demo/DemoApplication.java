package com.recipes.demo;

import com.recipes.demo.model.Ingredient;
import com.recipes.demo.model.Recipe;
import com.recipes.demo.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(RecipeRepository repository) {
		return (args) -> repository.saveAll(createRecipes());
	}

	private List<Recipe> createRecipes() {
		Recipe lasagna = new Recipe();
		lasagna.setName("Lasagna");
		lasagna.setImageUrl("https://www.365daysofbakingandmore.com/wp-content/uploads/2011/02/Lasagna-FEATURE.jpg");
		lasagna.setDescription("You will love it!");

		Ingredient pastaSheets = new Ingredient() {{
			setRecipe(lasagna);
			setName("Pasta sheets");
			setAmount(4);
		}};

		Ingredient stuffing = new Ingredient() {{
			setRecipe(lasagna);
			setName("Stuffing");
			setAmount(500);
		}};

		lasagna.setIngredients(Arrays.asList(pastaSheets, stuffing));

		Recipe risotto = new Recipe();
		risotto.setName("Risotto");
		risotto.setImageUrl("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/delish-191112-risotto-rice-0151-landscape-pf-1574723947.jpg");
		risotto.setDescription("The best of the best!");

		Ingredient rice = new Ingredient() {{
			setRecipe(risotto);
			setName("Rice");
			setAmount(150);
		}};

		Ingredient poultry = new Ingredient() {{
			setRecipe(risotto);
			setName("Poultry");
			setAmount(300);
		}};

		Ingredient wine = new Ingredient() {{
			setRecipe(risotto);
			setName("White wine");
			setAmount(150);
		}};

		risotto.setIngredients(Arrays.asList(rice, poultry, wine));

		return Arrays.asList(lasagna, risotto);
	}

}
