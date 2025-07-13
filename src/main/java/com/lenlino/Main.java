package com.lenlino;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Map;

/**
 * Example usage of the McMaterialTranslator library.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("McMaterialTranslator - Multi-Language Material Name Translator");
        System.out.println("=========================================================");

        // Part 1: Basic usage with default language (Japanese)
        System.out.println("\n--- Part 1: Basic Usage (Japanese) ---");

        // Get the translator instance with default language (Japanese)
        McMaterialTranslator japaneseTranslator = McMaterialTranslator.getInstance();
        System.out.println("Current language: " + japaneseTranslator.getLanguageCode());

        // Example 1: Translate a specific material
        Material stoneMaterial = Material.STONE;
        String stoneTranslation = japaneseTranslator.translate(stoneMaterial);
        System.out.println("Example 1: " + stoneMaterial.name() + " in Japanese is " + stoneTranslation);

        // Example 2: Translate using a string material name
        String diamondTranslation = japaneseTranslator.translate("DIAMOND");
        System.out.println("Example 2: DIAMOND in Japanese is " + diamondTranslation);

        // Part 2: Using different languages
        System.out.println("\n--- Part 2: Using Different Languages ---");

        // Example 3: Get a translator for a specific language (e.g., English)
        // Note: You need to have the en_us.json file in your resources/lang directory
        try {
            McMaterialTranslator englishTranslator = McMaterialTranslator.getInstance("en_us");
            System.out.println("Current language: " + englishTranslator.getLanguageCode());

            // Translate a material to English
            String englishStoneTranslation = englishTranslator.translate(stoneMaterial);
            System.out.println("Example 3: " + stoneMaterial.name() + " in English is " + englishStoneTranslation);

            // Or use the static method for one-off translations
            String englishDiamondTranslation = McMaterialTranslator.translate("DIAMOND", "en_us");
            System.out.println("Example 4: DIAMOND in English is " + englishDiamondTranslation);
        } catch (Exception e) {
            System.out.println("Note: English language file (en_us.json) not found. Add it to resources/lang to use English translations.");
        }

        // Part 3: Advanced features
        System.out.println("\n--- Part 3: Advanced Features ---");

        // Example 5: Check if a translation exists
        Material grassMaterial = Material.GRASS_BLOCK;
        boolean hasTranslation = japaneseTranslator.hasTranslation(grassMaterial);
        System.out.println("Example 5: Does " + grassMaterial.name() + " have a Japanese translation? " + hasTranslation);
        if (hasTranslation) {
            System.out.println("         Translation: " + japaneseTranslator.translate(grassMaterial));
        }

        // Example 6: Check if a translation exists in a specific language
        try {
            boolean hasEnglishTranslation = McMaterialTranslator.hasTranslation(grassMaterial, "en_us");
            System.out.println("Example 6: Does " + grassMaterial.name() + " have an English translation? " + hasEnglishTranslation);
        } catch (Exception e) {
            System.out.println("Example 6: Could not check English translation (en_us.json not found)");
        }

        // Example 7: Handle invalid material name
        try {
            String invalidTranslation = japaneseTranslator.translate("NOT_A_REAL_MATERIAL");
            System.out.println("Example 7: Invalid material name returns: " + invalidTranslation);
        } catch (Exception e) {
            System.out.println("Example 7: Error translating invalid material: " + e.getMessage());
        }

        // Example 8: Print a few common materials and their translations
        System.out.println("\nCommon Materials and their Japanese Translations:");
        System.out.println("----------------------------------------------");
        printMaterialTranslation(japaneseTranslator, Material.DIAMOND_SWORD);
        printMaterialTranslation(japaneseTranslator, Material.IRON_PICKAXE);
        printMaterialTranslation(japaneseTranslator, Material.GOLDEN_APPLE);
        printMaterialTranslation(japaneseTranslator, Material.OAK_LOG);
        printMaterialTranslation(japaneseTranslator, Material.COBBLESTONE);
        printMaterialTranslation(japaneseTranslator, Material.GRASS_BLOCK);
        printMaterialTranslation(japaneseTranslator, Material.WATER_BUCKET);

        // Example 9: Entity Type Translations
        System.out.println("\n--- Part 4: Entity Type Translations ---");

        // Translate entity types
        EntityType ghastType = EntityType.GHAST;
        String ghastTranslation = japaneseTranslator.translate(ghastType);
        System.out.println("Example 9: " + ghastType.name() + " in Japanese is " + ghastTranslation);

        // Translate using a string entity name
        String zombieTranslation = japaneseTranslator.translate("ZOMBIE");
        System.out.println("Example 10: ZOMBIE in Japanese is " + zombieTranslation);

        // Check if a translation exists
        EntityType creeperType = EntityType.CREEPER;
        boolean hasEntityTranslation = japaneseTranslator.hasTranslation(creeperType);
        System.out.println("Example 11: Does " + creeperType.name() + " have a Japanese translation? " + hasEntityTranslation);
        if (hasEntityTranslation) {
            System.out.println("         Translation: " + japaneseTranslator.translate(creeperType));
        }

        // Print a few common entities and their translations
        System.out.println("\nCommon Entities and their Japanese Translations:");
        System.out.println("----------------------------------------------");
        printEntityTranslation(japaneseTranslator, EntityType.ZOMBIE);
        printEntityTranslation(japaneseTranslator, EntityType.SKELETON);
        printEntityTranslation(japaneseTranslator, EntityType.CREEPER);
        printEntityTranslation(japaneseTranslator, EntityType.ENDERMAN);
        printEntityTranslation(japaneseTranslator, EntityType.VILLAGER);
    }

    /**
     * Helper method to print a material and its translation.
     */
    private static void printMaterialTranslation(McMaterialTranslator translator, Material material) {
        System.out.println(material.name() + " -> " + translator.translate(material));
    }

    /**
     * Helper method to print an entity type and its translation.
     */
    private static void printEntityTranslation(McMaterialTranslator translator, EntityType entityType) {
        System.out.println(entityType.name() + " -> " + translator.translate(entityType));
    }
}
