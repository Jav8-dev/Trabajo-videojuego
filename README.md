# 🚀 Proyecto: NIGHT CLASS

**Miembros del Equipo:**
* [Alejandro Hernandez]
* [Javier Garrocho]

---

## 📖 Nuestra Historia (El "Skin")

**Temática del Juego:** Escape / Backrooms

**Premisa:**
> Estabas caminando por el matapiojos tranquilamente por la noche, de repente te intentan asaltar, huyendo tropiezas y te das un duro golpe en la cabeza, piensas "me va a salir un gran chichon", pero de la nada caes inconsciente, despiertas en un sitio tetrico, este sitio parece un laberinto sin final, las paredes son del mismo color y la iluminacion es tenue y calurosa, ¿Que vas a hacer ahora?

**Objetivo:**
Descubrir qué ha pasado y encontrar la forma de salir de las backrooms.

---

## ⚙️ Estado del Proyecto (Fase 1: Motor Básico)

Esta primera versión del proyecto (Misión 1 / UD1-UD3) implementa el "núcleo" del motor de juego usando **programación procedural** (métodos estáticos) y **arrays**.

**Funcionalidad del Núcleo:**
* Bucle de juego principal (`while`).
* Mapa de habitaciones (Array `habitaciones[]`).
* Gestión de inventario (Array `inventario[]`).
* Gestión de objetos por sala (Matriz `objetosMapa[][]`).
* **Comandos implementados:** `ir derecha`, `ir izquierda`, `mirar`, `inventario`, `coger`, `ayuda` y `salir`.

**Tecnologías (Fase 1):**
* Java (JDK)
* Programación Procedural
* Arrays
* Git

## 🔜 Próximas Fases

* **Fase 2 (POO):** Refactorizar este código a Programación Orientada a Objetos (Clases `Habitacion`, `Jugador`, `Objeto`).
* **Fase 3 (Colecciones/Ficheros):** Cambiar los arrays del inventario por `ArrayLists` y añadir un sistema de **Guardar/Cargar Partida**.
* **Fase 4 (GUI/BBDD):** Crear una interfaz gráfica (GUI) y conectar a una base de datos para guardar progresos o logros.
