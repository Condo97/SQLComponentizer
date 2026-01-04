# AI Instructions: Adding SQLComponentizer to a Consuming Project

## Task
Add SQLComponentizer as a dependency from GitHub Packages.

## Steps

### 1. Update `build.gradle`

Add the GitHub Packages repository and the dependency:

```gradle
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Condo97/SQLComponentizer")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'io.github.condo97:SQLComponentizer:1.0.0'
}
```

### 2. Ensure `~/.gradle/gradle.properties` exists with credentials

The user needs these properties set (one-time global setup):

```properties
gpr.user=GITHUB_USERNAME
gpr.token=GITHUB_PERSONAL_ACCESS_TOKEN
```

The token needs `read:packages` scope. Create at: https://github.com/settings/tokens

### 3. For CI/CD (GitHub Actions)

If the consuming project uses GitHub Actions, add environment variables:

```yaml
env:
  GITHUB_USERNAME: ${{ github.actor }}
  GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

Or use a PAT with `read:packages` scope stored as a repository secret.

## Verification

Run `./gradlew build` to verify the dependency resolves correctly.

## Notes

- The package is hosted at: https://github.com/Condo97/SQLComponentizer/packages
- Group ID: `io.github.condo97`
- Artifact ID: `SQLComponentizer`
- Current version: `1.0.0`

