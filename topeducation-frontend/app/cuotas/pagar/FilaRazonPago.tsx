"use client";

import Razon from "@/types/Razon";
import { useState } from "react";

export default function FilaRazonPago({razon}:{razon: Razon}){  
  const [razonElegida, setRazonElegida] = useState(false);
  return(
    <tr>      
      <td>{ razon.id }</td>
      <td>{ razon.numero }</td>
      <td>{ razon.monto }</td>
      <td>{ razon.fechaInicio }</td>
      <td>{ razon.fechaFin }</td>
      <td>{ razon.tipo.tipo }</td>
      <td>{ razon.estado.estado }</td>
      <input type="checkbox" value={ razon.id } checked={razonElegida} onChange={ (e) => setRazonElegida(!razonElegida) }/>
    </tr>
  );
}