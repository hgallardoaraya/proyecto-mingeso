import FilaEstudiante from "./FilaEstudiante";
import { obtenerEstudiantes } from "./actions";
import { ResponseEstudiantes } from "@/types/Responses";

export default async function TablaEstudiantes(){
  const response: ResponseEstudiantes = await obtenerEstudiantes();
  const estudiantes = response.estudiantes;  

  return (
    <div className="px-5">
      <h1 className="font-bold mb-2">Listado de estudiantes</h1>
      <table>
        <thead>
          <tr>            
            <th>índice</th>
            <th>Rut</th>
            <th>1er Nombre</th>
            <th>2do Nombre</th>
            <th>1er Apellido</th>
            <th>2do Apellido</th>
            <th>Fecha nacimiento</th>
            <th>Año egreso</th>
            <th>Nombre colegio</th>
            <th>Tipo colegio</th>
            <th>Tipo pago arancel</th>
          </tr>
        </thead>
        <tbody>
          {estudiantes?.map((estudiante, index) => (
            <FilaEstudiante key={index} index={index} estudiante={estudiante} /> 
          ))}
        </tbody>
      </table>
    </div>
  );
}