
<h1>База студентов</h1>
<h2>Основные сущности:</h2>

- Студент
- Общежитие
- Курс-дисциплина

<h2>Интеграционные тесты:</h2>

- [Тесты для контроллеров](https://github.com/bendrikovski/StudentsBase/tree/master/src/test/java/com/ben/StudentsBase/controller)


<h2>Права доступа к методам:</h2>
 
 Basic Authentication
  
<h3>COURSES:</h3>

ALL
- GET ("/courses")
- GET("/courses/{disciplineId}")
- GET("/courses/{discipline}")

ADMIN
- POST("/courses")
- PUT("/courses")
- DELETE("/courses/{courseId}")
- GET("/courses/subscribers/{disciplineId}")

<h3>HOSTELS:</h3>
  
ALL
- GET("/hostels")
- GET("/hostels/{hostelId}")

ADMIN
- POST("/hostels")
- PUT("/hostels")
- DELETE("/hostels/{hostelId}")

<h3>STUDENTS:</h3>
  
ADMIN
- GET("/students")
- GET("/students/{studentId}")
- POST("/students")
- PUT("/students")
- DELETE("/students/{studentId}")
