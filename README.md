# Kiso
Personal Java lib for various projects

# Getting Started
Add Kiso to your project

Maven
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.cyr1en.kiso</groupId>
  <artifactId>kiso-module</artifactId>
  <version>Tag</version>
</dependency>
```

Gradle
```groovy
repositories {
maven { url 'https://jitpack.io' }
}
    
dependencies {
  compile 'com.github.cyr1en.kiso:kiso-module:Tag'
}    
```
# Modules
### Kiso-MC
This module is for developing plugins for Bukkit/Spigot.

dependecy info: `group = com.github.cyr1en.kiso, name = kiso-mc`

### Kiso-Utils
This module contains utility class that makes handling classes easier.

dependency info: `group = com.github.cyr1en.kiso, name = kiso-utils`.
