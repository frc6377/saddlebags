# Saddlebags
This repository is meant to be used as a git submodule within a robot code project. It contains Java sources that can be carried forward (and iterated on) from previous years.

![workflow_status](https://github.com/frc6377/saddlebags/actions/workflows/main.yml/badge.svg)

### Install instructions

#### As a Git Submodule
To add this repo as a submodule, run the following command from the robot project directory:

`git submodule add https://github.com/frc6377/saddlebags src/saddlebags`

Then append the following to the build.gradle file:

```
sourceSets {
    main {
        java {
            srcDirs("src/saddlebags/main")
        }
    }
    test {
        java {
            srcDirs("src/saddlebags/test")
        }
    }
}

```

#### As a GitHub Package
To simply install as a GitHub package instead, add the following to build.gradle:
```
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/frc6377/saddlebags")
        credentials {
            username = "Mechanical-Advantage-Bot"
            password = "\u0067\u0068\u0070\u005f\u006e\u0056\u0051\u006a\u0055\u004f\u004c\u0061\u0079\u0066\u006e\u0078\u006e\u0037\u0051\u0049\u0054\u0042\u0032\u004c\u004a\u006d\u0055\u0070\u0073\u0031\u006d\u0037\u004c\u005a\u0030\u0076\u0062\u0070\u0063\u0051"
        }
    }
}
```
And then add to the dependencies section
```
implementation 'frc6377.saddlebags:saddlebags-java:1.0.1'
```

### Cloning
When cloning a project that contains a submodule, you must additionally run:

`git submodule update --init`

This will clone the submodule into the right location.
