## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Scrubbing (Maven)

This project now uses Maven for dependency and build management.

## Folder Structure

The workspace contains two folders by default, where:

- `src/main/java`: application source code
- `src/test/java`: unit tests
- `pom.xml`: Maven configuration (dependencies, compiler, test plugins)

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

Dependencies are declared in `pom.xml`:

- `junit:junit:4.13.2` for unit testing
- `org.mockito:mockito-core:5.12.0` for mocks in tests

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Common Commands

```powershell
mvn clean test
mvn clean package
```
