# Установка и Запуск Проекта

Этот проект использует Maven для управления зависимостями и сборки проекта. Следуйте инструкциям ниже для установки и запуска проекта.

## Требования

- Установленный JDK (Java Development Kit) версии 8 или выше.
- Установленный Maven версии 3.6.3 или выше.

## Шаги по Установке

1. **Клонируйте репозиторий:**

    ```sh
    git clone https://github.com/Hackron-Dev/FourInARowMinimax.git
    ```

2. **Перейдите в директорию проекта:**

    ```sh
    cd FourInARowMinimax
    ```

3. **Соберите проект с помощью Maven:**

    ```sh
    mvn clean install
    ```

4. **Настройте конфигурацию аккаунта papergames.io:**

    Откройте файл `C:\Users\Hackron\Documents\servletTrain\WebAi\src\main\resources\config.properties` и введите данные от вашего аккаунта papergames.io:

    ```properties
    username=ваш_ник
    email=ваша_почта
    password=ваш_пароль
    ```

## Запуск Проекта

После успешной сборки проекта, выполните следующие шаги для запуска:

1. **Запустите проект с помощью Maven:**

    ```sh
    mvn exec:java -Dexec.mainClass="com.example.Main"
    ```

    Убедитесь, что вы заменили `com.example.Main` на правильное имя основного класса вашего проекта.

## Файл pom.xml

Убедитесь, что ваш `pom.xml` файл настроен правильно. Пример базового `pom.xml` файла:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>FourInARowMinimax</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Добавьте здесь свои зависимости -->
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.6.0</version>
                <configuration>
                    <mainClass>com.example.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
