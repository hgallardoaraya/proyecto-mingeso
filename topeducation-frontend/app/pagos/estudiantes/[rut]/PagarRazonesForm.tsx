"use client";

import Razon from "@/types/Razon";
import FilaRazonPago from "./FilaRazonPago";
import { FormEvent, useState } from "react";
import pagarRazones from "./actions";
import { ResponsePago } from "@/types/Responses";

export default function PagarRazonesForm({rut, razones}:{rut:string, razones:Array<Razon>}){
  const [razonesPorPagar, setRazonesPorPagar]:any = useState([]);

  const handleCheckboxChange = (e:any) => {    
    if(!e.target.checked && razonesPorPagar.includes(e.target.value)){      
      setRazonesPorPagar(razonesPorPagar.filter((id:number) => id != e.target.value));
    }

    if(e.target.checked && !razonesPorPagar.includes(e.target.value)){
      setRazonesPorPagar([...razonesPorPagar, e.target.value]);
    }    
  }

  const handleSubmit = async (e:FormEvent) => {    
    e.preventDefault();
    const response:ResponsePago = await pagarRazones(rut, razonesPorPagar);
    alert(response.message);
  }

  return(
    <>
      <form onSubmit={handleSubmit}>
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
              <th>Seleccionar</th>        
            </tr>
          </thead>
          <tbody>
            {razones?.map((razon) => (
              <FilaRazonPago key={razon.id} razon={razon} handleCheckboxChange={handleCheckboxChange}/> 
            ))}
          </tbody>
        </table>
        <button type="submit" className="px-3 py-2 text-white bg-blue-600 rounded-md">PAGAR</button>  
      </form>
    </>
  )
}