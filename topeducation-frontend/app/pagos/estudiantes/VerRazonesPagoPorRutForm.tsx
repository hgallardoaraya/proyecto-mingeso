"use client";

import Link from "next/link";
import { useState } from "react"

export default function VerRazonesPagoPorRutForm(){
  const [rut, setRut] = useState('');
  
  return(
    <form className="px-3 flex flex-col items-start gap-5">
      <div className="flex flex-col">
        <label htmlFor="rut">Rut</label>
        <input type="text" placeholder="Ej: 12.345.678-9" id="rut" value={rut} onChange={(e) => setRut(e.target.value)}/>
      </div>       
      <Link href={ `/pagos/estudiantes/${rut}` }>
        <button className="px-3 py-2 text-white bg-blue-600 rounded-md">INGRESAR</button>  
      </Link>
    </form>
  )
}