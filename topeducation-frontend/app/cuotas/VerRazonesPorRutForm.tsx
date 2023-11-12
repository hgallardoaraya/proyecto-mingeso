"use client";

import { useState } from "react";
import { ResponseRazones } from "@/types/Responses";
import Link from "next/link";

export default function VerRazonesPorRutForm(){
  const [rut, setRut] = useState('');

  return(
    <form className="px-3 flex flex-col items-start gap-5">
      <div className="flex flex-col">
        <label htmlFor="rut">Rut</label>
        <input type="text" placeholder="Ej: 12.345.678-9" id="rut" value={rut} onChange={(e) => setRut(e.target.value)}/>
      </div>       
      <Link href={ `cuotas/estudiante/${rut}` }>
        <button className="px-3 py-2 text-white bg-blue-600 rounded-md">VER CUOTAS</button>  
      </Link>
    </form>
  )
}