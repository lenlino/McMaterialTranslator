package com.lenlino;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A utility class for translating Minecraft material and entity names to different languages.
 */
public class McMaterialTranslator {
    private static final String BLOCK_PREFIX = "block.minecraft.";
    private static final String ITEM_PREFIX = "item.minecraft.";
    private static final String ENTITY_PREFIX = "entity.minecraft.";
    private static final String DEFAULT_LANGUAGE = "ja_jp";

    private final Map<Material, String> translationMap = new HashMap<>();
    private final Map<EntityType, String> entityTranslationMap = new HashMap<>();
    private static final Map<String, McMaterialTranslator> instances = new ConcurrentHashMap<>();
    private final String languageCode;

    /**
     * Gets the singleton instance of the translator with the default language (Japanese).
     *
     * @return The translator instance
     */
    public static McMaterialTranslator getInstance() {
        return getInstance(DEFAULT_LANGUAGE);
    }

    /**
     * Gets the singleton instance of the translator for the specified language.
     *
     * @param languageCode The language code (e.g., "ja_jp", "en_us")
     * @return The translator instance for the specified language
     */
    public static McMaterialTranslator getInstance(String languageCode) {
        return instances.computeIfAbsent(languageCode, McMaterialTranslator::new);
    }

    /**
     * Private constructor to enforce singleton pattern.
     * Loads translations from the JSON file for the specified language.
     *
     * @param languageCode The language code to load translations for
     */
    private McMaterialTranslator(String languageCode) {
        this.languageCode = languageCode;
        loadTranslations();
    }

