# McMaterialTranslator

McMaterialTranslator is a Java library for Spigot/Bukkit plugins that provides translations for Minecraft material names in multiple languages. It uses the official Minecraft language files (e.g., `ja_jp.json`, `en_us.json`) to translate material names.

## Features

- Translate Minecraft material names to different languages
- Support for both blocks and items
- Multiple language support with easy language switching
- Singleton pattern for easy access
- Comprehensive error handling
- Simple and intuitive API

## Installation

### Maven

Add the following to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>lenlino-repo</id>
        <url>https://repo.lenlino.com/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.lenlino</groupId>
        <artifactId>McMaterialTranslator</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Manual Installation

1. Clone this repository
2. Build the project with Maven: `mvn clean package`
3. Add the resulting JAR file to your project's dependencies

## Usage

### Basic Usage

```java
// Get the translator instance with default language (Japanese)
McMaterialTranslator translator = McMaterialTranslator.getInstance();

// Translate a material
Material stoneMaterial = Material.STONE;
String stoneTranslation = translator.translate(stoneMaterial);
System.out.println(stoneMaterial.name() + " in Japanese is " + stoneTranslation);

// Translate using a string material name
String diamondTranslation = translator.translate("DIAMOND");
System.out.println("DIAMOND in Japanese is " + diamondTranslation);
```

### Using Different Languages

```java
// Get a translator for a specific language (e.g., English)
McMaterialTranslator englishTranslator = McMaterialTranslator.getInstance("en_us");

// Translate a material to English
Material stoneMaterial = Material.STONE;
String stoneTranslation = englishTranslator.translate(stoneMaterial);
System.out.println(stoneMaterial.name() + " in English is " + stoneTranslation);

// Or use the static method for one-off translations
String diamondTranslation = McMaterialTranslator.translate("DIAMOND", "en_us");
System.out.println("DIAMOND in English is " + diamondTranslation);
```

### Check if Translation Exists

```java
Material material = Material.GRASS_BLOCK;
boolean hasTranslation = translator.hasTranslation(material);
if (hasTranslation) {
    System.out.println("Translation: " + translator.translate(material));
} else {
    System.out.println("No translation available for " + material.name());
}

// Check if translation exists in a specific language
boolean hasEnglishTranslation = McMaterialTranslator.hasTranslation(material, "en_us");
```

### Get All Translations

```java
// Get all translations for the current language
Map<Material, String> allTranslations = translator.getAllTranslations();
for (Map.Entry<Material, String> entry : allTranslations.entrySet()) {
    System.out.println(entry.getKey().name() + " -> " + entry.getValue());
}

// Get all translations for a specific language
Map<Material, String> allEnglishTranslations = McMaterialTranslator.getAllTranslations("en_us");
```

## Example

See the `Main.java` file for a complete example of how to use the library.

## Continuous Integration / Continuous Deployment

This project uses GitHub Actions for CI/CD to automatically build and deploy the library to a Nexus3 repository when code is pushed to the main branch.

### GitHub Actions Workflow

The workflow is defined in `.github/workflows/maven-deploy.yml` and performs the following steps:
1. Checks out the code
2. Sets up JDK 17
3. Builds the project with Maven
4. Deploys the artifacts to Nexus3 (only on push events, not on pull requests)

### Required GitHub Secrets

To use this workflow, you need to set up the following secrets in your GitHub repository:

- `NEXUS_URL`: The URL of your Nexus3 repository (e.g., `https://nexus.example.com`)
- `NEXUS_USERNAME`: The username for authentication with Nexus3
- `NEXUS_PASSWORD`: The password for authentication with Nexus3

### Setting Up Nexus3 Repository

1. Create a Maven repository in your Nexus3 instance
2. Configure the repository to allow deployments
3. Create a user with deployment permissions
4. Add the user credentials to GitHub secrets

### Using the Deployed Library

Once the library is deployed to Nexus3, you can use it in your projects by adding the following to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>nexus</id>
        <url>https://your-nexus-url/repository/maven-releases/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.lenlino</groupId>
        <artifactId>McMaterialTranslator</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- This library uses the official Minecraft language files (`ja_jp.json`, `en_us.json`)
- Thanks to the Spigot/Bukkit community for their support
