# AI Instructions: Publishing a New Library to GitHub Packages

## Task
Configure a Java/Gradle library project to publish to GitHub Packages with automated releases.

## Steps

### 1. Update `build.gradle`

Replace or update the build file with this template (adjust values in CAPS):

```gradle
plugins {
    id 'java-library'
    id 'maven-publish'
}

group = 'io.github.condo97'
version = System.getenv('VERSION') ?: '1.0.0-SNAPSHOT'

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies {
    // Add project dependencies here
}

tasks.named('test') {
    useJUnitPlatform()
}

publishing {
    publications {
        gpr(MavenPublication) {
            from components.java
            
            pom {
                name = 'LIBRARY_NAME'
                description = 'LIBRARY_DESCRIPTION'
                url = 'https://github.com/Condo97/REPO_NAME'
                
                licenses {
                    license {
                        name = 'MIT License'
                        url = 'https://opensource.org/licenses/MIT'
                    }
                }
                
                developers {
                    developer {
                        id = 'condo97'
                        name = 'Condo97'
                    }
                }
                
                scm {
                    connection = 'scm:git:git://github.com/Condo97/REPO_NAME.git'
                    developerConnection = 'scm:git:ssh://github.com/Condo97/REPO_NAME.git'
                    url = 'https://github.com/Condo97/REPO_NAME'
                }
            }
        }
    }
    
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Condo97/REPO_NAME")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") ?: ""
                password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.token") ?: ""
            }
        }
    }
}
```

**Replace these placeholders:**
- `LIBRARY_NAME` - Human-readable name
- `LIBRARY_DESCRIPTION` - Short description
- `REPO_NAME` - GitHub repository name (e.g., `SQLComponentizer`)

### 2. Clean up `settings.gradle`

Ensure it only contains the root project name (remove any unused `include` statements):

```gradle
rootProject.name = 'LIBRARY_NAME'
```

### 3. Create `.github/workflows/publish.yml`

Create the directory structure and workflow file:

```yaml
name: Publish to GitHub Packages

on:
  push:
    tags:
      - 'v*'

jobs:
  publish:
    runs-on: ubuntu-latest
    permissions:
      contents: read
      packages: write

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Setup Gradle
        uses: gradle/actions/setup-gradle@v3

      - name: Extract version from tag
        run: echo "VERSION=${GITHUB_REF#refs/tags/v}" >> $GITHUB_ENV

      - name: Build and publish
        run: ./gradlew publish
        env:
          GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
          GITHUB_ACTOR: ${{ github.actor }}
          VERSION: ${{ env.VERSION }}
```

### 4. Verify Build

Run locally to ensure it compiles:

```bash
./gradlew build
```

### 5. Commit, Push, and Tag

```bash
git add -A
git commit -m "Add GitHub Packages publishing with automated workflow"
git push

git tag v1.0.0
git push origin v1.0.0
```

### 6. Verify Publication

Check the Actions tab on GitHub to confirm the workflow succeeded.

## Adding as Dependency in Other Projects

Once published, add to consuming projects:

```gradle
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Condo97/REPO_NAME")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("gpr.token") ?: System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'io.github.condo97:ARTIFACT_NAME:1.0.0'
}
```

## Releasing New Versions

To release a new version:

```bash
git tag v1.1.0
git push origin v1.1.0
```

The workflow automatically extracts the version from the tag.

