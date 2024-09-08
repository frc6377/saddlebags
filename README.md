# Saddlebags
This repository is meant to be used as a git submodule within a robot code project. It contains Java sources that can be carried forward (and iterated on) from previous years.

![workflow_status](https://github.com/frc6377/saddlebags/actions/workflows/main.yml/badge.svg)

### Install instructions
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

### Cloning
When cloning a project that contains a submodule, you must additionally run:

`git submodule update --init`

This will clone the submodule into the right location.
