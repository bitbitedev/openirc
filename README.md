# OpenIRC - Open source IRC library

[![Build](https://github.com/ThatsNasu/OpenIRC/actions/workflows/gradle.yml/badge.svg?branch=release)](https://github.com/ThatsNasu/OpenIRC/actions/workflows/gradle.yml)
[![Version](https://img.shields.io/github/v/release/ThatsNasu/OpenIRC?display_name=tag&include_prereleases&label=Release)](https://github.com/ThatsNasu/OpenIRC/releases)

This Library implements IRC functionality based on the RFC2812 standard, which can be found [here](https://rfc-editor.org/rfc/rfc2812).
Since the RFC2812 standard superseeded the [RFC1459](https://rfc-editor.org/rfc/rfc1459) standard, there is no guarantee that this library will work with Nodes (may it be client or server) which still use the RFC1459 standard.

---

## Disclaimer

As long as there is no [release](https://github.com/thatsnasu/openirc/releases), all the above code snippts for importing this library will NOT work. I will post further updates regarding the status of this library here.

---

## Adding this library as dependency

is pretty straight forward. Just grab the jar file and add it to your projects dependencies, or use one of the following build tools.

### If you are using gradle

add the following line to your dependencies:

```groovy
implementation 'dev.thatsnasu:OpenIRC:0.1.0
```

### For Maven

it would be

```xml
<dependency>
    <groupId>dev.thatsnasu</groupId>
    <artifactId>OpenIRC</artifactId>
    <version>0.1.0</version>
</dependency>
```

### For everything else

Visit [maven central](https://search.maven.org/artifact/dev.thatsnasu/OpenIRC) and pick the release you like to find the code you need.
