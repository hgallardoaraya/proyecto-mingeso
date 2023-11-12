import Estudiante from "@/types/Estudiante";

interface FilaEstudianteProps {
  index: number,
  estudiante: Estudiante
}

export default function FilaEstudiante({ index, estudiante }:FilaEstudianteProps){  
  return(
    <tr>
      <td>{ index + 1 }</td>
      <td>{estudiante.rut}</td>
      <td>{estudiante.nombre1}</td>
      <td>{estudiante.nombre2}</td>      
      <td>{estudiante.apellido1}</td>      
      <td>{estudiante.apellido2}</td>      
      <td>{estudiante.fechaNacimiento}</td>      
      <td>{estudiante.anioEgreso}</td>      
      <td>{estudiante.nombreColegio}</td>      
      <td>{estudiante.tipoColegio.tipo}</td>      
      <td>{estudiante.tipoPagoArancel.tipo}</td>      
    </tr>
  );
}