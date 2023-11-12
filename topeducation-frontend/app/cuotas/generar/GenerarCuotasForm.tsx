"use client";

import { useState } from "react";
import { generarCuotas } from "./actions";
import { ResponseRazones } from "@/types/Responses";

export default function GenerarCuotasForm(){
  const [rut, setRut] = useState('');
  const [numCuotas, setNumCuotas] = useState('');
  
  const handleSubmit = async (e:React.MouseEvent<HTMLElement>) => {
    e.preventDefault();
    const response:ResponseRazones = await generarCuotas(rut, numCuotas);    
    alert(response.message);
    setRut('');
    setNumCuotas('');
  }

  return(
    <form className="px-7 flex flex-col items-start gap-5">
      <div className="flex flex-col">
        <label htmlFor="rut">Rut</label>
        <input type="text" placeholder="Ej: 12.345.678-9" id="rut" value={rut} onChange={(e) => setRut(e.target.value)}/>
      </div>       

      <div className="flex flex-col">
        <label htmlFor="numCuotas">Número de cuotas</label>
        <input type="number" placeholder="Ej: 1" id="numCuotas" value={numCuotas} onChange={(e) => setNumCuotas(e.target.value)}/>
      </div>       
      <button className="px-3 py-2 text-white bg-blue-600 rounded-md" onClick={handleSubmit}>GENERAR CUOTAS</button>  
    </form>
  )
}