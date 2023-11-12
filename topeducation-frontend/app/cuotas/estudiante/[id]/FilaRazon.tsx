import Estudiante from "@/types/Estudiante";
import Razon from "@/types/Razon";

interface FilaRazonProps {
  razon: Razon
}

export default function FilaRazon({ razon }:FilaRazonProps){  
  return(
    <tr>      
      <td>{ razon.id }</td>
      <td>{ razon.numero }</td>
      <td>{ razon.monto }</td>
      <td>{ razon.fechaInicio }</td>
      <td>{ razon.fechaFin }</td>
      <td>{ razon.tipo.tipo }</td>
      <td>{ razon.estado.estado }</td>
    </tr>
  );
}