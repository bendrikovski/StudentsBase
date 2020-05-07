ALL
@GetMapping("/courses")
@GetMapping("/courses/{disciplineId}")
@GetMapping("/courses/{discipline}")

ADMIN
@PostMapping("/courses")
@PutMapping("/courses")
@DeleteMapping("/courses/{courseId}")
@GetMapping("/courses/subscribers/{disciplineId}")

ALL
@GetMapping("/hostels")
@GetMapping("/hostels/{hostelId}")

ADMIN
@PostMapping("/hostels")
@PutMapping("/hostels")
@DeleteMapping("/hostels/{hostelId}")

ADMIN
@GetMapping("/students")
@GetMapping("/students/{studentId}")
@PostMapping("/students")
@PutMapping("/students")
@DeleteMapping("/students/{studentId}")