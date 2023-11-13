import Razon from "@/types/Razon";

export default function FilaRazonPago({razon, handleCheckboxChange}:{razon: Razon, handleCheckboxChange: any}){  
  
  return(
    <tr>      
      <td>{ razon.id }</td>
      <td>{ razon.numero }</td>
      <td>{ razon.monto }</td>
      <td>{ razon.fechaInicio }</td>
      <td>{ razon.fechaFin }</td>
      <td>{ razon.tipo.tipo }</td>
      <td>{ razon.estado.estado }</td>
      <td><input type="checkbox" value={ razon.id } onChange={ handleCheckboxChange } /></td>
    </tr>
  );
}