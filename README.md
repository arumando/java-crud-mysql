# 🗄️ CRUD de usuarios y materias con Java y MySQL

Aplicación de consola en **Java** que se conecta a **MySQL** con **JDBC** para **crear, consultar, actualizar y eliminar** (CRUD) usuarios y materias de una escuela.

> Contexto: [PENDIENTE: materia y semestre en que hiciste este proyecto]

## ✨ Qué hace

```
===== ESCUELA =====
 1. Listar usuarios      5. Listar materias
 2. Agregar usuario      6. Agregar materia
 3. Actualizar usuario   7. Actualizar materia
 4. Eliminar usuario     8. Eliminar materia
 0. Salir
```

- **CRUD completo** para dos tablas, con consultas parametrizadas (`PreparedStatement`) que evitan la **inyección SQL**.
- **Validación de datos** en el menú: campos vacíos, correos mal escritos y números negativos.
- **Mensajes de error claros**: correo o clave duplicados, servidor apagado, archivo de configuración faltante.
- **La contraseña no está en el código**: se lee de `config/db.properties`, que está en `.gitignore`.

## 🏗️ Estructura (patrón DAO)

```
src/escuela/
├── Main.java              # menú de consola
├── db/Conexion.java       # abre conexiones leyendo config/db.properties
├── modelo/                # clases que representan cada tabla
│   ├── Usuario.java
│   └── Materia.java
└── dao/                   # todo el SQL vive aquí (Data Access Object)
    ├── UsuarioDAO.java
    └── MateriaDAO.java
sql/schema.sql             # crea la base "escuela", las tablas y datos de ejemplo
config/db.properties.example
```

## 🛠️ Tecnologías

- Java 17 o superior
- MySQL 8
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) (controlador JDBC)

## ▶️ Cómo ejecutarlo

**1. Crea la base de datos.** Ejecuta `sql/schema.sql` en MySQL Workbench, o desde la terminal:

```bash
mysql -u root -p < sql/schema.sql
```

**2. Configura la conexión.** Copia `config/db.properties.example` como `config/db.properties` y escribe tu usuario y contraseña de MySQL.

**3. Descarga el conector JDBC.** Baja **MySQL Connector/J** (*Platform Independent*), descomprímelo y copia el archivo `mysql-connector-j-X.X.X.jar` a la carpeta `lib/`.

**4. Compila y ejecuta** desde la carpeta del proyecto.

Git Bash, Linux o macOS:

```bash
javac -encoding UTF-8 -cp "lib/*" -d out $(find src -name "*.java")
java -cp "out:lib/*" escuela.Main
```

PowerShell en Windows:

```powershell
javac -encoding UTF-8 -cp "lib/*" -d out (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp "out;lib/*" escuela.Main
```

> En Windows el separador del classpath es `;`; en Linux y macOS es `:`.

## 🔧 Mejoras respecto a la primera versión

| Antes | Ahora |
|---|---|
| `getConexion()` devolvía `null` si fallaba y el programa tronaba con `NullPointerException` | `Conexion.obtener()` lanza `SQLException` con un mensaje que explica qué revisar |
| Los errores se ocultaban con `e.printStackTrace()` | Los DAO lanzan la excepción y el menú muestra un mensaje entendible |
| `obtenerPorId()` devolvía `null` | Devuelve `Optional`, así no se olvida revisar si existe |
| Usuario `root` escrito en el código | Datos de acceso en un archivo fuera de Git |
| El id se escribía a mano | La base de datos lo genera (`AUTO_INCREMENT`) y se recupera con `getGeneratedKeys()` |
| No había script para crear las tablas | `sql/schema.sql` con llaves, `UNIQUE` y `CHECK` |

## 📸 Capturas

[PENDIENTE: captura del menú listando usuarios]

[PENDIENTE: captura de las tablas en MySQL Workbench]

## 📚 Qué aprendí

<!-- Revisa esta lista y escríbela con tus propias palabras. -->
- Conectar Java con MySQL usando JDBC.
- Separar el acceso a datos (DAO) de la interfaz (menú).
- Usar `PreparedStatement` para evitar inyección SQL y `try-with-resources` para cerrar conexiones.
- Manejar errores de base de datos sin que el programa se detenga.
- No guardar contraseñas dentro del código.

## 👤 Autor

**José Armando García Bandera** — [github.com/arumando](https://github.com/arumando)