    /**
     * Gets the language code for this translator instance.
     *
     * @return The language code
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * Loads translations from the language file.
     */
    private void loadTranslations() {
        try {
            String resourcePath = "lang/" + languageCode + ".json";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new IOException("Could not find " + resourcePath);
            }

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            // Process all materials
            for (Material material : Material.values()) {
                String materialName = material.name().toLowerCase();

                // Try to find as block first
                String blockKey = BLOCK_PREFIX + materialName;
                if (jsonObject.containsKey(blockKey)) {
                    translationMap.put(material, (String) jsonObject.get(blockKey));
                    continue;
                }

                // Try to find as item
                String itemKey = ITEM_PREFIX + materialName;
                if (jsonObject.containsKey(itemKey)) {
                    translationMap.put(material, (String) jsonObject.get(itemKey));
                    continue;
                }

                // Try with alternative naming conventions
                // Some materials might have different naming patterns
                String altMaterialName = convertMaterialNameToJsonKey(materialName);

                // Try alternative block key
                String altBlockKey = BLOCK_PREFIX + altMaterialName;
                if (jsonObject.containsKey(altBlockKey)) {
                    translationMap.put(material, (String) jsonObject.get(altBlockKey));
                    continue;
                }

                // Try alternative item key
                String altItemKey = ITEM_PREFIX + altMaterialName;
                if (jsonObject.containsKey(altItemKey)) {
                    translationMap.put(material, (String) jsonObject.get(altItemKey));
                }
            }

            // Process all entity types
            for (EntityType entityType : EntityType.values()) {
                String entityName = entityType.name().toLowerCase();

                // Try to find as entity
                String entityKey = ENTITY_PREFIX + entityName;
                if (jsonObject.containsKey(entityKey)) {
                    entityTranslationMap.put(entityType, (String) jsonObject.get(entityKey));
                    continue;
                }

                // Try with alternative naming conventions
                // Some entities might have different naming patterns
                String altEntityName = convertEntityNameToJsonKey(entityName);

                // Try alternative entity key
                String altEntityKey = ENTITY_PREFIX + altEntityName;
                if (jsonObject.containsKey(altEntityKey)) {
                    entityTranslationMap.put(entityType, (String) jsonObject.get(altEntityKey));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a material name to a format that might match the JSON keys.
     * Handles special cases and naming differences between Material enum and JSON keys.
     *
     * @param materialName The material name to convert
     * @return A string that might match a JSON key
     */
    private String convertMaterialNameToJsonKey(String materialName) {
        // Handle special cases
        switch (materialName) {
            case "grass_block": return "grass";
            case "water": return "water_bucket";
            case "lava": return "lava_bucket";
            case "tall_grass": return "grass";
            case "sunflower": return "double_plant";
            // Add more special cases as needed
            default: break;
        }

        // Remove _block suffix for some blocks
        if (materialName.endsWith("_block")) {
            String withoutBlock = materialName.substring(0, materialName.length() - 6);
            return withoutBlock;
        }

        return materialName;
    }

    /**
     * Converts an entity name to a format that might match the JSON keys.
     * Handles special cases and naming differences between EntityType enum and JSON keys.
     *
     * @param entityName The entity name to convert
     * @return A string that might match a JSON key
     */
    private String convertEntityNameToJsonKey(String entityName) {
        // Handle special cases
        switch (entityName) {
            case "player": return "player";
            case "armor_stand": return "armor_stand";
            // Add more special cases as needed
            default: break;
        }

        return entityName;
    }

    /**
     * Gets the translation for a material in the current language.
     *
     * @param material The material to translate
     * @return The translation in the current language, or the material name if no translation is found
     */
    public String translate(Material material) {
        if (material == null) {
            return "null";
        }

        return translationMap.getOrDefault(material, material.name());
    }

    /**
     * Gets the translation for a material in the specified language.
     *
     * @param material The material to translate
     * @param languageCode The language code to translate to
     * @return The translation in the specified language, or the material name if no translation is found
     */
    public static String translate(Material material, String languageCode) {
        return getInstance(languageCode).translate(material);
    }

    /**
     * Gets the translation for an entity type in the current language.
     *
     * @param entityType The entity type to translate
     * @return The translation in the current language, or the entity type name if no translation is found
     */
    public String translate(EntityType entityType) {
        if (entityType == null) {
            return "null";
        }

        return entityTranslationMap.getOrDefault(entityType, entityType.name());
    }

    /**
     * Gets the translation for an entity type in the specified language.
     *
     * @param entityType The entity type to translate
     * @param languageCode The language code to translate to
     * @return The translation in the specified language, or the entity type name if no translation is found
     */
    public static String translate(EntityType entityType, String languageCode) {
        return getInstance(languageCode).translate(entityType);
    }

    /**
     * Gets the translation for a material or entity name in the current language.
     *
     * @param name The name of the material or entity to translate
     * @return The translation in the current language, or the original name if no translation is found
     */
    public String translate(String name) {
        try {
            // Try as Material first
            Material material = Material.valueOf(name.toUpperCase());
            return translate(material);
        } catch (IllegalArgumentException e) {
            try {
                // Try as EntityType if Material fails
                EntityType entityType = EntityType.valueOf(name.toUpperCase());
                return translate(entityType);
            } catch (IllegalArgumentException e2) {
                // Return original name if both fail
                return name;
            }
        }
    }

    /**
     * Gets the translation for a material or entity name in the specified language.
     *
     * @param name The name of the material or entity to translate
     * @param languageCode The language code to translate to
     * @return The translation in the specified language, or the original name if no translation is found
     */
    public static String translate(String name, String languageCode) {
        return getInstance(languageCode).translate(name);
    }

    /**
     * Checks if a translation exists for the given material in the current language.
     *
     * @param material The material to check
     * @return true if a translation exists, false otherwise
     */
    public boolean hasTranslation(Material material) {
        return translationMap.containsKey(material);
    }

    /**
     * Checks if a translation exists for the given material in the specified language.
     *
     * @param material The material to check
     * @param languageCode The language code to check
     * @return true if a translation exists, false otherwise
     */
    public static boolean hasTranslation(Material material, String languageCode) {
        return getInstance(languageCode).hasTranslation(material);
    }

    /**
     * Checks if a translation exists for the given entity type in the current language.
     *
     * @param entityType The entity type to check
     * @return true if a translation exists, false otherwise
     */
    public boolean hasTranslation(EntityType entityType) {
        return entityTranslationMap.containsKey(entityType);
    }

    /**
     * Checks if a translation exists for the given entity type in the specified language.
     *
     * @param entityType The entity type to check
     * @param languageCode The language code to check
     * @return true if a translation exists, false otherwise
     */
    public static boolean hasTranslation(EntityType entityType, String languageCode) {
        return getInstance(languageCode).hasTranslation(entityType);
    }

    /**
     * Gets all available material translations in the current language.
     *
     * @return A map of materials to their translations in the current language
     */
    public Map<Material, String> getAllTranslations() {
        return new HashMap<>(translationMap);
    }

    /**
     * Gets all available material translations in the specified language.
     *
     * @param languageCode The language code to get translations for
     * @return A map of materials to their translations in the specified language
     */
    public static Map<Material, String> getAllTranslations(String languageCode) {
        return getInstance(languageCode).getAllTranslations();
    }

    /**
     * Gets all available entity translations in the current language.
     *
     * @return A map of entity types to their translations in the current language
     */
    public Map<EntityType, String> getAllEntityTranslations() {
        return new HashMap<>(entityTranslationMap);
    }

    /**
     * Gets all available entity translations in the specified language.
     *
     * @param languageCode The language code to get translations for
     * @return A map of entity types to their translations in the specified language
     */
    public static Map<EntityType, String> getAllEntityTranslations(String languageCode) {
        return getInstance(languageCode).getAllEntityTranslations();
    }
}
