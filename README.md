# Kiso
Code that provides the backbone for my projects

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

---
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Attribution 4.0 International License</a>.
