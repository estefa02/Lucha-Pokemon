# 🧠Algoritmos III: Pokémon Rojo Fuego

Este proyecto consiste en la implementación de una versión simplificada del juego **Pokémon Rojo Fuego** como trabajo práctico para la materia **Algoritmos y Programación III** de la facultad.

El objetivo principal es aplicar conceptos de diseño orientado a objetos, programación modular, testeo automatizado y uso de herramientas modernas de construcción y gestión de proyectos Java.

## 📸 Diagrama UML
![Diagrama UML](Diagrama_UML_Pokemon.png)

## 🛠️ Tecnologías y Herramientas

- Java 8+
- Maven
- JUnit
- Mockito
- Jackson
- JavaFX

## ▶️ Cómo ejecutar el proyecto

Desde la raíz del proyecto (donde está el `pom.xml`), podés ejecutar los siguientes comandos:

```bash
# Para ejecutar la versión con interfaz gráfica (JavaFX)
mvn exec:java -Dexec.mainClass="org.pokemon.MainJavaFx"

# Para ejecutar la versión por consola
mvn exec:java -Dexec.mainClass="org.pokemon.Main"
```

> Asegurate de tener Java y Maven correctamente instalados.

## ✅ Funcionalidades principales

- Selección de Pokémon inicial.
- Batallas por turnos entre Pokémon.
- Interfaz gráfica básica usando JavaFX.
- Persistencia de datos con Jackson.
- Cobertura de tests unitarios con JUnit y Mockito.

## 📁 Estructura del proyecto

El código fuente se encuentra en el directorio `src/main/java`, estructurado en paquetes que reflejan el modelo del juego y la lógica de negocio. También incluye tests en `src/test/java`.

## 👨‍💻 Autor

Trabajo realizado como parte del curso **Algoritmos y Programación III**.  
Repositorio adaptado para uso profesional y demostración.

