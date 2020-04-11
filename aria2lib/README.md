# aria2lib
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1ea29f828e684589896164e105e1a66b)](https://www.codacy.com/manual/devgianlu/aria2lib?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=devgianlu/aria2lib&amp;utm_campaign=Badge_Grade)
[![Crowdin](https://badges.crowdin.net/aria2lib/localized.svg)](https://crowdin.com/project/aria2lib)

This is a small module (Android library only) that allows to download and manage an [aria2](https://github.com/aria2/aria2) executable. It is used in [Aria2Android](https://github.com/devgianlu/Aria2Android) and [Aria2App](https://github.com/devgianlu/Aria2App).

> This also depends on [CommonUtils](https://github.com/devgianlu/CommonUtils). You can find how to include it there, but it is pretty similar to what comes below.

## How to
- Add as Git submodule in your project (`git submodule add https://github.com/devgianlu/aria2lib`)
- Add the Gradle module to your `settings.gradle`:
```
include ':aria2lib', ':CommonUtils' ...
project(':CommonUtils').projectDir = new File('./CommonUtils/utils')
project(':aria2lib').projectDir = new File('./aria2lib')
```
- Add it as a dependency:
```
dependencies {
    implementation project(':aria2lib')
    ...
}
```

## Compile aria2c executables
This repository already includes the necessary executables to run aria2, but if you want to build them yourself with `./gradlew compileAria2 -Pforce`.
The task will simply delete the current executables (because of the `force` flag) and execute the `./build_aria2c.sh <tag/commit/branch>` script. 
If executing it directly, you have to specify a reference to [devgianlu/aria2-android](https://github.com/devgianlu/aria2-android). 
