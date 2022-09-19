package com.erapps.foodrecipesapp.ui.screens.bycategory

enum class Category(val value: String) {
    Beef("Beef"),
    Breakfast("Breakfast"),
    Chicken("Chicken"),
    Dessert("Dessert"),
    Goat("Goat"),
    Lamb("Lamb"),
    Miscellaneous("Miscellaneous"),
    Pasta("Pasta"),
    Pork("Pork"),
    Seafood("Seafood"),
    Side("Side"),
    Starter("Starter"),
    Vegan("Vegan"),
    Vegetarian("Vegetarian")
}

fun getCategories(): List<Category> {
    return listOf(
        Category.Beef,
        Category.Breakfast,
        Category.Chicken,
        Category.Dessert,
        Category.Goat,
        Category.Lamb,
        Category.Miscellaneous,
        Category.Pasta,
        Category.Pork,
        Category.Seafood,
        Category.Side,
        Category.Starter,
        Category.Vegan,
        Category.Vegetarian
    )
}

fun getCategory(value: String): Category? {
    val map = Category.values().associateBy(Category::value)
    return map[value]
}