# Backend-GDTurno

## Integrantes
-  Mauricio Bochatay
-  Vicente Aloi
-  Tiago Pieroni
-  Mateo Tosello

## Sistema de Gestión de Turnos - EasyTurn
	
<p align="justify">
	El usuario ingresara a la página web, el mismo puede sacar un turno, buscar sus turnos, y cancelar algún turno. Si el cliente decide sacar un turno deberá primero buscar por especialidad, instituciones y la obra social que tenga el paciente para que la página le muestre los profesionales que cumplan con los filtros especificados. Una vez que se le muestren los diferentes profesionales, el paciente elegirá el profesional y se le mostrarán los horarios que el profesional tenga disponible, para completar la elección del turno, deberá ingresar todos sus datos (nombre, apellido, mail, teléfono, fecha de nacimiento). 
</p>
<p align="justify">
	El paciente puede buscar sus turnos con su email y su número de documento. Se le mostrarán sus turnos próximos, allí mismo puede cancelar el o los turnos a los cuales no podrá asistir.
El médico podrá ver sus turnos del día, los mismos atenderán en un horario fijo de 8 a 16 horas los días hábiles, cada turno será de media hora. Una vez que se llevó a cabo la atención el médico completará los datos pertinentes a la misma y la marcará como completa (el paciente asistió). Cada médico puede tener una o más especialidades y puede atender en una o más instituciones. A cada médico se le otorgará una cuenta institucional.
</p>
<p align="justify">
	El administrador podrá cargar las instituciones, los profesionales, y las especialidades.
</p>

### Requerimientos

| **Requerimiento**                | **Cant. mín.** (1 o 2 integ) | **Cant. máx.** (3 o 4 integ) | **Detalle/Listado de casos incluidos**                          |
|----------------------------------|------------------------------|------------------------------|-----------------------------------------------------------------|
| **ABMC simple**                  | 1 x integ                    | 1 x integ                    | Administrador, Obra Social, Institución, Especialidad           |
| **ABMC dependiente**             | 1                            | 2                            | Profesional (Dependiente de Especialidad e Institución), Paciente (Dependiente de Obra Social) |
| **CU NO-ABMC**                   | 1                            | 2                            | Crear turno, Administrar asistencia y cancelación de turno      |
| **Listado simple**               | 1                            | 3 (*)                        | Listado de denominaciones de especialidades, Listado de turnos de un paciente, Listados de médicos |
| **Listado complejo**             | 0                            | 1 (*)                        | Listado de turnos de un profesional (filtrados por fecha)       |


