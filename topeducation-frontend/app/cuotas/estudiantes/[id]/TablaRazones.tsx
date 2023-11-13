import { ResponseRazones } from "@/types/Responses";
import { obtenerRazonesPorRut } from "../../actions";
import FilaRazon from "./FilaRazon";

export default async function TablaRazonesPorRut({ rut }:{rut: string}){
  const response: ResponseRazones = await obtenerRazonesPorRut(rut);
  const razones = response.razones;  

  return (
    <div className="px-5">
      <h1 className="font-bold mb-2">Listado de razones estudiante rut: { rut }</h1>      
        <table>
          <thead>
            <tr>                        
              <th>Id</th>
              <th>Número</th>
              <th>Monto</th>
              <th>Fecha inicio</th>
              <th>Fecha fin</th>            
              <th>Tipo</th>
              <th>Estado</th>            
            </tr>
          </thead>
          <tbody>
            {razones?.map((razon) => (
              <FilaRazon key={razon.id} razon={razon} /> 
            ))}
          </tbody>
        </table>             
    </div>
  );
}